package mainFiles.controllers;

import mainFiles.Service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedController {
    private FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService) {this.feedService = feedService;}

    @RequestMapping(value = "/feed")
    public void getFeed(Integer userID){feedService.generateFeed(userID);}
}
