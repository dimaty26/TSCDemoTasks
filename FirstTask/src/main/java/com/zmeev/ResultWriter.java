package com.zmeev;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResultWriter {

    public void writeFile(String pathFile, List<String> strings) {
        if (pathFile == null) {
            ConsoleHelper.printMessage("Не был указан путь к выходному файлу.");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile))) {
                for (String s : strings) {
                    writer.write(s + "\n");
                }
            } catch (IOException e) {
                ConsoleHelper.printMessage("File was not found. Check if data correct and try again");
            }
        }
    }
}
