package webb_kurs.individuell_uppgift.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webb_kurs.individuell_uppgift.dtos.CreateFolderRequest;
import webb_kurs.individuell_uppgift.dtos.ErrorResponse;
import webb_kurs.individuell_uppgift.dtos.FolderResponse;
import webb_kurs.individuell_uppgift.exeptions.FolderAlreadyExistsException;
import webb_kurs.individuell_uppgift.user.UserModel;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService service;

    @PostMapping
    public ResponseEntity<?> createFolder(
            @RequestBody CreateFolderRequest request,
            Authentication authentication
    ) {
        try {
            //get logged-in user
            UserModel user = (UserModel) authentication.getPrincipal();

            //check if title in the request body is not empty
            if (request.getTitle() == null || request.getTitle().isBlank()) {
                return ResponseEntity
                        .badRequest()
                        .body(new ErrorResponse("Folder title cannot be empty"));
            }

            //check if the title is too short or too long
            if (request.getTitle().length() <= 3 || request.getTitle().length() >= 30) {
                return ResponseEntity
                        .badRequest()
                        .body(new ErrorResponse("Folder title should be between 3 and 30 characters long"));
            }

            Folder folder = service.createFolder(user.getId(), request.getTitle());
            return ResponseEntity.ok(FolderResponse.from(folder));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(exception.getMessage()));
        }
        //check if folder with the similar name exists
        catch (FolderAlreadyExistsException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Folder with this name already exists"));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("Unexpected error"));
        }
    }


    //Get folders for the logged-in user
    @GetMapping("/myFolders")
    public ResponseEntity<?> getMyFolders(Authentication authentication) {
        try {
            UserModel user = (UserModel) authentication.getPrincipal();

            List<Folder> folders = service.getFoldersForUser(user);

            //Folder response to showfolder info and files inside it
            List<FolderResponse> response = folders.stream()
                    .map(FolderResponse::from)
                    .toList();

            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("Unexpected error"));
        }
    }

    //delete folder by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFolder(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        //checks to see if user deletes their own folders
        try {
            UserModel user = (UserModel) authentication.getPrincipal();
            service.deleteFolder(id, user);
            return ResponseEntity.noContent().build();
        }

        //catch if the id not found
          catch (IllegalArgumentException exception) {
            return ResponseEntity
                    .status(404)
                    .body(new ErrorResponse("Folder not found"));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("Unexpected error"));
        }
    }
}