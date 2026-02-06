package webb_kurs.individuell_uppgift.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webb_kurs.individuell_uppgift.exeptions.*;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserModel createUser(String username, String password) throws CreateUserException {
        //check if username is empty or too short
        if (username.isBlank() || username.length() < 4) {
            throw new IllegalUsernameException();
        }

        //add list of possible password errors
        var passwordErrors = new ArrayList<PasswordError>();
        if (password.isBlank() || password.length() < 8) {
            passwordErrors.add(PasswordError.REQUIRES_MORE_THAN_EIGHT_CHARACTERS);
        }

        if (!password.matches(".*[A-Z].*")) {
            passwordErrors.add(PasswordError.REQUIRES_ONE_UPPERCASE);
        }

        if (!password.matches(".*\\d.*")) {
            passwordErrors.add(PasswordError.REQUIRES_ONE_NUMBER);
        }

        if (!passwordErrors.isEmpty()) {
            throw new IllegalPasswordException(passwordErrors);
        }

        //check if user exists already
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        //Hash password and create user
        UserModel user = new UserModel(username, passwordEncoder.encode(password));
        return userRepository.save(user);
    }
}
