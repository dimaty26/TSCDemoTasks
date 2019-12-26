package com.zmeev;

import java.util.List;

public class Calculator {

    public static double getAverageWageAmongEmployees(List<Employee> employees) {
        int count = employees.size();
        int sum = 0;

        for (Employee e : employees) {
            sum += e.getWage();
        }
        return (double) sum / count;
    }
}
