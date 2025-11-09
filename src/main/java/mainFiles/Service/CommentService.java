package mainFiles.Service;

import mainFiles.Data.CommentData;
import mainFiles.Data.PostData;
import mainFiles.Data.UserData;
import mainFiles.objects.Comment;
import mainFiles.objects.Post;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentData commentData;
    @Autowired
    private PostData postData;
    @Autowired
    private UserData userData;
    @Autowired
    private NotificationService notificationService;

    /*
     * Create a single comment on a post.
     * @param postId : the post to comment on
     * @param user : the author of the comment
     * @param text : the comment text
     */
    @Transactional
    public void createComment(Integer postId, User user, String text) {
        // check text
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Comment text cannot be empty");
        }
        // check post
        Post post = postData.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
    
        // Create and save comment
        Comment comment = new Comment(text, user, post);
        Comment saved = commentData.save(comment);

        // Notify post creator of comment
        if (post.getUser() != null && post.getUser().getUserID() != user.getUserID()) {
            notificationService.notifyComment(
                post.getUser().getUserID(),
                saved.getCommentID()    
            );
            }
    }


    /*
     * Deletes comment only if it belongs to the user that wrote it
     * @param : commentId The id of the comment to be deleted
     * @param : user The user that created the comment
     */
    @Transactional
    public void deleteComment(int commentId, User user) {
        // fetch comment
        Comment comment = commentData.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        if (comment.getUser().getUserID() != user.getUserID()) {
            throw new IllegalArgumentException("You can only delete your own comment");
        }
        commentData.delete(comment);
    }
}