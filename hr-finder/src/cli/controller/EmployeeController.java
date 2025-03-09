package cli.controller;

import cli.io.EmployeeIO;
import dto.Employees;
import service.EmployeeService;
import service.EmployeeServiceImpl;
import java.util.List;

public class EmployeeController extends BaseController {
    private EmployeeService employeeService = new EmployeeServiceImpl();
    private EmployeeIO employeeIO = new EmployeeIO();

    // 관리자 메뉴에서 사용하는 run() 메서드 (전체 기능 제공)
    public void run() {
        boolean exitProgram = false;
        while (!exitProgram) {
            printMenu("Employee Main Menu", "Add / Delete", "Update", "Search", "Sort", "Back to Main Menu");
            int mainChoice = readChoice("Select an option: ");
            switch (mainChoice) {
                case 1:
                    displayAddDeleteMenu();
                    break;
                case 2:
                    displayUpdateMenu();
                    break;
                case 3:
                    displaySearchMenu();
                    break;
                case 4:
                    displaySortMenu();
                    break;
                case 5:
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }

    // 일반 사용자용 메서드: 직원 검색 메뉴
    public void displaySearchMenu() {
        printSectionHeader("Search Menu");
        printMenu("Search Options", "Search by Employee ID", "Search by Last Name", "Search by First Name", "Search by Job Title", "Search by Hire Date", "Search by Employment Duration");
        int option = readChoice("Select an option: ");
        List<Employees> findList = null;
        switch (option) {
            case 1:
                findList = employeeService.searchByEmpId(employeeIO.readEmployeeId());
                break;
            case 2:
                findList = employeeService.searchByLastname(employeeIO.readLastName());
                break;
            case 3:
                findList = employeeService.searchByFirstname(employeeIO.readFirstName());
                break;
            case 4:
                findList = employeeService.searchByJobId(employeeIO.readJobId());
                break;
            case 5:
                findList = employeeService.searchByHireDate(employeeIO.readHireDate());
                break;
            case 6:
                findList = employeeService.searchByEmploymentDuration(employeeIO.readStartDate(), employeeIO.readEndDate());
                break;
            default:
                System.out.println("Invalid option in Search menu.");
        }
        printSectionHeader("Search SubMenu");
        printMenu("Search SubMenu", "Number of employees searched", "Information about employees searched");
        int subMenuChoice = employeeIO.readSubMenuChoice();
        searchSubMenu(subMenuChoice, findList);
    }

    // 일반 사용자용 메서드: 직원 정렬 메뉴
    public void displaySortMenu() {
        printSectionHeader("Sort Menu");
        printMenu("Sort Options", "Sort by Employee ID", "Sort by Name", "Sort by Hire Date", "Sort by Job Title");
        int option = readChoice("Select an option: ");
        List<Employees> findList = null;
        switch (option) {
            case 1:
                findList = employeeService.sortByEmpId();
                break;
            case 2:
                findList = employeeService.sortByName();
                break;
            case 3:
                findList = employeeService.sortByJHireDate();
                break;
            case 4:
                findList = employeeService.sortByJobId();
                break;
            default:
                System.out.println("Invalid option in Sort menu.");
        }
        printSectionHeader("Sort SubMenu");
        printMenu("Sort SubMenu", "Ascending", "Descending");
        int subMenuChoice = employeeIO.readSubMenuChoice();
        List<Employees> sortedList = employeeService.sortEmployees(findList, subMenuChoice);
        employeeIO.printSortSubMenu(sortedList);
    }

    // 관리자 메뉴용 메서드: 나머지 기능들
    private void displayAddDeleteMenu() {
        printSectionHeader("Add / Delete Menu");
        printMenu("Add / Delete Options", "Add Employee Information", "Delete Employee Information");
        int option = readChoice("Select an option: ");
        if (option == 1) {
            addEmployee();
        } else if (option == 2) {
            employeeService.deleteEmployee(employeeIO.readEmployeeId());
        } else {
            System.out.println("Invalid option in Add/Delete menu.");
        }
    }

    public void addEmployee() {
        int employee_id = employeeIO.readEmployeeId();
        String first_name = employeeIO.readFirstName();
        String last_name = employeeIO.readLastName();
        String email = employeeIO.readEmail();
        String phone_number = employeeIO.readPhone_number();
        java.sql.Date hire_date = employeeIO.readHireDate();
        String job_id = employeeIO.readJobId();
        java.math.BigDecimal salary = employeeIO.readSalary();
        java.math.BigDecimal commission_pct = employeeIO.readCommissionPct();
        int manager_id = employeeIO.readManagerId();
        int department_id = employeeIO.readDepartmentId();

        Employees employee = Employees.builder()
                .employee_id(employee_id)
                .first_name(first_name)
                .last_name(last_name)
                .email(email)
                .phone_number(phone_number)
                .hire_date(hire_date)
                .job_id(job_id)
                .salary(salary)
                .commission_pct(commission_pct)
                .manager_id(manager_id)
                .department_id(department_id)
                .build();
        employeeService.addEmployee(employee);
    }

    private void displayUpdateMenu() {
        printSectionHeader("Update Menu");
        System.out.print("Please Enter Full Name to be Modified: ");
        String oldFullName = employeeIO.readString();
        System.out.print("Enter new first name: ");
        String newFirstName = employeeIO.readFirstName();
        System.out.print("Enter new last name: ");
        String newLastName = employeeIO.readLastName();
        employeeService.updateName(oldFullName, newFirstName, newLastName);
    }

    private void searchSubMenu(int subMenuChoice, List<Employees> searchList) {
        switch (subMenuChoice) {
            case 1:
                int count = employeeService.searchSubMenu1(searchList);
                employeeIO.printSearchSubmenu1(count);
                break;
            case 2:
                List<Employees> employees = employeeService.searchSubMenu2(searchList);
                if (!employees.isEmpty()) {
                    employeeIO.printSearchSubmenu2(employees);
                }
                break;
            default:
                System.out.println("Please choose 1 or 2");
        }
    }
}