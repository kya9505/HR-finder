package dao;

import dto.Employees;
import java.util.Optional;
import java.util.List;

public interface EmployeeDao {
        <T> Optional<List<Employees>> findEmployee(String searchMenu, T searchValue);
        Optional<List<Employees>> findJobHistory(java.sql.Date startDate, java.sql.Date endDate);
        Optional<List<Employees>> loadEmployees();
        Optional<Employees> addEmployee(Employees employee);
        Optional<Employees> deleteEmployee(int employeeId);
        Optional<Employees> updateEmployee(Employees employee);
        Optional<List<Employees>> updateName(String oldFullName, String newFirstName, String newLastName);
        Optional<List<Employees>> updateByChoice(String fieldToUpdate, String oldValue, String newValue);
}