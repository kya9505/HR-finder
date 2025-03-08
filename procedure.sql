DELIMITER //
CREATE PROCEDURE getBackup_procedure()
BEGIN
SELECT
    backup_id,
    table_name,
    event_type,
    JSON_PRETTY(before_change) AS before_change,
    JSON_PRETTY(after_change) AS after_change,
    event_time
FROM eventBackupTbl;
END //
DELIMITER ;