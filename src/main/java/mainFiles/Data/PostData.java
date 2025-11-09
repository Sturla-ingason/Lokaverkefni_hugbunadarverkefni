package mainFiles.Data;

import mainFiles.objects.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;
import mainFiles.objects.User;

@Repository
public interface PostData extends JpaRepository<Post, Integer> {

    /*
     * Finds all post that a user has created
     * @param userId The id that is connected to the user
     * @return A list of posts from the user connected to the id
     */
    public List<Post> findAllByUserId(String userId);


    /*
     * finds all posts by hastag
     * @Param hastag : the hastag to search marked posts by
     * return a list of all posts maked by the hastag
     */
    public List<Post> findByHashtagsContaining(String hastag);

     /*
      * find all post by date
      * @Param timeFrom : 
      * @Param timeToo : 
      * @Param userToSearchBy : 
      * return a list of all post's between timeFrom and timeToo.
      */
      public List<Post> findByUserAndDateOfUploadBetween(User user, Date timeFrom, Date timeTo);


}

