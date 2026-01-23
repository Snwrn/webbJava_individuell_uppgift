package webb_kurs.individuell_uppgift.folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface IFolderRepository extends JpaRepository<Folder, UUID> {
    Optional<Folder> findById(UUID id);

    // @Query("SELECT f FROM folders f LEFT JOIN FETCH f.files WHERE f.id = :id")
    // Optional<Folder> findByIdWithComments(@Param("id") UUID id);
}
