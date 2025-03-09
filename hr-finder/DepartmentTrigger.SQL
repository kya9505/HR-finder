-
DELIMITER $$
CREATE TRIGGER trg_departments_manager_update
    AFTER UPDATE ON departments
    FOR EACH ROW
BEGIN
    IF NEW.manager_id <> OLD.manager_id THEN
    UPDATE employees
    SET manager_id = NEW.manager_id
    WHERE department_id = NEW.department_id;
END IF;
END $$
DELIMITER ;

--외래키 삭제
alter table employees drop foreign key employees_ibfk_1;
alter table employees drop foreign key employees_ibfk_2;
alter table employees drop foreign key employees_ibfk_3;


--키 확인하는 법
SELECT
    CONSTRAINT_NAME,
    TABLE_NAME,
    COLUMN_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = 'hr'
  AND TABLE_NAME = 'employees'
  AND REFERENCED_TABLE_NAME IS NOT NULL;

