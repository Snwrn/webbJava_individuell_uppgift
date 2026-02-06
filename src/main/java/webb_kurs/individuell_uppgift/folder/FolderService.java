package webb_kurs.individuell_uppgift.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webb_kurs.individuell_uppgift.exeptions.CreateFolderException;
import webb_kurs.individuell_uppgift.exeptions.FolderAlreadyExistsException;
import webb_kurs.individuell_uppgift.user.IUserRepository;
import webb_kurs.individuell_uppgift.user.UserModel;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final IFolderRepository folderRepository;
    private final IUserRepository userRepository;

    //Create folder with an ID of a logged in user and a title from the request body.
    public Folder createFolder(UUID userId, String title) {

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new CreateFolderException("User not found"));

        //check if title is not null, too short or too long
        if (title == null || title.length() < 3 || title.length() > 30) {
            throw new CreateFolderException("Title cannot be empty and should be between 3 and 30 characters.");
        }

        //check if folder exists for this user, but I should only check if folder exists, as I don't have the logic
        //...for the multiple folders with the same name for different users. That is a point of improvement.
        if (folderRepository.existsByTitleAndUser(title, user)) {
            throw new FolderAlreadyExistsException();
        }

        Folder folder = new Folder(title, user);
        return folderRepository.save(folder);
    }

    //get all folders for the user who is logged in
    public List<Folder> getFoldersForUser(UserModel user) {
        return folderRepository.findAllByUser(user);
    }

    //delete folder by id
    public void deleteFolder(UUID folderId) {
        if (!folderRepository.existsById(folderId)) {
            throw new CreateFolderException("Folder not found with id: " + folderId);
        }
        folderRepository.deleteById(folderId);
    }

}
