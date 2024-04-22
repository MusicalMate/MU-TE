package backend.musicalmate.Member;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Images", uniqueConstraints = {@UniqueConstraint(name = "Image_unique",columnNames = {"image_id"}),})
public class ImageMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long image_id;

    @Column(nullable = false)
    private String image_title;

    @Column
    private String image_description;

    @Column
    private int image_like;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private OauthMember upload_image_list;

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
    private  Long iucall_id;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageMember images;

    @ManyToOne
    @JoinColumn(name = "userplaylist_id") //참조할 콜룸 이름
    private UserPlayListMember userPlayLists;  //이 이름이 필드 이름 => mapped by에 사용할 값
}