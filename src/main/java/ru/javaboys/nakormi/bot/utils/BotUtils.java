package ru.javaboys.nakormi.bot.utils;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.javaboys.nakormi.entity.Food;
import ru.javaboys.nakormi.entity.PuckUpOrder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    public static boolean startsWithOrder(String text) {
        return text.startsWith("Заказ");
    }

    public static Integer extractOrderNumber(String text) {
        int startIndex = text.indexOf("Заказ") + 5;
        int endIndex = text.indexOf("от");
        if (startIndex < endIndex && startIndex > 0 && endIndex > 0) {
            String strNumber = text.substring(startIndex, endIndex).trim();

            return Integer.valueOf(strNumber.substring(strNumber.lastIndexOf('0') + 1));
        }
        return null;
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

    public static Integer getMessageIdSafe(Update update) {

        Integer messageId = 0;

        if (update.hasMessage()) {
            messageId = update.getMessage().getMessageId();
        } else if (update.hasCallbackQuery()) {
            messageId = update.getCallbackQuery().getMessage().getMessageId();
        }

        if (messageId == 0L) {
            throw new RuntimeException("Telegram messageId cannot be empty");
        }

        return messageId;
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

    public static String formatRemainders(Map<Food, Long> map) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Food, Long> entry : map.entrySet()) {
            result.append(entry.getKey().getName()).append(" - ").append(entry.getValue()).append("\n");
        }
        return result.toString();
    }


    public static String formatPuckUpOrders(List<PuckUpOrder> orders) {
        StringBuilder result = new StringBuilder();

        result.append(String.format("%-36s %-20s %-10s %-36s %-36s %-36s %-15s\n",
                "ID", "Date", "Number", "Creator ID", "Volunteer ID", "Warehouse ID", "Status"));

        for (PuckUpOrder order : orders) {
            UUID creatorId = order.getCreator() != null ? order.getCreator().getId() : null;
            UUID volunteerId = order.getVolunteer() != null ? order.getVolunteer().getId() : null;
            UUID warehouseId = order.getWarehouse() != null ? order.getWarehouse().getId() : null;

            result.append(String.format("%-36s %-20s %-10d %-36s %-36s %-36s %-15s\n",
                    order.getId(),
                    order.getDate(),
                    order.getNumber(),
                    creatorId,
                    volunteerId,
                    warehouseId,
                    order.getStatus()
            ));
        }

        return result.toString();
    }
}
