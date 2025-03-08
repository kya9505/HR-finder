package cli.controller;

import java.util.Scanner;

public abstract class BaseController {
    protected final Scanner scanner = new Scanner(System.in);

    // 공통 메뉴 출력 메소드
    protected void printMenu(String title, String... options) {
        System.out.println("========== " + title + " ==========");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    // 공통 헤더 출력 메소드
    protected void printSectionHeader(String title) {
        System.out.println("---------- " + title + " ----------");
    }

    // 공통 입력 읽기 메소드
    protected int readChoice(String prompt) {
        System.out.print(prompt);
        int choice = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 소비
        return choice;
    }

}