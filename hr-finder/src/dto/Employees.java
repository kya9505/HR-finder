package dto;


import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class Employees {
    int employee_id;
    String first_name;
    String last_name;
    String email;
    String phone_number;
    Date hire_date;
    String job_id;
    BigDecimal salary;
    BigDecimal commission_pct;
    int manager_id;
    int department_id;
    private int perfScore; // 성과 평가 점수git
    String newValue;
    String oldValue;
    String updateField;

    public static EmployeesBuilder builder() {
        return new EmployeesBuilder();
    }

    public static class EmployeesBuilder {
        private int employee_id;
        private String first_name = null;
        private String last_name;
        private String email;
        private String phone_number = null;
        private Date hire_date;
        private String job_id;
        private BigDecimal salary;
        private BigDecimal commission_pct = BigDecimal.ZERO;
        private int manager_id = 0;
        private int department_id = 0;
        private int perfScore;
        private String updateField;
        private String newValue;
        private String oldValue;

        public EmployeesBuilder employee_id(int employee_id) {
            this.employee_id = employee_id;
            return this;
        }

        public EmployeesBuilder first_name(String first_name) {
            this.first_name = first_name;
            return this;
        }

        public EmployeesBuilder last_name(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public EmployeesBuilder email(String email) {
            this.email = email;
            return this;
        }

        public EmployeesBuilder phone_number(String phone_number) {
            this.phone_number = phone_number;
            return this;
        }

        public EmployeesBuilder hire_date(Date hire_date) {
            this.hire_date = hire_date;
            return this;
        }

        public EmployeesBuilder job_id(String job_id) {
            this.job_id = job_id;
            return this;
        }

        public EmployeesBuilder salary(BigDecimal salary) {
            this.salary = salary;
            return this;
        }

        public EmployeesBuilder commission_pct(BigDecimal commission_pct) {
            this.commission_pct = commission_pct;
            return this;
        }

        public EmployeesBuilder manager_id(int manager_id) {
            this.manager_id = manager_id;
            return this;
        }

        public EmployeesBuilder department_id(int department_id) {
            this.department_id = department_id;
            return this;
        }
        public EmployeesBuilder perfScore(int perfScore) {
            this.perfScore = perfScore;
            return this;
        }

        public EmployeesBuilder updateField(String updateField) {
            this.updateField = updateField;
            return this;
        }

        public EmployeesBuilder newValue(String newValue) {
            this.newValue = newValue;
            return this;
        }

        public EmployeesBuilder oldValue(String oldValue) {
            this.oldValue = oldValue;
            return this;
        }

        public Employees build() {
            Employees employee = new Employees();
            employee.setEmployee_id(this.employee_id);
            employee.setFirst_name(this.first_name);
            employee.setLast_name(this.last_name);
            employee.setEmail(this.email);
            employee.setPhone_number(this.phone_number);
            employee.setHire_date(this.hire_date);
            employee.setJob_id(this.job_id);
            employee.setSalary(this.salary);
            employee.setCommission_pct(this.commission_pct);
            employee.setManager_id(this.manager_id);
            employee.setDepartment_id(this.department_id);
            employee.setPerfScore(this.perfScore);
            employee.setUpdateField(this.updateField);
            employee.setNewValue(this.newValue);
            employee.setOldValue(this.oldValue);
            return employee;
        }
    }

    //임의설정
    @Override
    public String toString() {
        return "Employees{" +
                "employee_id=" + employee_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", hire_date=" + hire_date +
                ", job_id='" + job_id + '\'' +
                ", salary=" + salary +
                ", commission_pct=" + commission_pct +
                ", manager_id=" + manager_id +
                ", department_id=" + department_id +
                ", perfScore=" + perfScore +
                ", newValue='" + newValue + '\'' +
                ", oldValue='" + oldValue + '\'' +
                ", updateField='" + updateField + '\'' +
                '}';
    }
}
