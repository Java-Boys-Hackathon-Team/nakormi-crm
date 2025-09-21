package ru.javaboys.nakormi.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import ru.javaboys.nakormi.bot.utils.TelegramContext;

@Configuration
@ConditionalOnProperty(name = "bot.token", havingValue = "", matchIfMissing = false)
public class Config {

    @Value("${bot.token}")
    private String token;

    @Bean
    public TelegramContext getTelegramContext() {
        return TelegramContext.builder()
                .token(token)
                .telegramClient(new OkHttpTelegramClient(token))
                .build();
    }
}
