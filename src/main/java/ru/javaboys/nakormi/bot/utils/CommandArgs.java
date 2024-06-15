package ru.javaboys.nakormi.bot.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandArgs {
    private String command;
    private String arguments;
}
