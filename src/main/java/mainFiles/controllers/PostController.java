package mainFiles.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import mainFiles.Service.PostService;
import mainFiles.objects.Post;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

   
    @PostMapping(path = "/posts")
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest req) {
        Post saved = postService.createNewPost(req.userId, req.description, req.hashTags);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // Request DTO kept at the boundary
    public static class CreatePostRequest {
        public Integer userId;
        public String description;
        public List<String> hashTags;
    }
  }