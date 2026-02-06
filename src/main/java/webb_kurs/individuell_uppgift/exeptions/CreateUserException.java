package webb_kurs.individuell_uppgift.exeptions;

public class CreateUserException extends RuntimeException {

    public CreateUserException() {}

    public CreateUserException(String message) {
        super(message);
    }

    public CreateUserException(String message, Throwable inner) {
        super(message, inner);
    }
}
