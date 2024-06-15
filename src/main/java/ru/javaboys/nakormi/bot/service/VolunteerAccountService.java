package ru.javaboys.nakormi.bot.service;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.javaboys.nakormi.bot.utils.BotFeaturesUtils;
import ru.javaboys.nakormi.bot.utils.CommonKeyboards;
import ru.javaboys.nakormi.bot.utils.TelegramContext;
import ru.javaboys.nakormi.service.AttachmentService;
import ru.javaboys.nakormi.service.TelegramService;

import java.util.Map;

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

    public void processVolunteerAccountCallback(Update update) throws TelegramApiException {

        var person = telegramContext.getTelegamUser().getVolunteer().getPerson();

        Map<String, String> buttons = Map.of(
                "1", "🏫 Остатки на моё складе",
                "2", "📣 Мои заказы",
                "3", "🆘 Животное в опасности!"
        );

        botFeaturesUtils.updateInlineKeyboard(
                update, """
                          Личный кабинет волонтера %s %s 🤝
                          
                          📊Активность
                          
                          Вы накормили бездомных животных: 6 раз! 💪👍😻
                          
                          Вы передали корм благополучателям: 12 раз! 🎉🎊😇
                          
                        """.formatted(person.getName(), person.getSurname()),
                buttons
        );

    }
}
