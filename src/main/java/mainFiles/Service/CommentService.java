package mainFiles.Service;

import mainFiles.Data.CommentData;
import mainFiles.Data.PostData;
import mainFiles.Data.UserData;
import mainFiles.objects.Comment;
import mainFiles.objects.Post;
import mainFiles.objects.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentData commentData;
    private final PostData postData;
    private final UserData userData;

    public CommentService(CommentData commentData, PostData postData, UserData userData) {
        this.commentData = commentData;
        this.postData = postData;
        this.userData = userData;
    }

    /**
     * Create a single comment on a post.
     *
     * @param postId  the post to comment on
     * @param userId  the author of the comment
     * @param text    the comment text
     * @return the saved Comment
     */
    @Transactional
    public Comment createComment(Integer postId, int userId, String text) {
        // check text
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Comment text cannot be empty");
        }
    
        // check user
        User user = userData.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
    
        // check post
        Post post = postData.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
    
        // Create and save comment
        Comment comment = new Comment(user, post, text);
        return commentData.save(comment);
    }


   

}