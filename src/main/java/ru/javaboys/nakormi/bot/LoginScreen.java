package ru.javaboys.nakormi.bot;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.javaboys.nakormi.entity.InvitationCode;
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

                    return null;
                });

                botFeaturesUtils.sendMessage(update, """
                        Отлично! Вам была создана учетна запись в системе "Накорми CRM".
                        Дла входа в систему перейдите по ссылке:
                        https://nakormi.kuramshin-dev.ru/
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
