package mainFiles.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "User")
public class User {
    //Global variables fyrir User
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;
    
    private String username;
    private String email;
    private String password;
    private String bio;
    private int imageId;

    //Methods fyrir User

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
