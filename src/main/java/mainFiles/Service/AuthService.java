package mainFiles.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import mainFiles.objects.User;
import mainFiles.Data.UserData;


@Service
public class AuthService {
    private UserData userData;

    public AuthService(){
        
    }
    
    /*
     * Allows a new user to create a account on the service
     * @param email Users email address connected to the account.
     * @param password Users password for the account.
     * @param userName the user name for the account.
     */
    public void signUpp(String email, String password, String userName){
        if(email == null && password == null && userName == null){
            throw new IllegalArgumentException("Missing input");
        }

        String uuid = generateNewUserID();

        User user = new User(uuid, userName, email, password);
        userData.save(user);

    }


    /*
     * Allows a existing user to log inn to the service
     * @param email
     * @param password
     */
    public void logInn(String email, String password){
        if(email == null && password == null){
            throw new IllegalArgumentException("Missing Input");
        }


    }


    /*
     * Allows a loged inn user to log out of the service
     */
    public void logOut(){

        

    }


    public String generateNewUserID(){
        return UUID.randomUUID().toString();
    }

    
}
