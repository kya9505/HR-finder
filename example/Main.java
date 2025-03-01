package example;

import java.util.Scanner;

/**
 * Main 클래스는 애플리케이션의 진입점입니다.
 * StudentManager 객체를 생성하고 실행합니다.
 */
public class Main {
    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager(studentDAO, scanner);
        manager.run();
    }
}
