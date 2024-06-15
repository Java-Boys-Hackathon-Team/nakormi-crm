package ru.javaboys.nakormi.bot.routing;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.javaboys.nakormi.bot.screen.LoginScreen;
import ru.javaboys.nakormi.bot.screen.VolunteerAccountScreen;
import ru.javaboys.nakormi.bot.utils.BotFeaturesUtils;
import ru.javaboys.nakormi.bot.utils.BotUtils;
import ru.javaboys.nakormi.bot.utils.CommandArgs;
import ru.javaboys.nakormi.bot.utils.TelegramContext;
import ru.javaboys.nakormi.service.TelegramService;

@Slf4j
@Component
public class NakormiProjectBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final String token;

    private final LoginScreen loginScreen;

    private final TelegramService telegramService;

    private final BotFeaturesUtils botFeaturesUtils;

    private final VolunteerAccountScreen volunteerAccountScreen;

    public NakormiProjectBot(TelegramContext telegramContext,
                             LoginScreen loginScreen,
                             TelegramService telegramService,
                             BotFeaturesUtils botFeaturesUtils,
                             VolunteerAccountScreen volunteerAccountScreen) {

        this.token = telegramContext.getToken();
        this.loginScreen = loginScreen;
        this.telegramService = telegramService;
        this.botFeaturesUtils = botFeaturesUtils;
        this.volunteerAccountScreen = volunteerAccountScreen;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @SneakyThrows
    @Override
    @Transactional
    public void consume(Update update) {

        telegramService.upsertTelegramUser(update);

        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageTest = update.getMessage().getText();
            CommandArgs commandArgs = BotUtils.parseCommand(messageTest);

            switch (commandArgs.getCommand()) {

                case Commands.START, Commands.CODE, Commands.LOGIN, Commands.REG, Commands.PERSON -> loginScreen.processUpdate(update);

                default -> botFeaturesUtils.sendMessage(update, "Команда не распознана. Неизвестная команда");
            }

        } else if (update.hasCallbackQuery()) {

            String callbackData = update.getCallbackQuery().getData();

            switch (callbackData) {

                case Callbacks.LOGIN_HAVE_CODE, Callbacks.LOGIN_ENTER, Callbacks.BACK_FROM_INVITATION_CODE_INPUT,
                     Callbacks.BACK_FROM_LOGIN_PASSWORD_INPUT -> loginScreen.processCallback(update, callbackData);

                case Callbacks.GO_TO_VOLUNTEER_ACCOUNT, Callbacks.REFRESH_VOLUNTEER_ACCOUNT -> volunteerAccountScreen.processCallback(update, callbackData);

                default -> botFeaturesUtils.sendMessage(update, "Команда не распознана. Неизвестная команда");
            }

        } else if (update.hasMessage() && update.getMessage().hasDocument()) {

            loginScreen.processDocument(update);

        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {

            loginScreen.processPhoto(update);

        }
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        log.info("Registered bot running state is: {}", botSession.isRunning());
    }
}
