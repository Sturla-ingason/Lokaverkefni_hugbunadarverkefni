package mainFiles.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mainFiles.Data.CommentData;
import mainFiles.Data.NotificationData;
import mainFiles.Data.PostData;
import mainFiles.Data.UserData;
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

    

    public List<Notification> getForUser(int userId) {
        User recipient = userData.findById(userId); 
        if (recipient == null) 
            throw new IllegalArgumentException("Recipient not found");
        return notificationData.findByRecipientOrderByCreatedAtDesc(recipient);
    }

    public long unreadCount(int userId) {
        User recipient = userData.findById(userId);
        if (recipient == null) 
            throw new IllegalArgumentException("Recipient not found");
        return notificationData.countByRecipientAndIsReadFalse(recipient);
    }

   

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
