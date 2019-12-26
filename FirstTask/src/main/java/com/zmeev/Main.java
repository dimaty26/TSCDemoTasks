package com.zmeev;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        EmployeesReader employeesReader = new EmployeesReader();
        List<String> listOfEmployees;
        List<Employee> employees = new ArrayList<>();

        listOfEmployees = employeesReader.readFile(args[0]);
        for (String s : listOfEmployees) {
            employees.add(FileParser.parse(s));
        }

        ConsoleWriter.printAverageWageByDept(employees);

        ResultWriter writer = new ResultWriter();
        writer.writeFile(args[1], employees);

    }
}
