package mainFiles.controllers;

import jakarta.servlet.http.HttpSession;
import mainFiles.Service.*;
import mainFiles.dto.UserDto;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    /*
     * Deleats a exsisting User
     * @Param session : Session of a the User to be deleated
     * return a string confirmation that a user has been deleated
     */
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


    /*
     * Updates the active users username
     * @Param session : Session of the user that want's to update their username
     * @Param username : The new username of the user
     */
    @PutMapping("/username")
    public void updateUsername(HttpSession session, @RequestParam String username) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.updateUsername(user, username);
    }


    /*
     * updates current users email
     * @Param session : session of the user that want's to update their email
     * @Param email : new email to assigne to the user
     */
    @PutMapping("/email")
    public void updateEmail(HttpSession session, @RequestParam String email) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.updateEmail(user, email);
    }


    /*
     * updates the current users bio
     * @Param session : session of the user that want's to update their user
     * @Param bio : The new bio of the user
     */
    @PutMapping("/bio")
    public void updateBio(HttpSession session, @RequestParam String bio) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.updateBio(user, bio);
    }


    /*
     * updates the current users password to their account
     * @Param session : session of the user that want's to reset their password
     * @Param password : The current password to the users account
     * @Param newPassword : The new password that user want's to set for the account
     */
    @PutMapping("/password")
    public void resetPassword(HttpSession session, @RequestParam String password, @RequestParam String newPassword) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.resetPassword(user, password, newPassword);
    }


    /*
     * Allows a user to follow another user
     * @Param session : session of the user that want's to follow another user
     * @Param userID : The ID of the user the current user want's to follow
     */
    @PatchMapping("/follow")
    public void follow(HttpSession session, @RequestParam int userID) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.follow(user, userID);
    }


    /*
     * Allows a user to unfollow another user
     * @Param session : session of the user that want's to unfollow another user
     * @Param userID : The Id of the user the current user want's to unfollow
     */
    @PatchMapping("/unfollow")
    public void unfollow(HttpSession session, @RequestParam int userID) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.unfollow(user, userID);
    }


    /*
     * Get all the followers of the current logged in user
     * @Param session : session of the user that we want to get the followers for
     * return a list of all the user's the logged inn user is following
     */
    @GetMapping("/allfollowers")
    public List<User> getAllFollowers(HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return user.getFollowers();
    }
   
    
    /*
     * Get all the user's the logged in user is following
     * @Param session : session of the user that we want to get all the user's that are following them
     * return a list of all the users the logged in user is following
     */
    @GetMapping("/allfollowing")
    public List<User> getAllFollowing(HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return user.getFollowing();
    }


    /*
     * Allows the logged inn user to remove a follower from their account
     * @Param session : session of the user that want's to remove a follower
     */
    @PatchMapping("/removefollower")
    public void removeFollower(HttpSession session, @RequestParam int userID) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.removeFollower(user, userID);
    }


    /*
     * Get's the logged inn user
     * @Param session : session of the user that is currently logged inn
     * returns a user element of the currently logged inn user
     */
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
    public UserDto viewUserProfileByUsername(HttpSession session, @RequestParam String username) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return userService.findByUsername(username);
    }

}
