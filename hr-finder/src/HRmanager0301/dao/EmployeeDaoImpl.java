package HRmanager0301.dao;

import HRmanager0301.dto.Employees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.Function;

import static java_advanced.src.Quest.dao.DBUtil.getConnection;

public class EmployeeDaoImpl {

    public <T> Employees findSEmployee(String subchoide , T serchvalue) {

        Function<T, String> converter = v -> (v instanceof String) ? (String) v : String.valueOf(v);
        String strValue = converter.apply(serchvalue); //String으로 변환

        Employees employees = null;
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT ? FROM STUDENT WHERE ? ");
            pstmt.setString(1, subchoide);
            pstmt.setString(2,serchvalue + " = " + serchvalue);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Employees.EmployeesBuilder builder = new Employees.EmployeesBuilder()
                            .employee_id()
                            .first_name()
                            .last_name()
                            .

                    Student.StudentBuilder builder = new Student.StudentBuilder()
                            .sno(sno)
                            .name(name);
                    builder.addSubject("korean", korean);
                    builder.addSubject("english", english);
                    builder.addSubject("math", math);
                    builder.addSubject("science", science);
                    student = builder.build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

}
