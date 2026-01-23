package webb_kurs.individuell_uppgift.folder;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
//import webb_kurs.individuell_uppgift.file.FileInstance;
import webb_kurs.individuell_uppgift.user.User;

import java.util.UUID;

@Entity(name = "folders")
@Getter
@Setter
@NoArgsConstructor
public class Folder {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String title;

    /*
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "folder_id")
    private List<FileInstance> files;
*/
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Folder(String title, User user) {
        this.title = title;
        this.user = user;
    }

}