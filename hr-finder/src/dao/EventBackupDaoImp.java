package dao;

import dto.EventBackup;
import config.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventBackupDaoImp implements EventBackupDao {
    @Override
    public List<EventBackup> backup() {
        List<EventBackup> backupList = new ArrayList<>();
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            conn = DBConnectionManager.getConnection();
            cs = conn.prepareCall("{call getBackup_procedure()}");
            rs = cs.executeQuery();
            while (rs.next()) {
                EventBackup eventBackup = new EventBackup();
                eventBackup.setBackup_id(rs.getInt("backup_id"));
                eventBackup.setTable_name(rs.getString("table_name"));
                eventBackup.setEvent_type(rs.getString("event_type"));
                eventBackup.setBefore_change(rs.getString("before_change"));
                eventBackup.setAfter_change(rs.getString("after_change"));
                eventBackup.setEvent_time(rs.getDate("event_time"));
                backupList.add(eventBackup);
            }
            for (EventBackup eventBackup : backupList) {
                System.out.println(eventBackup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnectionManager.closeQuietly(rs, cs, conn);
        }
        return backupList;
    }
}