package webb_kurs.individuell_uppgift.folder;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FolderRepository {
    private List<String> folders = new ArrayList<>();

    public void saveFolder(String folderName){
this.folders.add(folderName);
System.out.println("Saved: " + folderName);
    }

    public List<String> getFolders() {
        return folders;
    }
}
