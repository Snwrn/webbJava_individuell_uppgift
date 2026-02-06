package webb_kurs.individuell_uppgift.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webb_kurs.individuell_uppgift.dtos.*;
import webb_kurs.individuell_uppgift.exeptions.IllegalPasswordException;
import webb_kurs.individuell_uppgift.exeptions.IllegalUsernameException;
import webb_kurs.individuell_uppgift.exeptions.UserAlreadyExistsException;
import webb_kurs.individuell_uppgift.security.JWTService;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            UserModel user = userService.createUser(request.getUsername(), request.getPassword());
            return ResponseEntity.created(URI.create("/user")).body(UserResponse.fromModel(user));
            //catch if password is wrong and notify in response
        } catch (IllegalPasswordException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "message", "Invalid password",
                            "errors", exception.getErrors()
                    ));

            //catch if username is too short
        } catch (IllegalUsernameException ignored) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Invalid username, must have at least 5 characters"));

            //catch if user with tha username already exists
        } catch (UserAlreadyExistsException ignored) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("That username is taken"));

            //catch anything else
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("Unexpected error"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            UserModel user = (UserModel) authentication.getPrincipal();
            String token = jwtService.generateToken(user.getId());
            return ResponseEntity.ok(new LoginResponse(token, user.getUsername()));
        } catch (Exception exception) {
            return ResponseEntity
                    .status(401)
                    .body(new ErrorResponse("Invalid username or password"));
        }
    }

}