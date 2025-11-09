package mainFiles.Data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mainFiles.objects.Notification;
import mainFiles.objects.User;

@Repository
public interface NotificationData extends JpaRepository<Notification, Integer> {
    List<Notification> findByRecipientOrderByCreatedAtDesc(User recipient);
    long countByRecipientAndIsReadFalse(User recipient);
}


