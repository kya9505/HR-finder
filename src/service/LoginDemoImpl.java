package service;

import util.utildemo;
import java.sql.*;
import java.util.Scanner;

public class LoginDemoImpl {

    public static boolean login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your employee ID : ");
        String employeeId = scanner.nextLine();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isAdmin = false;

        try {
            conn = utildemo.getConnection();
            String sql = "SELECT DISTINCT e1.employee_id " +
                    "FROM employees e1 " +
                    "JOIN employees e2 ON e1.employee_id = e2.manager_id " +
                    "WHERE e1.employee_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, employeeId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.print("Please enter your Password : ");
                String inputPassword = scanner.nextLine();
                if ("1234".equals(inputPassword)) {
                    System.out.println("Administrator login successful");
                    isAdmin = true;
                } else {
                    System.out.println("The password is wrong");
                }
            } else {
                System.out.println("The employee ID is not an Administrator");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch(Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch(Exception e) {}
            try { if (conn != null) conn.close(); } catch(Exception e) {}
        }
        return isAdmin;
    }
}