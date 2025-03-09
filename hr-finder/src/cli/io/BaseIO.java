package cli.io;

import util.validation.ConsoleInputValidator;
import java.util.Scanner;

public abstract class BaseIO {
    protected final ConsoleInputValidator validator;
    protected final Scanner scanner;

    public BaseIO() {
        this.validator = new ConsoleInputValidator();
        this.scanner = new Scanner(System.in);
    }
}