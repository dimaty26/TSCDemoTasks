package com.zmeev;

public class FileParser {

    public static Employee parse(String string) {
        String name;
        String department;
        int wage;

        String[] s = string.split(" ");

        name = s[0] + " " + s[1];
        department = s[2];
        wage = Integer.parseInt(s[3]);

        return new Employee(name, department, wage);
    }
}
