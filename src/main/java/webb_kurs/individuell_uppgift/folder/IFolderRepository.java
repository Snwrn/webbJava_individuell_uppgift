package webb_kurs.individuell_uppgift.folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webb_kurs.individuell_uppgift.file.FileInstance;
import webb_kurs.individuell_uppgift.user.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface IFolderRepository extends JpaRepository<Folder, UUID> {

    Optional<Folder> findById(UUID id);

    //to have an exception and warn user that the folder exists by that title
    boolean existsByTitleAndUser(String title, UserModel user);

    //to find all folders by userID
    List<Folder> findAllByUser(UserModel user);
   }
