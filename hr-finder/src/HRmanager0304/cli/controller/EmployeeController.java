package HRmanager0304.cli.controller;

import HRmanager0304.cli.io.EmployeeIO;
import HRmanager0304.dto.Employees;
import HRmanager0304.service.EmployeeService;
import HRmanager0304.service.EmployeeServiceImpl;

import java.util.Comparator;
import java.util.List;

public class EmployeeController {
    // subMenu : 직원수만 확인하거나 해당 직원의 정보를 조회할 수 있다.
    private EmployeeIO employeeIO = new EmployeeIO();
    private EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    public void sortSubMenu(List<Employees> sortList, int choiceSubMenu) {
        List<Employees> sortedList = employeeService.sortEmployees(sortList, choiceSubMenu);
        employeeIO.printSortSubMenu(sortedList);
    }

    public void searchSubMenu(int subMenuChoice, List<Employees> searchList) {
        switch (subMenuChoice) {
            case 1:
                int count = employeeService.searchSubMenu1(searchList); // Service 호출
                employeeIO.printSearchSubmenu1(count); // View 호출
                break;
            case 2:
                List<Employees> employees = employeeService.searchSubMenu2(searchList); // Service 호출
                employeeIO.printSearchSubmenu2(employees); // View 호출
                break;
        }
    }
}

