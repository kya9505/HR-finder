package HRmanager0301.service;

import HRmanager0301.dao.EmployeeDaoImpl;
import HRmanager0301.dto.Employees;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EmployeeServiceImpl {
   // test용 main
    public static void main(String[] args) {
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        employeeService.searchByEmpId();
    }

    private final EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
    Scanner sc = new Scanner(System.in);

    //사원 번호를 기준으로 검색
    public List<Employees> searchByEmpId() {
        System.out.print("enter(employee_id) : ");
        int employee_id = sc.nextInt();
        sc.nextLine();
        List<Employees> searchList = employeeDao.findEmployee("employee_id", employee_id);
        if (searchList == null) {
            System.out.println("No applicable employees " + employee_id);
        } else {
            serchSubMenu(searchList);
        }
        return searchList;
    }

    //이름으로 검색 - last name 검색
    public void searchByLastname(String last_name) {
    }

    //이름 검색 - first name 검색
    public void searchByFirstname(String First_name) {
    }

    //직업 검색
    public void searchByJobId(String job_id) {
    }


    //고용일/근속기간 검색 - 고용일 검색
    public void searchByHireDate(Date hire_date) {
    }

    //고용일/근속기간 검색 - 근무기간 검색
    public void searchByEmploymentDuration(Date startDate, Date endDate) {
    }

    //고용일/근속기간 검색 - 연차 검색
    public void searchsByServiceYears(int minYears, int maxYears) {
    }

    //근무지역으로 검색 - 지역으로 검색 department,location join
    public void searchByLocation(int location_id) {
    }

    //subMenu : 직원수만 확인 하거나 해당 직원의 정보를 조회할 수 있다.
    public List<Employees> serchSubMenu(List<Employees> searchList) {

        while (true) {
            System.out.println("1. Count  2. All Load");
            String input = sc.nextLine();

            try {
                int subchoice = Integer.parseInt(input);
                switch (subchoice) {
                    case 1:
                        System.out.println("Number of applicable employees : " + searchList.size());
                        break;
                    case 2:
                        System.out.println("result applicable employees ");
                        for (Employees employees : searchList) {
                            System.out.println(employees);
                            System.out.println();
                        }
                        break;
                    default:
                        System.out.println("Please select again, 1 or 2");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        }
        return searchList;
    }
}


