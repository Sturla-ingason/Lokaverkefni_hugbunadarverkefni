package mainFiles.Data;

import mainFiles.objects.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface UserData extends JpaRepository<User, Integer> {

    /**
     * Finds a user by their user id
     * @param userId The id of the user
     * @return The user connected to the id
     */
    User findById(int userId);

    /*
     * Finds users by their email address
     * @param email : The email address connected to the user
     * @return The user connected to the email
     */
    User findByEmail(String email);


    /*
     * Finds a user by their email and password
     * @param email : The email address connected to the user
     * @param password : The password connected to the user
     * @return The user connected to the email and password
     */
    User findByEmailAndPassword(String email, String password);

    /**
     * Finds a user by their username
     * @param username the username of the user
     * @return The user connected to the username
     */
    User findByUsername(String username);

}
