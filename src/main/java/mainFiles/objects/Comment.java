package mainFiles.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "Comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentID;

    @Column(nullable = false, length = 1000)
    @NonNull
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    @JsonIgnore
    private Post post;

}
