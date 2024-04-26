package backend.musicalmate.Member;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Images", uniqueConstraints = {@UniqueConstraint(name = "Image_unique",columnNames = {"image_id"}),})
public class ImageMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "image_title", nullable = false)
    private String imageTitle;

    @Column(name = "image_description")
    private String imageDescription;

    @Column(name = "image_like")
    private int imageLike;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private OauthMember uploadImageList;

    @OneToMany(mappedBy = "images")
    private List<ImageUserPlayListCall> imageUserPlayListCalls = new ArrayList<>();

    @OneToMany(mappedBy = "hashTagActorsForImage")
    private List<HashTagActor> hashTagActor = new ArrayList<>();

    @OneToMany(mappedBy = "hashTagMusicalsForImage")
    private List<HashTagMusical> hashTagMusical = new ArrayList<>();
}

@Entity
class ImageUserPlayListCall{
    @Id
    @GeneratedValue
    @Column(name = "iucall_id")
    private  Long iucallId;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageMember images;

    @ManyToOne
    @JoinColumn(name = "userplaylist_id") //참조할 콜룸 이름
    private UserPlayListMember userPlayLists;  //이 이름이 필드 이름 => mapped by에 사용할 값
}