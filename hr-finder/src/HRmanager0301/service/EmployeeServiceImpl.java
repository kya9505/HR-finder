package HRmanager0301.service;

import HRmanager0301.dao.EmployeeDaoImpl;
import HRmanager0301.dto.Employees;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class EmployeeServiceImpl {
    private final EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
    ArrayList employeesList = new ArrayList<Employees>();
    Scanner sc = new Scanner(System.in);

    //사원 번호를 기준으로 검색
    public void findByEmpId(int employee_id) {
        int count = 0;
        employee_id = sc.nextInt();
        employeeDao.findSEmployee(serchSubMenu(),employee_id);
    }

    //이름으로 검색 - last name 검색
    public void findByLastname(String last_name) {
    }

    //이름 검색 - first name 검색
    public void findByFirstname(String First_name) {
    }

    //직업 검색
    public void findByJobId(String job_id) {
    }


    //고용일/근속기간 검색 - 고용일 검색
    public void findByHireDate(Date hire_date) {
    }

    //고용일/근속기간 검색 - 근무기간 검색
    public void findByEmploymentDuration(Date startDate, Date endDate) {
    }

    //고용일/근속기간 검색 - 연차 검색
    public void getEmployeesByServiceYears(int minYears, int maxYears) {
    }

    //근무지역으로 검색 - 지역으로 검색 department,location join
    public void findByLocation(int location_id) {
    }

    //subMenu : 직원수만 확인 하거나 해당 직원의 정보를 조회할 수 있다.
    public String serchSubMenu() {
        while (true) {
            System.out.println("1. Count  2. All Load");
            String input = sc.nextLine();

            try {
                int subchoice = Integer.parseInt(input);
                switch (subchoice) {
                    case 1:
                        return " count(employee_id) ";
                    case 2:
                        return " * ";
                    default:
                        System.out.println("re choice, 1 or 2");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
            }
        }
    }
}

