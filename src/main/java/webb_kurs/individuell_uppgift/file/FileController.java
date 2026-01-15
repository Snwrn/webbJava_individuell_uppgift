package webb_kurs.individuell_uppgift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import webb_kurs.individuell_uppgift.file.FileService;

import java.util.List;


public FileService service;

@Autowired
public FileController(FileService service) {
    this.service=service;
}

@PostMapping("/files")
public String createFile(@RequestBody String fileName) {
    this.service.createFile(fileName);
    return "File created!";
}

@GetMapping("/files")
public List<String> getFiles() {
    return this.service.getFiles();
}
