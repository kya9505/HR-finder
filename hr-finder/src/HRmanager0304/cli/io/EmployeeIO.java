package HRmanager0304.cli.io;

import HRmanager0304.dto.Employees;

import java.util.List;

public class EmployeeIO {

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
        System.out.println("Total Employees Found: " + count);
    }

    public void printSortSubMenu(List<Employees> sortedList) {
        System.out.println("Sorted Employee List:");
        for (Employees employee : sortedList) {
            System.out.println(employee);
        }
    }
}
