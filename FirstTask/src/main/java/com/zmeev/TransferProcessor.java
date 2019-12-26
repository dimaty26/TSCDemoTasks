package com.zmeev;

import java.util.*;
import java.util.stream.Collectors;

public class TransferProcessor {
    private static List<String> message = new ArrayList<>();

    public static List<String> getPossibleTransfer(List<Employee> employees) {

        Map<Department, List<Employee>> byDept = Department.getGroupedByDeptMap(employees);
        //сортируем список сотрудников по департаментам
        TreeMap<Department, List<Employee>> sortedMap = new TreeMap<>();
        sortedMap.putAll(byDept);
        //создаем развернутый список сотрудников
        TreeMap<Department, List<Employee>> reverseSortedMap = new TreeMap<>(Collections.reverseOrder());
        reverseSortedMap.putAll(sortedMap);
        //убираем последние элементы в обоих списках
        sortedMap.pollLastEntry();
        reverseSortedMap.pollLastEntry();

        for (Map.Entry<Department, List<Employee>> map : sortedMap.entrySet()) {
            for (Map.Entry<Department, List<Employee>> m : reverseSortedMap.entrySet()) {
                if (!(m.getKey().equals(map.getKey()))) {
                    message.add("\nPossible transfers between " +
                            m.getKey() + " and " + map.getKey() + " departments:\n");
                    //выполняем трансфер между двумя отделами
                    transferBtwDept(m.getValue(), map.getValue());
                }
            }
        }

        return message;
    }

    private static List<String> transferBtwDept(List<Employee> e1, List<Employee> e2) {

        //сортируем списке в порядке возрастания зарплаты
        Collections.sort(e1);
        Collections.sort(e2);

        double avgWage1 = Calculator.getAverageWageAmongEmployees(e1);
        double avgWage2 = Calculator.getAverageWageAmongEmployees(e2);

        List<Employee> e1Copied = e1.stream().map(Employee::clone).collect(Collectors.toList());
        List<Employee> e2Copied = e2.stream().map(Employee::clone).collect(Collectors.toList());

        if (!(e1.equals(e2))) {
            //переставляем сотрудников из первого отдела во второй и проверяем результат
            if (!checkTransfer(e1, e2, avgWage1, avgWage2))
                //переставляем сотрудников из второго отдела в первый и смотрим результат
                if (!checkTransfer(e2Copied, e1Copied, avgWage2, avgWage1))
                    //выводим сообщение, если перестановки не дали положительный результат
                    message.add("There are no possible variants to transfer");
        } else message.add("These arrays are equal");

        return message;
    }

    private static boolean checkTransfer(List<Employee> e1, List<Employee> e2, double avgWage1, double avgWage2) {
        List<Employee> removedWorkers = new ArrayList<>();

        List<Employee> e1Copied = e1.stream().map(Employee::clone).collect(Collectors.toList());
        List<Employee> e2Copied = e2.stream().map(Employee::clone).collect(Collectors.toList());

        for (int i = 0; i < e1Copied.size(); i++) {
            //копируем сотрудника из одного списка в другой
            e2Copied.add(e1Copied.get(i));
            //заносим этого сотрудника в лист удаленных сотрудников
            removedWorkers.add(e1Copied.get(i));
            //удаляем этого сотрудника из оригинального листа
            e1Copied.remove(e1Copied.get(i));
            //проверяем, изменилась ли средняя зарплата в отделе
            if (Calculator.getAverageWageAmongEmployees(e1Copied) > avgWage1
                    && Calculator.getAverageWageAmongEmployees(e2Copied) > avgWage2) {
                //записываем результат
                printMessageAboutPossibleTransfer(removedWorkers, e1Copied, e2Copied, i);
                return true;
            }
        }
        return false;
    }

    private static void printMessageAboutPossibleTransfer(List<Employee> transferredWorkers, List<Employee> e1, List<Employee> e2, int i) {

        for (Employee e : transferredWorkers) {
            message.add(e.getName() +
                    " could be transferred from department " +
                    e1.get(i).getDepartment() +
                    " to department " + e2.get(i).getDepartment());
        }
        message.add("Average wage in department " +
                e1.get(i).getDepartment() + " would increase up to " +
                Calculator.getAverageWageAmongEmployees(e1) +
                ", in department " + e2.get(i).getDepartment() +
                " to " + Calculator.getAverageWageAmongEmployees(e2));
    }
}
