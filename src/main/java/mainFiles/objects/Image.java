package mainFiles.objects;

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
    private Post post;

    //Mögulega bæta við fyrir profile picture
    // @ManyToOne(fetch = FetchType.LAZY)
    // private User user;

    private boolean isProfilePicture;

    private String imageName;

    private String imageType;

    @Lob
    @Basic(fetch = FetchType.LAZY) //Veit ekki alveg hvað þetta gerir en ChatGPT!!
    @Column(nullable = false)
    private byte[] imageData;

}
