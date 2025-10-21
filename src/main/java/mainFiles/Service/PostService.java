package mainFiles.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import mainFiles.Data.PostData;
import mainFiles.objects.Post;

@Service
public class PostService {

    private final PostData postData;

    public PostService(PostData postData) {
        this.postData = postData;
    }

    /**
     * Add a like to a post from the user
     *
     * @param postId 
     * @param userId 
     */
    @Transactional
    public void likePost(Integer postId, Integer userId) {
        
        //fetch post
        Post post = postData.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        //add user to posts list of likes
        boolean added = post.addLike(userId);
        if (!added) {
            throw new IllegalStateException("User already liked this post");
        }

        //save 
        postData.save(post);
    }

    /**
     * Remove a like from a post from the user
     *
     * @param postId 
     * @param userId 
     */
    @Transactional
    public void unlikePost(Integer postId, Integer userId) {
        
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
