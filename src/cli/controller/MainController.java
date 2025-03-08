package cli.controller;

public class MainController extends BaseController {

    public static void main(String[] args) {
        MainController controller = new MainController();
        controller.run();
    }

    public void run() {
        DepartmentController deptController = new DepartmentController();
        EmployeeController empController = new EmployeeController();
        SalaryController salaryController = new SalaryController();
        BackupController backupController = new BackupController();
        boolean exitProgram = false;

        while (!exitProgram) {
            printMenu("Main Menu", "Departments", "Employees", "Salary", "Backup", "Exit");
            int mainChoice = readChoice("Select an option: ");
            switch (mainChoice) {
                case 1:
                    deptController.run();
                    break;
                case 2:
                    empController.run();
                    break;
                case 3:
                    salaryController.run();
                    break;
                case 4:
                    backupController.run();
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }
}