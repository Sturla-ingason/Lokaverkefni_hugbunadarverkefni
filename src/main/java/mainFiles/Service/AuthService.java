package mainFiles.Service;

import mainFiles.Data.UserData;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UserData userData;

    /*
     * Allows a new user to create an account on the service
     * @param email Users email address connected to the account.
     * @param password Users password for the account.
     * @param userName the username for the account.
     * @return the new user we just created
     */
    public User signUpp(String email, String password, String userName){
        if(email == null || password == null || userName == null){
            throw new IllegalArgumentException("Missing input");
        }
        if (userData.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userData.findByUsername(userName) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User(userName, email, password);
        userData.save(user);
        return user;
    }


    /*
     * Allows an existing user to log inn to the service
     * @param email The Email of the user that wants to log inn
     * @param password The password of the user who wants to log inn
     * @return The logged-in user
     */
    public User logInn(String email, String password){
        if(email == null || password == null){
            throw new IllegalArgumentException("Missing Input");
        }
        if(userData.findByEmail(email) == null){
            throw new IllegalArgumentException("Email not registered");
        }

        return userData.findByEmailAndPassword(email, password);
    }
}
