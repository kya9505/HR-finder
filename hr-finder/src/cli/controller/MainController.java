package cli.controller;

import service.LoginDemoImpl;

public class MainController extends BaseController {

    public static void main(String[] args) {
        MainController controller = new MainController();
        controller.run();
    }

    public void run() {
        // 로그인 결과에 따라 관리자와 일반 사용자 메뉴를 분리합니다.
        boolean isAdmin = LoginDemoImpl.login();

        if (isAdmin) {
            adminMainMenu();
        } else {
            userMainMenu();
        }
    }

    // 관리자용 메뉴: 부서, 직원, 급여, 백업 기능 제공
    private void adminMainMenu() {
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
                    salaryController.runAdmin();
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

    // 일반 사용자용 메뉴: 직원과 급여(자신의 급여/퇴직금 조회) 기능 제공
    private void userMainMenu() {
        EmployeeController empController = new EmployeeController();
        SalaryController salaryController = new SalaryController();

        boolean exitProgram = false;
        while (!exitProgram) {
            printMenu("Main Menu", "Employees", "Salary", "Exit");
            int mainChoice = readChoice("Select an option: ");
            switch (mainChoice) {
                case 1:
                    printSectionHeader("Employees Submenu");
                    printMenu("Employees Options", "Search", "Sort");
                    int empChoice = readChoice("Select an option: ");
                    switch (empChoice) {
                        case 1:
                            empController.displaySearchMenu();
                            break;
                        case 2:
                            empController.displaySortMenu();
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                    break;
                case 2:
                    salaryController.runUser();
                    break;
                case 3:
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