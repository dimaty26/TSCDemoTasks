package com.zmeev;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResultWriter {

    public void writeFile(String pathFile, List<String> strings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile))) {
            for (String s : strings) {
                writer.write(s +"\n");
            }
        } catch (IOException e) {
            ConsoleHelper.printMessage("File was not found. Check if data correct and try again");
        }
    }
}
