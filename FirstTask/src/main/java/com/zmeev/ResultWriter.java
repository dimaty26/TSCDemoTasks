package com.zmeev;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResultWriter {

    public void writeFile(String pathFile, List<Employee> employees) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile))) {
            for (String s : TransferProcessor.getPossibleTransfer(employees)) {
                writer.write(s +"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
