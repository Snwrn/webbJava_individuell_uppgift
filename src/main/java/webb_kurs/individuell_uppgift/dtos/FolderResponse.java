package webb_kurs.individuell_uppgift.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import webb_kurs.individuell_uppgift.folder.Folder;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FolderResponse {
    private UUID id;
    private String title;
    String username;

    public static FolderResponse from(Folder folder) {
        return new FolderResponse(
                folder.getId(),
                folder.getTitle(),
                folder.getUser().getUsername()
        );
    }
}
