package com.zmeev;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

    public static List<Employee> parseEmployees(List<String> listOfEmployees) {
        List<Employee> employees = new ArrayList<>();

        for (String s : listOfEmployees) {
            try {
                if (parse(s) != null) {
                    employees.add(parse(s));
                } else {
                    ConsoleHelper.printMessage(String.format("Incorrect data in a row: %s", s));
                }
            } catch (NumberFormatException ex) {
                ConsoleHelper.printMessage(ex.getMessage());
            }
        }
        return employees;
    }

    public static Employee parse(String string) {

        String[] s = string.split("[^-\\w\\s]");

        if (s.length > 3) {
            return null;
        } else {

            //Parsing the name
            String name = parseName(string);
            if (name.isEmpty()) {
                name = "INCORRECT NAME";
            }

            //Parsing the department
            Department department = parseDepartment(string);

            //Parsing the wage
            BigDecimal wage = parseWage(string);

            if (wage != null) {
                if (wage.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new NumberFormatException(String
                            .format("У работника %s из отдела %s недопустимое значение зарплаты (<= 0)",
                                    name,
                                    department));
                } else if (wage.compareTo(new BigDecimal("1000000000")) >= 0) {
                    throw new NumberFormatException(String
                            .format("У работника %s из отдела %s слишком большая зарплата",
                                    name,
                                    department));
                }
            }

            return new Employee(name, department, wage);
        }
    }

    private static String parseName(String string) {
        String name = "";
        String[] s = string.split("[^-\\w\\s]");

        for (String str : s) {
            if (!isDigit(str.trim()) && !isUpperCase(str.trim())) {
                if (str.trim().matches(".*\\d.*")) name = "";
                else name = str;
            }
        }
        return name;
    }

    private static Department parseDepartment(String string) {
        String[] s = string.split("[^-\\w\\s]");
        for (String str : s) {
            if (isUpperCase(str.trim())) return new Department(str.trim());
        }
        return null;
    }

    private static BigDecimal parseWage(String string) {
        String[] s = string.split("[^-\\w\\s]");
        for (String str : s) {
            if (isDigit(str.trim())) {
                return new BigDecimal(str.trim());
            }
        }
        return null;
    }

    private static boolean isDigit(String s) {
        try {
            new BigDecimal(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static boolean isUpperCase(String s)
    {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) continue;
            if (!Character.isUpperCase(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
