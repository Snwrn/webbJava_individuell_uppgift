package webb_kurs.individuell_uppgift.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webb_kurs.individuell_uppgift.user.IUserRepository;
import webb_kurs.individuell_uppgift.user.User;
import webb_kurs.individuell_uppgift.utility.AuthUtil;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final IFolderRepository folderRepository;
    private final IUserRepository userRepository;

    public Folder createFolder(UUID userId, String title) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        //  if (!AuthUtil.validatePassword(user, request.getPassword())) {
        //    throw new CreateFolderAuthException();
        // }

        if (title == null || title.length() < 3 || title.length() > 30) {
            throw new IllegalArgumentException("Invalid folder title");
        }

        Folder folder = new Folder(title, user);
        Folder savedFolder = folderRepository.save(folder);

        System.out.println("Folder with title '" + folder.getTitle() + "' created");
        return savedFolder;
    }

}
