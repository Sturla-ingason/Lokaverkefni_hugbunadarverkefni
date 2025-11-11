package mainFiles.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "Users")
public class User {
    //Global variables fyrir User
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int userID;

    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private String bio;
    private int imageId;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private List<User> following = new ArrayList<>();


    // --- List of users who follow THIS user ---
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private List<User> followers = new ArrayList<>();

    //Follow another user
    public void follow(User user) {
        if (!following.contains(user)) {
            following.add(user);
        }
    }

    //Unfollow another user
    public void unfollow(User user) {
        following.remove(user);
    }

    //Get how many followers a user has
    public int getFollowerCount() {
        return followers.size();
    }

    //Get how many this user follows
    public int getFollowingCount() {
        return following.size();
    }

    //Remove a follower
    public void removeFollower(User user) {
        followers.remove(user);
    }
}
