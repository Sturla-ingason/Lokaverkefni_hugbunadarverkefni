package mainFiles.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //Global variables fyrir User
    @Id
    private int userID;
    
    private String username;
    private String email;
    private String password;
    private String bio;
    private int imageId;

    //Methods fyrir User

}
