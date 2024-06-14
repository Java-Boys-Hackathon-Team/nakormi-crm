package ru.javaboys.nakormi.bot;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.javaboys.nakormi.entity.Address;
import ru.javaboys.nakormi.entity.District;
import ru.javaboys.nakormi.entity.InvitationCode;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.PersonTypes;
import ru.javaboys.nakormi.entity.TelegamUser;
import ru.javaboys.nakormi.entity.Volunteer;
import ru.javaboys.nakormi.entity.Warehouse;
import ru.javaboys.nakormi.entity.WarehouseTypes;
import ru.javaboys.nakormi.service.TelegramService;

import java.time.LocalDate;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginScreen implements BotScreen {

    private final BotFeaturesUtils botFeaturesUtils;

    private final SystemAuthenticator systemAuthenticator;

    private final DataManager dataManager;

    private final TelegramService telegramService;

    private final TelegramContext telegramContext;

    @Override
    public void processUpdate(Update update) throws TelegramApiException {
        CommandArgs commandArgs = BotUtils.parseCommand(update.getMessage().getText());

        switch (commandArgs.getCommand()) {
            case Commands.START -> {
                Map<String, String> buttons = Map.of(
                        Callbacks.LOGIN_HAVE_CODE,"У меня есть код!",
                        Callbacks.LOGIN_ENTER, "Войти"
                );

                botFeaturesUtils.sendInlineKeyboard(
                        update,
                        "Добро пожаловать! Зарегистрируйтесь или войдите в систему.",
                        buttons
                );
            }
            case Commands.CODE -> {
                var optionalCode = systemAuthenticator.withSystem(() -> dataManager.load(InvitationCode.class)
                        .query("e.code = ?1", commandArgs.getArguments())
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

            case Commands.REG -> {
                var auth = commandArgs.getArguments().split(" ");

                systemAuthenticator.withSystem(() -> {
                    telegramService.registerUser(
                            auth[0],
                            auth[1]
                    );

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
                        
                        https://nakormi.kuramshin-dev.ru/
                        """);

                botFeaturesUtils.sendMessage(update, """
                        Для завершения регистрации укажите Имя, Фамилию, Район проживания, Улицу проживания, email, номер телефона именно в таком порядке.
                        
                        Пример: /person Иван Петровский Северный Вятская ivan@mail.ru +79001234567
                        """);
            }

            case Commands.PERSON -> {
                var personData = commandArgs.getArguments().split(" ");

                systemAuthenticator.begin();

                var district = dataManager.create(District.class);
                district.setName(personData[2]);
                district = dataManager.save(district);

                var address = dataManager.create(Address.class);
                address.setDistrict(district);
                address.setAddressText(personData[3]);
                address = dataManager.save(address);

                var person = dataManager.create(Person.class);
                person.setName(personData[0]);
                person.setSurname(personData[1]);
                person.setPhone(personData[5]);
                person.setAddress(address);
                person.setType(PersonTypes.VOLUNTEER);
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
                volunteer.setTelegramId(telegramContext.getTelegamUser().getTelegramUserName());
                volunteer.setWarehouse(warehouse);
                dataManager.save(volunteer);

                var user = telegramContext.getTelegamUser().getUser();
                user.setPerson(person);
                user.setFirstName(person.getName());
                user.setLastName(person.getSurname());
                user.setEmail(personData[4]);
                user = dataManager.save(user);

                var telegramUser = telegramContext.getTelegamUser();
                telegramUser.setUser(user);
                telegramContext.setTelegamUser(dataManager.save(telegramUser));

                systemAuthenticator.end();

                botFeaturesUtils.sendMessage(update, """
                        Для завершения регистрации нам потребуются скан-копия вашего паспорта.
                        Отправьте её как файл.
                        """);
            }

            case Commands.LOGIN -> {

            }
        }
    }

    @Override
    public void processCallback(Update update, String callbackData) throws TelegramApiException {
        switch (callbackData) {
            case Callbacks.LOGIN_HAVE_CODE -> processCode(update);
            case Callbacks.LOGIN_ENTER -> processEnter(update);
            case Callbacks.BACK_FROM_INVITATION_CODE_INPUT, Callbacks.BACK_FROM_LOGIN_PASSWORD_INPUT -> processBack(update);
        }
    }

    private void processCode(Update update) throws TelegramApiException {
        Map<String, String> buttons = Map.of(Callbacks.BACK_FROM_INVITATION_CODE_INPUT,"Назад");
        botFeaturesUtils.updateInlineKeyboard(
                update, """
                        Введите ваш пригласительный код.
                        Используйте команду /code
                        Пример: /code AGTGB
                        """,
                buttons);
    }

    private void processEnter(Update update) throws TelegramApiException {
        Map<String, String> buttons = Map.of(Callbacks.BACK_FROM_LOGIN_PASSWORD_INPUT,"Назад");
        botFeaturesUtils.updateInlineKeyboard(
                update, """
                     Введите ваши логин и пароль.
                     Используйте команду /login
                     Пример: /login ivan qwerty123
                     """,
                buttons);
    }

    private void processBack(Update update) throws TelegramApiException {
        Map<String, String> buttons = Map.of(
                Callbacks.LOGIN_HAVE_CODE,"У меня есть код!",
                Callbacks.LOGIN_ENTER, "Войти"
        );

        botFeaturesUtils.updateInlineKeyboard(
                update,
                "Добро пожаловать! Зарегистрируйтесь или войдите в систему.",
                buttons
        );
    }
}
