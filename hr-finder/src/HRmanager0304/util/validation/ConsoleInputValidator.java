package HRmanager0303.util.validation;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class ConsoleInputValidator implements Validator{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public <T> T readValidated(String prompt, Function<String, T> parser, Predicate<T> validator, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                T result = parser.apply(input);
                if (validator.test(result)) {
                    return result;
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(errorMessage);
            }
            System.out.println(errorMessage);
        }
    }

    public int readValidatedInt(String prompt, int min, int max, String errorMessage) {
        return readValidated(prompt, Integer::parseInt, i -> i >= min && i<=max , errorMessage);
    }
    // int korean = readValidatedInt("employee_id : ", 0, 10);

    public String readValidatedVarchar(String prompt, int maxLength, String errorMessage) {
        return readValidated(prompt, s -> s, s -> s.length() <= maxLength, errorMessage);
    }
    // String name = readValidatedVarchar("이름 (최대 20자): ", 20, "20자 이하로 입력하세요.");
}
