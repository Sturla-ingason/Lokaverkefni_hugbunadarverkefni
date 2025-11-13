// mainFiles/dto/UserDto.java
package mainFiles.dto;

import mainFiles.objects.User;

public record UserDto(
    int userID,
    String username,
    String email,
    String bio,
    int imageId,
    int followerCount,
    int followingCount
) {
    public static UserDto from(User u) {
        return new UserDto(
            u.getUserID(),
            u.getUsername(),
            u.getEmail(),
            u.getBio(),
            u.getImageId(),
            u.getFollowerCount(),
            u.getFollowingCount()
        );
    }
}
