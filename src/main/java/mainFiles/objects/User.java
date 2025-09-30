package mainFiles.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    //Global variables fyrir User
    String username = "test";

    String email;
    String password;

    //Methods fyrir User
    public String getUsername(){
        return username;
    }

}
