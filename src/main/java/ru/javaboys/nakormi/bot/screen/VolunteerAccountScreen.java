package ru.javaboys.nakormi.bot.screen;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.javaboys.nakormi.bot.routing.Callbacks;
import ru.javaboys.nakormi.bot.service.VolunteerAccountService;

import java.io.FileNotFoundException;

@Component
@RequiredArgsConstructor
public class VolunteerAccountScreen implements BotScreen  {

    private final VolunteerAccountService volunteerAccountService;

    @Override
    public void processUpdate(Update update) throws TelegramApiException {

    }

    @Override
    public void processCallback(Update update, String callbackData) throws TelegramApiException {

        switch (callbackData) {

            case Callbacks.GO_TO_VOLUNTEER_ACCOUNT -> volunteerAccountService.processVolunteerAccountCallback(update);
            case Callbacks.REFRESH_VOLUNTEER_ACCOUNT -> volunteerAccountService.processRefreshVolunteerAccountCallback(update);
            case Callbacks.GO_TO_VOLUNTEER_REMAINDERS -> volunteerAccountService.processVolunteerRemaindersCallback(update);
            case Callbacks.REFRESH_VOLUNTEER_REMAINDERS -> volunteerAccountService.processRefreshVolunteerRemaindersCallback(update);

        }

    }

    @Override
    public void processDocument(Update update) throws TelegramApiException, FileNotFoundException {

    }

    @Override
    public void processPhoto(Update update) throws TelegramApiException, FileNotFoundException {

    }
}
