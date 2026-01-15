package webb_kurs.individuell_uppgift.folder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {

    private FolderRepository repository;

    @Autowired
    public FolderService(FolderRepository repository){
        this.repository=repository;
    }
    public void createFolder(String folderName) {
        if (folderName == null || folderName.isBlank()) {
            throw new IllegalArgumentException("The title may not be blank.");
        }
    this.repository.saveFolder(folderName);
    }

    public List<String> getFolders(){
        return this.repository.getFolders();
    }
}
