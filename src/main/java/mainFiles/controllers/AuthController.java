package mainFiles.controllers;

import org.springframework.web.bind.annotation.RestController;

import mainFiles.Service.AuthService;

@RestController
public class AuthController {
    private AuthService authService;


    /*
     * 
     */
    public void SignUpp(String username, String password, String email){
        authService.signUpp(email, password, username);
    }


    /*
     * 
     */
    public void logInn(){
        authService.logInn();
    }


    /*
     * 
     */
    public void logOut(){
        authService.logOut();
    }

}
