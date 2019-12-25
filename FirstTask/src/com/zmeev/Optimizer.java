package com.zmeev;

import java.util.List;
import java.util.Map;

/**
Optimizer читает из файла информацию о сотрудниках и их принадлежности
к отделам, рассчитывает среднюю зарплату сотрудников в отделе, строит и выводит в файл
все варианты возможных переводов сотрудников из одного отдела в другой, при которых
средняя зарплата отдела увеличивается в обоих отделах.
* */

public interface Optimizer {

    /**
     * Метод считывает информацию из файла и добавляет сотрудников в список
     *@param filePath полный путь к файлу, где содержится инфомарция о сотрудниках
     * **/
    void readFile(String filePath);

    /**
     * Выводит информацию о всех сотрудниках компании
     * **/
    void printAllEmployees();

    /**
     * Выводит информацию о средней зарплате по отделам
     * **/
    void printAverageWageByDept();

    /**
     * Выводит информацию о возможных перемещениях
     * **/
    void printPossibleTransfers();

    void writeFile(String filePath);

    Employee parser(String string);

    void showAverageWageByDept(List<Employee> employees);

    Map<String, List<Employee>> getAverageWageByDept(List<Employee> employees);

    double getAverageWage(List<Employee> employees);

    void checkPossibleTransfer(List<Employee> employees);

    void transferBtwDept(List<Employee> e1, List<Employee> e2);

    List<String> writeMessage(List<Employee> transferredWorkers, List<Employee> e1, List<Employee> e2, int i);

    boolean checkTransfer(List<Employee> e1, List<Employee> e2, double avgWage1, double avgWage2);
}
