package backend.musicalmate.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "image_like")
    private int imageLike;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private OauthMember uploadImageList;

    @OneToMany(mappedBy = "images")
    private List<ImageUserPlayListCall> imageUserPlayListCalls = new ArrayList<>();

    @OneToMany(mappedBy = "hashTagActorsForImage")
    private List<ImageActorHashCall> hashTagActor = new ArrayList<>();

    @OneToMany(mappedBy = "hashTagMusicalsForImage")
    private List<ImageMusicalHashCall> hashTagMusical = new ArrayList<>();
}

@Entity
@Getter
@Setter
class ImageMusicalHashCall{
    @Id
    @GeneratedValue
    @Column(name = "imhcall_id")
    private  Long imhcallId;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageMember hashTagMusicalsForImage;

    @ManyToOne
    @JoinColumn(name = "hashtag_musical_id") //참조할 콜룸 이름
    private HashTagMusical imagesForHashTagAMusical;  //이 이름이 필드 이름 => mapped by에 사용할 값
}

@Entity
@Getter
@Setter
class ImageActorHashCall{
    @Id
    @GeneratedValue
    @Column(name = "iahcall_id")
    private  Long iucallId;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageMember hashTagActorsForImage;

    @ManyToOne
    @JoinColumn(name = "hashtag_actor_id") //참조할 콜룸 이름
    private HashTagActor imagesForHashTagActor;  //이 이름이 필드 이름 => mapped by에 사용할 값
}

@Entity
@Getter
@Setter
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