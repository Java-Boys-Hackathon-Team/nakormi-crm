package ru.javaboys.nakormi.bot.utils;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
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

    public static Point getDefaultGeoPoint() {

        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(1.0, 2.0);

        return geometryFactory.createPoint(coordinate);
    }

    public static Long getChatIdSafe(Update update) {

        Long chatId = 0L;

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }

        if (chatId == 0L) {
            throw new RuntimeException("Telegram chatId cannot be empty");
        }

        return chatId;
    }

    public static Long getUserIdSafe(Update update) {

        Long userId = 0L;

        if (update.hasMessage()) {
            userId = update.getMessage().getFrom().getId();
        } else if (update.hasCallbackQuery()) {
            userId = update.getCallbackQuery().getMessage().getChat().getId();
        }

        if (userId == 0L) {
            throw new RuntimeException("Telegram userId cannot be empty");
        }

        return userId;
    }
}
