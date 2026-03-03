package webb_kurs.individuell_uppgift.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import webb_kurs.individuell_uppgift.exeptions.CreateFileException;
import webb_kurs.individuell_uppgift.exeptions.CreateFolderException;
import webb_kurs.individuell_uppgift.exeptions.FolderAlreadyExistsException;
import webb_kurs.individuell_uppgift.file.FileInstance;
import webb_kurs.individuell_uppgift.user.IUserRepository;
import webb_kurs.individuell_uppgift.user.UserModel;

import java.util.List;
import java.util.UUID;

/**
 * Service class responsible for handling logic related to folder management.
 * <p>
 * This service provides functionality for creating,
 * retrieving, and deleting folders that belong to users.</p>
 */

@Service
@RequiredArgsConstructor
public class FolderService {

    private final IFolderRepository folderRepository;
    private final IUserRepository userRepository;

    /**
     * Creates a new folder for a specific user.
     *
     * <p>
     * The method validates that:
     * <ul>
     *     <li>The user exists in the system</li>
     *     <li>The folder title is between 3 and 30 characters</li>
     *     <li>No folder with the same title already exists for the user</li>
     * </ul>
     * If any validation fails, an exception is thrown.</p>
     *
     * @param userId the unique identifier of the user creating the folder
     * @param title the title of the new folder
     * @return the newly created and saved folder
     * @throws CreateFolderException if the user does not exist or the title is invalid
     * @throws FolderAlreadyExistsException if a folder with the same title already exists
     */
    public Folder createFolder(UUID userId, String title) {

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new CreateFolderException("User not found"));

        if (title == null || title.length() < 3 || title.length() > 30) {
            throw new CreateFolderException("Title cannot be empty and should be between 3 and 30 characters.");
        }

        if (folderRepository.existsByTitleAndUser(title, user)) {
            throw new FolderAlreadyExistsException();
        }

        Folder folder = new Folder(title, user);
        return folderRepository.save(folder);
    }

    /**
     * Retrieves all folders belonging to a specific user.
     *
     * @param user the authenticated user
     * @return a list of folders owned by the user
     */
    public List<Folder> getFoldersForUser(UserModel user) {
        return folderRepository.findAllByUser(user);
    }


    /**
     * Deletes a folder by its unique identifier.
     *
     * <p>The method verifies that:
     * <ul>
     *     <li>The folder exists</li>
     *     <li>The requesting user is the owner of the folder</li>
     * </ul>
     * If the folder does not exist or the user is not the owner,
     * an exception is thrown.</p>
     *
     * @param folderId the unique identifier of the folder to delete
     * @param user the authenticated user attempting to delete a folder
     * @throws CreateFolderException if the folder does not exist
     * @throws AccessDeniedException if the user does not own the folder
     */
    public void deleteFolder(UUID folderId, UserModel user) {
        Folder folder = folderRepository.findById(folderId)
        .orElseThrow(() -> new CreateFolderException("Folder not found with id: " + folderId));

        if (!folder.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not own this folder.");
        }
        folderRepository.deleteById(folderId);
    }

}
