package ru.javaboys.nakormi.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BotUtils {

    public static CommandArgs parseCommand(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be null or blank");
        }

        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        return new CommandArgs(command, arguments);
    }

    public static boolean isCommand(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    public static boolean isDocumentOrPhoto(Update update) {
        return update.hasMessage();
    }

    public static boolean validateArgsCount(String args, int expectedCount) {
        return  args.split(" ").length == expectedCount;
    }

    public static boolean validateArgsCount(String[] args, int expectedCount) {
        return  args.length == expectedCount;
    }

    public static String generatePhotoName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "photo_" + now.format(formatter) + ".jpg";
    }
}
