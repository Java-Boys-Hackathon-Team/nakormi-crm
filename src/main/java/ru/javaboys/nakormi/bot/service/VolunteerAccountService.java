package ru.javaboys.nakormi.bot.service;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.javaboys.nakormi.bot.routing.Callbacks;
import ru.javaboys.nakormi.bot.utils.BotFeaturesUtils;
import ru.javaboys.nakormi.bot.utils.BotUtils;
import ru.javaboys.nakormi.bot.utils.CommonKeyboards;
import ru.javaboys.nakormi.bot.utils.TelegramContext;
import ru.javaboys.nakormi.entity.TransferTypes;
import ru.javaboys.nakormi.service.AttachmentService;
import ru.javaboys.nakormi.service.StatisticService;
import ru.javaboys.nakormi.service.TelegramService;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VolunteerAccountService {

    private final BotFeaturesUtils botFeaturesUtils;

    private final SystemAuthenticator systemAuthenticator;

    private final DataManager dataManager;

    private final TelegramService telegramService;

    private final TelegramContext telegramContext;

    private final AttachmentService attachmentService;

    private final CommonKeyboards commonKeyboards;

    private final StatisticService statisticService;

    public void processVolunteerAccountCallback(Update update) throws TelegramApiException {
        processVolunteerAccount(update);
    }

    public void processRefreshVolunteerAccountCallback(Update update) throws TelegramApiException {
        processVolunteerAccount(update);
    }

    public void processVolunteerRemaindersCallback(Update update) throws TelegramApiException {
        processVolunteerRemainders(update);
    }

    public void processRefreshVolunteerRemaindersCallback(Update update) throws TelegramApiException {
        processVolunteerRemainders(update);
    }

    private void processVolunteerRemainders(Update update) throws TelegramApiException {

        Map<String, String> buttons = Map.of(
                Callbacks.REFRESH_VOLUNTEER_REMAINDERS, "🔁 Обновить статистику",
                Callbacks.GO_TO_VOLUNTEER_ACCOUNT, "↩️ Назад"
        );


        var remainders = statisticService.findRemaindersForVolunteer(telegramContext.getTelegamUser().getVolunteer());

        String text = """
                Остатки на вашем складе:
                
                %s
                """.formatted(BotUtils.formatRemainders(remainders));

        botFeaturesUtils.updateInlineKeyboard(update, text, buttons);
    }

    private void processVolunteerAccount(Update update) throws TelegramApiException {
        String text = getTextForVolunteerAccount();
        Map<String, String> buttons = getButtonsForVolunteerAccount();

        botFeaturesUtils.updateInlineKeyboard(update, text, buttons);
    }

    private String getTextForVolunteerAccount() {

        var volunteer =  telegramContext.getTelegamUser().getVolunteer();

        var person = volunteer.getPerson();

        UUID volunteerId = volunteer.getId();

        systemAuthenticator.begin();

        String jpqlQueryFeed = "SELECT COUNT(ft) FROM FoodTransfer ft " +
                "WHERE ft.volunteer.id = :volunteerId " +
                "AND ft.transferType = :transferTypeFeed";

        long countFeed = dataManager.loadValue(jpqlQueryFeed, Long.class)
                .parameter("volunteerId", volunteerId)
                .parameter("transferTypeFeed", TransferTypes.FEED.getId())
                .one();

        String jpqlQueryBeneficiary = "SELECT COUNT(ft) FROM FoodTransfer ft " +
                "WHERE ft.volunteer.id = :volunteerId " +
                "AND ft.transferType = :transferTypeBeneficiary";

        long countBeneficiary = dataManager.loadValue(jpqlQueryBeneficiary, Long.class)
                .parameter("volunteerId", volunteerId)
                .parameter("transferTypeBeneficiary", TransferTypes.TRANSFER_TO_BENEFICIARY.getId())
                .one();

        systemAuthenticator.end();

        String text ="""
                        Личный кабинет волонтера %s %s 🤝
                        
                        📊Активность
                        
                        Вы накормили бездомных животных: %s раз! 💪👍😻
                        
                        Вы передали корм благополучателям: %s раз! 🎉🎊😇
                                                
                        """.formatted(person.getName(), person.getSurname(), countFeed, countBeneficiary);

        return text;
    }

    private Map<String, String> getButtonsForVolunteerAccount() {

        Map<String, String> buttons = Map.of(
                Callbacks.GO_TO_VOLUNTEER_REMAINDERS, "🏫 Остатки на моё складе",
                "2", "📣 Мои заказы",
                "3", "🆘 Животное в опасности!",
                Callbacks.REFRESH_VOLUNTEER_ACCOUNT, "🔁 Обновить статистику"
        );

        return buttons;
    }
}
