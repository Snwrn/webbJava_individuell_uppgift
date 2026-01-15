package webb_kurs.individuell_uppgift.file;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FileService {

    private FileRepository repository;

    @Autowired
    public FileService(FileRepository repository){
        this.repository=repository;
    }
    public void createFile(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("The title may not be blank.");
        }
        this.repository.saveFile(fileName);
    }

    public List<String> getFiles(){
        return this.repository.getFiles();
    }
}
