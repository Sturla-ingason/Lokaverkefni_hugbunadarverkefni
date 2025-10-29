package mainFiles.objects;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "Posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_userid",
            referencedColumnName = "userID",
            insertable = false,
            updatable = false
    )
    @NonNull
    private User user;

    @NonNull
    private String description;

    @Column(name = "user_userid", nullable = false)
    private String userId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> image;

    private Date dateOfUpload;

    private boolean isPostRead;

    private List<String> hashtags;

    private List<Integer> likesOnPost = new ArrayList<>();

    //Adds a like to a post
    public boolean addLike(Integer userId) {
        if (likesOnPost == null) likesOnPost = new ArrayList<>();
        if (likesOnPost.contains(userId)) return false;
        return likesOnPost.add(userId);
    }

    //Removes a like from a post
    public boolean removeLike(Integer userId) {
        if (likesOnPost == null) return false;
        return likesOnPost.remove(userId);
    }
}
