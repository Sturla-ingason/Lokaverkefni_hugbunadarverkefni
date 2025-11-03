package mainFiles.controllers;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;

import mainFiles.objects.*;
import mainFiles.Service.*;

public class SearchController {

    private SearchService searchService;

    /*
     * Search for user by username
     * @Param username : 
     */
    @GetMapping("/usersearch")
    public void userSearch(String username){

    }


    /*
     * Search for posts taged by the searched tag
     * @Param hastag : 
     */
    @GetMapping("/hashtagsearch")
    public void hastagSearch(String hastag){
        if(hastag == null){
            throw new IllegalArgumentException("Missing Input");
        }

        searchService.hashTagSearch(hastag);

    }


    /*
     * search for posts in a certain timeframe
     * @Param timeFrom : 
     * @Param timeToo : 
     * @Param user : 
     */
    @GetMapping("/datesearch")
    public void dateSearch(Date timeFrom, Date timeToo, User user){

    }

}
