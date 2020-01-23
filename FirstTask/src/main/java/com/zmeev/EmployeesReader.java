package com.zmeev;

import java.io.*;
import java.util.*;

public class EmployeesReader {

    public List<Employee> readFile(String filePath) {
        List<Employee> stringList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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
        return stringList;
    }
}
