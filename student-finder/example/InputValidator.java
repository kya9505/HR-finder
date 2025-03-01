package example;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * InputValidator 인터페이스는 사용자 입력 검증 기능을 제네릭 메서드로 제공합니다.
 */
public interface InputValidator {
    <T> T readValidated(String prompt, Function<String, T> parser, Predicate<T> validator, String errorMessage);
}
