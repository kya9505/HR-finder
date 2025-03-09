package cli.controller;

import cli.io.SalaryIO;
import service.SalaryService;
import service.SalaryServiceImpl;

public class SalaryController extends BaseController {
    private SalaryService salaryService = new SalaryServiceImpl();
    private SalaryIO salaryIO = new SalaryIO();

    // 관리자용 급여 메뉴: 전체 CRUD 기능 제공
    public void runAdmin() {
        boolean exit = false;
        while (!exit) {
            printMenu("Salary Main Menu (Admin)",
                    "Salary Calculation",
                    "Salary Modification",
                    "Salary Information",
                    "Back to Main Menu");
            int choice = readChoice("Select an option: ");
            switch (choice) {
                case 1:
                    salaryCalculationMenuAdmin();
                    break;
                case 2:
                    salaryModificationMenuAdmin();
                    break;
                case 3:
                    salaryInformationMenuAdmin();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }

    // 일반 사용자용 급여 메뉴: 단순히 자기 급여 및 퇴직금 정보만 조회
    public void runUser() {
        boolean exit = false;
        while (!exit) {
            printMenu("Salary Main Menu (User)",
                    "Check My Salary",
                    "Check My Retirement Pay",
                    "Back to Main Menu");
            int choice = readChoice("Select an option: ");
            switch (choice) {
                case 1:
                    userSalaryCalculationMenu();
                    break;
                case 2:
                    userRetirementPayMenu();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }

    // 관리자용: 급여 계산 메뉴
    private void salaryCalculationMenuAdmin() {
        boolean back = false;
        while (!back) {
            printMenu("Salary Calculation Menu",
                    "Calculate Annual Salary",
                    "Calculate Salary After Tax",
                    "Simulate Annual Salary Raise",
                    "Calculate Bonus",
                    "Calculate Overtime Pay",
                    "Calculate Retirement Pay",
                    "Back");
            int choice = readChoice("Select an option: ");
            switch (choice) {
                case 1:
                    calculateAnnualSalary();
                    break;
                case 2:
                    calculateSalaryAfterTax();
                    break;
                case 3:
                    simulateAnnualRaise();
                    break;
                case 4:
                    calculateBonus();
                    break;
                case 5:
                    calculateOvertimePay();
                    break;
                case 6:
                    calculateRetirementPay();
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
            System.out.println();
        }
    }

    // 관리자용: 급여 수정 메뉴
    private void salaryModificationMenuAdmin() {
        boolean back = false;
        while (!back) {
            printMenu("Salary Modification Menu",
                    "Apply Salary Increase",
                    "Apply Batch Salary Increase",
                    "Update Employee Salary",
                    "Back");
            int choice = readChoice("Select an option: ");
            switch (choice) {
                case 1:
                    applySalaryIncrease();
                    break;
                case 2:
                    applyBatchSalaryIncrease();
                    break;
                case 3:
                    updateEmployeeSalary();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
            System.out.println();
        }
    }

    // 관리자용: 급여 정보 조회 메뉴
    private void salaryInformationMenuAdmin() {
        boolean back = false;
        while (!back) {
            printMenu("Salary Information Menu",
                    "Check Employee Salary Details",
                    "Calculate Average Salary by Job Title",
                    "Calculate Average Salary by Department",
                    "Calculate Increased Salary by Performance Grade",
                    "Check if Budget is Exceeded",
                    "Back");
            int choice = readChoice("Select an option: ");
            switch (choice) {
                case 1:
                    checkEmployeeSalaryDetails();
                    break;
                case 2:
                    calculateAvgSalaryByJobId();
                    break;
                case 3:
                    calculateAvgSalaryByDeptId();
                    break;
                case 4:
                    calculateIncreasedSalary();
                    break;
                case 5:
                    checkBudgetExceeded();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
            System.out.println();
        }
    }

    // 일반 사용자용: 자신의 급여(세전/세후) 조회
    private void userSalaryCalculationMenu() {
        int empId = salaryIO.readEmployeeId();
        double annualSalary = salaryService.getAnnualSalary(empId);
        double afterTax = salaryService.getSalaryAfterTax(empId);
        System.out.println("Your Annual Salary (Before Tax): " + annualSalary);
        System.out.println("Your Annual Salary (After Tax): " + afterTax);
    }

    // 일반 사용자용: 자신의 퇴직금 조회
    private void userRetirementPayMenu() {
        int empId = salaryIO.readEmployeeId();
        double retirementPay = salaryService.calculateRetirementPay(empId);
        System.out.println("Your Retirement Pay: " + retirementPay);
    }

    // 관리자 및 공통 기능

    private void calculateAnnualSalary() {
        int empId = salaryIO.readEmployeeId();
        double annualSalary = salaryService.getAnnualSalary(empId);
        System.out.println("Employee " + empId + "'s annual salary: " + annualSalary);
    }

    private void calculateSalaryAfterTax() {
        int empId = salaryIO.readEmployeeId();
        double afterTax = salaryService.getSalaryAfterTax(empId);
        System.out.println("Employee " + empId + "'s salary after tax: " + afterTax);
    }

    private void simulateAnnualRaise() {
        int empId = salaryIO.readEmployeeId();
        double percentage = salaryIO.readPercentage();
        double simulatedSalary = salaryService.simulateAnnSalaryRaise(empId, percentage);
        System.out.println("Simulated annual salary after " + percentage + "% raise for employee " + empId + ": " + simulatedSalary);
    }

    private void calculateBonus() {
        int empId = salaryIO.readEmployeeId();
        double performanceFactor = salaryIO.readPercentage();
        double bonus = salaryService.calculateBonus(empId, performanceFactor);
        System.out.println("Bonus for employee " + empId + ": " + bonus);
    }

    private void calculateOvertimePay() {
        int empId = salaryIO.readEmployeeId();
        double overtimeHours = salaryIO.readOvertimeHours();
        double overtimePay = salaryService.calculateOvertimePay(empId, overtimeHours);
        System.out.println("Overtime pay for employee " + empId + ": " + overtimePay);
    }

    private void calculateRetirementPay() {
        int empId = salaryIO.readEmployeeId();
        double retirementPay = salaryService.calculateRetirementPay(empId);
        System.out.println("Retirement pay for employee " + empId + ": " + retirementPay);
    }

    private void calculateAvgSalaryByJobId() {
        int jobIdInt = salaryIO.readJobId();
        String jobId = String.valueOf(jobIdInt);
        double avgSalary = salaryService.getAvgAnnSalJobId(jobId);
        System.out.println("Average annual salary for job " + jobId + ": " + avgSalary);
    }

    private void calculateAvgSalaryByDeptId() {
        int deptId = salaryIO.readDepartmentId();
        double avgSalary = salaryService.getAverageSalaryDpt(deptId);
        System.out.println("Average annual salary for department " + deptId + ": " + avgSalary);
    }

    private void calculateIncreasedSalary() {
        int empId = salaryIO.readEmployeeId();
        char grade = salaryIO.readPerformanceGrade();
        double increasedSalary = salaryService.getIncreasedAnnualSalary(empId, grade);
        System.out.println("Employee " + empId + "'s increased annual salary (grade " + grade + "): " + increasedSalary);
    }

    private void checkEmployeeSalaryDetails() {
        int empId = salaryIO.readEmployeeId();
        double annualSalary = salaryService.getAnnualSalary(empId);
        double monthlySalary = annualSalary / 12;
        double afterTax = salaryService.getSalaryAfterTax(empId);
        System.out.println("Employee " + empId + " Salary Details:");
        System.out.println("Monthly (Before Tax): " + monthlySalary);
        System.out.println("Annual (Before Tax): " + annualSalary);
        System.out.println("Annual (After Tax): " + afterTax);
    }

    private void checkBudgetExceeded() {
        double budget = salaryIO.readBudget();
        boolean exceeded = salaryService.isBudgetExceeded(budget);
        if (exceeded) {
            System.out.println("Total annual salary exceeds the budget.");
        } else {
            System.out.println("Total annual salary is within the budget.");
        }
    }

    // 관리자 전용 수정 기능
    private void applySalaryIncrease() {
        int empId = salaryIO.readEmployeeId();
        double rate = salaryIO.readPercentage();
        boolean result = salaryService.applySalaryIncrease(empId, rate);
        System.out.println(result ? "Salary increase applied for employee " + empId
                : "Failed to apply salary increase for employee " + empId);
    }

    private void applyBatchSalaryIncrease() {
        double percentage = salaryIO.readPercentage();
        int updatedCount = salaryService.applyBatchSalaryIncrease(percentage);
        System.out.println(updatedCount + " employees had their salary increased.");
    }

    private void updateEmployeeSalary() {
        int empId = salaryIO.readEmployeeId();
        double newSalary = salaryIO.readSalary();
        boolean result = salaryService.updateEmployeeSalary(empId, newSalary);
        System.out.println(result ? "Employee " + empId + "'s salary updated to " + newSalary
                : "Failed to update salary for employee " + empId);
    }
}