package mainFiles.controllers;

import jakarta.servlet.http.HttpSession;
import mainFiles.Service.AuthService;
import mainFiles.Service.UserService;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    /**
     *  Creates and saves a new user based on the information
     *  given in the parameter
     * @param user Information to create the new user
     * @return The user that was signed up
     */
    @PostMapping("/signup")
    public User SignUpp(@RequestBody User user){
        return authService.signUpp(user.getEmail(), user.getPassword(), user.getUsername());
    }
    /**
     * Logs the user in to the service
     * @param email email of the user
     * @param password password of the user
     * @param session The current session
     * @return Confirmation that the user was successfully logged in
     */
    @PostMapping("/login")
    public String logInn(@RequestParam String email, @RequestParam String password, HttpSession session){
        User user = authService.logInn(email, password);
        if (user == null){
            return "User not found";
        }
        session.setAttribute("userId", user.getUserID());
        return "User " +  session.getAttribute("userId") + " was successfully logged in";
    }
    /**
     * Logs a user out and ends the session
     * @param session The current session
     * @return Confirmation that the user was logged out
     */
    @GetMapping("/logout")
    public String logOut(HttpSession session){
        if (session.getAttribute("userId") == null) {
            return "No active session found";
        }
        session.invalidate();
        return "User was successfully logged out";
    }

}
