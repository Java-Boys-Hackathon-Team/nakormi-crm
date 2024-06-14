package ru.javaboys.nakormi.bot;

import org.springframework.stereotype.Component;

@Component
public class BotUtils {
    public static CommandArgs parseCommand(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be null or blank");
        }

        // Разделение строки на части по первому пробелу
        String[] parts = input.split(" ", 2);
        String command = parts[0];  // Команда
        String arguments = parts.length > 1 ? parts[1] : "";  // Аргументы

        return new CommandArgs(command, arguments);
    }
}
