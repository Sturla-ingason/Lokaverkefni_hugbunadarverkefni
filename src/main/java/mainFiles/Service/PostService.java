package mainFiles.Service;

import jakarta.transaction.Transactional;
import mainFiles.Data.PostData;
import mainFiles.Data.UserData;
import mainFiles.dto.PostDto;
import mainFiles.dto.UserDto;
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
    private UserData userData;
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
     * Edits a post's description and/or images.
     * Only the author of the post is allowed to edit it.
     * @param postId : The id of the post to edit
     * @param user : The user attempting the edit
     * @param newDescription : The new description of the post
     * @param removeImageIds : IDs of existing images to remove (may be null)
     * @param newImages : New image files to add (may be null)
     * @return The updated post
     */
    @Transactional
    public PostDto editPost(int postId, User user, String newDescription,
                            List<Long> removeImageIds, MultipartFile[] newImages) throws IOException {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (newDescription == null || newDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        Post post = postData.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (post.getUser() == null || post.getUser().getUserID() != user.getUserID()) {
            throw new IllegalArgumentException("You can only edit your own post");
        }

        String desc = newDescription.trim();
        post.setDescription(desc);
        post.setHashtags(extractHashtags(desc));
        post.setUpdatedAt(new java.util.Date());

        // remove images by id (orphanRemoval=true on Post.image handles DB deletion)
        if (removeImageIds != null && !removeImageIds.isEmpty()) {
            post.getImage().removeIf(img -> removeImageIds.contains(img.getId()));
        }

        // add new images
        if (newImages != null) {
            for (MultipartFile file : newImages) {
                if (file == null || file.isEmpty()) continue;

                Image img = new Image();
                img.setPost(post);
                img.setImageName(file.getOriginalFilename());
                img.setImageType(file.getContentType());
                img.setImageData(file.getBytes());
                img.setProfilePicture(false);

                post.getImage().add(img);
            }
        }

        Post saved = postData.save(post);
        return PostDto.from(saved, user.getUserID());
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


    @Transactional
    public List<PostDto> getPostsByUser(User user){

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        List<Post> resault = postData.findAllByUserId(user.getUserID());

        if(resault == null){
            return null;
        }

        List<PostDto> postDtos = new ArrayList<>();
        
        for (Post post : resault) {
            postDtos.add(PostDto.from(post, user.getUserID()));
        }
        return postDtos;

    }

    /*
     * Returns a list of users who have liked a given post.
     * @param postId : The id of the post
     * @return A list of UserDto for each user who liked the post
     */
    public List<UserDto> getPostLikers(int postId) {
        Post post = postData.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        List<Integer> likerIds = post.getLikesOnPost();
        if (likerIds == null || likerIds.isEmpty()) {
            return new ArrayList<>();
        }

        return userData.findAllById(likerIds).stream()
                .map(UserDto::from)
                .collect(java.util.stream.Collectors.toList());
    }



    @Transactional
    public List<PostDto> getPostsByUserId(int userId, int currentUserId) {
        List<Post> posts = postData.findByUser_UserID(userId);
        List<PostDto> postDtos = new ArrayList<>();

        for (Post post : posts) {
            postDtos.add(PostDto.from(post, currentUserId));
        }

        return postDtos;
    }
}
