package com.zmeev;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class TransferProcessor {
    private static List<String> messages = new ArrayList<>();

    static List<String> getPossibleTransfer(List<Employee> employees) {

        Map<Department, List<Employee>> groupedByDeptMap = Department.getGroupedByDeptMap(employees);

        for (Map.Entry<Department, List<Employee>> entry1 : groupedByDeptMap.entrySet()) {
            Department key1 = entry1.getKey();
            List<Employee> value1 = entry1.getValue();
            for (Map.Entry<Department, List<Employee>> entry2 : groupedByDeptMap.entrySet()) {
                Department key2 = entry2.getKey();
                if (key1.compareTo(key2) == 0) continue;
                List<Employee> value2 = entry2.getValue();
                getPossibleTransferBtwnDepartments(value1, value2);
            }
        }
        return messages;
    }

    private static void getPossibleTransferBtwnDepartments(List<Employee> employees1, List<Employee> employees2) {
        int r = employees1.size() - 1;

        while (r > 0) {
            printCombination(employees1, employees2, r);
            r--;
        }
    }

    private static void employeesCombinationUtil(List<Employee> employees1, List<Employee> tempEmployees,
                                                 List<Employee> employees2, int start, int end, int index, int r,
                                                 BigDecimal wage1, BigDecimal wage2) {
        BigDecimal newAvgWage1;
        BigDecimal newAvgWage2;
        StringBuilder sb;

        List<Employee> e1Copied = employees1.stream().map(Employee::clone).collect(Collectors.toList());
        List<Employee> e2Copied = employees2.stream().map(Employee::clone).collect(Collectors.toList());

        if (index == r) {
            sb = new StringBuilder();
            e1Copied.removeAll(tempEmployees);
            e2Copied.addAll(tempEmployees);
            if ((newAvgWage1 = Employee.getAverageWageAmongEmployees(e1Copied)).compareTo(wage1) > 0
                    && (newAvgWage2 = Employee.getAverageWageAmongEmployees(e2Copied)).compareTo(wage2) > 0) {
                if (tempEmployees.size() != 1) {
                    for (Employee e : tempEmployees) {
                        sb.append(e.getName()).append(", ");
                    }
                    messages.add(sb.toString().trim().substring(0, sb.length() - 2) + " could be transferred from department " +
                            e1Copied.get(0).getDepartment() + " to department " +
                            e2Copied.get(0).getDepartment());
                } else {
                    for (Employee e : tempEmployees) {
                        messages.add(e.getName() + " could be transferred from department " +
                                e.getDepartment() + " to department " +
                                e2Copied.get(0).getDepartment());
                    }
                }
                messages.add(String.format("Average wage in department %s will increase to " + newAvgWage2
                                + "\nAverage wage in department %s will increase to " + newAvgWage1,
                        e1Copied.get(0).getDepartment(),
                        e2Copied.get(0).getDepartment()));
            }
            return ;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            tempEmployees.set(index, employees1.get(i));
            employeesCombinationUtil(employees1, tempEmployees, employees2, i + 1, end, index + 1, r, wage1, wage2);
        }
    }

    private static void printCombination(List<Employee> employees1, List<Employee> employees2, int r) {
        BigDecimal wage1 = Employee.getAverageWageAmongEmployees(employees1);
        BigDecimal wage2 = Employee.getAverageWageAmongEmployees(employees2);

        int n = employees1.size();
        // A temporary list to store all combination one by one
        List<Employee> data = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            data.add(null);
        }
        employeesCombinationUtil(employees1, data, employees2, 0, n-1, 0, r, wage1, wage2);
    }
}
