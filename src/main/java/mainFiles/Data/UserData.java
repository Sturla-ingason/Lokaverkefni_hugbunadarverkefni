package mainFiles.Data;

import mainFiles.objects.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mainFiles.dto.LoginUserView;
import mainFiles.dto.UserDto;






@Repository
public interface UserData extends JpaRepository<User, Integer> {

    /* núþegar built in þarf ekki. held eg
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

    /*
     * Finds a user by their username
     * @param username the username of the user
     * @return The user connected to the username
     */
    User findByUsername(String username);

    /*
     * Finds all users with the username as a full name and substring
     * @Param username : the username to search by
     * return a list of all users with the username or with the username as a substring
     */
    @Query("SELECT u FROM User u WHERE u.username = :username OR u.username LIKE %:username%")
    List<User> findAllByUsername(String username);


    /*
     * Finds all users with the username as a full name and substring
     * @Param username : the username to search by
     * return a list of all users with the username or with the username as a substring
     */
    List<User> findByUsernameContainingIgnoreCase(String username);

    /*
     * Removes all rows from user_blocked where this user is either the blocker or the blocked party.
     */
    @Modifying
    @Query(value = "DELETE FROM user_blocked WHERE user_id = :userId OR blocked_id = :userId", nativeQuery = true)
    void deleteAllBlockEntriesForUser(@Param("userId") int userId);

    /*
     * Removes all rows from user_following where this user is either side.
     */
    @Modifying
    @Query(value = "DELETE FROM user_following WHERE user_id = :userId OR following_id = :userId", nativeQuery = true)
    void deleteAllFollowingEntriesForUser(@Param("userId") int userId);

    /*
     * Removes all rows from user_followers where this user is either side.
     */
    @Modifying
    @Query(value = "DELETE FROM user_followers WHERE user_id = :userId OR follower_id = :userId", nativeQuery = true)
    void deleteAllFollowerEntriesForUser(@Param("userId") int userId);



    @Query("SELECT u.userID as userID, u.email as email, u.password as password FROM User u WHERE u.email = :email")
    LoginUserView findLoginViewByEmail(@Param("email") String email);

    // Test 
    @Query(value = "SELECT COUNT(*) FROM user_followers uf WHERE uf.user_id = :userId", nativeQuery = true)
    int countFollowersByUserId(@Param("userId") int userId);

    @Query(value = "SELECT COUNT(*) FROM user_following uf WHERE uf.user_id = :userId", nativeQuery = true)
    int countFollowingByUserId(@Param("userId") int userId);

    @Query(value = "SELECT COUNT(*) > 0 FROM user_following WHERE user_id = :userId AND following_id = :targetId", nativeQuery = true)
    boolean existsFollowing(@Param("userId") int userId, @Param("targetId") int targetId);

    }
