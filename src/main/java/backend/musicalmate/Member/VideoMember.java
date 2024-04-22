package backend.musicalmate.Member;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//제약 조건 = video_id는 중복 될 수 없음
@Entity
@Table(name="Videos",
        uniqueConstraints = {@UniqueConstraint(name="video_unique",columnNames = {"video_id"}),})
public class VideoMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long video_id;

    @Column(nullable = false)
    private String video_title;

    @Column
    private String video_description;

    @Column
    private int video_like;

    @OneToMany(mappedBy = "videos")
    private List<VideoUserPlayListCall> videoUserPlayListCalls = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private OauthMember users;

    @OneToMany(mappedBy = "hashTagActorsForVideo")
    private List<HashTagActor> hashTagActor = new ArrayList<>();

    @OneToMany(mappedBy = "hashTagMusicalsForVideo")
    private List<HashTagMusical> hashTagMusical = new ArrayList<>();
}

@Entity
class VideoUserPlayListCall{
    @Id
    @GeneratedValue
    private  Long uvcall_id;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private VideoMember videos;

    @ManyToOne
    @JoinColumn(name = "userplaylist_id") //참조할 콜룸 이름
    private UserPlayListMember userPlayLists;  //이 이름이 필드 이름 => mapped by에 사용할 값
}


