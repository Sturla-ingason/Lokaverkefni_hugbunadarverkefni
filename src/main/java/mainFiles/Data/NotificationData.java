package mainFiles.Data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mainFiles.objects.Comment;
import mainFiles.objects.Notification;
import mainFiles.objects.Post;
import mainFiles.objects.User;

@Repository
public interface NotificationData extends JpaRepository<Notification, Integer> {
    
    @Query("""
        select n from Notification n
        join fetch n.recipient r
        left join fetch n.actor a
        left join fetch n.post p
        where r.userID = :userId
        order by n.createdAt desc
    """)
    List<Notification> findAllForUserWithJoins(@Param("userId") Integer userId);
    int countByRecipientAndIsReadFalse(User recipient);

    void deleteByComment(Comment comment);

    void deleteByRecipient(User user);

    void deleteByActor(User user);

    void deleteByPost(Post post);

}


