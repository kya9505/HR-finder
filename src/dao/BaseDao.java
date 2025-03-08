package dao;

import config.DBConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDao {
    protected Connection getConnection() throws SQLException {
        return DBConnectionManager.getConnection();
    }
}