package mainFiles.controllers;

import jakarta.servlet.http.HttpSession;
import mainFiles.Service.AuthService;
import mainFiles.Service.UserService;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @DeleteMapping("/delete")
    public String deleteUser(HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        String username = user.getUsername();
        userService.delete(user);
        return "User " + username + " has been deleted";
    }

    @PutMapping("/username")
    public void updateUsername(HttpSession session, @RequestParam String username) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.updateUsername(user, username);
    }

    @PutMapping("/email")
    public void updateEmail(HttpSession session, @RequestParam String email) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.updateEmail(user, email);
    }

    @PutMapping("/bio")
    public void updateBio(HttpSession session, @RequestParam String bio) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.updateBio(user, bio);
    }

    @PutMapping("/password")
    public void resetPassword(HttpSession session, @RequestParam String password, @RequestParam String newPassword) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.resetPassword(user, password, newPassword);
    }

    @PatchMapping("/follow")
    public void follow(HttpSession session, @RequestParam int userID) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.follow(user, userID);
    }

    @PatchMapping("/unfollow")
    public void unfollow(HttpSession session, @RequestParam int userID) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.unfollow(user, userID);
    }

    @GetMapping("/allfollowers")
    public List<User> getAllFollowers(HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return user.getFollowers();
    }

    @GetMapping("/allfollowing")
    public List<User> getAllFollowing(HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return user.getFollowing();
    }

    @PatchMapping("/removefollower")
    public void removeFollower(HttpSession session, @RequestParam int userID) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.removeFollower(user, userID);
    }

    @GetMapping("/getuser")
    public User getUser(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return userService.findByID((int) session.getAttribute("userId"));
    }

    /**
     * Gets a user's profile by id
     * 
     * @param session The current session
     * @param userId  The id of the user to view
     * @return The user connected to the id
     */
    @GetMapping("/profile")
    public User viewUserProfileById(HttpSession session, @RequestParam int userId) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return userService.findByID(userId);
    }

    /**
     * Gets a user's profile by username
     * 
     * @param session  The current session
     * @param username The username of the user to view
     * @return The user connected to the username
     */
    @GetMapping("/profile/by-username")
    public User viewUserProfileByUsername(HttpSession session, @RequestParam String username) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return userService.findByUsername(username);
    }

}
