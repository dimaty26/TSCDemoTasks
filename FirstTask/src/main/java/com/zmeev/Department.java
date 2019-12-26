package com.zmeev;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Department {
    private String name;

    public Department(String name) {
        this.name = name;
    }

    static Map<String, List<Employee>> getGroupedByDeptMap(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
