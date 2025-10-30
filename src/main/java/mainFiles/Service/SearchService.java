package mainFiles.Service;
import mainFiles.objects.User;

public class SearchService {

    /*
     * Allows a user to search for another user.
     * @Param username : The name of the user to search for.
     * return all user's with that name or with that name as a substring.
     */
    public User userSearch(){

        User user = new User();

        return user;
    }


    /*
     * Search for all posts under a given hastag.
     * @Param hashtag : The marked tag for post's the user want's to find
     * return all post's maked with the hastag.
     */
    public void hashTagSearch(){

    }


    /*
     * Searches for all post's posted by a user in a given timeframe
     * @Param timeFrom : The beginin of the time the user want's to search for
     * @Param timeToo : The end of the time frame the user want's to search inn.
     * @Param user : in what user's post we are searching inn.
     * return a list of all the post's in the given timeframe.
     */
    public void dateSearch(){

    }
    
}
