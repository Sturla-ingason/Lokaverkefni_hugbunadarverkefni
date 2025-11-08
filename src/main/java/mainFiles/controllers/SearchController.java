package mainFiles.controllers;

import java.util.Date;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mainFiles.objects.*;
import mainFiles.Service.*;

public class SearchController {

    private SearchService searchService;

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
    public List<Post> hastagSearch(@RequestParam String hastag){

        if(hastag != null){
            return searchService.hashTagSearch(hastag);
        }

        return null;

    }


    /*
     * search for posts in a certain timeframe
     * @Param timeFrom : 
     * @Param timeToo : 
     * @Param user : 
     */
    @GetMapping("/datesearch")
    public List<Post> dateSearch(@RequestParam Date timeFrom, @RequestParam Date timeToo, @RequestParam User user){

        if(timeFrom != null && timeToo != null && user != null){
            return searchService.dateSearch(timeFrom, timeToo, user);
        }

        return null;

    }

}
