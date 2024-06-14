package ru.javaboys.nakormi.bot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Component
public class NakormiProjectBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final String token;

    private final TelegramClient telegramClient;

    private final LoginScreen loginScreen;

    public NakormiProjectBot(TelegramContext telegramContext, LoginScreen loginScreen) {
        this.token = telegramContext.getToken();
        this.telegramClient = telegramContext.getTelegramClient();
        this.loginScreen = loginScreen;
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
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageTest = update.getMessage().getText();

            switch (messageTest) {
                case Commands.START -> loginScreen.processUpdate(update);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();

            switch (callbackData) {
                case Callbacks.LOGIN_HAVE_CODE, Callbacks.LOGIN_ENTER -> loginScreen.processCallback(update, callbackData);
            }

        }
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        log.info("Registered bot running state is: {}", botSession.isRunning());
    }
}
