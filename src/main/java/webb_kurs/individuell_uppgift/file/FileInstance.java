package webb_kurs.individuell_uppgift.file;
/*
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import webb_kurs.individuell_uppgift.folder.Folder;

import java.util.UUID;
@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class FileInstance {

    @Id
    @GeneratedValue
    private UUID id = UUID.randomUUID();

    @Column(unique = true, nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    private Folder folder;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] data;


    public FileInstance(String title, Folder folder, byte[] data) {
        this.title = title;
        this.folder = folder;
        this.data = data;
    }

}

*/