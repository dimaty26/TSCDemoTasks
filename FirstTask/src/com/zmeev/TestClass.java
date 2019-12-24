package com.zmeev;

import java.util.ArrayList;
import java.util.List;

public class TestClass {

    public static void main(String[] args) {
        List<Employee> e1 = new ArrayList<>();
        List<Employee> e2 = new ArrayList<>();

        e1.add(new Employee("Rick", "SAP", 4));
        e1.add(new Employee("Bob", "SAP", 3));
        e1.add(new Employee("Will", "SAP", 5));
        e1.add(new Employee("Dimitry", "SAP", 2));

        e2.add(new Employee("Bill", "SP", 6));
        e2.add(new Employee("John", "SP", 2));
        e2.add(new Employee("Steve", "SP", 7));
        e2.add(new Employee("Stan", "SP", 2));

        Main.transferBtwDept(e1, e2);
    }
}
