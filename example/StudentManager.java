package example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * StudentManager 클래스는 학생 정보를 관리하는 메뉴 기반의 애플리케이션을 구현합니다.
 * 이 클래스는 학생 입력, 출력, 검색, 정렬, 백업, 종료 기능을 각 기능별 인터페이스를 구현하여 분리합니다.
 */
public class StudentManager extends StudentDBIO implements StudentInputService, StudentOutputService,
        StudentSearchService, StudentSortService, StudentBackupService, StudentExitService, InputValidator {

    private final Scanner scanner;
    private final StudentDAO studentDAO;
    private static final Pattern SNO_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z가-힣]+$");


    private final Map<Integer, Runnable> menuChoice = new HashMap<>();

    public StudentManager(StudentDAO studentDAO, Scanner scanner) {
        this.studentDAO = studentDAO;
        this.scanner = scanner;
        mainMenu();
    }

    private void mainMenu() {
        menuChoice.put(1, this::inputStudent);
        menuChoice.put(2, this::outputStudent);
        menuChoice.put(3, this::searchBySno);
        menuChoice.put(4, this::sortStudents);
        menuChoice.put(5, this::backupToFile);
        menuChoice.put(6, this::exitApp);
    }

    private void printMenu() {
        System.out.println("1. add student info");
        System.out.println("2. view student info");
        System.out.println("3. search student info");
        System.out.println("4. sort student info");
        System.out.println("5. backup to file");
        System.out.println("6. exit");
        System.out.println("choice menu");
    }

    public void run() {
        while (true) {
            printMenu();
            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                continue;
            }
            Optional.ofNullable(menuChoice.get(choice))
                    .ifPresentOrElse(Runnable::run, () -> System.out.println("재입력"));
        }
    }


    @Override
    public <T> T readValidated(String prompt, Function<String, T> parser, Predicate<T> validator, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                T result = parser.apply(input);
                if (validator.test(result)) {
                    return result;
                } else {
                    System.out.println(errorMessage);
                }
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
            }
        }
    }

    private String readValidatedString(String prompt, Pattern pattern, String errorMessage) {
        return readValidated(prompt, s -> s, s -> pattern.matcher(s).matches(), errorMessage);
    }

    private int readValidatedInt(String prompt, int min, int max) {
        return readValidated(prompt, Integer::parseInt, i -> i >= min && i <= max, min + " ~ " + max);
    }


    private int[] readSubjectScores() {
        int korean = readValidatedInt("korean: ", 0, 100);
        int english = readValidatedInt("english: ", 0, 100);
        int math = readValidatedInt("math: ", 0, 100);
        int science = readValidatedInt("science: ", 0, 100);
        return new int[]{korean, english, math, science};
    }


    private Student createStudent(String sno, String name, int korean, int english, int math, int science) {
        return new Student.StudentBuilder()
                .sno(sno)
                .name(name)
                .addSubject("korean", korean)
                .addSubject("english", english)
                .addSubject("math", math)
                .addSubject("science", science)
                .build();
    }

    @Override
    public void inputStudent() {
        System.out.println("add");
        String sno = readValidatedString("sno (10자리수): ", SNO_PATTERN, "정확히 10자리 수 재입력");

        Student existingStudent = studentDAO.findStudentBySno(sno);
        if (existingStudent == null) {
            String name = readValidatedString("name (한, 영): ", NAME_PATTERN, "한, 영문으로 재입력");
            int[] scores = readSubjectScores();
            Student student = createStudent(sno, name, scores[0], scores[1], scores[2], scores[3]);
            studentDAO.save(student);
            students.add(student);
        } else {
            System.out.println("already regist sno, student name: " + existingStudent.getName());
            System.out.println("1. edit all info");
            System.out.println("2. edit subject score");
            System.out.println("3. exit add");
            String option = scanner.nextLine().trim();
            if ("1".equals(option)) {
                String name = readValidatedString("name (한, 영): ", NAME_PATTERN, "한, 영문으로 재입력");
                int[] scores = readSubjectScores();
                Student student = createStudent(sno, name, scores[0], scores[1], scores[2], scores[3]);
                studentDAO.updateStudent(student);
                updateInMemoryStudent(student);
            } else if ("2".equals(option)) {
                int[] scores = readSubjectScores();
                Student student = createStudent(sno, existingStudent.getName(), scores[0], scores[1], scores[2], scores[3]);
                studentDAO.updateStudentScores(student);
                updateInMemoryStudent(student);
            } else if ("3".equals(option)) {
                System.out.println("exit");
                return;
            } else {
                System.out.println("잘못된 입력");
            }
        }
        System.out.println("success");
    }

    private void updateInMemoryStudent(Student student) {
        students.removeIf(s -> s.getSno().equals(student.getSno()));
        students.add(student);
    }

    @Override
    public void outputStudent() {
        studentDAO.getAllStudents().forEach(System.out::println);
        deleteStudentInfo();
    }

    public void deleteStudentInfo() {
        System.out.print("삭제할 학생의 학번을 입력 (삭제하지 않으려면 그냥 엔터): ");
        String deleteSno = scanner.nextLine().trim();
        if (!deleteSno.isEmpty()) {
            deleteStudent(deleteSno);
        } else {
            System.out.println("삭제 없이 종료합니다");
        }
    }

    private void deleteStudent(String sno) {
        studentDAO.delete(sno);
        students.removeIf(s -> s.getSno().equals(sno));
        System.out.println("삭제 완료");
    }

    @Override
    public void searchBySno() {
        System.out.print("enter (sno 기준검색): ");
        String searchSno = scanner.nextLine().trim();
        Student foundStudent = studentDAO.findStudentBySno(searchSno);
        if (foundStudent == null) {
            System.out.println("no " + searchSno);
        } else {
            System.out.println("result");
            System.out.println(foundStudent);
        }
    }

    @Override
    public void sortStudents() {
        System.out.println("select");
        System.out.println("1. Sort by total score");
        System.out.println("2. Sort by sno");
        String option = scanner.nextLine().trim();
        int sortChoice;
        try {
            sortChoice = Integer.parseInt(option);
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력");
            return;
        }

        List<Student> studentList = studentDAO.getAllStudents();
        if (studentList.isEmpty()) {
            System.out.println("DB에 저장된 학생 데이터가 없습니다.");
            return;
        }

        Map<Integer, Runnable> sortActions = new HashMap<>();
        sortActions.put(1, () -> sortByTotal(studentList));
        sortActions.put(2, () -> sortBySno(studentList));

        Optional.ofNullable(sortActions.get(sortChoice))
                .ifPresentOrElse(Runnable::run, () -> System.out.println("잘못된 입력"));
        System.out.println("Sorted Students:");
        studentList.forEach(System.out::println);
    }

    @Override
    public void sortByTotal(List<Student> studentList) {
        studentList.sort(Comparator.comparingInt(Student::getTotal).reversed());
        System.out.println("Sorted by total score (descending):");
    }

    @Override
    public void sortBySno(List<Student> studentList) {
        studentList.sort(Comparator.comparing(Student::getSno));
        System.out.println("Sorted by sno:");
    }

    @Override
    public void backupToFile() {
        List<Student> studentList = studentDAO.getAllStudents();
        if (studentList.isEmpty()) {
            System.out.println("백업할 데이터가 없습니다.");
            return;
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "students_backup_" + timestamp + ".csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String header = "sno,name,korean,english,math,science,total,average,grade";
            writer.write(header);
            writer.newLine();
            for (Student s : studentList) {
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
            System.out.println("파일 백업 완료: " + fileName);
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

    @Override
    public void exitApp() {
        System.exit(0);
    }
}
