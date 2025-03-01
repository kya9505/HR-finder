package example;

import java.util.ArrayList;
import java.util.List;

/**
 * Student 클래스는 학생 정보를 표현하며, 빌더 패턴을 사용하여 객체를 생성합니다.
 */
public class Student {
    private String sno;
    private String name;
    private List<Subject> subjects;

    private Student(StudentBuilder builder) {
        this.sno = builder.sno;
        this.name = builder.name;
        this.subjects = builder.subjects;
    }

    public String getSno() {
        return sno;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public int getTotal() {
        return subjects.stream().mapToInt(Subject::getScore).sum();
    }

    public double getAverage() {
        return subjects.isEmpty() ? 0 : (double) getTotal() / subjects.size();
    }

    public String computeGrade() {
        double avg = getAverage();
        if (avg >= 90) return "A";
        else if (avg >= 80) return "B";
        else if (avg >= 70) return "C";
        else if (avg >= 60) return "D";
        else return "F";
    }

    @Override
    public String toString() {
        return "sno='" + sno + "', name='" + name + "', subjects=" + subjects +
                ", total=" + getTotal() + ", average=" + getAverage() + ", grade=" + computeGrade();
    }

    /**
     * Subject 클래스는 학생의 과목 정보(과목명과 점수)를 표현합니다.
     */
    public static class Subject {
        private String name;
        private int score;

        public Subject(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() { return name; }
        public int getScore() { return score; }

        @Override
        public String toString() {
            return name + ":" + score;
        }
    }

    /**
     * StudentBuilder 클래스는 빌더 패턴을 통해 Student 객체를 생성합니다.
     */
    public static class StudentBuilder {
        private String sno;
        private String name;
        private List<Subject> subjects = new ArrayList<>();

        public StudentBuilder sno(String sno) {
            this.sno = sno;
            return this;
        }

        public StudentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StudentBuilder addSubject(String subjectName, int score) {
            subjects.add(new Subject(subjectName, score));
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
