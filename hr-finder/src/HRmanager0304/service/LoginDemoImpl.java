package HRmanager0304.service;

import HRmanager0303.dao.EmployeeDaoImpl;

import java.sql.*;
import java.util.Scanner;

public class LoginDemoImpl {
    private static final String URL = "jdbc:mysql://localhost:3306/hr?serverTimezone=Asia/Seoul";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void main(String[] args) {
        final EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your employee ID : ");
        String employeeId = scanner.nextLine();

        System.out.print("Please enter your PIN number : ");
        String inputPassword = scanner.nextLine();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String sql = "SELECT DISTINCT e1.employee_id, e1.manager_id, e1.first_name, e1.last_name " +
                    "FROM employees e1 " +
                    "JOIN employees e2 ON e1.employee_id = e2.manager_id " +
                    "WHERE e1.employee_id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, employeeId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                if("1234".equals(inputPassword)){
                    System.out.println("Administrator login successful");
                   // 여기에 관리자 로그인 들어가는 메서드 호출
                } else {
                    System.out.println("The password is wrong");
                }
            } else {
                System.out.println("The employee ID is not an administrator");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if(rs != null) rs.close(); } catch(Exception e) {}
            try { if(pstmt != null) pstmt.close(); } catch(Exception e) {}
            try { if(conn != null) conn.close(); } catch(Exception e) {}
        }
    }
}
