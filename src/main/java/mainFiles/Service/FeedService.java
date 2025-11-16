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
        
        if(followedUsers.isEmpty()){
            throw new IllegalStateException("You don't follow any users");
        }
        
        List<Post> posts = new ArrayList<>();

        for (User u : followedUsers) {
            posts.addAll(postData.findAllByUserId(u.getUserID()));
        }

        if(posts.isEmpty()){
            throw new IllegalStateException("Your feed is empty");
        }
        
        List<PostDto> dtoList = new ArrayList<>();
        for (Post p : posts) {
            dtoList.add(PostDto.from(p));
        }

        return dtoList;
    }

    
}
