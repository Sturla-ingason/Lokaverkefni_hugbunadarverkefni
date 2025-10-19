package mainFiles.Data;

import org.springframework.stereotype.Repository;

@Repository
public interface FollowerData{

    void save(int userID, int userID2);

    void delete(int userID, int userID2);

    //TODO be able to follow another user.

    //TODO be able to get all the followers of a user.

    //TODO be able to get all the users a user is following.

    //TODO get the follower amount of a user.

    //TODO get the following amount of a user.

}
