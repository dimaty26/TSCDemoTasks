package com.zmeev;

import java.math.BigDecimal;

public class FileParser {

    public static Employee parseStringToEmployee(String string) {
        String name = "";
        Department department = null;
        BigDecimal wage = null;

        if (string != null) {
            String[] s = string.split("[^-\\w\\s\\.\\,\\[\\u0400-\\u04FF]");
            if (s.length == 3) {
                if (isLettersOrDash(s[0])) {
                  name = s[0];
                } else {
                    name = "INCORRECT NAME";
                }
                try {
                    department = new Department(s[1].trim());
                } catch (IllegalAccessException e) {
                    ConsoleHelper.printMessage(e.getMessage() + String.format(" in a row: %s\n", string));
                }

                try {
                    wage = new BigDecimal(s[2].replaceAll(",", ".").trim());
                    if (wage.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new NumberFormatException(String
                                .format("У работника %s из отдела %s недопустимое значение зарплаты (<= 0).\n",
                                        name,
                                        department));
                    } else if (wage.compareTo(new BigDecimal("1000000000")) >= 0) {
                        throw new NumberFormatException(String
                                .format("У работника %s из отдела %s слишком большая зарплата.\n",
                                        name,
                                        department));
                    }
                } catch (NumberFormatException e) {
                    ConsoleHelper.printMessage(e.getMessage());
                    wage = null;
                }
            } else {
                ConsoleHelper.printMessage(String.format("Incorrect number of parameters in a row: %s.\n", string)
                        + "Should be 3:\n"
                        + "1. Name of Employee.\n"
                        + "2. Name of Department.\n"
                        + "3. Wage of Employee.\n");
            }
        }

        if (name.isEmpty() || department == null || wage == null) {
            return null;
        } else {
            return new Employee(name, department, wage);
        }
    }

    private static boolean isLettersOrDash(String stringToCheck) {
        char[] chars = stringToCheck.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c) && c != ' ' && c != '-') {
                return false;
            }
        }
        return true;
    }
}
