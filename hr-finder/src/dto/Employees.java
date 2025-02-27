package dto;

import java.math.BigDecimal;
import java.sql.Date;

public class Employees {
    int employee_id;
    String first_name;
    String last_name;
    String email;
    String phone_number;
    Date hire_date;
    String job_id;
    BigDecimal salary;
    BigDecimal commission;
    int manager_id;
    int department_id;
}
