package com.zmeev;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransferProcessor {
    private static List<String> message = new ArrayList<>();

    public static List<String> getPossibleTransfer(List<Employee> employees) {

        Map<Department, List<Employee>> groupedByDeptMap = Department.getGroupedByDeptMap(employees);

        for (Map.Entry<Department, List<Employee>> entry1 : groupedByDeptMap.entrySet()) {
            Department key1 = entry1.getKey();
            int hash1 = System.identityHashCode(key1);
            List<Employee> value1 = entry1.getValue();
            for (Map.Entry<Department, List<Employee>> entry2 : groupedByDeptMap.entrySet()) {
                Department key2 = entry2.getKey();
                int hash2 = System.identityHashCode(key2);
                if (hash1 >= hash2) continue;
                List<Employee> value2 = entry2.getValue();
                message.add(String
                        .format("Check possible transfer between %s and %s departments:",
                                value1.get(0).getDepartment(),
                                value2.get(0).getDepartment()));
                transfer(value1, value2);
            }
        }
        return message;
    }

    private static List<String> transfer(List<Employee> e1, List<Employee> e2) {
        BigDecimal avgWage1 = Employee.getAverageWageAmongEmployees(e1);
        BigDecimal avgWage2 = Employee.getAverageWageAmongEmployees(e2);

        if (avgWage1.compareTo(avgWage2) > 0) {
            transferBetweenDepartments(e1, e2);
        } else if (avgWage2.compareTo(avgWage1) > 0) {
            transferBetweenDepartments(e2, e1);
        } else {
            message.add("No possible transfers");
        }
        return message;
    }

    private static void transferBetweenDepartments(List<Employee> e1, List<Employee> e2) {
        List<Employee> e1Copied = e1.stream().map(Employee::clone).collect(Collectors.toList());
        List<Employee> e2Copied = e2.stream().map(Employee::clone).collect(Collectors.toList());

        BigDecimal avgWage1 = Employee.getAverageWageAmongEmployees(e1Copied);
        BigDecimal avgWage2 = Employee.getAverageWageAmongEmployees(e2Copied);

        for (Iterator<Employee> iterator = e1Copied.iterator(); iterator.hasNext();) {
            if (e1Copied.size() != 1) {
                Employee e = iterator.next();
                if (e.getWage().compareTo(avgWage2) > 0 && e.getWage().compareTo(avgWage1) < 0) {
                    e2Copied.add(e);
                    iterator.remove();
                    message.add(e.getName() + " could be transferred from department " +
                            e.getDepartment() + " to department " +
                            e2Copied.get(0).getDepartment()); //сотрудники уже отсортированы по департаменту
                    avgWage1 = Employee.getAverageWageAmongEmployees(e1Copied);
                    avgWage2 = Employee.getAverageWageAmongEmployees(e2Copied);
                    message.add(String.format("Average wage in department %s will increase to " + avgWage2
                                    + "\nAverage wage in department %s will increase to " + avgWage1,
                            e.getDepartment(),
                            e2Copied.get(0).getDepartment()));
                }
            } else break;
        }
    }
}
