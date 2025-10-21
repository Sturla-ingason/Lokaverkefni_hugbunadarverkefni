package mainFiles.controllers;
import mainFiles.Service.PostService;
import mainFiles.objects.Post;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import mainFiles.Service.PostService;

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

    public PostController(PostService postService){
      this.postService = postService;
    }

     public void createNewPost(){
       //TODO
    }


    @PostMapping("/{postId}/like")
    public void likePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        postService.likePost(postId, userId);
    }

    @DeleteMapping("/{postId}/like")
    public void unlikePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        postService.unlikePost(postId, userId);
    }


}
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
