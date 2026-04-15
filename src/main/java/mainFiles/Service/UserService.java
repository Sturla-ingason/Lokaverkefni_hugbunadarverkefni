package mainFiles.Service;

import jakarta.transaction.Transactional;
import mainFiles.Data.CommentData;
import mainFiles.Data.ImageData;
import mainFiles.Data.PostData;
import mainFiles.Data.UserData;
import mainFiles.Data.NotificationData;
import mainFiles.dto.UserDto;
import mainFiles.objects.Image;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserData userData;
    @Autowired
    private PostData postData;
    @Autowired
    private CommentData commentData;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationData notificationData;
    @Autowired
    private ImageData imageData;

    
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

        int uid = user.getUserID();

        // 1. Clean up all follow/block join tables via native queries (handles both sides)
        userData.deleteAllFollowingEntriesForUser(uid);
        userData.deleteAllFollowerEntriesForUser(uid);
        userData.deleteAllBlockEntriesForUser(uid);

        // 2. Delete all posts owned by this user (with their comments and images)
        postData.findAllByUserId(uid).forEach(post -> {
            // Delete notifications for comments on this post first (FK constraint)
            if (post.getComment() != null) {
                post.getComment().forEach(comment -> notificationData.deleteByComment(comment));
            }
            // Delete notifications for the post itself
            notificationData.deleteByPost(post);
            // Delete comments explicitly (avoids double-delete conflict with cascade)
            commentData.deleteByPost(post);
            // Delete the post (cascade removes images via orphanRemoval)
            postData.delete(post);
        });

        // 3. Delete likes the user placed on other people's posts
        postData.findAll().forEach(post -> post.removeLike(uid));

        // 4. Delete comments the user made on other people's posts
        commentData.findAll().forEach(comment -> {
            if (comment.getUser() != null && comment.getUser().getUserID() == uid) {
                notificationData.deleteByComment(comment);
                commentData.delete(comment);
            }
        });

        // 5. Delete any remaining notifications involving this user
        notificationData.deleteByRecipient(user);
        notificationData.deleteByActor(user);

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
    public void follow(int currentUserId, int targetUserId) {
        User currentUser = userData.findById(currentUserId);
        User targetUser = userData.findById(targetUserId);

        if (currentUser == null || targetUser == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (currentUser.getUserID() == targetUser.getUserID()) {
            throw new IllegalArgumentException("You cannot follow yourself");
        }

        if (!currentUser.getFollowing().contains(targetUser)) {
            currentUser.getFollowing().add(targetUser);
        }

        if (!targetUser.getFollowers().contains(currentUser)) {
            targetUser.getFollowers().add(currentUser);
        }

        userData.save(currentUser);
        userData.save(targetUser);

        notificationService.notifyFollow(targetUser.getUserID(), currentUser.getUserID());
    }



    /*
     * Lets one user unfollow another user
     * @param user : The user who wants to unfollow
     * @param userToUnfollow : The user to unfollow
     */
    @Transactional
    public void unfollow(int currentUserId, int targetUserId) {
        User currentUser = userData.findById(currentUserId);
        User targetUser = userData.findById(targetUserId);

        if (currentUser == null || targetUser == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (currentUser.getUserID() == targetUser.getUserID()) {
            throw new IllegalArgumentException("You cannot unfollow yourself");
        }

        currentUser.getFollowing().remove(targetUser);
        targetUser.getFollowers().remove(currentUser);

        userData.save(currentUser);
        userData.save(targetUser);
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
     * Blocks a user so their posts are hidden from the blocker
     */
    @Transactional
    public void block(User blocker, int targetId) {
        // Reload fresh managed entities inside the transaction to avoid lazy loading issues
        User user = userData.findById(blocker.getUserID());
        User target = userData.findById(targetId);
        if (target == null) throw new IllegalArgumentException("User not found");
        if (user.getUserID() == targetId) throw new IllegalArgumentException("Cannot block yourself");
        if (user.getBlockedUsers().stream().noneMatch(u -> u.getUserID() == targetId)) {
            user.getBlockedUsers().add(target);
            // Also unfollow each other if following
            user.getFollowing().remove(target);
            target.getFollowers().remove(user);
            userData.save(target);
            userData.save(user);
        }
    }


    /*
     * Unblocks a previously blocked user
     */
    @Transactional
    public void unblock(User blocker, int targetId) {
        User user = userData.findById(blocker.getUserID());
        user.getBlockedUsers().removeIf(u -> u.getUserID() == targetId);
        userData.save(user);
    }


    /*
     * Returns true if the user has blocked the target
     */
    @Transactional
    public boolean isBlocked(User blocker, int targetId) {
        User user = userData.findById(blocker.getUserID());
        return user.getBlockedUsers().stream().anyMatch(u -> u.getUserID() == targetId);
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
    public UserDto findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing Input");
        }
        User u = userData.findByUsername(username.trim());
        if (u == null) {
            throw new IllegalArgumentException("User not found");
        }
        return UserDto.from(u);
    }

    /*
     * Updates the user's profile in one call.
     * Only updates fields that have actually changed.
     * @param user : The user to update
     * @param username : New username (null to skip)
     * @param email : New email (null to skip)
     * @param password : New password (null or empty to skip)
     * @param bio : New bio (null to skip)
     */
   @Transactional
    public void updateProfile(User user, String username, String email, String password, String bio) {
        if (username != null && !username.isBlank() && !username.equals(user.getUsername())) {
            if (userData.findByUsername(username) != null) {
                throw new IllegalArgumentException("Username already exists");
            }
            user.setUsername(username);
        }

        if (email != null && !email.isBlank() && !email.equals(user.getEmail())) {
            if (userData.findByEmail(email) != null) {
                throw new IllegalArgumentException("Email already exists");
            }
            user.setEmail(email);
        }

        if (password != null && !password.isBlank()) {
            user.setPassword(password);
        }

        if (bio != null) {
            user.setBio(bio);
        }

        userData.save(user);
    }

    
    @Transactional
    public void updateProfilePicture(int userId, MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return;
        }

        User user = userData.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Image image = user.getImage();

        if (image == null) {
            image = new Image();
            image.setUser(user);
            image.setProfilePicture(true);
        }

        image.setImageName(imageFile.getOriginalFilename());
        image.setImageType(imageFile.getContentType());
        image.setImageData(imageFile.getBytes());

        Image savedImage = imageData.save(image);

        user.setImage(savedImage);
        user.setImageId(savedImage.getId().intValue());

        userData.save(user);
    }





   
}