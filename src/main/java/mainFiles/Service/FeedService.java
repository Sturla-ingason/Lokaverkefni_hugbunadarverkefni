package mainFiles.Service;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;

import mainFiles.Data.*;
import mainFiles.dto.PostDto;
import mainFiles.objects.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class FeedService {

    @Autowired
    private PostData postData;
    @Autowired
    private UserService userService;

    /*
     * Generates the feed for the current user
     * @param : user The Current user
     * @return List of posts
     */
    @Transactional
    public List<PostDto> generateFeed(User user){
        List<User> followedUsers = userService.getAllFollowedByUser(user);

        if (followedUsers.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> blockedIds = user.getBlockedUsers()
                .stream().map(User::getUserID).toList();

        List<Post> posts = new ArrayList<>();

        for (User u : followedUsers) {
            if (!blockedIds.contains(u.getUserID())) {
                posts.addAll(postData.findAllByUserId(u.getUserID()));
            }
        }
        
        List<PostDto> dtoList = new ArrayList<>();
        for (Post p : posts) {
            dtoList.add(PostDto.from(p, user.getUserID()));
        }

        return dtoList;
    }

    
}
