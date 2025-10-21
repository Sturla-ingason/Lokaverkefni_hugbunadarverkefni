package mainFiles.controllers;

import jakarta.servlet.http.HttpSession;
import mainFiles.Service.AuthService;
import mainFiles.Service.UserService;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path="/user")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {this.userService = userService; this.authService = authService;}

    public String requestMethodName(@RequestParam String param) {
        return new String();
    }
    
    public void createNewUser(String email, String password, String userName){
        authService.signUpp(email, password, userName);
    }

    @PostMapping("/delete")
    public String deleteUser(HttpSession session){
        User user = userService.findByID((int) session.getAttribute("userId"));
        String username = user.getUsername();
        userService.delete(user);
        return "User " + username + " has been deleted";
    }


    public void updateUsername(User user, String newUsername){
        userService.updateUsername(user, newUsername);
    }


    public void updateEmail(User user, String newEmail){
        userService.updateEmail(user, newEmail);
    }


    public void resetPassword(String userEmail, String newPassword){
        userService.resetpassword(userEmail, newPassword);
    }

    public void updatePassword(User user, String newPassword){
        userService.updatePassword(user, newPassword);
    }

    @PostMapping("/follow")
    public void follow(HttpSession session,@RequestParam int userID){
        User user = userService.findByID((int) session.getAttribute("userId"));
        userService.follow(user, userID);
    }

    @PostMapping("/unfollow")
    public void unfollow(HttpSession session,@RequestParam int userID){
        User user = userService.findByID((int) session.getAttribute("userId"));
        userService.unfollow(user, userID);
    }

    @PostMapping("/allfollowers")
    public List<User> getAllFollowers(HttpSession session){
        User user = userService.findByID((int) session.getAttribute("userId"));
        return user.getFollowers();
    }
    @PostMapping("/allfollowing")
    public List<User> getAllFollowing(HttpSession session){
        User user = userService.findByID((int) session.getAttribute("userId"));
        return user.getFollowing();
    }

    @PostMapping("/removefollower")
    public void removeFollower(HttpSession session,@RequestParam int userID){
        User user = userService.findByID((int) session.getAttribute("userId"));
        userService.removeFollower(user, userID);
    }
}
