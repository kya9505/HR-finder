package service;

import dto.Departments;
import java.util.List;

public interface DepartmentService {
    List<Departments> addDepartments(Departments departments);
    List<Departments> deleteDepartmentIfEmpty(int department_id);
    List<Departments> updateDepartmentsManger_id(int department_id, int manager_id);
    List<Departments> updateDepartments_name(int department_id, String department_name);
}