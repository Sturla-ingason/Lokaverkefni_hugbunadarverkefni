package mainFiles.Service;

import lombok.RequiredArgsConstructor;
import mainFiles.Data.CommentData;
import mainFiles.Data.FollowerData;
import mainFiles.Data.PostData;
import mainFiles.objects.Post;
import mainFiles.objects.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    private PostData postData;
    private CommentData commentData;
    private FollowerData followerData;

    public List<Post> generateFeed(Integer userID){
        if (followerData.getAllFollowedByUser(userID) != null){
            List<User> followedUsers = followerData.getAllFollowedByUser(userID);
            List<Post> posts = new ArrayList<>();
            for (User user : followedUsers){posts.addAll(postData.getAllPostsByUser(user));}
            return posts;
        } else {
            return Collections.emptyList();
        }
    }

    //TODO
    //Veit ekki alveg hvernig logic á að vera á þessu
    //hvernig á að finna út hvort user hefur seð post og hvernig
    //holdum við um það
    //tékkar hvort user hafi séð postin aður
    //public boolean isPostRead(){return true;}

    
}
