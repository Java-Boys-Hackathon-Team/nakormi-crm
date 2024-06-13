package ru.javaboys.nakormi.utils;

import java.io.File;

public class FileUtils {

    public static void createFolder(String folderName) {
        File folder = new File("./" + folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }   
    }
}
