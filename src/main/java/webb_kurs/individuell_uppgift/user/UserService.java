package webb_kurs.individuell_uppgift.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webb_kurs.individuell_uppgift.exeptions.*;

import java.util.ArrayList;

/**
 * Service class responsible for user-related logic.
 *
 * <p>This service handles user registration, including validation
 * of usernames and passwords, prevention of duplicate users,
 * and secure password hashing.</p>
 */

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new user account after validating username and password.
     *
     * <p>The method validates the following:
     * <ul>
     *     <li>Username must not be blank and must contain at least 4 characters</li>
     *     <li>Password must contain at least 8 characters</li>
     *     <li>Password must contain at least one uppercase letter</li>
     *     <li>Password must contain at least one numeric digit</li>
     *     <li>Username must not already exist in the system</li>
     * </ul>
     * If validation fails, an exception is thrown.</p>
     *
     * <p>If all validations pass, the password is securely hashed
     * using PasswordEncoder before the user is saved.</p>
     *
     * @param username the username for the new user
     * @param password the unhashed password
     * @return the UserModel
     * @throws IllegalUsernameException if the username is invalid
     * @throws IllegalPasswordException if the password does not meet the requirements
     * @throws UserAlreadyExistsException if a user with the same username already exists
     */
    @Override
    public UserModel createUser(String username, String password) throws CreateUserException {

        if (username.isBlank() || username.length() < 4) {
            throw new IllegalUsernameException();
        }

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

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        UserModel user = new UserModel(username, passwordEncoder.encode(password));
        return userRepository.save(user);
    }
}
