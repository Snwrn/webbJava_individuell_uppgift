package webb_kurs.individuell_uppgift.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
//import webb_kurs.individuell_uppgift.folder.Folder;

import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    private UUID id = UUID.randomUUID();

    private String username;

    @JsonIgnore
    private String password;
/*
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Folder> folders;
*/
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

