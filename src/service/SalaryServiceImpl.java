package service;

import dao.EmployeeDaoImpl;
import dto.Employees;
import util.salarycalculator.SalaryCalculator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalaryServiceImpl implements SalaryService {
    private final EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

    private Optional<Employees> findEmployee(int employeeId) {
        Optional<List<Employees>> optList = employeeDao.findEmployee("employee_id", employeeId);
        if (optList.isPresent() && !optList.get().isEmpty()) {
            return Optional.of(optList.get().get(0));
        }
        return Optional.empty();
    }

    private List<Employees> getAllEmployees() {
        return employeeDao.loadEmployees().orElse(new ArrayList<>());
    }

    private double getSalaryBeforeTax(int employeeId) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        return optEmp.map(emp -> emp.getSalary().doubleValue()).orElse(0.0);
    }

    @Override
    public double getSalaryAfterTax(int employeeId) {
        double annualSalary = getAnnualSalary(employeeId);
        double tax = SalaryCalculator.calculateSalTax(annualSalary);
        return annualSalary - tax;
    }

    @Override
    public double getAnnualSalary(int employeeId) {
        double monthlySalary = getSalaryBeforeTax(employeeId);
        return monthlySalary * 12;
    }

    @Override
    public double getAvgAnnSalJobId(String jobId) {
        List<Employees> allEmployees = getAllEmployees();
        List<Employees> matchedEmployees = new ArrayList<>();
        for (Employees emp : allEmployees) {
            if (jobId.equals(emp.getJob_id())) {
                matchedEmployees.add(emp);
            }
        }
        double totalSal = matchedEmployees.stream()
                .mapToDouble(emp -> emp.getSalary().doubleValue() * 12)
                .sum();
        int count = matchedEmployees.size();
        return count > 0 ? totalSal / count : 0.0;
    }

    @Override
    public double getAverageSalaryDpt(int departmentId) {
        List<Employees> allEmployees = getAllEmployees();
        List<Employees> matchedEmployees = new ArrayList<>();
        for (Employees emp : allEmployees) {
            if (departmentId == emp.getDepartment_id()) {
                matchedEmployees.add(emp);
            }
        }
        double totalSal = matchedEmployees.stream()
                .mapToDouble(emp -> emp.getSalary().doubleValue() * 12)
                .sum();
        int count = matchedEmployees.size();
        return count > 0 ? totalSal / count : 0.0;
    }

    @Override
    public double simulateAnnSalaryRaise(int employeeId, double percentage) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            double currentAnnual = optEmp.get().getSalary().doubleValue() * 12;
            return currentAnnual * (1 + percentage / 100.0);
        } else {
            System.out.println("Employee not found.");
            return 0.0;
        }
    }

    @Override
    public double getIncreasedAnnualSalary(int employeeId, char performanceGrade) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            double annualSalary = optEmp.get().getSalary().doubleValue() * 12;
            double raisePercentage;
            switch (performanceGrade) {
                case 'S':
                    raisePercentage = 0.15;
                    break;
                case 'A':
                    raisePercentage = 0.10;
                    break;
                case 'B':
                    raisePercentage = 0.05;
                    break;
                case 'C':
                    raisePercentage = 0.03;
                    break;
                case 'D':
                    raisePercentage = 0.01;
                    break;
                default:
                    System.out.println("Invalid performanceGrade. Please check the employee's performanceGrade again.");
                    return annualSalary;
            }
            return annualSalary * (1 + raisePercentage);
        } else {
            System.out.println("Employee not found.");
            return 0.0;
        }
    }

    @Override
    public boolean isBudgetExceeded(double budget) {
        double totalAnnualSalary = 0;
        for (Employees emp : getAllEmployees()) {
            totalAnnualSalary += emp.getSalary().doubleValue() * 12;
        }
        return totalAnnualSalary > budget;
    }

    @Override
    public double calculateRetirementPay(int employeeId) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            double monthlySalary = optEmp.get().getSalary().doubleValue();
            return monthlySalary * 3 * 0.8;
        } else {
            System.out.println("Employee not found.");
            return -1.0;
        }
    }

    @Override
    public boolean applySalaryIncrease(int employeeId, double rate) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            Employees emp = optEmp.get();
            double newSalary = emp.getSalary().doubleValue() * (1 + rate / 100.0);
            emp.setSalary(BigDecimal.valueOf(newSalary));
            Optional<Employees> updated = employeeDao.updateEmployee(emp);
            return updated.isPresent();
        } else {
            System.out.println("Employee not found.");
            return false;
        }
    }

    @Override
    public double calculateBonus(int employeeId, double performanceFactor) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            double monthlySalary = optEmp.get().getSalary().doubleValue();
            return monthlySalary * performanceFactor / 100.0;
        } else {
            System.out.println("Employee not found.");
            return 0.0;
        }
    }

    @Override
    public double calculateOvertimePay(int employeeId, double overtimeHours) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            double monthlySalary = optEmp.get().getSalary().doubleValue();
            double hourlyWage = monthlySalary / 160.0;
            return hourlyWage * overtimeHours * 1.5;
        } else {
            System.out.println("Employee not found.");
            return 0.0;
        }
    }

    @Override
    public int applyBatchSalaryIncrease(double percentage) {
        List<Employees> allEmployees = getAllEmployees();
        int updatedCount = 0;
        for (Employees emp : allEmployees) {
            double newSalary = emp.getSalary().doubleValue() * (1 + percentage / 100.0);
            emp.setSalary(BigDecimal.valueOf(newSalary));
            Optional<Employees> updated = employeeDao.updateEmployee(emp);
            if (updated.isPresent()) {
                updatedCount++;
            }
        }
        return updatedCount;
    }

    @Override
    public boolean updateEmployeeSalary(int employeeId, double newSalary) {
        Optional<Employees> optEmp = findEmployee(employeeId);
        if (optEmp.isPresent()) {
            Employees emp = optEmp.get();
            emp.setSalary(BigDecimal.valueOf(newSalary));
            Optional<Employees> updated = employeeDao.updateEmployee(emp);
            return updated.isPresent();
        } else {
            System.out.println("Employee not found.");
            return false;
        }
    }
}