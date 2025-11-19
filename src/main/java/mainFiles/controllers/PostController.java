package mainFiles.controllers;

import jakarta.servlet.http.HttpSession;
import mainFiles.Service.CommentService;
import mainFiles.Service.PostService;
import mainFiles.Service.UserService;
import mainFiles.dto.PostDto;
import mainFiles.objects.Post;
import mainFiles.objects.User;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    /*
     * Likes a post
     * 
     * @param postId  The post to be liked
     * @param session The current session
     */
    @PostMapping("/like")
    public void likePost(@RequestParam int postId, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        Integer userId = (Integer) session.getAttribute("userId");
        postService.likePost(postId, userId);
    }

    /*
     * unlikes a post
     * 
     * @param postId  The post to unlike
     * @param session The current session
     */
    @PatchMapping("/unlike")
    public void unlikePost(@RequestParam int postId, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        int userId = (int) session.getAttribute("userId");
        postService.unlikePost(postId, userId);
    }

    /*
     * Creates a new post
     * 
     * @param post    What the post should contain
     * @param session The current session
     * @return The created post
     */
    @PostMapping("/create")
    public PostDto createPost(HttpSession session, @RequestParam String description,
                            @RequestParam(required = false, name = "image") MultipartFile[] imageFile) throws IOException {

        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        Post saved = postService.createNewPost(user, description, imageFile);
        return PostDto.from(saved); 
    }

    /*
     * Creates a new comment
     * 
     * @param postId  The id of the post to be commented on
     * @param text    The text of the comment
     * @param session the current session
     */
    @PostMapping("/comment/create")
    public void createComment(@RequestParam int postId, @RequestParam String text, HttpSession session) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        commentService.createComment(postId, user, text);
    }

    /*
     * Delets a comment
     * 
     * @param session   The current session
     * @param commentId The id of the comment to be deleted
     */
    @DeleteMapping("/comment/delete")
    public void deleteComment(HttpSession session, @RequestParam int commentId) {
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }
        commentService.deleteComment(commentId, user);
    }

    /*
     * Edits a post's description
     * 
     * @param session     The current session
     * @param postId      The id of the post to edit
     * @param description The new description of the post
     * @return The updated post
     */
    @PutMapping("/edit")
    public PostDto editPost(HttpSession session,
            @RequestParam int postId,
            @RequestParam String description) {
        Object sid = session.getAttribute("userId");
        if (sid == null) {
            throw new IllegalStateException("No active user found");
        }
        User user = userService.findByID((int) sid);

        return postService.editPostDescription(postId, user, description);
        
    }


    @DeleteMapping("/delete")
    public void deletePost(@RequestParam int postID, HttpSession session){
        User user = userService.findByID((int) session.getAttribute("userId"));
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("No active user found");
        }

        postService.deletePost(postID, user);
    }

}