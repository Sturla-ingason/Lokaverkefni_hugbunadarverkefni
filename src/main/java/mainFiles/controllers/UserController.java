package mainFiles.controllers;

import jakarta.servlet.http.HttpSession;
import mainFiles.Service.*;
import mainFiles.dto.UserDto;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    /*
     * Deleats a exsisting User
     * @Param session : Session of a the User to be deleated
     * return a string confirmation that a user has been deleated
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    public String deleteUser(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        int userId = (int) session.getAttribute("userId");

        // Protect test accounts (alice=3, bob=4, carol=5, dave=6)
        List<Integer> protectedIds = List.of(3, 4, 5, 6);
        if (protectedIds.contains(userId)) {
            throw new IllegalStateException("Test accounts cannot be deleted");
        }

        User user = userService.findByID(userId);
        String username = user.getUsername();
        userService.delete(user);
        session.invalidate();
        return "User " + username + " has been deleted";
    }


    /*
     * Updates the active users username
     * @Param session : Session of the user that want's to update their username
     * @Param username : The new username of the user
     */
    @PutMapping("/username")
    public void updateUsername(HttpSession session, @RequestParam String username) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.updateUsername(user, username);
    }


    /*
     * updates current users email
     * @Param session : session of the user that want's to update their email
     * @Param email : new email to assigne to the user
     */
    @PutMapping("/email")
    public void updateEmail(HttpSession session, @RequestParam String email) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.updateEmail(user, email);
    }


    /*
     * updates the current users bio
     * @Param session : session of the user that want's to update their user
     * @Param bio : The new bio of the user
     */
    @PutMapping("/bio")
    public void updateBio(HttpSession session, @RequestParam String bio) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.updateBio(user, bio);
    }


    /*
     * updates the current users password to their account
     * @Param session : session of the user that want's to reset their password
     * @Param password : The current password to the users account
     * @Param newPassword : The new password that user want's to set for the account
     */
    @PutMapping("/password")
    public void resetPassword(HttpSession session, @RequestParam String password, @RequestParam String newPassword) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.resetPassword(user, password, newPassword);
    }


    /*
     * Allows a user to follow another user
     * @Param session : session of the user that want's to follow another user
     * @Param userID : The ID of the user the current user want's to follow
     */
    @PatchMapping("/follow")
    public void follow(HttpSession session, @RequestParam int userID) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.follow(user, userID);
    }


    /*
     * Allows a user to unfollow another user
     * @Param session : session of the user that want's to unfollow another user
     * @Param userID : The Id of the user the current user want's to unfollow
     */
    @PatchMapping("/unfollow")
    public void unfollow(HttpSession session, @RequestParam int userID) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.unfollow(user, userID);
    }


    /*
     * Checks if the current logged in user is following another user
     * @Param session : session of the logged in user
     * @Param userID : The ID of the user to check
     * return true if following, false otherwise
     */
    @GetMapping("/isfollowing")
    public boolean isFollowing(HttpSession session, @RequestParam int userID) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        User user = userService.findByID((int) session.getAttribute("userId"));
        return user.getFollowing().stream().anyMatch(u -> u.getUserID() == userID);
    }


    /*
     * Checks if another user is following the current logged in user
     * @Param session : session of the logged in user
     * @Param userID : The ID of the user to check
     * return true if userID follows the active user, false otherwise
     */
    @GetMapping("/isfollowedby")
    public boolean isFollowedBy(HttpSession session, @RequestParam int userID) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        User user = userService.findByID((int) session.getAttribute("userId"));
        return user.getFollowers().stream().anyMatch(u -> u.getUserID() == userID);
    }


    /*
     * Blocks another user — their posts will be hidden from the blocker
     */
    @PatchMapping("/block")
    public void blockUser(HttpSession session, @RequestParam int userID) {
        if (session.getAttribute("userId") == null) throw new IllegalStateException("No active user found");
        User user = userService.findByID((int) session.getAttribute("userId"));
        userService.block(user, userID);
    }


    /*
     * Unblocks a previously blocked user
     */
    @PatchMapping("/unblock")
    public void unblockUser(HttpSession session, @RequestParam int userID) {
        if (session.getAttribute("userId") == null) throw new IllegalStateException("No active user found");
        User user = userService.findByID((int) session.getAttribute("userId"));
        userService.unblock(user, userID);
    }


    /*
     * Returns true if the current user has blocked the given user
     */
    @GetMapping("/isblocked")
    public boolean isBlocked(HttpSession session, @RequestParam int userID) {
        if (session.getAttribute("userId") == null) throw new IllegalStateException("No active user found");
        User user = userService.findByID((int) session.getAttribute("userId"));
        return userService.isBlocked(user, userID);
    }


    /*
     * Get all the followers of a user by their ID
     * @Param session : active session
     * @Param userId  : the ID of the user whose followers to fetch
     * return a list of users that follow the given user
     */
    @GetMapping("/allfollowers")
    public List<User> getAllFollowers(HttpSession session, @RequestParam int userId) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return userService.findByID(userId).getFollowers();
    }


    /*
     * Get all the users a given user is following
     * @Param session : active session
     * @Param userId  : the ID of the user whose following list to fetch
     * return a list of users that the given user follows
     */
    @GetMapping("/allfollowing")
    public List<User> getAllFollowing(HttpSession session, @RequestParam int userId) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return userService.findByID(userId).getFollowing();
    }


    /**
     * Allows us to get the amount of other users ther user is following
     * @param session
     * @return number of how many users they are following
     */
    @GetMapping("/followingcount")
    public int getFollowingCount(HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }

        return userService.getFollowingAmount(user);
    }
    

    /**
     * Allows us to get all the followers of a user
     * @param session
     * @return number of followers for the user
     */
    @GetMapping("/followercount")
    public int getFollowersCount(HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }

        return userService.getFollowerAmount(user);
    }


    /*
     * Allows the logged inn user to remove a follower from their account
     * @Param session : session of the user that want's to remove a follower
     */
    @PatchMapping("/removefollower")
    public void removeFollower(HttpSession session, @RequestParam int userID) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        userService.removeFollower(user, userID);
    }


    /*
     * Get's the logged inn user
     * @Param session : session of the user that is currently logged inn
     * returns a user element of the currently logged inn user
     */
    @GetMapping("/getuser")
    public User getUser(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return userService.findByID((int) session.getAttribute("userId"));
    }

    /*
     * Gets a user's profile by id
     * 
     * @param session The current session
     * @param userId  The id of the user to view
     * @return The user connected to the id
     */
    @GetMapping("/profile")
    public UserDto viewUserProfileById(HttpSession session, @RequestParam int userId) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return UserDto.from(userService.findByID(userId));
    }

    /*
     * Gets a user's profile by username
     * 
     * @param session  The current session
     * @param username The username of the user to view
     * @return The user connected to the username
     */
    @GetMapping("/profile/by-username")
    public UserDto viewUserProfileByUsername(HttpSession session, @RequestParam String username) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        return userService.findByUsername(username);
    }

    /*
     * Updates the current user's profile in one call.
     * All fields are optional — only non-null values are applied.
     */
    @PostMapping("/update")
    public String updateProfile(HttpSession session,
                                @RequestParam(required = false) String username,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String password,
                                @RequestParam(required = false) String bio) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        User user = userService.findByID((int) session.getAttribute("userId"));
        userService.updateProfile(user, username, email, password, bio);
        return "Profile updated";
    }

}
