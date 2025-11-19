package mainFiles.Service;

import mainFiles.Data.UserData;
import mainFiles.objects.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class AuthService {

    @Autowired
    private UserData userData;

    /*
     * Allows a new user to create an account on the service
     * @param email Users email address connected to the account.
     * @param password Users password for the account.
     * @param userName the username for the account.
     * @param image : the pictre the user want's to have as their profile picture
     * @return the new user we just created
     */
    public User signUpp(String email, String password, String userName, MultipartFile[] image)throws IOException{
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

        //sets profile picture for the user
        if(image != null){

            for(MultipartFile file : image){
                if(file.isEmpty()) continue;

                Image img = new Image();
                img.setImageName(file.getOriginalFilename());
                img.setImageType(file.getContentType());
                img.setImageData(file.getBytes());
                img.setProfilePicture(true);

                userData.save(user);
                user.setImage(img);
            }

        }

        User saved = userData.save(user);

        if (saved.getImage() != null && saved.getImage().getId() != null) {
            saved.setImageId(saved.getImage().getId().intValue());
            saved = userData.save(saved); 
        }

        return saved;

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
