package dto;

import lombok.Data;

import java.util.Date;
@Data
public class EventBackup {
    private int backup_id;
    private  String table_name;
    private String event_type;
    private String before_change;
    private String after_change;
    private Date event_time;

}