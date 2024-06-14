package ru.javaboys.nakormi.bot;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface BotScreen {
    void processUpdate(Update update) throws TelegramApiException;
    void processCallback(Update update, String callbackData) throws TelegramApiException;
}
