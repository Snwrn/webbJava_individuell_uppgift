package webb_kurs.individuell_uppgift.exeptions;

public class CreateFolderException extends RuntimeException {

    public CreateFolderException() {}

    public CreateFolderException(String message) {
        super(message);
    }
}