package webb_kurs.individuell_uppgift.file;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import webb_kurs.individuell_uppgift.folder.Folder;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FileInstance {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(unique = true, nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    private Folder folder;

    public FileInstance(String title, Folder folder) {
        this.title = title;
        this.folder = folder;
    }

}

