package example;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentDBIO 클래스는 데이터베이스 기반의 학생 I/O 기능을 구현한 클래스입니다.
 * StudentIO 인터페이스를 구현하며, 기본적인 CRUD 기능을 제공합니다.
 */
public class StudentDBIO extends ObjectIO implements StudentIO {
    protected List<Student> students = new ArrayList<>();

    @Override
    public void sortByTotal(List<Student> students) {}

    @Override
    public void sortBySno(List<Student> students) {}

    @Override
    public void save(Student student) {}

    @Override
    public List<Student> getAllStudents() {
        return List.of();
    }

    @Override
    public void delete(String sno) {}
}
