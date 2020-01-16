package com.zmeev;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

public class Employee implements Comparable<Employee>, Cloneable {
    private String name;
    private Department department;
    private BigDecimal wage;

    public Employee(String name, Department department, BigDecimal wage) {
        this.name = name;
        this.department = department;
        this.wage = wage;
    }

    public static BigDecimal getAverageWageAmongEmployees(List<Employee> employees) {
        BigDecimal count = new BigDecimal(employees.size());
        BigDecimal sum = BigDecimal.ZERO;

        for (Employee e : employees) {
            if (e.getWage() == null) continue;
            sum = sum.add(e.getWage());
        }
        return sum.divide(count, 2, RoundingMode.HALF_UP);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public BigDecimal getWage() {
        return wage;
    }

    public void setWage(BigDecimal wage) {
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
        return this.wage.compareTo(o.wage);
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
