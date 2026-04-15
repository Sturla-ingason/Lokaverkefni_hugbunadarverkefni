package mainFiles.Service;
import mainFiles.objects.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public List<UserDto> userSearch(String username){
    if (username == null || username.isBlank()) {
        return List.of();
    }

    List<User> users = userData.findByUsernameContaining(username);
    List<UserDto> dtos = new ArrayList<>();

    for (User u : users) {
        int userId = u.getUserID();
        int followerCount = userData.countFollowersByUserId(userId);
        int followingCount = userData.countFollowingByUserId(userId);

        dtos.add(new UserDto(
            u.getUserID(),
            u.getUsername(),
            u.getEmail(),
            u.getBio(),
            u.getImageId(),
            followerCount,
            followingCount
        ));
    }

    return dtos;
}


    /*
     * Search for all posts under a given hastag.
     * @Param hashtag : The marked tag for post's the user want's to find
     * return all post's maked with the hastag.
     */
    @Transactional
    public List<PostDto> hashTagSearch(String hashtag, User currentUser){
        if(hashtag == null){
            throw new IllegalArgumentException("No hastag inputed");
        }

        String Htag = hashtag.startsWith("#")
                ? hashtag.substring(1).toLowerCase()
                : hashtag.toLowerCase();
        
        List<Post> posts = postData.searchByHashtag(Htag);
        List<PostDto> postDtos = new ArrayList<>();

        List<Integer> blockedIds = (currentUser != null)
                ? currentUser.getBlockedUsers().stream().map(User::getUserID).toList()
                : List.of();

        for (Post p : posts) {
            Integer authorId = p.getUser() != null ? p.getUser().getUserID() : null;
            if (authorId == null || !blockedIds.contains(authorId)) {
                postDtos.add(PostDto.from(p, currentUser != null ? currentUser.getUserID() : -1));
            }
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
            postDtos.add(PostDto.from(p, user.getUserID()));
        }

        return postDtos;

    }
    
}
