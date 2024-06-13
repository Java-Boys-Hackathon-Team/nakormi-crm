package ru.javaboys.nakormi.bot;

import lombok.Builder;
import lombok.Data;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Data
@Builder
public class TelegramContext {
    private String token;
    private TelegramClient telegramClient;
}
