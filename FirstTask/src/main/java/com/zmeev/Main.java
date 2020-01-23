package com.zmeev;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String pathToFileWithEmployees = null;
        String pathToResultFile = null;

        List<Employee> listOfEmployees;

        EmployeesReader employeesReader = new EmployeesReader();

        if (args.length == 2) {
            if (Files.isRegularFile(Path.of(args[1])) && Files.isRegularFile(Path.of(args[0]))) {
                if (new File(args[0]).length() == 0) {
                    pathToResultFile = args[0];
                    pathToFileWithEmployees = args[1];
                } else {
                    pathToResultFile = args[1];
                    pathToFileWithEmployees = args[0];
                }
            } else {
                ConsoleHelper.printMessage("Arguments should be paths.");
            }
        } else {
            ConsoleHelper.printMessage("Incorrect number of arguments. Should be 2.");
        }

        listOfEmployees = employeesReader.readFile(pathToFileWithEmployees);

        ConsoleHelper.printAllEmployees(listOfEmployees);
        ConsoleHelper.printAverageWageByDept(listOfEmployees);

        ConsoleHelper.printMessage("\n");
        ConsoleHelper.printPossibleTransfers(listOfEmployees);


        ResultWriter writer = new ResultWriter();
        List<String> possibleTransfers = TransferProcessor.getPossibleTransfer(listOfEmployees);
        writer.writeFile(pathToResultFile, possibleTransfers);
    }
}