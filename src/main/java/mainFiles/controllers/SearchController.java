package mainFiles.controllers;

import java.util.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mainFiles.objects.*;
import mainFiles.Service.*;
import mainFiles.dto.PostDto;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;
    @Autowired
    private UserService userService;


    /*
     * Search for user by username
     * @Param username : the username to search for
     * return a list of users with the name of the searched user name or with it as a substring in the name
     */
    @GetMapping("/usersearch")
    public List<User> userSearch(@RequestParam String username){
        
        if(username != null){
            return searchService.userSearch(username);
        }

        return null;

    }


    /*
     * Search for posts taged by the searched tag
     * @Param hastag : the tag to search by
     * returns a list of all the posts marked with the hastag that was searched by
     */
    @GetMapping("/hashtagsearch")
    public List<PostDto> hastagSearch(@RequestParam String hastag){

        if(hastag != null){
            return searchService.hashTagSearch(hastag);
        }

        return null;

    }


    /*
     * search for posts in a certain timeframe
     * @Param timeFrom : Time to search from
     * @Param timeToo : Time to search too
     * @Param user : The user we want to search the posts by date
     * return a list of posts with inn the two diffrent dates.
     */
    @GetMapping("/datesearch")
    public List<Post> dateSearch(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH") Date timeFrom,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH") Date timeToo,
        @RequestParam int userId)
        {
        User user = userService.findByID(userId);
        if(timeFrom != null && timeToo != null && user != null){
            return searchService.dateSearch(timeFrom, timeToo, user);
        }

        return List.of();

    }

}
