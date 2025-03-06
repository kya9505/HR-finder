package dao;

import dto.Employees;
import dto.EventBackup;
import util.utildemo;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class EmployeeDaoImpl {
    Connection conn = null;
    PreparedStatement pstmt = null;

    // search : employee table
    public <T> Optional<List<Employees>> findEmployee(String searchMenu, T searchvalue) {
        Function<T, String> converter = v -> (v instanceof String) ? (String) v : String.valueOf(v);
        String strValue = converter.apply(searchvalue); //String으로 변환

        List<Employees> findEmployeeList = new ArrayList<>();

        try {
            conn = utildemo.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM employees WHERE " + searchMenu + " = ?");
            pstmt.setString(1, strValue);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employees.EmployeesBuilder builder = new Employees.EmployeesBuilder()
                            .employee_id(rs.getInt("employee_id"))
                            .last_name(rs.getString("last_name"))
                            .email(rs.getString("email"))
                            .hire_date(rs.getDate("hire_date"))
                            .job_id(rs.getString("job_id"))
                            .salary(rs.getBigDecimal("salary"));
                    builder.first_name(rs.getString("first_name"));
                    builder.phone_number(rs.getString("phone_number"));
                    builder.commission_pct(rs.getBigDecimal("commission_pct"));
                    builder.manager_id(rs.getInt("manager_id"));
                    builder.department_id(rs.getInt("department_id"));
                    findEmployeeList.add(builder.build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return findEmployeeList.isEmpty() ? Optional.empty() : Optional.of(findEmployeeList);
    }

    //search : job_history join
    public Optional<List<Employees>> findJobHistory(Date startDate, Date endDate) {
        List<Employees> findJobHistoryList = new ArrayList<>();
        try {
            conn = utildemo.getConnection();
            pstmt = conn.prepareStatement("SELECT * " +
                    "FROM employees e ,job_history j  " +
                    "WHERE e.employee_id = j.employee_id AND (j.start_date BETWEEN ? AND ?) AND (j.end_date BETWEEN ? AND ? OR j.end_date IS NULL)");
            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);
            pstmt.setDate(3, startDate);
            pstmt.setDate(4, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employees.EmployeesBuilder builder = new Employees.EmployeesBuilder()
                            .employee_id(rs.getInt("employee_id"))
                            .last_name(rs.getString("last_name"))
                            .email(rs.getString("email"))
                            .hire_date(rs.getDate("hire_date"))
                            .job_id(rs.getString("job_id"))
                            .salary(rs.getBigDecimal("salary"));
                    builder.first_name(rs.getString("first_name"));
                    builder.phone_number(rs.getString("phone_number"));
                    builder.commission_pct(rs.getBigDecimal("commission_pct"));
                    builder.manager_id(rs.getInt("manager_id"));
                    builder.department_id(rs.getInt("department_id"));
                    findJobHistoryList.add(builder.build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return findJobHistoryList.isEmpty() ? Optional.empty() : Optional.of(findJobHistoryList);
    }

    /**
     *
     * @return
     */
    public Optional<List<Employees>> loadEmployees() {
        List<Employees> loadEmployeeList = new ArrayList<>();
        try {
            conn = utildemo.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM employees");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employees.EmployeesBuilder builder = new Employees.EmployeesBuilder()
                            .employee_id(rs.getInt("employee_id"))
                            .last_name(rs.getString("last_name"))
                            .email(rs.getString("email"))
                            .hire_date(rs.getDate("hire_date"))
                            .job_id(rs.getString("job_id"))
                            .salary(rs.getBigDecimal("salary"));
                    builder.first_name(rs.getString("first_name"));
                    builder.phone_number(rs.getString("phone_number"));
                    builder.commission_pct(rs.getBigDecimal("commission_pct"));
                    builder.manager_id(rs.getInt("manager_id"));
                    builder.department_id(rs.getInt("department_id"));
                    loadEmployeeList.add(builder.build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadEmployeeList.isEmpty() ? Optional.empty() : Optional.of(loadEmployeeList);
    }

    public Optional<Employees> addEmployee(Employees employee) {

        conn = utildemo.getConnection();
        ResultSet rs = null;
        try {
            String insertSql = "INSERT INTO employees (employee_id, first_name, last_name, email, phone_number, hire_date, job_id, salary, commission_pct, manager_id, department_id) " +
                    "VALUES (?, ?, ?, ?, ?, now(), ?, ?, ?, ?,?)";
            pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, employee.getEmployee_id());
            pstmt.setString(2, employee.getFirst_name());
            pstmt.setString(3, employee.getLast_name());
            pstmt.setString(4, employee.getEmail());
            pstmt.setString(5, employee.getPhone_number());
            pstmt.setString(6, employee.getJob_id());
            pstmt.setBigDecimal(7, employee.getSalary());
            pstmt.setBigDecimal(8, employee.getCommission_pct());
            pstmt.setInt(9, employee.getManager_id());
            pstmt.setInt(10, employee.getDepartment_id());

            int cnt = pstmt.executeUpdate();
            if (cnt > 0) {
                if(pstmt != null) {
                    try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
                }
                String selectSql = "SELECT * FROM employees WHERE employee_id = ?";
                pstmt = conn.prepareStatement(selectSql);
                pstmt.setInt(1, employee.getEmployee_id());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    Employees inserted = Employees.builder()
                            .employee_id(rs.getInt("employee_id"))
                            .first_name(rs.getString("first_name"))
                            .last_name(rs.getString("last_name"))
                            .email(rs.getString("email"))
                            .phone_number(rs.getString("phone_number"))
                            .hire_date(rs.getDate("hire_date"))
                            .job_id(rs.getString("job_id"))
                            .salary(rs.getBigDecimal("salary"))
                            .commission_pct(rs.getBigDecimal("commission_pct"))
                            .manager_id(rs.getInt("manager_id"))
                            .department_id(rs.getInt("department_id"))
                            .build();
                    return Optional.of(inserted);
                }
            } else {
                System.out.println("Failed to add employee information for employee_id: " + employee.getEmployee_id());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (pstmt != null) {
                try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return Optional.empty();
    }
    public Optional<Employees> deleteEmployee(int employeeId) {
         conn = utildemo.getConnection();
        try {
            String deleteSql = "DELETE FROM employees WHERE employee_id = ?";
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, employeeId);
            int cnt = pstmt.executeUpdate();
            if (cnt > 0) {
                System.out.println("Successfully deleted employee with employee_id: " + employeeId);
            } else {
                System.out.println("Failed to delete employee with employee_id: " + employeeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return Optional.empty();
    }
    public Optional<Employees> updateEmployee(Employees employee) {
        conn = utildemo.getConnection();
        PreparedStatement updateStmt = null;
        PreparedStatement selectStmt = null;
        ResultSet rs = null;
        try {
            String updateSql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, phone_number = ?, hire_date = ?, job_id = ?, salary = ?, commission_pct = ?, manager_id = ?, department_id = ? WHERE employee_id = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, employee.getFirst_name());
            updateStmt.setString(2, employee.getLast_name());
            updateStmt.setString(3, employee.getEmail());
            updateStmt.setString(4, employee.getPhone_number());
            updateStmt.setDate(5, employee.getHire_date());
            updateStmt.setString(6, employee.getJob_id());
            updateStmt.setBigDecimal(7, employee.getSalary());
            updateStmt.setBigDecimal(8, employee.getCommission_pct());
            updateStmt.setInt(9, employee.getManager_id());
            updateStmt.setInt(10, employee.getDepartment_id());
            updateStmt.setInt(11, employee.getEmployee_id());

            int cnt = updateStmt.executeUpdate();
            if (cnt > 0) {
                String selectSql = "SELECT * FROM employees WHERE employee_id = ?";
                selectStmt = conn.prepareStatement(selectSql);
                selectStmt.setInt(1, employee.getEmployee_id());
                rs = selectStmt.executeQuery();
                if (rs.next()) {
                    Employees updated = Employees.builder()
                            .employee_id(rs.getInt("employee_id"))
                            .first_name(rs.getString("first_name"))
                            .last_name(rs.getString("last_name"))
                            .email(rs.getString("email"))
                            .phone_number(rs.getString("phone_number"))
                            .hire_date(rs.getDate("hire_date"))
                            .job_id(rs.getString("job_id"))
                            .salary(rs.getBigDecimal("salary"))
                            .commission_pct(rs.getBigDecimal("commission_pct"))
                            .manager_id(rs.getInt("manager_id"))
                            .department_id(rs.getInt("department_id"))
                            .build();
                    return Optional.of(updated);
                }
            } else {
                System.out.println("Failed to update employee information for employee_id: " + employee.getEmployee_id());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (selectStmt != null) {
                try { selectStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (updateStmt != null) {
                try { updateStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return Optional.empty();
    }

    public Optional<List<Employees>> updateName(String oldFullName, String newFirstName, String newLastName) {
        conn = utildemo.getConnection();
        PreparedStatement updateStmt = null;
        PreparedStatement selectStmt = null;
        ResultSet rs = null;
        List<Employees> updatedList = new ArrayList<>();
        try {
            String updateSql = "UPDATE employees SET first_name = ?, last_name = ? WHERE CONCAT(first_name, ' ', last_name) = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, newFirstName);
            updateStmt.setString(2, newLastName);
            updateStmt.setString(3, oldFullName);
            int updateCount = updateStmt.executeUpdate();
            if (updateCount > 0) {
                String selectSql = "SELECT * FROM employees WHERE first_name = ? AND last_name = ?";
                selectStmt = conn.prepareStatement(selectSql);
                selectStmt.setString(1, newFirstName);
                selectStmt.setString(2, newLastName);
                rs = selectStmt.executeQuery();
                while (rs.next()) {
                    Employees.EmployeesBuilder builder = Employees.builder()
                            .employee_id(rs.getInt("employee_id"))
                            .first_name(rs.getString("first_name"))
                            .last_name(rs.getString("last_name"))
                            .email(rs.getString("email"))
                            .phone_number(rs.getString("phone_number"))
                            .hire_date(rs.getDate("hire_date"))
                            .job_id(rs.getString("job_id"))
                            .salary(rs.getBigDecimal("salary"))
                            .commission_pct(rs.getBigDecimal("commission_pct"))
                            .manager_id(rs.getInt("manager_id"))
                            .department_id(rs.getInt("department_id"));
                    updatedList.add(builder.build());
                }
                if (updatedList.isEmpty()) {
                    System.out.println("No employee name updated for full name: " + oldFullName);
                    return Optional.empty();
                } else {
                    return Optional.of(updatedList);
                }
            } else {
                System.out.println("No employee name updated for full name: " + oldFullName);
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (selectStmt != null) {
                try { selectStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (updateStmt != null) {
                try { updateStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
    public Optional<List<Employees>> updateByChoice(String fieldToUpdate, String oldValue, String newValue) {
        List<String> allowedFields = List.of("first_name", "last_name", "email", "phone_number", "job_id", "salary", "commission_pct", "manager_id", "department_id");
        if (!allowedFields.contains(fieldToUpdate)) {
            System.out.println("Invalid update field: " + fieldToUpdate);
            return Optional.empty();
        }
        conn = utildemo.getConnection();
        PreparedStatement updateStmt = null;
        PreparedStatement selectStmt = null;
        ResultSet rs = null;
        List<Employees> updatedList = new ArrayList<>();
        try {
            String updateSql = "UPDATE employees SET " + fieldToUpdate + " = ? WHERE " + fieldToUpdate + " = ?";
            updateStmt = conn.prepareStatement(updateSql);
            if (fieldToUpdate.equals("salary") || fieldToUpdate.equals("commission_pct")) {
                updateStmt.setBigDecimal(1, new BigDecimal(newValue));
                updateStmt.setBigDecimal(2, new BigDecimal(oldValue));
            } else if (fieldToUpdate.equals("manager_id") || fieldToUpdate.equals("department_id")) {
                updateStmt.setInt(1, Integer.parseInt(newValue));
                updateStmt.setInt(2, Integer.parseInt(oldValue));
            } else {
                updateStmt.setString(1, newValue);
                updateStmt.setString(2, oldValue);
            }
            int updateCount = updateStmt.executeUpdate();
            if (updateCount > 0) {
                String selectSql = "SELECT * FROM employees WHERE " + fieldToUpdate + " = ?";
                selectStmt = conn.prepareStatement(selectSql);
                if (fieldToUpdate.equals("salary") || fieldToUpdate.equals("commission_pct")) {
                    selectStmt.setBigDecimal(1, new BigDecimal(newValue));
                } else if (fieldToUpdate.equals("manager_id") || fieldToUpdate.equals("department_id")) {
                    selectStmt.setInt(1, Integer.parseInt(newValue));
                } else {
                    selectStmt.setString(1, newValue);
                }
                rs = selectStmt.executeQuery();
                while (rs.next()) {
                    Employees.EmployeesBuilder builder = Employees.builder()
                            .employee_id(rs.getInt("employee_id"))
                            .first_name(rs.getString("first_name"))
                            .last_name(rs.getString("last_name"))
                            .email(rs.getString("email"))
                            .phone_number(rs.getString("phone_number"))
                            .hire_date(rs.getDate("hire_date"))
                            .job_id(rs.getString("job_id"))
                            .salary(rs.getBigDecimal("salary"))
                            .commission_pct(rs.getBigDecimal("commission_pct"))
                            .manager_id(rs.getInt("manager_id"))
                            .department_id(rs.getInt("department_id"));
                    updatedList.add(builder.build());
                }
                if (updatedList.isEmpty()) {
                    System.out.println("No update performed on " + fieldToUpdate + " from " + oldValue + " to " + newValue);
                    return Optional.empty();
                } else {
                    return Optional.of(updatedList);
                }
            } else {
                System.out.println("No update performed on " + fieldToUpdate + " from " + oldValue + " to " + newValue);
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (selectStmt != null) {
                try { selectStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (updateStmt != null) {
                try { updateStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    public void backup() {
        List<EventBackup> backupList = new ArrayList<>();

        try {
            conn = utildemo.getConnection();
            CallableStatement cs = conn.prepareCall("{call prc_backup()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()){
                EventBackup eventBackup = new EventBackup();
                eventBackup.setBackup_id(rs.getInt("backup_id"));
                eventBackup.setTable_name(rs.getString("table_name"));
                eventBackup.setEvent_type(rs.getString("event_type"));
                eventBackup.setBefore_change(rs.getString("before_change"));
                eventBackup.setAfter_change(rs.getString("after_change"));
                eventBackup.setEvent_time(rs.getDate("event_time"));
                backupList.add(eventBackup);
            }
            for (EventBackup eventBackup : backupList) System.out.println(eventBackup);
            if(rs!=null) rs.close();
            if(cs != null) cs.close();
            if(conn != null) conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

