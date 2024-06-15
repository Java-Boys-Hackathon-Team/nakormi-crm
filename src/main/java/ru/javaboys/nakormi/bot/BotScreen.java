package ru.javaboys.nakormi.bot;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;

public interface BotScreen {
    void processUpdate(Update update) throws TelegramApiException;
    void processCallback(Update update, String callbackData) throws TelegramApiException;
    void processDocument(Update update) throws TelegramApiException, FileNotFoundException;
    void processPhoto(Update update) throws TelegramApiException, FileNotFoundException;
}
