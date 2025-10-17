package mainFiles.Service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mainFiles.Data.FollowerData;
import mainFiles.Data.UserData;
import mainFiles.objects.User;

@Service
public class UserService{
    private UserData userData;
    private FollowerData followerData;

    
    /*
     * Saves user data to database
     * @param user The user to be saved into the database
     */
    public void save(User user) {
        if(user == null){
            throw new IllegalArgumentException("The user can not be empty");
        }

        userData.save(user);
        
    }

    
    /*
     * Deleates user data from database
     * @param user The user to be deleated from database
     */
    public void deleate(User user){
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
     * 
     */
    public void updateBio(User user, String bio){
        if(user == null){
            throw new IllegalArgumentException("There is no user");
        }


    }


    /*
     * 
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
    public void follow(User user, User userToFollow){
        followerData.save(user.getUserID(), userToFollow.getUserID());
    }


    /*
     * Lets one user unfollow another user
     * @param user
     * @param userToUnfollow
     */
    public void unfollow(User user, User userToUnfollow){
        
    }


    /*
     * gets the follower amount of a user
     * @param user The user we want to get the amount of followers of.
     * @return The amount of followers of a user
     */
    public void getFollowerAmountU(User user){

    }

    /*
     * Gets all the followers of a user
     * @param user The user we want to get the followers of.
     * @return
     */
    public void getAllFollowersOfUser(User user){

    }


    /*
     * Gets all the people a user is following
     * @param user The user that we want to see who he is following.
     * @return
     */
    public void getAllFollowedByUser(User user){

    }



}