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

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService,  UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }


    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post, HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        return postService.createNewPost(user, post.getDescription());
    }

  }