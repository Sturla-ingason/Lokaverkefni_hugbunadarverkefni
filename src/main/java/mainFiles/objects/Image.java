package mainFiles.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userid")
    private User user;

    private boolean isProfilePicture;

    private String imageName;

    private String imageType;

    @Lob
    @Basic(fetch = FetchType.LAZY) //Veit ekki alveg hvað þetta gerir en ChatGPT!!
    @Column(nullable = false)
    @JsonIgnore
    private byte[] imageData;

}
