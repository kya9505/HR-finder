package cli.io;

import java.math.BigDecimal;

public class SalaryIO extends BaseIO {

    public int readEmployeeId() {
        return validator.readValidatedInt("employee_id(int) : ", 0, 9999, "Please Check Employee ID.");
    }

    public double readBudget() {
        return validator.readValidated("Enter company budget: ", Double::parseDouble, d -> d >= 0, "Budget must be non-negative.");
    }

    public double readPercentage() {
        return validator.readValidated("Enter percentage for salary raise: ", Double::parseDouble, d -> d >= 0, "Percentage must be non-negative.");
    }

    public int readJobId() {
        return validator.readValidatedInt("job_id(int) : ", 0, 10, "Please Check Job ID.");
    }

    public int readDepartmentId() {
        return validator.readValidatedInt("department_id(int) : ", 0, 10, "Please Check Department ID.");
    }

    public char readPerformanceGrade() {
        return validator.readValidated("Enter performance grade (S, A, B, C, D): ", input -> {
            String upper = input.toUpperCase();
            if (upper.matches("[SABCD]") && upper.length() == 1) {
                return upper.charAt(0);
            } else {
                throw new IllegalArgumentException();
            }
        }, grade -> true, "Please enter a valid grade (S, A, B, C, D).");
    }

    public double readSalary() {
        return validator.readValidatedBigDecimal("salary : ", new BigDecimal("0.00"), new BigDecimal("9999999.99"), 2, "Please Check Salary.").doubleValue();
    }

    public double readTaxAmount() {
        return validator.readValidated("Enter tax amount: ", Double::parseDouble, d -> d >= 0, "Tax amount must be non-negative.");
    }

    public double readOvertimeHours() {
        return validator.readValidated("Enter overtime hours: ", Double::parseDouble, h -> h >= 0, "Overtime hours must be non-negative.");
    }
}