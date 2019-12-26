package com.zmeev;

public class FileParser {

    public static Employee parse(String string) {
        String name;
        Department department;
        int wage;

        String[] s = string.split(" ");

        name = s[0] + " " + s[1];
        department = new Department(s[2]);
        wage = Integer.parseInt(s[3]);

        return new Employee(name, department, wage);
    }
}
