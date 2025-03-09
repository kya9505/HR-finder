package cli.controller;

import cli.io.DepartmentIO;
import dto.Departments;
import service.DepartmentService;
import service.DepartmentServiceImpl;

public class DepartmentController extends BaseController {
    private DepartmentService departmentService = new DepartmentServiceImpl();
    private DepartmentIO departmentIO = new DepartmentIO();

    public void run() {
        boolean exitProgram = false;
        while (!exitProgram) {
            printMenu("Departments Menu", "Add", "Delete", "Update", "Back to Main Menu");
            int mainChoice = readChoice("Select an option: ");

            switch (mainChoice) {
                case 1:
                    displayAddMenu();
                    break;
                case 2:
                    displayDeleteMenu();
                    break;
                case 3:
                    displayUpdateMenu();
                    break;
                case 4:
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }

    private void displayAddMenu() {
        printSectionHeader("Add Menu");
        addDepartment();
    }

    public void addDepartment() {
        int department_id = departmentIO.readDepartmentId();
        String department_name = departmentIO.readDepartmentName();
        Integer department_manager_id = departmentIO.readDepartmentManagerId();
        Integer department_location_id = departmentIO.readDepartmentLocation();

        Departments departments = Departments.builder()
                .department_id(department_id)
                .department_name(department_name)
                .manager_id(department_manager_id)
                .location_id(department_location_id)
                .build();

        departmentService.addDepartments(departments);
    }

    private void displayDeleteMenu() {
        printSectionHeader("Delete Menu");
        System.out.print("Please Enter Department ID: ");
        int department_id = readChoice("");
        departmentService.deleteDepartmentIfEmpty(department_id);
    }

    private void displayUpdateMenu() {
        printSectionHeader("Update Menu");
        System.out.print("Please Enter Department ID: ");
        int department_id = readChoice("");

        System.out.println("1. Update manager_id");
        System.out.println("2. Update department_name");
        int option = readChoice("Select an option: ");

        switch (option) {
            case 1:
                System.out.print("Please Enter new manager_id: ");
                int manager_id = readChoice("");
                departmentService.updateDepartmentsManger_id(department_id, manager_id);
                break;
            case 2:
                System.out.print("Please Enter new department_name: ");
                String department_name = scanner.nextLine();
                departmentService.updateDepartments_name(department_id, department_name);
                break;
            default:
                System.out.println("Invalid option in Update menu.");
        }
    }
}