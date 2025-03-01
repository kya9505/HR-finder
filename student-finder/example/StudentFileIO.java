package example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentFileIO 클래스는 파일 기반의 학생 I/O 기능을 구현합니다.
 * 단일 인스턴스 패턴(Singleton)을 사용하여 인스턴스를 관리합니다.
 */
public class StudentFileIO extends StudentDBIO {
    private static final StudentFileIO INSTANCE = new StudentFileIO();
    private static final String FILE_PATH = "students_backup.csv";

    private StudentFileIO() {}

    public static StudentFileIO getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String csvLine = String.format("%s,%s,%d,%d,%d,%d,%d,%.2f,%s",
                    student.getSno(),
                    student.getName(),
                    getSubjectScore(student, "korean"),
                    getSubjectScore(student, "english"),
                    getSubjectScore(student, "math"),
                    getSubjectScore(student, "science"),
                    student.getTotal(),
                    student.getAverage(),
                    student.computeGrade());
            writer.write(csvLine);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 6) {
                    String sno = tokens[0];
                    String name = tokens[1];
                    int korean = Integer.parseInt(tokens[2]);
                    int english = Integer.parseInt(tokens[3]);
                    int math = Integer.parseInt(tokens[4]);
                    int science = Integer.parseInt(tokens[5]);
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    @Override
    public void delete(String sno) {
        List<Student> studentList = getAllStudents();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student s : studentList) {
                if (!s.getSno().equals(sno)) {
                    String csvLine = String.format("%s,%s,%d,%d,%d,%d,%d,%.2f,%s",
                            s.getSno(),
                            s.getName(),
                            getSubjectScore(s, "korean"),
                            getSubjectScore(s, "english"),
                            getSubjectScore(s, "math"),
                            getSubjectScore(s, "science"),
                            s.getTotal(),
                            s.getAverage(),
                            s.computeGrade());
                    writer.write(csvLine);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
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
