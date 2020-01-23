package com.zmeev;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Department implements Comparable<Department>{
    private String name;

    public Department(String name) throws IllegalAccessException {
        if (!isDigitsOnly(name)) {
            this.name = name;
        } else {
            throw new IllegalAccessException("Incorrect name of Department");
        }
    }

    static Map<Department, List<Employee>> getGroupedByDeptMap(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    private static boolean isDigitsOnly(String stringToCheck) {
        return stringToCheck.chars().allMatch(Character::isDigit);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Department o) {
        return this.name.compareTo(o.name);
    }
}
