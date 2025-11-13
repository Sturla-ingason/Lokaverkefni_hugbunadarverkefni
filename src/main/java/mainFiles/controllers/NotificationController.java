package mainFiles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import mainFiles.Service.NotificationService;
import mainFiles.Service.UserService;
import mainFiles.dto.NotificationDto;
import mainFiles.objects.User;

import java.util.List;



@RestController
@RequestMapping("/notification")
public class NotificationController {

@Autowired
private NotificationService notificationService;
@Autowired
private UserService userService;

    /**
     * Gets all notifications for a user
     * @param userId The ID of the user
     * @return List of notifications
     */
    @GetMapping("/get")
    public List<NotificationDto> get(HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return notificationService.listForUser(user.getUserID());
        
    }
    

    /**
     * Gets count of unread notifications for a user
     * @param userId The ID of the user
     * @return Number of unread notifications
     */
    @GetMapping("/unread/count")
    public long getUnreadCount(HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return notificationService.unreadCount(user.getUserID());
    }

    /**
     * Marks one notification as read
     * @param userId The ID of the user
     * @param notificationId The ID of the notification
     */
    @PostMapping("/mark-read")
    public void markRead(HttpSession session, @RequestParam int notificationId) {

        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }

        notificationService.markRead(notificationId, user.getUserID());
        }
    }

