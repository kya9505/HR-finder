package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentDAO 클래스는 데이터베이스와의 연동을 통해 학생 정보를 저장, 조회, 수정, 삭제하는 기능을 제공합니다.
 */
public class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/Test";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void save(Student student) {
        int total = student.getTotal();
        double average = student.getAverage();
        String grade = student.computeGrade();
        int korean = getSubjectScore(student, "korean");
        int english = getSubjectScore(student, "english");
        int math = getSubjectScore(student, "math");
        int science = getSubjectScore(student, "science");

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO STUDENT (sno, name, korean, english, math, science, total, average, grade) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, student.getSno());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, korean);
            pstmt.setInt(4, english);
            pstmt.setInt(5, math);
            pstmt.setInt(6, science);
            pstmt.setInt(7, total);
            pstmt.setDouble(8, average);
            pstmt.setString(9, grade);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Student findStudentBySno(String sno) {
        Student student = null;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM STUDENT WHERE sno = ?")) {
            pstmt.setString(1, sno);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    int korean = rs.getInt("korean");
                    int english = rs.getInt("english");
                    int math = rs.getInt("math");
                    int science = rs.getInt("science");

                    student = new Student.StudentBuilder()
                            .sno(sno)
                            .name(name)
                            .addSubject("korean", korean)
                            .addSubject("english", english)
                            .addSubject("math", math)
                            .addSubject("science", science)
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    public void delete(String sno) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM STUDENT WHERE sno = ?")) {
            pstmt.setString(1, sno);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM STUDENT");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String sno = rs.getString("sno");
                String name = rs.getString("name");
                int korean = rs.getInt("korean");
                int english = rs.getInt("english");
                int math = rs.getInt("math");
                int science = rs.getInt("science");

                Student student = new Student.StudentBuilder()
                        .sno(sno)
                        .name(name)
                        .addSubject("korean", korean)
                        .addSubject("english", english)
                        .addSubject("math", math)
                        .addSubject("science", science)
                        .build();
                studentList.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentList;
    }

    public void updateStudent(Student student) {
        int total = student.getTotal();
        double average = student.getAverage();
        String grade = student.computeGrade();
        int korean = getSubjectScore(student, "korean");
        int english = getSubjectScore(student, "english");
        int math = getSubjectScore(student, "math");
        int science = getSubjectScore(student, "science");

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE STUDENT SET name=?, korean=?, english=?, math=?, science=?, total=?, average=?, grade=? WHERE sno = ?")) {
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, korean);
            pstmt.setInt(3, english);
            pstmt.setInt(4, math);
            pstmt.setInt(5, science);
            pstmt.setInt(6, total);
            pstmt.setDouble(7, average);
            pstmt.setString(8, grade);
            pstmt.setString(9, student.getSno());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStudentScores(Student student) {
        int total = student.getTotal();
        double average = student.getAverage();
        String grade = student.computeGrade();
        int korean = getSubjectScore(student, "korean");
        int english = getSubjectScore(student, "english");
        int math = getSubjectScore(student, "math");
        int science = getSubjectScore(student, "science");

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE STUDENT SET korean=?, english=?, math=?, science=?, total=?, average=?, grade=? WHERE sno = ?")) {
            pstmt.setInt(1, korean);
            pstmt.setInt(2, english);
            pstmt.setInt(3, math);
            pstmt.setInt(4, science);
            pstmt.setInt(5, total);
            pstmt.setDouble(6, average);
            pstmt.setString(7, grade);
            pstmt.setString(8, student.getSno());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getSubjectScore(Student student, String subjectName) {
        return student.getSubjects().stream()
                .filter(subject -> subject.getName().equalsIgnoreCase(subjectName))
                .mapToInt(Student.Subject::getScore)
                .findFirst()
                .orElse(0);
    }
}
