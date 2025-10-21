package mainFiles.controllers;
import mainFiles.Service.PostService;
import mainFiles.objects.Post;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import mainFiles.Service.PostService;

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
