package mainFiles.Data;

import mainFiles.objects.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostData extends JpaRepository<Post, Integer> {
    public List<Post> findAllByUserId(String userId);
}

