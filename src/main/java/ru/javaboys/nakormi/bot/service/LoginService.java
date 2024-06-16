package ru.javaboys.nakormi.bot.service;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.javaboys.nakormi.bot.routing.Callbacks;
import ru.javaboys.nakormi.bot.utils.BotFeaturesUtils;
import ru.javaboys.nakormi.bot.utils.BotUtils;
import ru.javaboys.nakormi.bot.utils.CommandArgs;
import ru.javaboys.nakormi.bot.utils.CommonKeyboards;
import ru.javaboys.nakormi.bot.utils.TelegramContext;
import ru.javaboys.nakormi.entity.Address;
import ru.javaboys.nakormi.entity.District;
import ru.javaboys.nakormi.entity.InvitationCode;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.PersonGenderType;
import ru.javaboys.nakormi.entity.PersonTypes;
import ru.javaboys.nakormi.entity.TelegamUser;
import ru.javaboys.nakormi.entity.Volunteer;
import ru.javaboys.nakormi.entity.Warehouse;
import ru.javaboys.nakormi.entity.WarehouseTypes;
import ru.javaboys.nakormi.service.AttachmentService;
import ru.javaboys.nakormi.service.TelegramService;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginService {

    private final BotFeaturesUtils botFeaturesUtils;

    private final SystemAuthenticator systemAuthenticator;

    private final DataManager dataManager;

    private final TelegramService telegramService;

    private final TelegramContext telegramContext;

    private final AttachmentService attachmentService;

    private final CommonKeyboards commonKeyboards;

    public void processCommandStart(Update update) throws TelegramApiException {
        Map<String, String> buttons = Map.of(
                Callbacks.LOGIN_HAVE_CODE,"🔢 У меня есть код!",
                Callbacks.LOGIN_ENTER, "🔑 Войти"
        );

        botFeaturesUtils.sendInlineKeyboard(
                update,
                "Добро пожаловать! Зарегистрируйтесь или войдите в систему.",
                buttons
        );
    }

    public void processCommandCode(Update update) throws TelegramApiException {
        CommandArgs commandArgs = BotUtils.parseCommand(update.getMessage().getText());

        var userCode = commandArgs.getArguments();
        if (!BotUtils.validateArgsCount(userCode, 1)) {
            botFeaturesUtils.sendMessage(update, "Неверное кол-во аргументов команды /code");
            return;
        }

        var optionalCode = systemAuthenticator.withSystem(() -> dataManager.load(InvitationCode.class)
                .query("e.code = ?1", userCode)
                .optional());

        if (optionalCode.isPresent()) {
            var code = optionalCode.get();

            if (code.getUsed()) {
                botFeaturesUtils.sendMessage(update, String.format("Пригласительный код %s был ранее использован", code.getCode()));
                return;
            }

            if (code.getExpirationDate().isBefore(LocalDate.now())) {
                botFeaturesUtils.sendMessage(update, String.format("Пригласительный код %s был просрочен", code.getCode()));
                return;
            }

            systemAuthenticator.begin();

            TelegamUser telegamUser = telegramContext.getTelegamUser();
            telegamUser.setInvitationCode(code);
            telegamUser.setInvitationCodeOk(true);

            telegramContext.setTelegamUser(dataManager.save(telegamUser));

            systemAuthenticator.end();

            botFeaturesUtils.sendMessage(update, """
                            Ваш код действителен. Мы создадим Вам учетную запись волонтера!
                            
                            Придумайте логин и пароль для своей учетной записи.
                            
                            Пример: /reg ivan qwerty
                            """);
        } else {
            botFeaturesUtils.sendMessage(update, String.format("Пригласительный код %s не найден", commandArgs.getArguments()));
        }
    }

    public void processCommandReg(Update update) throws TelegramApiException {
        CommandArgs commandArgs = BotUtils.parseCommand(update.getMessage().getText());

        var auth = commandArgs.getArguments().split(" ");
        if (!BotUtils.validateArgsCount(auth, 2)) {
            botFeaturesUtils.sendMessage(update, "Неверное кол-во аргументов команды /reg");
            return;
        }

        systemAuthenticator.withSystem(() -> {
            telegramService.registerUser(auth[0], auth[1]);

            TelegamUser telegamUser = telegramContext.getTelegamUser();
            InvitationCode invitationCode = telegamUser.getInvitationCode();
            invitationCode.setUsed(true);

            telegamUser.setInvitationCode(dataManager.save(invitationCode));
            telegamUser.setNakormiCrmRegistrationOk(true);
            telegramContext.setTelegamUser(dataManager.save(telegamUser));

            return null;
        });

        botFeaturesUtils.sendMessage(update, """
                        Отлично! Вам была создана учетна запись в системе "Накорми CRM".
                        Для входа в систему перейдите по ссылке:
                        
                        https://nakormi-crm.ru/
                        """);

        botFeaturesUtils.sendMessage(update, """
                        Для завершения регистрации укажите Имя, Фамилию, Район проживания, Улицу проживания, email, номер телефона именно в таком порядке.
                        
                        Пример: /person Иван Петровский Северный Вятская ivan@mail.ru +79001234567
                        """);

    }

    public void processCommandPerson(Update update) throws TelegramApiException {
        CommandArgs commandArgs = BotUtils.parseCommand(update.getMessage().getText());

        var personData = commandArgs.getArguments().split(" ");
        if (!BotUtils.validateArgsCount(personData, 6)) {
            botFeaturesUtils.sendMessage(update, "Неверное кол-во аргументов команды /person");
            return;
        }

        systemAuthenticator.begin();

        var district = dataManager.create(District.class);
        district.setName(personData[2]);
        district = dataManager.save(district);

        var address = dataManager.create(Address.class);
        address.setDistrict(district);
        address.setAddressText(personData[3]);
        address.setCoordinate(BotUtils.getDefaultGeoPoint());
        address = dataManager.save(address);

        var person = dataManager.create(Person.class);
        person.setName(personData[0]);
        person.setSurname(personData[1]);
        person.setPhone(personData[5]);
        person.setAddress(address);
        person.setType(PersonTypes.VOLUNTEER);
        person.setGender(PersonGenderType.MALE);
        person = dataManager.save(person);

        var warehouse = dataManager.create(Warehouse.class);
        warehouse.setDescription(String.format("Склад %s %s", person.getName(), person.getSurname()));
        warehouse.setAddress(address);
        warehouse.setStorageType(WarehouseTypes.PERSONAL);
        warehouse.setContacts(person.getPhone());
        warehouse.setSupervisor(person);
        warehouse = dataManager.save(warehouse);

        var volunteer = dataManager.create(Volunteer.class);
        volunteer.setPerson(person);
        TelegamUser tgUser = telegramContext.getTelegamUser();
        volunteer.setWarehouse(warehouse);
        volunteer = dataManager.save(volunteer);
        tgUser.setVolunteer(volunteer);
        dataManager.save(tgUser);

        var user = telegramContext.getTelegamUser().getUser();
        user.setPerson(person);
        user.setFirstName(person.getName());
        user.setLastName(person.getSurname());
        user.setEmail(personData[4]);
        dataManager.save(user);

        systemAuthenticator.end();

        botFeaturesUtils.sendMessage(update, """
                        Для завершения регистрации нам потребуются скан-копия вашего паспорта.
                        Отправьте её как файл.
                        
                        При отправке добавьте файлу подпись "паспорт".
                        """);

    }

    public void processCommandLogin(Update update) throws TelegramApiException {
        CommandArgs commandArgs = BotUtils.parseCommand(update.getMessage().getText());

        var auth = commandArgs.getArguments().split(" ");
        if (!BotUtils.validateArgsCount(auth, 2)) {
            botFeaturesUtils.sendMessage(update, "Неверное кол-во аргументов команды /login");
            return;
        }

        var result = telegramService.authenticate(auth[0], auth[1]);

        if (result) {
            commonKeyboards.sendHelloAndAccountKeyboard(update);
        } else {
            botFeaturesUtils.sendMessage(update, "Неверный логин или пароль");
        }
    }

    public void processDocument(Update update) throws TelegramApiException, FileNotFoundException {
        String caption = update.getMessage().getCaption();

        switch (caption) {

            case "паспорт" -> {

                Document document = update.getMessage().getDocument();
                var file = botFeaturesUtils.downloadFile(document.getFileId());
                var attachment = attachmentService.save(file, document.getFileId(), document.getFileName());

                systemAuthenticator.begin();

                var volunteerId = telegramContext.getTelegamUser().getVolunteer().getId();

                dataManager.load(Volunteer.class)
                        .id(volunteerId)
                        .optional()
                        .ifPresent(v -> {
                            v.setAttachments(List.of(attachment));
                            dataManager.save(v);
                        });

                var tgUser = telegramContext.getTelegamUser();
                tgUser.setNakormiCrmAccountOk(true);
                dataManager.save(tgUser);

                systemAuthenticator.end();

                botFeaturesUtils.sendMessage(update, """
                        Регистрация завершена!
                        
                        Вы стали волонтером проекта "Накорми".
                        """);

                commonKeyboards.sendHelloAndAccountKeyboard(update);
            }

            default -> {
                botFeaturesUtils.sendMessage(update, "Файл не распознан. Повторите попытку");
            }
        }
    }

    public void processPhoto(Update update) throws TelegramApiException, FileNotFoundException {
        List<PhotoSize> photos = update.getMessage().getPhoto();

        String fileId = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                .map(PhotoSize::getFileId)
                .orElse("");

        var file = botFeaturesUtils.downloadFile(fileId);

        var attachment = attachmentService.save(file, fileId, BotUtils.generatePhotoName());
    }

}
