package mainFiles.controllers;

import jakarta.servlet.http.HttpSession;
import mainFiles.Service.FeedService;
import mainFiles.Service.UserService;
import mainFiles.objects.Post;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;
    @Autowired
    private UserService userService;

    /**
     * Gets the feed for the current user
     * @param session The ongoing session
     * @return The users feed as a list og posts
     */
    @RequestMapping("/getfeed")
    public List<Post> getFeed(HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        return feedService.generateFeed(user);
    }
}
