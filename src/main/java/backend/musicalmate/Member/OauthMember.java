package backend.musicalmate.Member;

import backend.musicalmate.oauth.OauthId;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="Users",
uniqueConstraints = {@UniqueConstraint(name="user_unique",columnNames = {"email"}),
})
public class OauthMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Embedded
    private OauthId oauthId;
    private String nickname;
    private String picture;

    @Column(name = "user_key")
    private String userKey;
    @Column
    private String email;

    @OneToMany(mappedBy = "uploadVideoList")
    @Column(name = "upload_video_list")
    private List<VideoMember> uploadVideoList;

    @OneToMany(mappedBy = "uploadImageList")
    @Column(name = "upload_image_list")
    private List<ImageMember> uploadImageList;

    @OneToMany(mappedBy = "uploadLinkList")
    @Column(name = "upload_link_list")
    private List<LinkMember> uploadLinkList;

    @OneToMany(mappedBy = "usersAllPlayList")
    private List<AllPlayListCall> allPlayListCall = new ArrayList<>();

}
@Entity
class AllPlayListCall{
    @Id
    @GeneratedValue
    @Column(name = "allplaylist_id")
    private  Long allplaylistId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private OauthMember usersAllPlayList;

    @OneToMany
    @JoinColumn(name = "userplaylist_id") //참조할 콜룸 이름-db에 저장된 이름 써야함
    private List<UserPlayListMember> userplaylistId;  //이 이름이 필드 이름 => mapped by에 사용할 값

    @OneToMany
    @JoinColumn(name = "playlist_id")
    private List<PlayListMember> playlistId;
}
