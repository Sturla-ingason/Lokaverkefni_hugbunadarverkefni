package mainFiles.Data;

import mainFiles.objects.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerData{
    
    void followUser(Integer userID, Integer followerID);
    void unfollowUser(Integer userID,  Integer followerID);
    List<User> getAllFollowedByUser(Integer userID);
    Integer getAmountOfFollowers(Integer userID);

}
