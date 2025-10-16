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
    

    public void save(User user) {
        userData.save(user);
    }

    
    public void deleate(User user){
        userData.delete(user);
    }

    @Transactional
    public void updateEmail(User user, String newEmail){
        user.setEmail(newEmail);
    }


    public void updateUsername(User user, String NewUsername){
        user.setUsername(NewUsername);
    }


    @Transactional
    public void updatePassword(User user, String newPassword){
        user.setPassword(newPassword);
    }

    @Transactional
    public void resetpassword(String email, String newPassword){
        User user = userData.findByEmail(email);
        user.setPassword(newPassword);
    }


    public void follow(User user, User userToFollow){
        followerData.save(user.getUserID(), userToFollow.getUserID());
    }


    public void unfollow(User user, User userToUnfollow){
        
    }


    public void getFollowerAmountU(User user){

    }


}