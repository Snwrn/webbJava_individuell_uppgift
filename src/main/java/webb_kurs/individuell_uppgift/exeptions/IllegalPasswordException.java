package webb_kurs.individuell_uppgift.exeptions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class IllegalPasswordException extends CreateUserException {

    private List<PasswordError> errors;

    public IllegalPasswordException(ArrayList<PasswordError> errors) {
        this.errors = errors;
    }

}
