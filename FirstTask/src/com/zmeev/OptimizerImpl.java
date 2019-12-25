package com.zmeev;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OptimizerImpl implements Optimizer{
    private List<Employee> employees = new ArrayList<>();

    public void readFile(String filePath) {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String strings;
            while ((strings = reader.readLine()) != null) {
                employees.add(parser(strings));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printAllEmployees() {
        for (Employee e : employees) {
            System.out.println(e.toString());
        }
    }

    public void printAverageWageByDept() {
        System.out.println("\nAverage wage by Department\n");
        showAverageWageByDept(employees);
    }

    public void printPossibleTransfers() {
        checkPossibleTransfer(employees);
    }

    public Employee parser(String string) {
        String name;
        String department;
        int wage;

        String[] s = string.split(" ");

        name = s[0] + " " + s[1];
        department = s[2];
        wage = Integer.parseInt(s[3]);

        return new Employee(name, department, wage);
    }

    public void showAverageWageByDept(List<Employee> employees) {
        getAverageWageByDept(employees)
                .forEach((String key, List<Employee> empList) -> System.out.println(key + ": " + getAverageWage(empList)));
    }

    public Map<String, List<Employee>> getAverageWageByDept(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    public double getAverageWage(List<Employee> employees) {
        int count = employees.size();
        int sum = 0;

        for (Employee e : employees) {
            sum += e.getWage();
        }
        return (double) sum / count;
    }

    public void checkPossibleTransfer(List<Employee> employees) {
        Map<String, List<Employee>> byDept = getAverageWageByDept(employees);
        for (Map.Entry<String, List<Employee>> map : byDept.entrySet()) {
            for (Map.Entry<String, List<Employee>> m : byDept.entrySet()) {
                if (!(m.getKey().equals(map.getKey()))) {
                    transferBtwDept(m.getValue(), map.getValue());
                }
            }
        }
    }

    public void transferBtwDept(List<Employee> e1, List<Employee> e2) {
        Collections.sort(e1);
        Collections.sort(e2);

        double avgWage1 = getAverageWage(e1);
        double avgWage2 = getAverageWage(e2);

        List<Employee> e1Copied = e1.stream().map(Employee::clone).collect(Collectors.toList());
        List<Employee> e2Copied = e2.stream().map(Employee::clone).collect(Collectors.toList());

        if (!(e1.equals(e2))) {
            if (!checkTransfer(e1, e2, avgWage1, avgWage2))
                if (!checkTransfer(e2Copied, e1Copied, avgWage2, avgWage1))
                    System.out.println("There are no possible variants to transfer");
        } else System.out.println("These arrays are equal");
    }

    public void writeMessage(List<Employee> transferredWorkers, List<Employee> e1, List<Employee> e2, int i) {
        for (Employee e : transferredWorkers) {
            System.out.println(e.getName() +
                    " could be transferred from department " +
                    e1.get(i).getDepartment() +
                    " to department " + e2.get(i).getDepartment());
        }
        System.out.println("Average wage in department " +
                e1.get(i).getDepartment() + " would increase up to " +
                getAverageWage(e1) +
                ", in department " + e2.get(i).getDepartment() +
                " to " + getAverageWage(e2));
    }

    public boolean checkTransfer(List<Employee> e1, List<Employee> e2, double avgWage1, double avgWage2) {
        List<Employee> removedWorkers = new ArrayList<>();

        for (int i = 0; i < e1.size(); i++) {
            e2.add(e1.get(i));
            removedWorkers.add(e1.get(i));
            e1.remove(e1.get(i));
            if (getAverageWage(e1) > avgWage1 && getAverageWage(e2) > avgWage2) {
                writeMessage(removedWorkers, e1, e2, i);
                return true;
            }
        }
        return false;
    }
}
