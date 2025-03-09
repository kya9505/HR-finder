package cli.io;

public class DepartmentIO extends BaseIO {

    public int readDepartmentId() {
        return validator.readValidatedIntNoMax("department_id(int) : ", 0, "Please Check Department ID");
    }

    public String readDepartmentName() {
        return validator.readValidatedVarchar("department_name (max:30) : ", 30, "Please Check Department Name");
    }

    public Integer readDepartmentManagerId() {
        return validator.readValidatedIntNoMax("department_manager_id(int) : ", 0, "Please Check Department Manager ID");
    }

    public Integer readDepartmentLocation() {
        return validator.readValidatedIntNoMax("department_location (int) : ", 0, "Please Check Department Location");
    }
}