package mainFiles.controllers;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import mainFiles.Service.CommentService;
import mainFiles.objects.Comment;

@RestController
@RequestMapping(path = "/comment")
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping("/create")
    public Comment createComment(@RequestParam Integer postId,
            @RequestParam String userId,
            @RequestParam String text) {
        return commentService.createComment(postId, userId, text);
    }
}