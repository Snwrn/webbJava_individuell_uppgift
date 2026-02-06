package webb_kurs.individuell_uppgift.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webb_kurs.individuell_uppgift.file.FileInstance;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoResponse {
    private UUID id;
    private String title;
    private UUID folderId;
    private String folderTitle;
    private long fileSizeBytes;

    public static FileInfoResponse from(FileInstance file) {
        return new FileInfoResponse(
                file.getId(),
                file.getTitle(),
                file.getFolder().getId(),
                file.getFolder().getTitle(),
                file.getData() != null ? file.getData().length : 0
        );
    }
}