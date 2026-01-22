package webb_kurs.individuell_uppgift.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webb_kurs.individuell_uppgift.dtos.CreateUserRequest;
import webb_kurs.individuell_uppgift.dtos.ErrorResponse;
import webb_kurs.individuell_uppgift.dtos.UserResponse;
import webb_kurs.individuell_uppgift.exeptions.IllegalPasswordException;
import webb_kurs.individuell_uppgift.exeptions.IllegalUsernameException;
import webb_kurs.individuell_uppgift.exeptions.UserAlreadyExistsException;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        try {
            var user = userService.createUser(request.getUsername(), request.getPassword());
            return ResponseEntity.created(URI.create("/user")).body(UserResponse.fromModel(user));
        } catch (IllegalPasswordException exeption) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of (
                            "message", "Invalid password",
                            "errors", exeption.getErrors()
                    ));
        }catch (IllegalUsernameException ignored) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Invalid username, must have at least 5 characters"));
        } catch (UserAlreadyExistsException ignored) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("That username is taken"));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("Unexpected error"));
        }
    }
}
