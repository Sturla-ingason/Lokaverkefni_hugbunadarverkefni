package mainFiles.controllers;

import mainFiles.Service.CommentService;
import mainFiles.objects.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {this.commentService = commentService;}

    @PostMapping("/create")
    public Comment createComment(@RequestParam Integer postId,
            @RequestParam int userId,
            @RequestParam String text) {
        return commentService.createComment(postId, userId, text);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Integer commentId,
                          @RequestParam Integer userId) {
    commentService.deleteComment(commentId, userId);
}

}