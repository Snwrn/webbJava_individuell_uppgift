package webb_kurs.individuell_uppgift.exeptions;

public class CreateFileException extends RuntimeException {

    public CreateFileException() {}

    public CreateFileException(String message) {
        super(message);
    }
}