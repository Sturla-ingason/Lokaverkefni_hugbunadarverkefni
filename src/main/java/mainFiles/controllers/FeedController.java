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
public class FeedController {
    private final FeedService feedService;
    private final UserService userService;

    @Autowired
    public FeedController(FeedService feedService, UserService userService) {
        this.feedService = feedService;
        this.userService = userService;
    }

    @RequestMapping(value = "/feed")
    public List<Post> getFeed(HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        return feedService.generateFeed(user);
    }
}
