package mainFiles.objects;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationID;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private NotificationType type;

    @Column(nullable = false, length = 500)
    private String message;

    @Column(nullable = false)
    private boolean isRead = false;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User recipient;


    @ManyToOne(fetch = FetchType.LAZY)
    private User actor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    public Notification(NotificationType type, String message, User recipient, User actor, Post post, Comment comment) {
        this.type = type;
        this.message = message;
        this.recipient = recipient;
        this.actor = actor;
        this.post = post;
        this.comment = comment;
    }

    public enum NotificationType {
        COMMENT,
        LIKE,
        FOLLOW
    }
}
