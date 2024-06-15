package ru.javaboys.nakormi.bot.screen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.javaboys.nakormi.bot.routing.Callbacks;
import ru.javaboys.nakormi.bot.routing.Commands;
import ru.javaboys.nakormi.bot.service.LoginService;
import ru.javaboys.nakormi.bot.utils.BotFeaturesUtils;
import ru.javaboys.nakormi.bot.utils.BotUtils;
import ru.javaboys.nakormi.bot.utils.CommandArgs;
import ru.javaboys.nakormi.bot.utils.CommonKeyboards;
import ru.javaboys.nakormi.bot.utils.TelegramContext;
import ru.javaboys.nakormi.entity.Address;
import ru.javaboys.nakormi.entity.District;
import ru.javaboys.nakormi.entity.InvitationCode;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.PersonTypes;
import ru.javaboys.nakormi.entity.TelegamUser;
import ru.javaboys.nakormi.entity.Volunteer;
import ru.javaboys.nakormi.entity.Warehouse;
import ru.javaboys.nakormi.entity.WarehouseTypes;
import ru.javaboys.nakormi.service.AttachmentService;
import ru.javaboys.nakormi.service.TelegramService;
import ru.javaboys.nakormi.bot.utils.CommandArgs;

import java.io.FileNotFoundException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginScreen implements BotScreen {

    private final BotFeaturesUtils botFeaturesUtils;

    private final LoginService loginService;

    @Override
    @Transactional
    public void processUpdate(Update update) throws TelegramApiException {
        CommandArgs commandArgs = BotUtils.parseCommand(update.getMessage().getText());

        switch (commandArgs.getCommand()) {
            case Commands.START -> {
                loginService.processCommandStart(update);
            }

            case Commands.CODE -> {
                loginService.processCommandCode(update);
            }

            case Commands.REG -> {
                loginService.processCommandReg(update);

            }

            case Commands.PERSON -> {
                loginService.processCommandPerson(update);
            }

            case Commands.LOGIN -> {
                loginService.processCommandLogin(update);
            }
        }
    }

    @Override
    public void processCallback(Update update, String callbackData) throws TelegramApiException {
        switch (callbackData) {

            case Callbacks.LOGIN_HAVE_CODE -> processCodeCallback(update);

            case Callbacks.LOGIN_ENTER -> processEnterCallback(update);

            case Callbacks.BACK_FROM_INVITATION_CODE_INPUT, Callbacks.BACK_FROM_LOGIN_PASSWORD_INPUT -> processBackCallback(update);

        }
    }

    @Override
    public void processDocument(Update update) throws TelegramApiException, FileNotFoundException {
        loginService.processDocument(update);
    }

    @Override
    public void processPhoto(Update update) throws TelegramApiException, FileNotFoundException {
        loginService.processPhoto(update);
    }

    private void processCodeCallback(Update update) throws TelegramApiException {
        Map<String, String> buttons = Map.of(Callbacks.BACK_FROM_INVITATION_CODE_INPUT,"↩️ Назад");
        botFeaturesUtils.updateInlineKeyboard(
                update, """
                        Введите ваш пригласительный код.
                        Используйте команду /code
                        Пример: /code AGTGB
                        """,
                buttons);
    }

    private void processEnterCallback(Update update) throws TelegramApiException {
        Map<String, String> buttons = Map.of(Callbacks.BACK_FROM_LOGIN_PASSWORD_INPUT,"↩️ Назад");
        botFeaturesUtils.updateInlineKeyboard(
                update, """
                     Введите ваши логин и пароль.
                     Используйте команду /login
                     Пример: /login ivan qwerty123
                     """,
                buttons);
    }

    private void processBackCallback(Update update) throws TelegramApiException {
        Map<String, String> buttons = Map.of(
                Callbacks.LOGIN_HAVE_CODE,"🔢 У меня есть код!",
                Callbacks.LOGIN_ENTER, "🔑 Войти"
        );

        botFeaturesUtils.updateInlineKeyboard(
                update,
                "Добро пожаловать! Зарегистрируйтесь или войдите в систему.",
                buttons
        );
    }
}
