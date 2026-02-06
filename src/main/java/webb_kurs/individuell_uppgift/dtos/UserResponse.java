package webb_kurs.individuell_uppgift.dtos;

import lombok.Getter;
import lombok.Setter;
import webb_kurs.individuell_uppgift.user.UserModel;

import java.util.UUID;

//response to remove password from the response
@Getter
@Setter
public class UserResponse {

    private final UUID id;
    private String username;

    public UserResponse(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserResponse fromModel(UserModel user) {
        return new UserResponse(
                user.getId(),
                user.getUsername()
        );
    }
}