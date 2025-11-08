package mainFiles.Service;

import jakarta.transaction.Transactional;
import mainFiles.Data.CommentData;
import mainFiles.Data.PostData;
import mainFiles.Data.UserData;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserData userData;
    @Autowired
    private PostData postData;
    @Autowired
    private CommentData commentData;

    
    /*
     * Deletes a user data from database.
     * Also deletes that user in any following
     * and followers list of other users.
     * @param user : The user to be deleted from database
     */
    @Transactional
    public void delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException("The user can not be empty");
        }
        // Delete the user in any following and followers list
        user.getFollowers().forEach(f -> f.getFollowing().remove(user));
        user.getFollowing().forEach(f -> f.getFollowers().remove(user));
        // Delete the users following and followers list
        user.getFollowers().clear();
        user.getFollowing().clear();

        // Delete all the likes from the user
        postData.findAll().forEach(postData -> {
            postData.removeLike(user.getUserID());
        });

        // Delete all the comments from user
        commentData.findAll().forEach(comment -> {
            if (comment.getUser() == user) {
                commentData.delete(comment);
            }
        });

        userData.delete(user);
    }


    /*
     * Updates user email in database
     * @param user : The user who wants to change their email
     * @param newEmail : The new email the user wants to use for the account
     */
    @Transactional
    public void updateEmail(User user, String newEmail) {
        if (user == null || newEmail == null) {
            throw new IllegalArgumentException("Missing Input");
        }
        if (user.getEmail().equals(newEmail)) {
            throw new IllegalArgumentException("Cannot change email to current email");
        }
        if (userData.findByEmail(newEmail) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        user.setEmail(newEmail);
    }


    /*
     * Updates username of a user in database
     * @param user : The user who wants to change their username
     * @param newUsername : The new username
     */
    @Transactional
    public void updateUsername(User user, String NewUsername) {
        if (user == null || NewUsername == null) {
            throw new IllegalArgumentException("Missing Input");
        }
        if (userData.findByUsername(NewUsername) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setUsername(NewUsername);
    }


    /*
     * Lets the user reset his/hers password in the database
     * @param : user the user who wants to change their password
     * @param : password The current password of the user
     * @param : newPassword The new password of the user
     */
    @Transactional
    public void resetPassword(User user, String password, String newPassword) {
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Password doesn't match user");
        }
        if (password.equals(newPassword)) {
            throw new IllegalArgumentException("New password cannot be the old password");
        }
        user.setPassword(newPassword);
    }


    /*
     * Allows a user to update their bio on the service
     * @param user : The user who wants to update their bio
     * @param bio : New and updated bio from the user
     */
    @Transactional
    public void updateBio(User user, String bio) {
        if (bio == null) {
            throw new IllegalArgumentException("Bio cannot be null");
        }
        user.setBio(bio);
    }


    /*
     * Lets one user follow another user
     * @param user : The user who want to follow another user
     * @param userToFollow : The user to be followed
     */
    @Transactional
    public void follow(User user, int IdToFollow) {
        User userToFollow;
        try {
            userToFollow = userData.findById(IdToFollow);
        } catch (Exception e) {
            throw new IllegalArgumentException("User to follow not found");
        }

        if (user.equals(userToFollow)) {
            throw new IllegalArgumentException("A user cannot follow themselves.");
        }
        user.follow(userToFollow); // Follows the user to follow
        userToFollow.getFollowers().add(user); // Add user to the follower list of user to follow
        // Updates both users
        userData.save(userToFollow);
        userData.save(user);
    }


    /*
     * Lets one user unfollow another user
     * @param user : The user who wants to unfollow
     * @param userToUnfollow : The user to unfollow
     */
    @Transactional
    public void unfollow(User user, int IdToUnfollow) {
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
        userToUnfollow.getFollowers().remove(user);// Removes user from the follower list of user to unfollow

        userData.save(userToUnfollow);
        userData.save(user);
    }


    /*
     * gets the follower amount of a user
     * @param user : The user we want to get the amount of followers of.
     * @return The amount of followers a user has
     */
    @Transactional
    public int getFollowerAmount(User user) {
        return user.getFollowerCount();
    }


    /*
     * gets the amount of users the user is following
     * @param user : The user we want to get the amount of following for.
     * @return The amount a user follows
     */
    @Transactional
    public int getFollowingAmount(User user) {
        return user.getFollowingCount();
    }


    /*
     * Removes a follower of a user
     * @param user : The user who wants to remove a follower
     * @param IdToRemove : The id of the user to be removed
     */
    @Transactional
    public void removeFollower(User user, int IdToRemove) {
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
     * Gets all the followers of a users
     * @param user : The user we want to get the followers of.
     * @return List of users that follow the user
     */
    @Transactional
    public List<User> getAllFollowersOfUser(User user) {
        return user.getFollowers();
    }


    /*
     * Gets all the people a user is following 
     * @param user : The user that we want to see who he is following.
     * @return List of users that the user follows
     */
    @Transactional
    public List<User> getAllFollowedByUser(User user) {
        return user.getFollowing();
    }


    /*
     * Finds a user by their id
     * @param userId : The id of the user
     * @return The user connected to the id
     */
    @Transactional
    public User findByID(int userId) {
        return userData.findById(userId);
    }

    /*
     * Finds a user by their username
     * @param username : The username to search for
     * @return The user connected to the username
     */
    @Transactional
    public User findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing Input");
        }
        User u = userData.findByUsername(username.trim());
        if (u == null) {
            throw new IllegalArgumentException("User not found");
        }
        return u;
    }

}