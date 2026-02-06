package webb_kurs.individuell_uppgift.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webb_kurs.individuell_uppgift.folder.Folder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
//This is needed to display folder and the files inside it
public class FolderResponse {
    private UUID id;
    private String title;
    private List<String> fileTitles;

    public static FolderResponse from(Folder folder) {
        // Get all file titles from the folder
        List<String> fileTitles = folder.getFiles()
                .stream()
                .map(file -> file.getTitle())
                .collect(Collectors.toList());

        return new FolderResponse(
                folder.getId(),
                folder.getTitle(),
                fileTitles
        );
    }
}