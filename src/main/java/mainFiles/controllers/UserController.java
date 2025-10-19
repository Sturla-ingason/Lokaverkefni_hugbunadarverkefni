package mainFiles.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import mainFiles.Service.UserService;
import mainFiles.Service.AuthService;
import mainFiles.objects.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mainFiles.objects.User;


@RestController
@RequestMapping(path="/user")
public class UserController {
    private UserService userService;
    private AuthService authService;

    public String requestMethodName(@RequestParam String param) {
        return new String();
    }
    
    public void createNewUser(String email, String password, String userName){
        authService.signUpp(email, password, userName);
    }


    public void deleateUser(User user){
        userService.deleate(user);
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


    public void follow(User user, User user2){
        userService.follow(user, user2);
    }


    public void unfollow(User user, User user2){
        userService.unfollow(user, user2);
    }
}
