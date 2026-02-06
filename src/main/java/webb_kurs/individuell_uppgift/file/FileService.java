package webb_kurs.individuell_uppgift.file;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import webb_kurs.individuell_uppgift.exeptions.CreateFileException;
import webb_kurs.individuell_uppgift.folder.Folder;
import webb_kurs.individuell_uppgift.folder.IFolderRepository;
import webb_kurs.individuell_uppgift.user.UserModel;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final IFileRepository fileRepository;
    private final IFolderRepository folderRepository;

    //I didnt really know how to work with files, one of the ways to solve it seems to be this Multipart thing.
    public FileInstance createFileFromMultipart(
            String title,
            UUID folderId,
            MultipartFile file,
            UserModel user
    ) throws IOException {

        //Find folder by ID
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new CreateFileException("Folder not found"));

        //If folder does not belong to the logged-in user, Deny access
        if (!folder.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not own this folder");
        }

        //If title is empty
        if (title == null || title.isBlank()) {
            throw new CreateFileException("Title cannot be empty");
        }

        FileInstance fileInstance = new FileInstance(
                title,
                folder,
                file.getBytes()
        );
        return fileRepository.save(fileInstance);
    }


    @Transactional(readOnly = true)
    public FileInstance getFileByTitleForUser(String title, UserModel user) {
        FileInstance file = fileRepository.findByTitle(title)
                .orElseThrow(() -> new CreateFileException("File not found with title: " + title));

        //Check if file is owned by the user
        if (!file.getFolder().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not own this file");
        }

        return file;
    }

    public void deleteFileForUser(UUID fileId, UserModel user) {

        FileInstance file = fileRepository.findById(fileId)
                .orElseThrow(() -> new CreateFileException("File not found"));

        //Check if file is owned by the user
        if (!file.getFolder().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not own this file");
        }

        fileRepository.delete(file);
    }
}