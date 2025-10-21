package mainFiles.Data;

import mainFiles.objects.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface UserData extends JpaRepository<User, Integer> {

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


    /*
     * Finds user by their user ID
     * @param userID : The user's ID
     * @return A user connected to that ID
     */
    //User findByUserID(String userID);


        //@Modifying
    //@Transactional
    //@Query("UPDATE User u SET u.password = :password WHERE u.username = :username")
    //int updatePasswordByUsername(@Param("username") String username, @Param("password") String password);

    //User findByEmail(String email);
}
