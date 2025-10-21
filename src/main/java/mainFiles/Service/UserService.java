package mainFiles.Service;

import jakarta.transaction.Transactional;
import mainFiles.Data.UserData;
import mainFiles.objects.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
    private final UserData userData;

    public UserService(UserData userData) {this.userData = userData;}


    /*
     * Deleates user data from database
     * @param user The user to be deleated from database
     */
    public void delete(User user){
        if(user == null){
            throw new IllegalArgumentException("The user can not be empty");
        }

        userData.delete(user);
    }


    /*
     * Updates user email in database
     * @param user The user who want's to change their email
     * @param newEmail The new email the user wants to use for the account
     */
    @Transactional
    public void updateEmail(User user, String newEmail){
        if(user == null || newEmail == null){
            throw new IllegalArgumentException("Missing Input");
        }

        user.setEmail(newEmail);
    }


    /*
     * Updates username of user in database
     * @param user
     * @param newUsername
     * @return 
     */
    @Transactional
    public User updateUsername(User user, String NewUsername){
        if(user == null && NewUsername == null){
            throw new IllegalArgumentException("Missing Input");
        }

        user.setUsername(NewUsername);
        return user;
    }


    /*
     * updates users password in database
     * @param user
     * @param newPassword
     * @return
     */
    @Transactional
    public User updatePassword(User user, String newPassword){
        if(user == null && newPassword == null){
            throw new IllegalArgumentException("Missing Input");
        }

        user.setPassword(newPassword);
        return user;
    }


    /*
     * Lets the user reset his/hers password in the database
     * @param email
     * @param newPassword
     * @return
     */
    @Transactional
    public User resetpassword(String email, String newPassword){
        if(email == null && newPassword == null){
            throw new IllegalArgumentException("missing input");
        }

        User user = userData.findByEmail(email);
        user.setPassword(newPassword);
        return user;
    }


    /*
     * Allows a user to update their bio on the service
     * @param user The user who want's to update their bio
     * @param bio New and updated bio from the user
     */
    public void updateBio(User user, String bio){
        if(user == null){
            throw new IllegalArgumentException("There is no user");
        }


    }


    /*
     * Allows the user to update their profile picture
     */
    public void updateProfilePicture(User user){

    }


    //Follower
    //------------------------------------------------------------------------------------------

    /*
     * Lets one user follow another user
     * @param user
     * @param userToFollow
     */
    @Transactional
    public void follow(User user, int IdToFollow){
        User userToFollow;
        try {
            userToFollow = userData.findById(IdToFollow);
        } catch (Exception e) {
            throw new IllegalArgumentException("User to follow not found");
        }

        if (user.equals(userToFollow)) {
            throw new IllegalArgumentException("A user cannot follow themselves.");
        }

        user.follow(userToFollow); //Follows the user to follow
        userToFollow.getFollowers().add(user); //Add user to the follower list of user to follow

        //Updates both users
        userData.save(userToFollow);
        userData.save(user);
    }


    /*
     * Lets one user unfollow another user
     * @param user
     * @param userToUnfollow
     */
    @Transactional
    public void unfollow(User user, int IdToUnfollow){
       User userToUnfollow;
       try {
           userToUnfollow = userData.findById(IdToUnfollow);
       } catch (Exception e) {
           throw new IllegalArgumentException("User to unfollow not found");
       }
        if (user.equals(userToUnfollow)) {
            throw new IllegalArgumentException("A user cannot unfollow themselves.");
        }
        user.unfollow(userToUnfollow); // Unfollows the user to unfollow
        userToUnfollow.getFollowers().remove(user);//Removes user from the follower list of user to unfollow

        userData.save(userToUnfollow);
        userData.save(user);

    }


    /*
     * gets the follower amount of a user
     * @param user The user we want to get the amount of followers of.
     * @return The amount of followers a user has
     */
    @Transactional
    public int getFollowerAmount(User user){
        return user.getFollowerCount();
    }

    /*
     * gets the amount of users the user is following
     * @param user The user we want to get the amount of following for.
     * @return The amount a user follows
     */
    @Transactional
    public int getFollowingAmount(User user){
        return user.getFollowingCount();
    }

    @Transactional
    public void removeFollower(User user, int IdToRemove){
        User userToRemove;
        try {
            userToRemove = userData.findById(IdToRemove);
        } catch (Exception e) {
            throw new IllegalArgumentException("User to remove not found");
        }
        user.removeFollower(userToRemove);
        userToRemove.unfollow(user);
    }

    /*
     * Gets all the followers of a user
     * @param user The user we want to get the followers of.
     * @return
     */
    public List<User> getAllFollowersOfUser(User user){
        return user.getFollowers();
    }


    /*
     * Gets all the people a user is following
     * @param user The user that we want to see who he is following.
     * @return
     */
    public List<User> getAllFollowedByUser(User user){
        return user.getFollowing();
    }


    public User findByID(int userId) {
        return userData.findById(userId);
    }
}