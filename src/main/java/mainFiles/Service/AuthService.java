package mainFiles.Service;

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
     */
    public void signUpp(String email, String password, String userName){
        if(email == null && password == null && userName == null){
            throw new IllegalArgumentException("Missing input");
        }

        User user = new User(userName, email, password);
        userData.save(user);

    }


    /*
     * Allows a existing user to log inn to the service
     */
    public void logInn(){



    }


    /*
     * Allows a loged inn user to log out of the service
     */
    public void logOut(){

        

    }

    
}
