package mainFiles.controllers;

import org.springframework.stereotype.Controller;
import mainFiles.Service.UserService;

@Controller
public class UserController {
    private UserService userService;
    

    public void createNewUser(){
        userService.save();
    }


    public void deleateUser(){
        userService.deleate();
    }


    public void updateUsername(){
        userService.updateUsername();
    }


    public void updateEmail(){
        userService.updateEmail();
    }


    public void resetPassword(){
        userService.resetpassword();
    }

    public void updatePassword(){
        userService.updatePassword();
    }


    public void follow(){
        userService.follow();
    }


    public void unfollow(){
        userService.unfollow();
    }


}
