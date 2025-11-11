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
    @JoinColumn(name = "user_userid", referencedColumnName = "userid", insertable = false, updatable = false)
    @NonNull
    private User user;

    @NonNull
    private String description;

    @Column(name = "user_userid", nullable = false)
    private int userId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> image;

    private Date dateOfUpload;

    private boolean isPostRead;

    @ElementCollection
    @CollectionTable(name = "post_hashtags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "hashtag")
    private List<String> hashtags;

    private List<Integer> likesOnPost = new ArrayList<>();

    // Adds a like to a post
    public boolean addLike(Integer userId) {
        if (likesOnPost == null)
            likesOnPost = new ArrayList<>();
        if (likesOnPost.contains(userId))
            return false;
        return likesOnPost.add(userId);
    }

    // Removes a like from a post
    public boolean removeLike(Integer userId) {
        if (likesOnPost == null)
            return false;
        return likesOnPost.remove(userId);
    }

    // Timestamp for last edit of a post description
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    //Manages timestamps
    @PrePersist
    protected void onCreate() {
        this.updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
