package webb_kurs.individuell_uppgift.file;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import webb_kurs.individuell_uppgift.folder.Folder;

import java.util.UUID;
@Entity(name = "files")
@Getter
@Setter
@NoArgsConstructor
public class FileInstance {

    //I had issues with files, the id seemed to be the problem.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    private Folder folder;

    //I had problems with uploading files, abd BYTEA is the only thing that worked
    @Column(columnDefinition = "BYTEA")
    private byte[] data;


    public FileInstance(String title, Folder folder, byte[] data) {
        this.title = title;
        this.folder = folder;
        this.data = data;
    }

}



