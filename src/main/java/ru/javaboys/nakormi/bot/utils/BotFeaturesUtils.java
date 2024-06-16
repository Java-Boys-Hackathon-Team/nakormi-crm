package ru.javaboys.nakormi.bot.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BotFeaturesUtils {

    private final TelegramContext telegramContext;

    public void sendInlineKeyboard(Update update, String text, Map<String, String> buttons) throws TelegramApiException {

        var keyboardMarkup = getInlineKeyboard(buttons);

        SendMessage sm = SendMessage.builder()
                .chatId(BotUtils.getChatIdSafe(update))
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build();

        telegramContext.getTelegramClient().execute(sm);
    }

    public void updateInlineKeyboard(Update update, String text, Map<String, String> buttons) throws TelegramApiException {

        var keyboardMarkup = getInlineKeyboard(buttons);

        EditMessageText sm = EditMessageText.builder()
                .chatId(BotUtils.getChatIdSafe(update))
                .messageId(Math.toIntExact(BotUtils.getMessageIdSafe(update)))
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build();

        telegramContext.getTelegramClient().execute(sm);
    }

    public void sendReplyKeyboardMarkup(Update update, String text, ReplyKeyboardMarkup replyKeyboardMarkup) throws TelegramApiException {

        SendMessage sm = SendMessage.builder()
                .chatId(BotUtils.getChatIdSafe(update))
                .text(text)
                .replyMarkup(replyKeyboardMarkup)
                .build();

        telegramContext.getTelegramClient().execute(sm);
    }

    public void sendMessage(Update update, String text) throws TelegramApiException {

        SendMessage message = SendMessage
                .builder()
                .chatId(BotUtils.getChatIdSafe(update))
                .text(text)
                .build();

        telegramContext.getTelegramClient().execute(message);
    }

    public File downloadFile(String fileId) throws TelegramApiException {

        var getFile = GetFile.builder().fileId(fileId).build();

        var fileFromTgApi = telegramContext.getTelegramClient().execute(getFile);

        var f = telegramContext.getTelegramClient().downloadFile(fileFromTgApi.getFilePath());

        return f;
    }

    public void sendPhoto(Update update, String text, String fileId) throws TelegramApiException {
        SendPhoto msg = SendPhoto
                .builder()
                .chatId(BotUtils.getChatIdSafe(update))
                .photo(new InputFile(fileId))
                .caption(text)
                .build();

        telegramContext.getTelegramClient().execute(msg);
    }

    private InlineKeyboardMarkup getInlineKeyboard(Map<String, String> buttons) {
        var keyboardMarkupBuilder = InlineKeyboardMarkup.builder();

        for (Map.Entry<String, String> buttonEntry : buttons.entrySet()) {
            InlineKeyboardButton row = InlineKeyboardButton.builder()
                    .text(buttonEntry.getValue())
                    .callbackData(buttonEntry.getKey())
                    .build();
            keyboardMarkupBuilder.keyboardRow(new InlineKeyboardRow(row));  // Добавление каждой кнопки в новый ряд
        }

        return keyboardMarkupBuilder.build();
    }
}
