package mainFiles.Data;

import mainFiles.objects.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageData extends JpaRepository<Image, Long> {
}
