package mainFiles.Service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mainFiles.Data.PostData;
import mainFiles.Data.UserData;
import mainFiles.objects.Post;
import mainFiles.objects.User;

@Service
public class PostService {

    private final PostData postData;
    private final UserData userData;

    public PostService(PostData postData, UserData userData) {
        this.postData = postData;
        this.userData = userData;
    }

    @Transactional
    public Post createNewPost(int userId, String description, List<String> hashTags) {
        User user = userData.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Post p = new Post();
        p.setUser(user);
        p.setDescription(description);

        // Use these if your entity has them:
        try { p.getHashTags().addAll(hashTags); } catch (Exception ignored) {}
        try { p.setPostRead(false); } catch (Exception ignored) {}
        try { p.getClass().getMethod("setCreatedAt", Instant.class); p.setCreatedAt(Instant.now()); } catch (Exception ignored) {}

        return postData.save(p);
    }
}
