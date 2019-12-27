package com.zmeev;

import java.math.BigDecimal;

public class FileParser {
    private static String name;
    private static Department department;
    private static BigDecimal wage;

    public static Employee parse(String string) {

        String[] s = string.split("[^-\\w\\s\\_]");

        name = s[0];
        department = new Department(s[1].replaceAll(" ", ""));
        wage = intParser(s[2]);

        return new Employee(name, department, wage);
    }

    private static BigDecimal intParser(String string) {
        BigDecimal bigDecimal = new BigDecimal(string.replaceAll(" ", ""));

        if (bigDecimal.compareTo(new BigDecimal("0")) <= 0) {
            throw new NumberFormatException(String
                    .format("У работника %s из отдела %s недопустимое значение зарплаты (<= 0)",
                            name,
                            department));
        } else if (bigDecimal.compareTo(new BigDecimal("1000000000")) >= 0) {
            throw new NumberFormatException(String
                    .format("У работника %s из отдела %s слишком большая зарплата",
                            name,
                            department));
        }

        return bigDecimal;
    }
}
