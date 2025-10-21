package mainFiles.Service;

import java.nio.file.AccessDeniedException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    /**
     * Deletes comment only if it belongs to the user that wrote it
     * 
     * @param commentId
     * @param userId
     */
    @Transactional
    public void deleteComment(Integer commentId, Integer userId) {
        // fetch comment
        Comment comment = commentData.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        // check comment creator
        if (comment.getUser() == null || comment.getUser().getUserID() == null) {
            throw new IllegalArgumentException("Cannot verify comment creator");
        }

        // check if user is comment creator
        boolean isOwner = comment.getUser().getUserID().equals(userId);
        if (!isOwner) {
            throw new IllegalArgumentException("You can only delete your own comment");
        }

        // delete comment
        commentData.delete(comment);
    }

   

}