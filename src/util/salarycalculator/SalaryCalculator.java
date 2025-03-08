package util.salarycalculator;

public class SalaryCalculator {
    public static double calculateSalTax(double annualSalary) {
        double tax = 0.0;
        if (annualSalary <= 14000000) {
            tax = annualSalary * 0.06;
        } else if (annualSalary <= 50000000) {
            tax = 14000000 * 0.06 + (annualSalary - 14000000) * 0.15;
        } else if (annualSalary <= 88000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15 + (annualSalary - 50000000) * 0.24;
        } else if (annualSalary <= 150000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15
                    + (88000000 - 50000000) * 0.24 + (annualSalary - 88000000) * 0.35;
        } else if (annualSalary <= 300000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15
                    + (88000000 - 50000000) * 0.24 + (150000000 - 88000000) * 0.35
                    + (annualSalary - 150000000) * 0.38;
        } else if (annualSalary <= 500000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15
                    + (88000000 - 50000000) * 0.24 + (150000000 - 88000000) * 0.35
                    + (300000000 - 150000000) * 0.38 + (annualSalary - 300000000) * 0.4;
        } else if (annualSalary <= 1000000000) {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15
                    + (88000000 - 50000000) * 0.24 + (150000000 - 88000000) * 0.35
                    + (300000000 - 150000000) * 0.38 + (500000000 - 300000000) * 0.4
                    + (annualSalary - 500000000) * 0.42;
        } else {
            tax = 14000000 * 0.06 + (50000000 - 14000000) * 0.15
                    + (88000000 - 50000000) * 0.24 + (150000000 - 88000000) * 0.35
                    + (300000000 - 150000000) * 0.38 + (500000000 - 300000000) * 0.4
                    + (1000000000 - 500000000) * 0.42 + (annualSalary - 1000000000) * 0.45;
        }
        double localTax = tax * 0.1;
        return tax + localTax;
    }
}