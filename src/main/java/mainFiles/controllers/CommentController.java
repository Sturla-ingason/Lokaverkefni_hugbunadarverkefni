package mainFiles.controllers;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import mainFiles.Service.CommentService;
import mainFiles.objects.Comment;

@RestController
@RequestMapping(path = "/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }


    @PostMapping("/create")
    public Comment createComment(@RequestParam Integer postId,
            @RequestParam String userId,
            @RequestParam String text) {
        return commentService.createComment(postId, userId, text);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Integer commentId,
                          @RequestParam String userId) {
    commentService.deleteComment(commentId, userId);
}

}