package webb_kurs.individuell_uppgift.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import webb_kurs.individuell_uppgift.folder.Folder;

import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private final UUID id = UUID.randomUUID();

    private String username;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Folder> folders;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

