package webb_kurs.individuell_uppgift.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface IFileRepository extends JpaRepository<FileInstance, UUID> {
    boolean existsByTitle(String title);

    Optional<FileInstance> findByTitle(String title);
}
