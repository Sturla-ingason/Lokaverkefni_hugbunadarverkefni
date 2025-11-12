package mainFiles.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mainFiles.Data.CommentData;
import mainFiles.Data.NotificationData;
import mainFiles.Data.PostData;
import mainFiles.Data.UserData;
import mainFiles.dto.NotificationDto;
import mainFiles.objects.Comment;
import mainFiles.objects.Notification;
import mainFiles.objects.Notification.NotificationType; 
import mainFiles.objects.Post;
import mainFiles.objects.User;

@Service
public class NotificationService {

    @Autowired 
    private NotificationData notificationData;
    @Autowired 
    private UserData userData;
    @Autowired 
    private PostData postData;
    @Autowired 
    private CommentData commentData;

    
    @Transactional(readOnly = true)
    public List<NotificationDto> listForUser(int userId) {
    return notificationData.findAllForUserWithJoins(userId)
            .stream()
            .map(NotificationDto::from)
            .toList();
}



    /**
     * Counts how many notifications a user has that are still unread.
     *
     * @param userId the ID of the user
     * @return the number of unread notifications
     * @throws IllegalArgumentException if the user does not exist
     */
    public long unreadCount(int userId) {
        User recipient = userData.findById(userId);
        if (recipient == null) 
            throw new IllegalArgumentException("Recipient not found");
        return notificationData.countByRecipientAndIsReadFalse(recipient);
    }

   
    /**
     * Marks a specific notification as read only if the user
     * requesting the change is the recipient of the notification.
     *
     * @param notificationId the ID of the notification to mark as read
     * @param userId the ID of the user attempting the action
     * @throws IllegalArgumentException if the notification is not found
     * or if the user is not the owner of the notification                                 
     */

    @Transactional
    public void markRead(int notificationId, int userId) {
        Notification n = notificationData.findById(notificationId)
            .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
            if (n.getRecipient() == null || n.getRecipient().getUserID() != userId) {
                throw new IllegalArgumentException("Cannot mark notifications for other users");
            }
            
        n.setRead(true);
        notificationData.save(n);
    }
 

    /**
     * Creates and saves a notification when someone comments on a users post
     *
     * @param recipientUserId the ID of the user who should receive the notification
     * @param commentId the ID of the comment 
     * @return the created Notification 
     * @throws IllegalArgumentException if the user or the comment does not exist
     */

    @Transactional
    public Notification notifyComment(int recipientUserId, int commentId) {
        Comment c = commentData.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        User recipient = userData.findById(recipientUserId);
        if (recipient == null) 
            throw new IllegalArgumentException("Recipient not found");

        String actorName = (c.getUser() != null ? c.getUser().getUsername() : "Someone");
        String msg = actorName + " commented on your post";

        Notification n = new Notification(
            NotificationType.COMMENT,
            msg,
            recipient,
            c.getUser(),
            c.getPost(),
            c
        );
        return notificationData.save(n);
    }


    /**
     * Creates and saves a notification when someone likes a users post.
     *
     * @param recipientUserId the ID of the user receiving the notification
     * @param actorUserId the ID of the user that liked post
     * @param postId the ID of the liked post
     * @return the created Notification 
     * @throws IllegalArgumentException if recipient, actor, or post does not exist
     */
    @Transactional
    public Notification notifyLike(int recipientUserId, int actorUserId, int postId) {
        User recipient = userData.findById(recipientUserId);
        if (recipient == null) 
            throw new IllegalArgumentException("Recipient not found");

        User actor = userData.findById(actorUserId);
        if (actor == null) 
            throw new IllegalArgumentException("Actor not found");

        Post post = postData.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        String msg = actor.getUsername() + " liked your post";
        Notification n = new Notification(NotificationType.LIKE, msg, recipient, actor, post, null);
        return notificationData.save(n);
    }



    /**
     * Creates and saves a notification when a user starts following another user.
     *
     * @param recipientUserId the ID of the user being followed
     * @param followerUserId the ID of the user who followed
     * @return the created Notification 
     * @throws IllegalArgumentException if either user does not exist
     */
    @Transactional
    public Notification notifyFollow(int recipientUserId, int followerUserId) {
        User recipient = userData.findById(recipientUserId);
        if (recipient == null) throw new IllegalArgumentException("Recipient not found");

        User actor = userData.findById(followerUserId);
        if (actor == null) throw new IllegalArgumentException("Follower not found");

        String msg = actor.getUsername() + " started following you";
        Notification n = new Notification(NotificationType.FOLLOW, msg, recipient, actor, null, null);
        return notificationData.save(n);
    }
}
