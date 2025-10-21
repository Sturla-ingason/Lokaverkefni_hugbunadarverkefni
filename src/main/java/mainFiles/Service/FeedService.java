package mainFiles.Service;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import mainFiles.Data.PostData;
import mainFiles.Data.UserData;
import mainFiles.objects.Post;
import mainFiles.objects.User;
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
    private UserData userData;
    @Autowired
    private UserService userService;

//    public FeedService(PostData postData, UserData userData, UserService userService) {
//        this.postData = postData;
//        this.userData = userData;
//        this.userService = userService;
//    }

    @Transactional
    public List<Post> generateFeed(User user){
        List<User> followedUsers = userService.getAllFollowedByUser(user);
        List<Post> posts =  new ArrayList<>();
        for (User u : followedUsers) {posts.addAll(postData.findAllByUserId(String.valueOf(u.getUserID())));}
        return posts;
    }

    //TODO
    //Veit ekki alveg hvernig logic á að vera á þessu
    //hvernig á að finna út hvort user hefur seð post og hvernig
    //holdum við um það
    //tékkar hvort user hafi séð postin aður
    //public boolean isPostRead(){return true;}

    
}
