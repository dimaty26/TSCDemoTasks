package com.zmeev;

/*
Написать программу, которая читает из файла информацию о сотрудниках и их принадлежности
к отделам, рассчитывает среднюю зарплату сотрудников в отделе, строит и выводит в файл
все варианты возможных переводов сотрудников из одного отдела в другой, при которых
средняя зарплата отдела увеличивается в обоих отделах.
* */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader("d:/TempFiles/data"))) {
            String strings;
            while ((strings = reader.readLine()) != null) {
                employees.add(parser(strings));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Employee e : employees) {
            System.out.println(e.toString());
        }
        System.out.println("\nAverage wage by Department\n");
        showAverageWageByDept(employees);
    }

    private static Employee parser(String string) {
        String name;
        String department;
        int wage;

        String[] s = string.split(" ");

        name = s[0] + " " + s[1];
        department = s[2];
        wage = Integer.parseInt(s[3]);

        return new Employee(name, department, wage);
    }

    private static void showAverageWageByDept(List<Employee> employees) {
        Map<String, List<Employee>> byDept =
                employees.stream()
                        .collect(Collectors.groupingBy(Employee::getDepartment));

        byDept.forEach((String key, List<Employee> empList) -> System.out.println(key + ": " + getAverageWage(empList)));
    }

    private static double getAverageWage(List<Employee> employees) {
        int count = employees.size();
        int sum = 0;

        for (Employee e : employees) {
            sum += e.getWage();
        }
        return (double) sum / count;
    }
}
