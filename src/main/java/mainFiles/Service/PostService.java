package mainFiles.Service;

import jakarta.transaction.Transactional;
import mainFiles.Data.PostData;
import mainFiles.objects.Post;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostData postData;

    /**
     * Creates a new post
     * @param user The user who want to create a post
     * @param description The text of the post
     * @return The created post
     */
    @Transactional
    public Post createNewPost(User user, String description) {
        if (description.isEmpty()){
            throw new IllegalArgumentException("Description cannot be empty");
        }
        Post p = new Post(user, description);
        p.setUserId(String.valueOf(user.getUserID()));
        return postData.save(p);
    }

    /**
     * Add a like to a post from a user
     * @param postId The id of the post to like
     * @param userId The user who wants to like a post
     */
    @Transactional
    public void likePost(int postId, Integer userId) {
        //fetch post
        Post post = postData.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        if (post.getLikesOnPost() != null){
            if (post.getLikesOnPost().contains(userId)) {
                throw new IllegalArgumentException("User already liked post");
            }
        }

        post.addLike(userId);
        postData.save(post);
    }

    /**
     * Remove a like from a post from the user
     * @param postId The id of the post to unlike
     * @param userId The user who wants to unlike a post
     */
    @Transactional
    public void unlikePost(int postId, Integer userId) {
        //fetch post
        Post post = postData.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        //remove user from posts list of likes
        boolean removed = post.removeLike(userId);
        if (!removed) {
            throw new IllegalStateException("User has not liked this post");
        }
        //save
        postData.save(post);
    }
}
