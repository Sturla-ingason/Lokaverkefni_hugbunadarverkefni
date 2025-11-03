package mainFiles.Service;
import mainFiles.objects.*;

import java.util.Date;
import java.util.List;

import mainFiles.Data.*;

public class SearchService {

    private UserData userdata;
    private PostData postData;

    /*
     * Allows a user to search for another user.
     * @Param username : The name of the user to search for.
     * return all user's with that name or with that name as a substring.
     */
    public List<User> userSearch(String username){
        if(username == null){
            throw new IllegalArgumentException("Now username to search by");
        }
        
        List<User> listOfUsers = userdata.findByUsernameEqualsOrUsernameContaining(username);

        return listOfUsers;
    }


    /*
     * Search for all posts under a given hastag.
     * @Param hashtag : The marked tag for post's the user want's to find
     * return all post's maked with the hastag.
     */
    public List<Post> hashTagSearch(String hastag){
        if(hastag == null){
            throw new IllegalArgumentException("No hastag inputed");
        }

        List<Post> listOfPosts = postData.findAllByHashtags(hastag);

        return listOfPosts;

    }


    /*
     * Searches for all post's posted by a user in a given timeframe
     * @Param timeFrom : The beginin of the time the user want's to search for
     * @Param timeToo : The end of the time frame the user want's to search inn.
     * @Param user : in what user's post we are searching inn.
     * return a list of all the post's in the given timeframe.
     */
    public List<Post> dateSearch(Date timeFrom, Date timeToo, User user){
        if(timeFrom == null || timeToo == null || user == null){
            throw new IllegalArgumentException("Missing info");
        }

        List<Post> listOfPostsBetweenDates = postData.findByUserAnddateOfUploadBetween(user, timeFrom, timeToo);

        return listOfPostsBetweenDates;

    }
    
}
