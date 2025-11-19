package mainFiles.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mainFiles.objects.Comment;
import mainFiles.objects.Post;

public record PostDto(
    int postID,
    Integer userId,
    String username,
    List<Long> imageIds,
    String description,
    List<String> hashtags,
    Date dateOfUpload,
    List<CommentDto> comments,
    int likeCount
) {

    public static PostDto from(Post p) {
    List<CommentDto> commentDtos = null;

    if (p.getComment() != null) {
        commentDtos = new ArrayList<>();
        for (Comment c : p.getComment()) {
            commentDtos.add(CommentDto.from(c));
        }
    }


        return new PostDto(
            p.getPostID(),
            p.getUser() != null ? p.getUser().getUserID() : null,
            p.getUser() != null ? p.getUser().getUsername() : null,
            p.getImageIds(),
            p.getDescription(),
            p.getHashtags(),
            p.getDateOfUpload(),
            commentDtos,
            p.getLikesOnPost() != null ? p.getLikesOnPost().size() : 0
        );
    }
}



