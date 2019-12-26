package com.zmeev;

import java.util.List;

public class ConsoleWriter {

    public static void printAllEmployees(List<Employee> employees) {
        for (Employee e : employees) {
            System.out.println(e.toString());
        }
    }

    public static void printPossibleTransfers(List<Employee> employees) {
        List<String> messages = TransferProcessor.getPossibleTransfer(employees);

        for (String s : messages) {
            System.out.println(s);
        }
    }

    public static void printAverageWageByDept(List<Employee> employees) {
        System.out.println("\nAverage wage by Department\n");
        Department.getGroupedByDeptMap(employees)
                .forEach((Department key, List<Employee> empList) ->
                        System.out.println(key + ": " + Calculator.getAverageWageAmongEmployees(empList)));
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }


}
