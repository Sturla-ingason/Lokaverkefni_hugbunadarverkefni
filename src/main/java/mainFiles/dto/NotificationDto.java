package mainFiles.dto;
import mainFiles.objects.Notification;


public record NotificationDto(
        Integer id,
        String type,              
        String message,
        boolean read,
        Integer actorId,
        String actorUsername,
        Integer postId,
        java.time.Instant createdAt
) {
    public static NotificationDto from(Notification n) {
        return new NotificationDto(
            n.getNotificationID(),       
            n.getType() != null ? n.getType().name() : null,
            n.getMessage(),
            n.isRead(),                  
            n.getActor() != null ? n.getActor().getUserID() : null,
            n.getActor() != null ? n.getActor().getUsername() : null,
            n.getPost()  != null ? n.getPost().getPostID() : null,
            n.getCreatedAt()
        );
    }
}
