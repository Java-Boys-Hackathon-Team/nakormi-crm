package ru.javaboys.nakormi.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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

    public void sendInlineKeyboard(Update update, String text, Map<String, String> buttons) throws TelegramApiException {
        var keyboardMarkupBuilder = InlineKeyboardMarkup.builder();

        for (Map.Entry<String, String> buttonEntry : buttons.entrySet()) {
            InlineKeyboardButton row = InlineKeyboardButton.builder()
                    .text(buttonEntry.getValue())
                    .callbackData(buttonEntry.getKey())
                    .build();
            keyboardMarkupBuilder.keyboardRow(new InlineKeyboardRow(row));  // Добавление каждой кнопки в новый ряд
        }

        InlineKeyboardMarkup keyboardMarkup = keyboardMarkupBuilder.build();

        SendMessage sm = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build();

        telegramContext.getTelegramClient().execute(sm);
    }
}
