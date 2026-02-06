package webb_kurs.individuell_uppgift.folder;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import webb_kurs.individuell_uppgift.file.FileInstance;
import webb_kurs.individuell_uppgift.user.UserModel;

import java.util.ArrayList;
import java.util.List;
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

    //connect files to folders
    @OneToMany(mappedBy = "folder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FileInstance> files = new ArrayList<>();

    //connect user to folders
    @ManyToOne(fetch = FetchType.EAGER)
    private UserModel user;

    public Folder(String title, UserModel user) {
        this.title = title;
        this.user = user;
    }

}