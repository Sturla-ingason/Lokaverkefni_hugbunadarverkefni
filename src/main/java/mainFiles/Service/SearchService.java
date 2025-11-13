package mainFiles.Service;
import mainFiles.objects.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mainFiles.Data.*;
import mainFiles.dto.PostDto;
import mainFiles.dto.UserDto;

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
    public List<UserDto> userSearch(String username){
        if(username == null){
            throw new IllegalArgumentException("Now username to search by");
        }
        
       List<User> users = userData.findByUsernameContaining(username);
       List<UserDto> dtos = new ArrayList<>();

        for (User u : users) {
            dtos.add(UserDto.from(u));
        }

        return dtos;
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
        
        List<Post> posts = postData.findByHashtagsContaining(Htag);
        List<PostDto> postDtos = new ArrayList<>();

        for (Post p : posts) {
            postDtos.add(PostDto.from(p));
        }

        return postDtos;
    }


    /*
     * Searches for all post's posted by a user in a given timeframe
     * @Param timeFrom : The beginin of the time the user want's to search for
     * @Param timeToo : The end of the time frame the user want's to search inn.
     * @Param user : in what user's post we are searching inn.
     * return a list of all the post's in the given timeframe.
     */
    public List<PostDto> dateSearch(Date timeFrom, Date timeToo, User user){
        if(timeFrom == null || timeToo == null || user == null){
            throw new IllegalArgumentException("Missing info");
        }

        List<Post> posts = postData.findByUserAndDateOfUploadBetween(user, timeFrom, timeToo);
        List<PostDto> postDtos = new ArrayList<>();

        for (Post p : posts) {
            postDtos.add(PostDto.from(p));
        }

        return postDtos;

    }
    
}
