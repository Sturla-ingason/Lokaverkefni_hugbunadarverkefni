package mainFiles.dto;

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
    List<Comment> comments
) {
    public static PostDto from(Post p) {
        return new PostDto(
            p.getPostID(),
            p.getUser() != null ? p.getUser().getUserID() : null,
            p.getUser() != null ? p.getUser().getUsername() : null,
            p.getImageIds(),
            p.getDescription(),
            p.getHashtags(),
            p.getDateOfUpload(),
            p.getComment()
        );
    }
}


