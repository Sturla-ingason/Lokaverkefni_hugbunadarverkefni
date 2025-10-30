package mainFiles.controllers;

import jakarta.servlet.http.HttpSession;
import mainFiles.Service.AuthService;
import mainFiles.Service.UserService;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;


    /*
     * Deleats a exsisting User
     * @Param session : Session of a the User to be deleated
     * return a string confirmation that a user has been deleated
     */
    @DeleteMapping("/delete")
    public String deleteUser(HttpSession session){
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
     * @Param session :
     * @Param username : 
     */
    @PutMapping("/username")
    public void updateUsername(HttpSession session,@RequestParam String username){
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.updateUsername(user, username);
    }


    /*
     * updates current users email
     * @Param session : 
     * @Param email : 
     */
    @PutMapping("/email")
    public void updateEmail(HttpSession session, @RequestParam String email){
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.updateEmail(user, email);
    }


    /*
     * updates the current users bio
     * @Param session :
     * @Param bio : 
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
     * @Param session : 
     * @Param password : The current password to the users account
     * @Param newPassword : The new password that user want's to set for the account
     */
    @PutMapping("/password")
    public void resetPassword(HttpSession session,@RequestParam String password, @RequestParam String newPassword){
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.resetPassword(user, password, newPassword);
    }


    /*
     * Allows a user to follow another user
     * @Param session : 
     * @Param userID : The ID of the user the current user want's to follow
     */
    @PatchMapping("/follow")
    public void follow(HttpSession session,@RequestParam int userID){
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.follow(user, userID);
    }


    /*
     * Allows a user to unfollow another user
     * @Param session : 
     * @Param userID : The Id of the user the current user want's to unfollow
     */
    @PatchMapping("/unfollow")
    public void unfollow(HttpSession session,@RequestParam int userID){
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.unfollow(user, userID);
    }


    /*
     * Get all the followers of the current logged in user
     * @Param session : 
     * return a list of all the user's the logged inn user is following
     */
    @GetMapping("/allfollowers")
    public List<User> getAllFollowers(HttpSession session){
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return user.getFollowers();
    }
   
    
    /*
     * Get all the user's the logged in user is following
     * @Param session : 
     * return a list of all the users the logged in user is following
     */
    @GetMapping("/allfollowing")
    public List<User> getAllFollowing(HttpSession session){
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return user.getFollowing();
    }


    /*
     * Allows the logged inn user to remove a follower from their account
     * @Param session : 
     */
    @PatchMapping("/removefollower")
    public void removeFollower(HttpSession session,@RequestParam int userID){
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.removeFollower(user, userID);
    }


    /*
     * Get's the logged inn user
     * @Param session : 
     */
    @GetMapping("/getuser")
    public User getUser(HttpSession session){
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return userService.findByID((int) session.getAttribute("userId"));
    }



}
