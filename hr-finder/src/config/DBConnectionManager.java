package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DBConnectionManager {
    private static ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("config.dbinfo");
        try {
            Class.forName(bundle.getString("driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                bundle.getString("url"),
                bundle.getString("user"),
                bundle.getString("password"));
    }

    public static void closeQuietly(ResultSet rs, Statement stmt, Connection conn) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { /* 로깅 가능 */ }
        try { if (stmt != null) stmt.close(); } catch (SQLException e) { /* 로깅 가능 */ }
        try { if (conn != null) conn.close(); } catch (SQLException e) { /* 로깅 가능 */ }
    }
}