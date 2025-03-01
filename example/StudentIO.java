package example;

import java.util.List;

/**
 * StudentIO 인터페이스는 학생 정보를 입력, 출력, 검색, 정렬 및 저장, 삭제 기능을 포함합니다.
 */
public interface StudentIO extends StudentInput, SearchStudent, SortedStudent {
    void save(Student student);
    List<Student> getAllStudents();
    void delete(String sno);
}
