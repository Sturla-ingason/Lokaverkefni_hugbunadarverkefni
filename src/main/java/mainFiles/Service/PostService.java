package mainFiles.Service;

import jakarta.transaction.Transactional;
import mainFiles.Data.PostData;
import mainFiles.dto.PostDto;
import mainFiles.Data.CommentData;
import mainFiles.Data.NotificationData;
import mainFiles.objects.Image;
import mainFiles.objects.Post;
import mainFiles.objects.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostService {

    @Autowired
    private PostData postData;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private CommentData commentData;
    @Autowired
    private NotificationData notificationData;


    /*
     * Creates a new post
     * @param user : The user who want to create a post
     * @param description : The text of the post
     * @return The created post
     */
    @Transactional
    public Post createNewPost(User user, String description, MultipartFile[] imageFile) throws IOException {
        if (description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        Post p = new Post(user, description);
        p.setUserId(user.getUserID());
        p.setHashtags(extractHashtags(description));

        if (imageFile != null) {
            for (MultipartFile file : imageFile) {
                if (file.isEmpty()) continue;
        
                Image img = new Image();
                img.setPost(p);
                img.setImageName(file.getOriginalFilename());
                img.setImageType(file.getContentType());
                img.setImageData(file.getBytes());
                img.setProfilePicture(false);
        
                p.getImage().add(img);   
            }

        }


        return postData.save(p);
    }


    /*
     * 
     */
    private static List<String> extractHashtags(String description) {
        if (description == null) return Collections.emptyList();
        
        Pattern p = Pattern.compile("#\\w+");
        Matcher m = p.matcher(description);

        Set<String> set = new HashSet<>();
        while (m.find()) {
            set.add(m.group().substring(1).toLowerCase(Locale.ROOT));
        }
        return new ArrayList<>(set);
    }



    /*
     * Add a like to a post from a user
     * @param postId : The id of the post to like
     * @param userId : The user who wants to like a post
     */
    @Transactional
    public void likePost(int postId, Integer userId) {
        // fetch post
        Post post = postData.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        if (post.getLikesOnPost() != null) {
            if (post.getLikesOnPost().contains(userId)) {
                throw new IllegalArgumentException("User already liked post");
            }
        }

        post.addLike(userId);
        postData.save(post);

        // notify post owner
        User owner = post.getUser();          
        if (owner != null && owner.getUserID() != userId) {
            notificationService.notifyLike(
                owner.getUserID(),            
                userId,                       
                postId                        
            );
        }
    }

    /*
     * Remove a like from a post from the user
     * @param : postId The id of the post to unlike
     * @param : userId The user who wants to unlike a post
     */
    @Transactional
    public void unlikePost(int postId, Integer userId) {
        // fetch post
        Post post = postData.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        // remove user from posts list of likes
        boolean removed = post.removeLike(userId);
        if (!removed) {
            throw new IllegalStateException("User has not liked this post");
        }
        // save
        postData.save(post);
    }

    /*
     * Edits the description of a post.
     * Only the author of the post is allowed to edit it.
     * @param postId : The id of the post to edit
     * @param user : The user attempting the edit
     * @param newDescription : The new description of the post
     * @return The updated post
     */
    @Transactional
    public PostDto editPostDescription(int postId, User user, String newDescription) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (newDescription == null || newDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        // get the post
        Post post = postData.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // only author can edit
        if (post.getUser() == null || post.getUser().getUserID() != user.getUserID()) {
            throw new IllegalArgumentException("You can only edit your own post");
        }

        // update fields

        String desc = newDescription.trim();

        post.setDescription(desc);

        post.setHashtags(extractHashtags(desc));

        post.setUpdatedAt(new java.util.Date());

        Post saved = postData.save(post);

        return PostDto.from(saved);
    }


    @Transactional
    public void deletePost(int postID, User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        Post post = postData.findById(postID)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        
        if (post.getUser() == null || post.getUser().getUserID() != user.getUserID()) {
            throw new IllegalArgumentException("You can only edit your own post");
        }

        notificationData.deleteByPost(post);

        commentData.deleteByPost(post);

        postData.delete(post);

    }

}
