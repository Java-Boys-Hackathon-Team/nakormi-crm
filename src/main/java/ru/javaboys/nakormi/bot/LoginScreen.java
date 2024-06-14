package ru.javaboys.nakormi.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginScreen implements BotScreen {

    private final TelegramContext telegramContext;

    private final BotFeaturesUtils botFeaturesUtils;

    @Override
    public void processUpdate(Update update) throws TelegramApiException {
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
        botFeaturesUtils.updateInlineKeyboard(update, "Введите ваш пригласительный код", buttons);
    }

    private void processEnter(Update update) throws TelegramApiException {
        Map<String, String> buttons = Map.of(Callbacks.BACK_FROM_LOGIN_PASSWORD_INPUT,"Назад");
        botFeaturesUtils.updateInlineKeyboard(update, "Введите ваши логин и пароль", buttons);
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
