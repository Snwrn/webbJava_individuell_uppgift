package webb_kurs.individuell_uppgift.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException exception) {
        Map<String, String> body = new HashMap<>();
        body.put("error", exception.getMessage() != null ? exception.getMessage() : "Invalid argument");
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException exception) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "Access denied");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserExists(UserAlreadyExistsException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "USER_ALREADY_EXISTS");
        body.put("message", ex.getMessage() != null ? ex.getMessage() : "User already exists");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(IllegalUsernameException.class)
    public ResponseEntity<?> handleIllegalUsername(IllegalUsernameException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "USERNAME_SHOULD BE");
        body.put("message", ex.getMessage() != null ? ex.getMessage() : "User already exists");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}