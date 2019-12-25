package com.zmeev;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class OptimizerImpl implements Optimizer{
    private List<Employee> employees = new ArrayList<>();
    private List<String> message = new ArrayList<>();

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
        for (String s : message) {
            System.out.println(s);
        }
    }

    public void writeFile(String pathFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile))) {
            for (String s : message) {
                writer.write(s +"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        TreeMap<String, List<Employee>> sortedMap = new TreeMap<>();
        sortedMap.putAll(byDept);
        TreeMap<String, List<Employee>> reverseSortedMap = new TreeMap<>(Collections.reverseOrder());
        reverseSortedMap.putAll(sortedMap);
        sortedMap.pollLastEntry();
        reverseSortedMap.pollLastEntry();

        for (Map.Entry<String, List<Employee>> map : sortedMap.entrySet()) {
            for (Map.Entry<String, List<Employee>> m : reverseSortedMap.entrySet()) {
                if (!(m.getKey().equals(map.getKey()))) {
                    message.add("\nPossible transfers between " +
                            m.getKey() + " and " + map.getKey() + " departments:\n");
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

        List<List<Employee>> lists = cloneTwoLists(e1, e2);

        if (!(e1.equals(e2))) {
            if (!checkTransfer(e1, e2, avgWage1, avgWage2))
                if (!checkTransfer(lists.get(1), lists.get(0), avgWage2, avgWage1))
                    message.add("There are no possible variants to transfer");
        } else message.add("These arrays are equal");
    }

    public List<String> writeMessage(List<Employee> transferredWorkers, List<Employee> e1, List<Employee> e2, int i) {

        for (Employee e : transferredWorkers) {
            message.add(e.getName() +
                    " could be transferred from department " +
                    e1.get(i).getDepartment() +
                    " to department " + e2.get(i).getDepartment());
        }
        message.add("Average wage in department " +
                e1.get(i).getDepartment() + " would increase up to " +
                getAverageWage(e1) +
                ", in department " + e2.get(i).getDepartment() +
                " to " + getAverageWage(e2));

        return message;
    }

    public boolean checkTransfer(List<Employee> e1, List<Employee> e2, double avgWage1, double avgWage2) {
        List<Employee> removedWorkers = new ArrayList<>();

        List<List<Employee>> lists = cloneTwoLists(e1, e2);

        for (int i = 0; i < lists.get(0).size(); i++) {
            lists.get(1).add(lists.get(0).get(i));
            removedWorkers.add(lists.get(0).get(i));
            lists.get(0).remove(lists.get(0).get(i));
            if (getAverageWage(lists.get(0)) > avgWage1 && getAverageWage(lists.get(1)) > avgWage2) {
                writeMessage(removedWorkers, lists.get(0), lists.get(1), i);
                return true;
            }
        }
        return false;
    }

    public List<List<Employee>> cloneTwoLists(List<Employee> e1, List<Employee> e2) {
        List<List<Employee>> lists = new ArrayList<>();

        List<Employee> e1Copied = e1.stream().map(Employee::clone).collect(Collectors.toList());
        List<Employee> e2Copied = e2.stream().map(Employee::clone).collect(Collectors.toList());

        lists.add(e1Copied);
        lists.add(e2Copied);

        return lists;
    }
}
