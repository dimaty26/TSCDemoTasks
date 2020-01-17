package com.zmeev;

import java.io.*;
import java.util.*;

public class EmployeesReader {

    public List<String> readFile(String filePath) {
        List<String> stringList = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String strings;
            while ((strings = reader.readLine()) != null) {
                stringList.add(strings);
            }
        } catch (FileNotFoundException e) {
            ConsoleHelper.printMessage("Файл не найден. Проверьте корректность введенных данных.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }
}
