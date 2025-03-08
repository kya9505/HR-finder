package dao;

import dto.Departments;
import java.util.Optional;

public interface DepartmentDao {
    int countEmployeesByDepartment(int department_id);
    int countDepartmentId(int department_id);
    Optional<Departments> addDepartments(Departments departments);
    Optional<Departments> deleteDepartments(int department_id);
    Optional<Departments> updateDepartmentsManager_id(int department_id, int manager_id);
    Optional<Departments> updateDepartments_name(int department_id, String department_name);
}