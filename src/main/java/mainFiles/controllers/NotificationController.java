package mainFiles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mainFiles.Service.NotificationService;
import mainFiles.dto.NotificationDto;

import java.util.List;



@RestController
@RequestMapping("/notification")
public class NotificationController {

@Autowired
private NotificationService notificationService;

    /**
     * Gets all notifications for a user
     * @param userId The ID of the user
     * @return List of notifications
     */
    @GetMapping("/get")
    public List<NotificationDto> get(@RequestParam int userId) {
        return notificationService.listForUser(userId);  
    }
    

    /**
     * Gets count of unread notifications for a user
     * @param userId The ID of the user
     * @return Number of unread notifications
     */
    @GetMapping("/unread/count")
    public long getUnreadCount(@RequestParam int userId) {
        return notificationService.unreadCount(userId);
    }

    /**
     * Marks one notification as read
     * @param userId The ID of the user
     * @param notificationId The ID of the notification
     */
    @PostMapping("/mark-read")
    public void markRead(@RequestParam int userId, @RequestParam int notificationId) {
        notificationService.markRead(notificationId, userId);
    }
}
