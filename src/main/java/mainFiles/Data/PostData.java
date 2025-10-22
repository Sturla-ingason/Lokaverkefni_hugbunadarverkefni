package mainFiles.Data;

import mainFiles.objects.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostData extends JpaRepository<Post, Integer> {

    /**
     * Finds all post that a user has created
     * @param userId The id that is connected to the user
     * @return A list of posts from the user connected to the id
     */
    public List<Post> findAllByUserId(String userId);
}

