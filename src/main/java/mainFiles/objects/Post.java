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

    @ElementCollection
    @CollectionTable(
        name = "post_likes",
        joinColumns = @JoinColumn(name = "post_id")
    )
    @Column(name = "user_id", nullable = false)
    private List<Integer> likes = new ArrayList<>();

    //Adds a like to a post
    public boolean addLike(Integer userId) {
        if (likes == null) likes = new ArrayList<>();
        if (likes.contains(userId)) return false;
        return likes.add(userId);
    }

    //Removes a like from a post
    public boolean removeLike(Integer userId) {
        if (likes == null) return false;
        return likes.remove(userId);
    }
}
