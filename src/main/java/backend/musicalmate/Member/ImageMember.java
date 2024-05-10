package backend.musicalmate.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
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

    @Column(name = "image_key")
    private String imageKey;

    @Column(name = "image_description")
    private String imageDescription;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "small_image_url")
    private String smallImageUrl;

    @Column(name = "small_image_key")
    private String smallImageKey;

    @Column(name = "image_like")
    private int imageLike;

    @Column(name = "image_time")
    private String imageTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private OauthMember uploadImageList;

    @OneToMany(mappedBy = "images")
    private List<ImageUserPlayListCall> imageUserPlayListCalls = new ArrayList<>();

    @OneToMany(mappedBy = "imageActorCalling")
    private List<ImageActorCall> imageActorCall = new ArrayList<>();

    @OneToMany(mappedBy = "imageMusicalCalling")
    private List<ImageMusicalCall> imageMusicalCall = new ArrayList<>();

    @OneToMany(mappedBy = "imageHashtagCalling")
    private List<HashtagCall> hashtagCalls = new ArrayList<>();
}

@Entity
@Getter
@Setter
class ImageMusicalCall{
    @Id
    @GeneratedValue
    @Column(name = "imhcall_id")
    private  Long imhcallId;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageMember imageMusicalCalling;

    @ManyToOne
    @JoinColumn(name = "musical_id") //참조할 콜룸 이름
    private MusicalMember musicalImageCalling;  //이 이름이 필드 이름 => mapped by에 사용할 값
}

@Entity
@Getter
@Setter
class ImageActorCall{
    @Id
    @GeneratedValue
    @Column(name = "iahcall_id")
    private  Long iucallId;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageMember imageActorCalling;

    @ManyToOne
    @JoinColumn(name = "actor_id") //참조할 콜룸 이름
    private ActorMember actorImageCalling;  //이 이름이 필드 이름 => mapped by에 사용할 값
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