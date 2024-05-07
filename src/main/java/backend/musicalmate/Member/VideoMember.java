package backend.musicalmate.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//제약 조건 = video_id는 중복 될 수 없음
@Entity
@Getter
@Setter
@Table(name="Videos",
        uniqueConstraints = {@UniqueConstraint(name="video_unique",columnNames = {"video_id"}),})
public class VideoMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "video_title", nullable = false)
    private String videoTitle;

    @Column(name = "video_description")
    private String videoDescription;

    @Column(name = "video_like")
    private int videoLike;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Column(name = "video_key")
    private String videoKey;

    @Column(name = "cover_image_key")
    private String coverImageKey;

    @OneToMany(mappedBy = "videos")
    private List<VideoUserPlayListCall> videoUserPlayListCalls = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private OauthMember uploadVideoList;

    @OneToMany(mappedBy = "hashTagActorsForVideo")
    private List<VideoActorHashCall> hashTagActor = new ArrayList<>();

    @OneToMany(mappedBy = "hashTagMusicalsForVideo")
    private List<VideoMusicalHashCall> hashTagMusical = new ArrayList<>();
}
@Entity
@Getter
@Setter
class VideoMusicalHashCall{
    @Id
    @GeneratedValue
    @Column(name = "vmhcall_id")
    private  Long vmhcallId;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private VideoMember hashTagMusicalsForVideo;

    @ManyToOne
    @JoinColumn(name = "hashtag_musical_id") //참조할 콜룸 이름
    private HashTagMusical videosForHashTagMusical;  //이 이름이 필드 이름 => mapped by에 사용할 값
}

@Entity
@Getter
@Setter
class VideoActorHashCall{
    @Id
    @GeneratedValue
    @Column(name = "vahcall_id")
    private  Long iucallId;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private VideoMember hashTagActorsForVideo;

    @ManyToOne
    @JoinColumn(name = "hashtag_actor_id") //참조할 콜룸 이름
    private HashTagActor videosForHashTagActor;  //이 이름이 필드 이름 => mapped by에 사용할 값
}

@Entity
@Getter
@Setter
class VideoUserPlayListCall{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vucall_id")
    private  Long vucallId;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private VideoMember videos;

    @ManyToOne
    @JoinColumn(name = "userplaylist_id") //참조할 콜룸 이름
    private UserPlayListMember userPlayLists;  //이 이름이 필드 이름 => mapped by에 사용할 값
}


