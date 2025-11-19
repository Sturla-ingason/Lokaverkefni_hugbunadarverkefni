package mainFiles.dto;

import mainFiles.objects.Comment;

public record CommentDto(
    int commentID,
    String comment,
    Integer userId,
    String username
) {
    public static CommentDto from(Comment c) {
        return new CommentDto(
            c.getCommentID(),
            c.getComment(),
            c.getUser() != null ? c.getUser().getUserID() : null,
            c.getUser() != null ? c.getUser().getUsername() : null
        );
    }
}

