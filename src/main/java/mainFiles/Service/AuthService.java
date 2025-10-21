package mainFiles.Service;

import mainFiles.Data.UserData;
import mainFiles.objects.User;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class AuthService {
    private UserData userData;

    public AuthService(UserData userData) {
        this.userData = userData;
    }
    
    /*
     * Allows a new user to create a account on the service
     * @param email Users email address connected to the account.
     * @param password Users password for the account.
     * @param userName the user name for the account.
     * @return the new user we just created
     */
    public User signUpp(String email, String password, String userName){
        if(email == null && password == null && userName == null){
            throw new IllegalArgumentException("Missing input");
        }

        //String uuid = generateNewUserID();

        User user = new User(userName, email, password);
        userData.save(user);

        return user;
    }


    /*
     * Allows a existing user to log inn to the service
     * @param email The Email of the user that wants to log inn
     * @param password The password of the user who want's to log inn
     */
    public User logInn(String email, String password){
        if(email == null && password == null){
            throw new IllegalArgumentException("Missing Input");
        }

        User user = userData.findByEmailAndPassword(email, password);
        return user;
    }


    /*
     * Allows a loged inn user to log out of the service
     * @param user The user who want's to log out
     */
    public void logOut(User user){

        

    }


    public String generateNewUserID(){
        return UUID.randomUUID().toString();
    }
    
}
