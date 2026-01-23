package webb_kurs.individuell_uppgift.folder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webb_kurs.individuell_uppgift.dtos.CreateFolderRequest;
import webb_kurs.individuell_uppgift.dtos.FolderResponse;

import java.util.List;

@RestController
@RequestMapping("/folders")
public class FolderController {

    public FolderService service;

    @Autowired
    public FolderController(FolderService service) {
        this.service=service;
    }

    @PostMapping
    public FolderResponse createFolder(@RequestBody CreateFolderRequest request) {
        Folder folder = service.createFolder(
                request.getUserId(),
                request.getTitle()
        );
        return FolderResponse.from(folder);
    }

    @GetMapping
    public List<Folder> getFolders() {
        return this.getFolders();
    }
}