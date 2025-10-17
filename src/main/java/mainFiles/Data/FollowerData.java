package mainFiles.Data;

import org.springframework.stereotype.Repository;

@Repository
public interface FollowerData{

    void save(String userID, String userID2);
    


}
