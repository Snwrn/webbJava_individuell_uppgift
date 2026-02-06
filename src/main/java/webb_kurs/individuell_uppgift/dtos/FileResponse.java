package webb_kurs.individuell_uppgift.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webb_kurs.individuell_uppgift.file.FileInstance;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
//To show file info
public class FileResponse {
    private UUID id;
    private String title;
    private UUID folderId;
    private long fileSize;

    public static FileResponse from(FileInstance file) {
        return new FileResponse(
                file.getId(),
                file.getTitle(),
                file.getFolder().getId(),
                file.getData() != null ? file.getData().length : 0
        );
    }
}
