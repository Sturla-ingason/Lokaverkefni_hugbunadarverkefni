package mainFiles.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mainFiles.objects.Comment;
import mainFiles.objects.Post;

@Repository
public interface CommentData extends JpaRepository<Comment, Integer> {
    
    void deleteByPost(Post post);
}
