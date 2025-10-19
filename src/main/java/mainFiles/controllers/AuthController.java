package mainFiles.controllers;

import org.springframework.web.bind.annotation.RestController;
import mainFiles.objects.User;

import mainFiles.Service.AuthService;

@RestController
public class AuthController {
    private AuthService authService;


    /*
     * Allows a user to create a account with our service
     * @param username : The name a user want's to user for their account
     * @param password : The password a user want's to use for their account
     * @param email : The email connected to the user's account
     * @return The new user that was just created
     */
    public User SignUpp(String username, String password, String email){
        User user = authService.signUpp(email, password, username);
        return user;
    }


    /*
     * Allows a existing user to logg inn to their account
     * @param email : The email of the users account
     * @param password : The password of the users account
     */
    public void logInn(String email, String password){
        authService.logInn(email, password);
    }


    /*
     * Allows a loged inn user to log out of the service
     * @param user : The user who want's to log out
     */
    public void logOut(User user){
        authService.logOut(user);
    }

}
