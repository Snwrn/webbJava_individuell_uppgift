package webb_kurs.individuell_uppgift.dtos;

import lombok.Getter;
import lombok.Setter;
import webb_kurs.individuell_uppgift.user.User;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class UserResponse {

    private final UUID id;
    private String username;

    public UserResponse(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserResponse fromModel(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername()
        );
    }
}