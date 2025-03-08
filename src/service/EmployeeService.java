package service;

import dto.Employees;
import java.util.Date;
import java.util.List;

public interface EmployeeService {
    List<Employees> addEmployee(Employees employee);
    List<Employees> deleteEmployee(int employeeId);
    List<Employees> updateEmployee(Employees employee);
    List<Employees> updateName(String oldFullName, String newFirstName, String newLastName);
    List<Employees> updateByChoice(String fieldToUpdate, String oldValue, String newValue);
    List<Employees> searchByEmpId(int employee_id);
    List<Employees> searchByLastname(String Last_name);
    List<Employees> searchByFirstname(String First_name);
    List<Employees> searchByJobId(String job_id);
    List<Employees> searchByHireDate(Date hire_date);
    List<Employees> searchByEmploymentDuration(Date startDate, Date endDate);
    List<Employees> sortByEmpId();
    List<Employees> sortByName();
    List<Employees> sortByJHireDate();
    List<Employees> sortByJobId();
    int searchSubMenu1(List<Employees> searchList);
    List<Employees> searchSubMenu2(List<Employees> searchList);
    List<Employees> sortEmployees(List<Employees> sortList, int choiceSubMenu);
}