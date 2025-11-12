package mainFiles.Service;
import mainFiles.objects.*;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mainFiles.Data.*;
import mainFiles.dto.PostDto;

@Service
public class SearchService {

    @Autowired
    private UserData userData;
    @Autowired
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
        
        return userData.findByUsernameContaining(username);
    }


    /*
     * Search for all posts under a given hastag.
     * @Param hashtag : The marked tag for post's the user want's to find
     * return all post's maked with the hastag.
     */
    public List<PostDto> hashTagSearch(String hashtag){
        if(hashtag == null){
            throw new IllegalArgumentException("No hastag inputed");
        }

        // taka # af 
        String Htag = hashtag.startsWith("#")
                ? hashtag.substring(1).toLowerCase()
                : hashtag.toLowerCase();
        
        return postData.findByHashtagsContaining(Htag)
                .stream()
                .map(PostDto::from)
                .toList();
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

        return postData.findByUserAndDateOfUploadBetween(user, timeFrom, timeToo);

    }
    
}
