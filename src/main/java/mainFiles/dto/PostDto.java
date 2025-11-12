package mainFiles.dto;

import java.util.Date;
import java.util.List;

import mainFiles.objects.Post;

public record PostDto(
    int postID,
    String description,
    List<String> hashtags,
    Integer userId,
    String username,
    Date dateOfUpload
) {
    public static PostDto from(Post p) {
        return new PostDto(
            p.getPostID(),
            p.getDescription(),
            p.getHashtags(),
            p.getUser() != null ? p.getUser().getUserID() : null,
            p.getUser() != null ? p.getUser().getUsername() : null,
            p.getDateOfUpload()
        );
    }
}

