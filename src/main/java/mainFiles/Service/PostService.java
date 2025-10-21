package mainFiles.Service;

import jakarta.transaction.Transactional;
import mainFiles.Data.PostData;
import mainFiles.objects.Post;
import mainFiles.objects.User;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostData postData;

    public PostService(PostData postData) {
        this.postData = postData;
    }

    @Transactional
    public Post createNewPost(User user, String description) {
        Post p = new Post(user, description);

        return postData.save(p);
    }
}
