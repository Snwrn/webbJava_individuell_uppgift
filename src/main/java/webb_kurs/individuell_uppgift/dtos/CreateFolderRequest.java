package webb_kurs.individuell_uppgift.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateFolderRequest {
    private String title;
    private UUID userId;
}


