package com.zmeev;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        OptimizerImpl optimizer = new OptimizerImpl();

        optimizer.readFile("d:/TempFiles/data");
        optimizer.printAllEmployees();
        optimizer.printAverageWageByDept();
        optimizer.printPossibleTransfers();
        optimizer.writeFile("d:/TempFiles/data.txt");

//        List<Employee> e1 = new ArrayList<>();
//        List<Employee> e2 = new ArrayList<>();
//
//        e1.add(new Employee("Rick", "SAP", 4));
//        e1.add(new Employee("Bob", "SAP", 3));
//        e1.add(new Employee("Will", "SAP", 5));
//        e1.add(new Employee("Dimitry", "SAP", 2));
//
//        e2.add(new Employee("Bill", "SP", 6));
//        e2.add(new Employee("John", "SP", 2));
//        e2.add(new Employee("Steve", "SP", 7));
//        e2.add(new Employee("Stan", "SP", 2));
//
//        OptimizerImpl o = new OptimizerImpl();
//        o.transferBtwDept(e1, e2);
    }
}
