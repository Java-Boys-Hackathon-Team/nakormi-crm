package ru.javaboys.nakormi.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommonKeyboards {

    private final BotFeaturesUtils botFeaturesUtils;

    public void sendHelloAndAccountKeyboard(Update update) throws TelegramApiException {
        Map<String, String> buttons = Map.of(
                Callbacks.GO_TO_VOLUNTEER_ACCOUNT,"Перейти в Личный Кабинет Волонтера"
        );

        botFeaturesUtils.sendInlineKeyboard(
                update,
                "Добро пожаловать в систему \"Накорми CRM\"!",
                buttons
        );

    }
}
