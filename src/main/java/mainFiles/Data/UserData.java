package mainFiles.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import mainFiles.objects.User;
import java.util.List;




@Repository
public interface UserData extends JpaRepository<User, String> {

    /*
     * Saves user to database
     * @param user : The user to save into the database
     * @return The user that has been saved
     */
    User save(User user);


    /*
     * Deleates user from database
     * @param user : The user to be deleated
     */
    void delete(User user);


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
    User findByEmailAndByPassword(String email, String password);


    /*
     * Finds user by their user ID
     * @param userID : The user's ID
     * @return A user connected to that ID
     */
    User findByUserID(Integer userID);


        //@Modifying
    //@Transactional
    //@Query("UPDATE User u SET u.password = :password WHERE u.username = :username")
    //int updatePasswordByUsername(@Param("username") String username, @Param("password") String password);

    User findByEmail(String email);
}
