package com.zmeev;

public class Employee {
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
}
