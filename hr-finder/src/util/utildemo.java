package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class utildemo {
    private static ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("config.dbinfo");
        try {
            Class.forName(bundle.getString("driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    bundle.getString("url"),
                    bundle.getString("user"),
                    bundle.getString("password"));
        } catch (SQLException e) {
            System.out.println("연결실패");
            return null;
        }
    }
}