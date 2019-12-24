package com.zmeev;

import java.util.Objects;

public class Employee implements Comparable<Employee>, Cloneable {
    private String name;
    private String department;
    private int wage;

    public Employee(String name, String department, int wage) {
        this.name = name;
        this.department = department;
        this.wage = wage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    @Override
    public String toString() {
        return "name: " + name +
                ", department: " + department +
                ", wage: " + wage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return wage == employee.wage &&
                Objects.equals(name, employee.name) &&
                Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, department, wage);
    }

    @Override
    public int compareTo(Employee o) {
        return this.wage - o.wage;
    }

    @Override
    protected Employee clone() {
        try {
            return (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
