package mainFiles.controllers;

import jakarta.servlet.http.HttpSession;
import mainFiles.Service.PostService;
import mainFiles.Service.UserService;
import mainFiles.objects.Post;
import mainFiles.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    /**
     * Creates a new post
     * @param post What the post should contain
     * @param session The current session
     * @return The created post
     */
    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post, HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        return postService.createNewPost(user, post.getDescription());
    }

  }