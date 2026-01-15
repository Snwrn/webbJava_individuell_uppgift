package webb_kurs.individuell_uppgift.file;

import java.util.ArrayList;
import java.util.List;

public class FileRepository {
    private List<String> files = new ArrayList<>();

    public void saveFile(String fileName){
        this.files.add(fileName);
        System.out.println("Saved: " + fileName);
    }

    public List<String> getFiles() {
        return files;
    }
}
