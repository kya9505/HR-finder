package dto;

import java.sql.Date;

public class EventBackup {
    private int backup_id;
    private String table_name;
    private String event_type;
    private String before_change;
    private String after_change;
    private Date event_time;

    public int getBackup_id() {
        return backup_id;
    }

    public void setBackup_id(int backup_id) {
        this.backup_id = backup_id;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getBefore_change() {
        return before_change;
    }

    public void setBefore_change(String before_change) {
        this.before_change = before_change;
    }

    public String getAfter_change() {
        return after_change;
    }

    public void setAfter_change(String after_change) {
        this.after_change = after_change;
    }

    public Date getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Date event_time) {
        this.event_time = event_time;
    }

    @Override
    public String toString() {
        return " backup_id : " + backup_id +
                " | table_name :'" + table_name +
                " | event_type : '" + event_type +
                " | event_time : " + event_time +
                " \nbefore_change :'" + before_change +
                " \nafter_change :'" + after_change;
    }
}