package webb_kurs.individuell_uppgift.folder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FolderController {

    public FolderService service;

    @Autowired
    public FolderController(FolderService service) {
        this.service=service;
    }

    @PostMapping("/folders")
    public String createFolder(@RequestBody String folderName) {
this.service.createFolder(folderName);
return "Good!";
    }

    @GetMapping("/folders")
    public List<String> getFolders() {
        return this.service.getFolders();
    }
}
