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

/**
 * Service class responsible for handling file-related business logic.
 *
 * <p>This service provides functionality for uploading, retrieving,
 * and deleting files associated with user-owned folders. I did not really know how to work
 * with files, one of the ways to solve it seems to be this Multipart thing.</p>
 */
@Service
@RequiredArgsConstructor
public class FileService {

    private final IFileRepository fileRepository;
    private final IFolderRepository folderRepository;


    /**
     * Creates and stores a file using multipart upload.
     *
     * <p>The method validates that:
     * <ul>
     *     <li>The specified folder containing the file exists</li>
     *     <li>The folder the file is in belongs to the authenticated user</li>
     *     <li>The file title is not null or blank</li>
     * </ul>
     * The uploaded file content is stored as a byte array in the database.</p>
     *
     * @param title the title of the file
     * @param folderId the unique identifier of the folder where the file will be stored
     * @param file the uploaded multipart file containing file data
     * @param user the authenticated user uploading the file
     * @return the saved FileInstance
     * @throws CreateFileException if validation fails or folder does not exist
     * @throws AccessDeniedException if the folder does not belong to the user
     * @throws IOException if an error occurs while reading file bytes
     */
    public FileInstance createFileFromMultipart(
            String title,
            UUID folderId,
            MultipartFile file,
            UserModel user
    ) throws IOException {

        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new CreateFileException("Folder not found"));

        if (!folder.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not own this folder");
        }

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

    /**
     * Retrieves a file by its title for a specific user.
     *
     * <p>The method verifies that the file exists and that it is placed
     * in a folder owned by the authenticated user.</p>
     *
     * @param title the title of the file
     * @param user the authenticated user searching the file
     * @return the matching FileInstance
     * @throws CreateFileException if the file does not exist
     * @throws AccessDeniedException if the file does not belong to the user
     */
    @Transactional(readOnly = true)
    public FileInstance getFileByTitleForUser(String title, UserModel user) {
        FileInstance file = fileRepository.findByTitle(title)
                .orElseThrow(() -> new CreateFileException("File not found with title: " + title));

        if (!file.getFolder().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not own this file");
        }

        return file;
    }

    /**
     * Deletes a file by its unique identifier for a specific user.
     *
     * <p>The method ensures that the file exists and that it is owned
     * by the authenticated user, then deletes the file.</p>
     *
     * @param fileId the unique identifier of the file to delete
     * @param user the authenticated user who is trying to delete the file
     * @throws CreateFileException if the file does not exist
     * @throws AccessDeniedException if the file does not belong to the user
     */
    public void deleteFileForUser(UUID fileId, UserModel user) {

        FileInstance file = fileRepository.findById(fileId)
                .orElseThrow(() -> new CreateFileException("File not found"));

        if (!file.getFolder().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not own this file");
        }

        fileRepository.delete(file);
    }
}