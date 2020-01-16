package com.zmeev;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String pathToFileWithEmployees;
        String pathToResultFile;

        List<String> listOfEmployees;
        List<Employee> employees;

        EmployeesReader employeesReader = new EmployeesReader();

        while (true) {
            if (args.length == 2) {
                if (Files.isRegularFile(Path.of(args[1])) && Files.isRegularFile(Path.of(args[0]))) {
                    if (new File(args[0]).length() == 0) {
                        pathToResultFile = args[0];
                        pathToFileWithEmployees = args[1];
                    } else {
                        pathToResultFile = args[1];
                        pathToFileWithEmployees = args[0];
                        break;
                    }
                } else {
                    ConsoleHelper.printMessage("Arguments should be paths.");
                    System.exit(1);
                }
            } else {
                ConsoleHelper.printMessage("Incorrect number of arguments. Please try again");
                System.exit(1);
            }
        }

        listOfEmployees = employeesReader.readFile(pathToFileWithEmployees);

        employees = FileParser.parseEmployees(listOfEmployees);

        ConsoleHelper.printAllEmployees(employees);
        ConsoleHelper.printAverageWageByDept(employees);

        ConsoleHelper.printMessage("\n");
        ConsoleHelper.printPossibleTransfers(employees);

        ResultWriter writer = new ResultWriter();
        List<String> possibleTransfers = TransferProcessor.getPossibleTransfer(employees);
        writer.writeFile(pathToResultFile, possibleTransfers);
    }
}