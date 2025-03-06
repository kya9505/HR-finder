-- departmentsInsertTrg
DELIMITER //
CREATE TRIGGER departmentsInsertTrg
    AFTER INSERT ON departments
    FOR EACH ROW
BEGIN
    DECLARE beforeJson JSON;
    DECLARE afterJson JSON;
    SET beforeJson = NULL; -- INSERT이므로 이전 값 없음
    SET afterJson = JSON_OBJECT(
            'department_id', NEW.department_id,
            'department_name', NEW.department_name,
            'manager_id', NEW.manager_id,
            'location_id', NEW.location_id
                    );

    INSERT INTO eventBackupTbl (table_name, event_type, before_change, after_change)
    VALUES ('departments', 'INSERT', beforeJson, afterJson);
END;
//
DELIMITER ;

-- departmentsDeleteTrg
DELIMITER //
CREATE TRIGGER departmentsDeleteTrg
    AFTER DELETE ON departments
    FOR EACH ROW
BEGIN
    DECLARE beforeJson JSON;
    DECLARE afterJson JSON;
    SET beforeJson = JSON_OBJECT(
            'department_id', OLD.department_id,
            'department_name', OLD.department_name,
            'manager_id', OLD.manager_id,
            'location_id', OLD.location_id
                     );
    SET afterJson = NULL; -- DELETE이므로 이후 값 없음

    INSERT INTO eventBackupTbl (table_name, event_type, before_change, after_change)
    VALUES ('departments', 'DELETE', beforeJson, afterJson);
END;
//
DELIMITER ;

-- departmentsUpdateTrg
DELIMITER //
CREATE TRIGGER departmentsUpdateTrg
    BEFORE UPDATE ON departments
    FOR EACH ROW
BEGIN
    DECLARE beforeJson JSON;
    DECLARE afterJson JSON;
    SET beforeJson = JSON_OBJECT('department_id', OLD.department_id);
    SET afterJson = JSON_OBJECT('department_id', NEW.department_id);

    IF OLD.department_name <> NEW.department_name THEN
        SET beforeJson = JSON_INSERT(beforeJson, '$.department_name', OLD.department_name);
        SET afterJson = JSON_INSERT(afterJson, '$.department_name', NEW.department_name);
END IF;

IF OLD.manager_id <> NEW.manager_id THEN
        SET beforeJson = JSON_INSERT(beforeJson, '$.manager_id', OLD.manager_id);
        SET afterJson = JSON_INSERT(afterJson, '$.manager_id', NEW.manager_id);
END IF;

    IF OLD.location_id <> NEW.location_id THEN
        SET beforeJson = JSON_INSERT(beforeJson, '$.location_id', OLD.location_id);
        SET afterJson = JSON_INSERT(afterJson, '$.location_id', NEW.location_id);
END IF;

INSERT INTO eventBackupTbl (table_name, event_type, before_change, after_change)
VALUES ('departments', 'UPDATE', beforeJson, afterJson);
END;
//
DELIMITER ;