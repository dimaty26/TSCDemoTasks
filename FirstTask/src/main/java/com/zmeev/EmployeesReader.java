package com.zmeev;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class EmployeesReader {

    public List<Employee> readFile(String filePath) {
        List<Employee> stringList = new ArrayList<>();

        if (filePath == null){
            ConsoleHelper.printMessage("Не был указан путь к файлу с данными.");
        } else {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
                String strings;

                while ((strings = reader.readLine()) != null) {
                    Employee employee = FileParser.parseStringToEmployee(strings);
                    if (employee != null) {
                        stringList.add(employee);
                    }
                }
            } catch (FileNotFoundException e) {
                ConsoleHelper.printMessage("Файл не найден. Проверьте корректность введенных данных.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringList;
    }

}
