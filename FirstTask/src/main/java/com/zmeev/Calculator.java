package com.zmeev;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Calculator {

    public static BigDecimal getAverageWageAmongEmployees(List<Employee> employees) {
        BigDecimal count = new BigDecimal(employees.size());
        BigDecimal sum = new BigDecimal("0");

        for (Employee e : employees) {
            sum = sum.add(e.getWage());
        }
        return sum.divide(count, 2, RoundingMode.HALF_UP);
    }
}
