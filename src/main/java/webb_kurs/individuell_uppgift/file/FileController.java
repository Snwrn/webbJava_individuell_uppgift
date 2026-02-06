package webb_kurs.individuell_uppgift.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webb_kurs.individuell_uppgift.dtos.ErrorResponse;
import webb_kurs.individuell_uppgift.dtos.FileInfoResponse;
import webb_kurs.individuell_uppgift.dtos.FileResponse;
import webb_kurs.individuell_uppgift.user.UserModel;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    // Upload file with title, folder id and the file itself through multipart form
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("title") String title,
            @RequestParam("folderId") UUID folderId,
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) throws IOException {
        try {
            //get what user is logged-in
        UserModel user = (UserModel) authentication.getPrincipal();

        //If file title is empty, bad request
            if (title == null || title.isBlank()) {
                return ResponseEntity
                        .badRequest()
                        .body(new ErrorResponse("Title cannot be empty"));
            }

        FileInstance createdFile = fileService.createFileFromMultipart(
                title,
                folderId,
                file,
                user
        );


        return ResponseEntity.ok(FileResponse.from(createdFile));
    } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(exception.getMessage()));
        }
    }


    // Download file with title
    @GetMapping("/download/title/{title}")
    public ResponseEntity<byte[]> downloadFileByTitle(
            @PathVariable String title,
            Authentication authentication
    ) { //get what user is logged-in
        UserModel user = (UserModel) authentication.getPrincipal();

        FileInstance file = fileService.getFileByTitleForUser(title, user);

        //I am not sure what this is, but it works
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getTitle() + "\""
                )
                .body(file.getData());
    }


    //this is to get info of the file without downloading, it is so that the user can find ID of the file if
    //...they want to delete a file, as everything works with title but delete.
    @GetMapping("/info/title/{title}")
    public ResponseEntity<FileInfoResponse> getFileInfoByTitle(
            @PathVariable String title,
            Authentication authentication
    ) {
        UserModel user = (UserModel) authentication.getPrincipal();

        FileInstance file = fileService.getFileByTitleForUser(title, user);

        return ResponseEntity.ok(FileInfoResponse.from(file));
    }

    // Delete file with id. Id is used so it is harder to accidentally delete a wrong file.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        UserModel user = (UserModel) authentication.getPrincipal();

        fileService.deleteFileForUser(id, user);

        return ResponseEntity.noContent().build();
    }
}