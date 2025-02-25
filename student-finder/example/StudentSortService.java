package example;

import java.util.List;

/**
 * StudentSortService 인터페이스는 학생 정보를 정렬하는 기능을 정의합니다.
 */
public interface StudentSortService {
    void sortStudents();
    void sortByTotal(List<Student> students);
    void sortBySno(List<Student> students);
}
