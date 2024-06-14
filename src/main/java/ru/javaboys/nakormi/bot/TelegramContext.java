package ru.javaboys.nakormi.bot;

import lombok.Builder;
import lombok.Data;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.javaboys.nakormi.entity.TelegamUser;

@Data
@Builder
public class TelegramContext {
    private String token;
    private TelegramClient telegramClient;
    private TelegamUser telegamUser;
}
