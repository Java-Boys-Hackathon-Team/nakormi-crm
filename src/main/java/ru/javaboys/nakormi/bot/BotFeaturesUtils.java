package ru.javaboys.nakormi.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class BotFeaturesUtils {

    private final TelegramContext telegramContext;

    public InlineKeyboardMarkup getInlineKeyboard(Map<String, String> buttons) {
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

    public void sendInlineKeyboard(Update update, String text, Map<String, String> buttons) throws TelegramApiException {

        var keyboardMarkup = getInlineKeyboard(buttons);

        SendMessage sm = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build();

        telegramContext.getTelegramClient().execute(sm);
    }

    public void updateInlineKeyboard(Update update, String text, Map<String, String> buttons) throws TelegramApiException {

        var keyboardMarkup = getInlineKeyboard(buttons);

        EditMessageText sm = EditMessageText.builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId())
                .messageId(Math.toIntExact(update.getCallbackQuery().getMessage().getMessageId()))
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build();

        telegramContext.getTelegramClient().execute(sm);
    }

    public void sendMessage(Update update, String text) throws TelegramApiException {
        SendMessage message = SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text(text)
                .build();

        telegramContext.getTelegramClient().execute(message);
    }
}
