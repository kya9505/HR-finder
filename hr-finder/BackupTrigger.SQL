use hrdb;
drop table if exists eventBackupTbl;
create table eventBackupTbl(
                               backup_id int AUTO_INCREMENT PRIMARY KEY,
                               table_name varchar(255) not null,
                               event_type varchar(30),
                               before_change JSON ,
                               after_change JSON ,
                               event_time datetime default now()
);
--
-- employeeInsertTrg
delimiter //
create trigger employeeInsertTrg
    AFTER insert
    on employees
    for each row
    begin
        declare beforeJson json;
        declare afterJson json;
        set beforeJson = null; -- insert이므로 이전 값이 존재하지 않음
        set afterJson = JSON_OBJECT('employee_id',new.employee_id,
                                    'first_name',new.first_name,
                                    'last_name',NEW.last_name,
                                    'job_id',new.job_id,
                                    'department_id',NEW.department_id
        );

        insert into eventBackupTbl (table_name,event_type, before_change, after_change)
            values ('employees', 'INSERT', beforeJson , afterJson);
end //
delimiter ;

-- employeeDeleteTrg
delimiter //
create trigger employeeDeleteTrg
    AFTER delete
    on employees
    for each row
    begin
        declare beforeJson json;
        declare afterJson json;
        set beforeJson = JSON_OBJECT('employee_id',OLD.employee_id,
                                    'first_name',OLD.first_name,
                                    'last_name',OLD.last_name,
                                    'job_id',OLD.job_id,
                                    'department_id',OLD.department_id
        );
        set afterJson = null; -- delete이므로 새로운 값이 존재하지 않음

        insert into eventBackupTbl (table_name,event_type, before_change, after_change)
            values ('employees', 'Delete', beforeJson , afterJson);
end //
delimiter ;


-- employeeUpdateTrg
DELIMITER //
CREATE TRIGGER employeeUpdateTrg
    BEFORE UPDATE ON employees
    FOR EACH ROW
BEGIN
    DECLARE beforeJson JSON;
    DECLARE afterJson JSON;

    -- ID와 이름은 기본 포함
    SET beforeJson = JSON_OBJECT('employee_id', old.employee_id, 'last_name', old.last_name, 'first_name', old.first_name);
    SET afterJson = JSON_OBJECT('employee_id', new.employee_id, 'last_name', new.last_name, 'first_name', new.first_name);

    -- 변경된 필드만 자동 추가 (변경이 있을 경우에만 추가)
    IF OLD.email <> NEW.email THEN
        SET beforeJson = JSON_INSERT(beforeJson, '$.email', OLD.email);
        SET afterJson = JSON_INSERT(afterJson, '$.email', NEW.email);
    END IF;

    IF OLD.phone_number <> NEW.phone_number THEN
        SET beforeJson = JSON_INSERT(beforeJson, '$.phone_number', OLD.phone_number);
        SET afterJson = JSON_INSERT(afterJson, '$.phone_number', NEW.phone_number);
    END IF;

    IF OLD.hire_date <> NEW.hire_date THEN
        SET beforeJson = JSON_INSERT(beforeJson, '$.hire_date', OLD.hire_date);
        SET afterJson = JSON_INSERT(afterJson, '$.hire_date', NEW.hire_date);
    END IF;

    IF OLD.job_id <> NEW.job_id THEN
        SET beforeJson = JSON_INSERT(beforeJson, '$.job_id', OLD.job_id);
        SET afterJson = JSON_INSERT(afterJson, '$.job_id', NEW.job_id);
    END IF;

    IF OLD.salary <> NEW.salary THEN
        SET beforeJson = JSON_INSERT(beforeJson, '$.salary', OLD.salary);
        SET afterJson = JSON_INSERT(afterJson, '$.salary', NEW.salary);
    END IF;

    IF OLD.commission_pct <> NEW.commission_pct THEN
        SET beforeJson = JSON_INSERT(beforeJson, '$.commission_pct', OLD.commission_pct);
        SET afterJson = JSON_INSERT(afterJson, '$.commission_pct', NEW.commission_pct);
    END IF;

    IF OLD.manager_id <> NEW.manager_id THEN
        SET beforeJson = JSON_INSERT(beforeJson, '$.manager_id', OLD.manager_id);
        SET afterJson = JSON_INSERT(afterJson, '$.manager_id', NEW.manager_id);
    END IF;

    IF OLD.department_id <> NEW.department_id THEN
        SET beforeJson = JSON_INSERT(beforeJson, '$.department_id', OLD.department_id);
        SET afterJson = JSON_INSERT(afterJson, '$.department_id', NEW.department_id);
    END IF;

    -- 변경된 내용만 이벤트 테이블에 저장
    INSERT INTO eventBackupTbl (table_name, event_type, before_change, after_change)
    VALUES ('employees', 'UPDATE', beforeJson, afterJson);

END; //
DELIMITER ;

UPDATE employees
SET email = 'ddd', phone_number = 'ssdq'
WHERE employee_id = 100;  -- 임의의 employee_id (예: 101)

SELECT
    backup_id,
    table_name,
    event_type,
    JSON_PRETTY(before_change) AS before_change,
    JSON_PRETTY(after_change) AS after_change,
    event_time
FROM eventBackupTbl;


