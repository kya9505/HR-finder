package HRmanager0301.dao;

import HRmanager0301.dto.Employees;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java_advanced.src.Quest.dao.DBUtil.getConnection;

public class EmployeeDaoImpl {
    private static final String URL = "jdbc:mysql://localhost:3306/hrdb?serverTimezone=Asia/Seoul";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    //db임의 연동
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    public <T>  List<Employees> findEmployee(String searchMenu, T serchvalue) {

        Function<T, String> converter = v -> (v instanceof String) ? (String) v : String.valueOf(v);
        String strValue = converter.apply(serchvalue); //String으로 변환

        List<Employees> findList = new ArrayList<>();

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM employees WHERE " + searchMenu +" = ?");
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
                            builder.commission(rs.getBigDecimal("commission_pct"));
                            builder.manager_id(rs.getInt("manager_id"));
                            builder.department_id(rs.getInt("department_id"));
                            findList.add(builder.build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return findList;
    }

}
