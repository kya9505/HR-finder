package cli.io;

import dto.Employees;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class EmployeeIO extends BaseIO {

    public int readEmployeeId() {
        return validator.readValidatedIntNoMax("employee_id(int) : ", 0, "Please Check Employee ID");
    }

    public String readString() {
        return scanner.nextLine();
    }

    public Integer readInt() {
        return Integer.parseInt(scanner.nextLine());
    }

    public String readFirstName() {
        return validator.readValidatedVarchar("first_name(max: 20) : ", 20, "Please Check First Name");
    }

    public String readLastName() {
        return validator.readValidatedVarchar("last_name(max: 25) : ", 25, "Please Check Last Name");
    }

    public String readPhone_number() {
        return validator.readValidatedVarchar("phone_number(max: 20) : ", 20, "Please Check Phone Number");
    }

    public String readEmail() {
        return validator.readValidatedVarchar("email(max: 25) : ", 25, "Please Check Email");
    }

    public Date readHireDate() {
        System.out.print("enter date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        try {
            return Date.valueOf(dateString);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return readHireDate();
        }
    }

    public String readJobId() {
        return validator.readValidatedVarchar("job_id(max: 10) : ", 10, "Please Check Job ID");
    }

    public BigDecimal readSalary() {
        return validator.readValidatedBigDecimal("salary : ", new BigDecimal("0.00"), new BigDecimal("999999.99"), 2, "Please Check Salary");
    }

    public BigDecimal readCommissionPct() {
        return validator.readValidatedBigDecimal("commission_pct : ", new BigDecimal("0.00"), new BigDecimal("99.99"), 2, "Please Check Commission Percent");
    }

    public int readManagerId() {
        return validator.readValidatedIntNoMax("manager_id(int) : ", 0, "Please Check Manager ID");
    }

    public int readDepartmentId() {
        return validator.readValidatedIntNoMax("department_id(int) : ", 0, "Please Check Department ID");
    }

    public Date readStartDate() {
        System.out.print("enter start date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        try {
            return Date.valueOf(dateString);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return readStartDate();
        }
    }

    public Date readEndDate() {
        System.out.print("enter end date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        try {
            return Date.valueOf(dateString);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return readEndDate();
        }
    }

    public void printSearchSubmenu2(List<Employees> searchList) {
        if (searchList.isEmpty()) {
            System.out.println("No matching employees found.");
        } else {
            System.out.println("Result applicable employees:");
            for (Employees employee : searchList) {
                System.out.println(employee);
            }
        }
    }

    public void printSearchSubmenu1(int count) {
        if (count == 0) {
            System.out.println("No matching employees found.");
        }
        System.out.println("Total Employees Found: " + count);
    }

    public void printSortSubMenu(List<Employees> sortedList) {
        System.out.println("Sorted Employee List:");
        for (Employees employee : sortedList) {
            System.out.println(employee);
        }
    }

    public int readSubMenuChoice() {
        return validator.readValidated(
                "Enter sub menu choice (1 or 2): ",
                input -> {
                    try {
                        return Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid input. Please enter 1 or 2.");
                    }
                },
                choice -> choice == 1 || choice == 2,
                "Invalid choice. Please enter 1 or 2."
        );
    }
}