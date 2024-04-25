package backend.musicalmate.Member;

import backend.musicalmate.Member.AllPlayListCall;
import backend.musicalmate.Member.ImageMember;
import backend.musicalmate.Member.VideoMember;
import backend.musicalmate.oauth.OauthId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.repository.cdi.Eager;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="Users",
uniqueConstraints = {@UniqueConstraint(name="user_unique",columnNames = {"email"}),
})
public class OauthMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Embedded
    private OauthId oauthId;
    private String nickname;
    private String picture;

    @Column
    private String user_key;
    @Column
    private String email;

    @OneToMany(mappedBy = "upload_video_list")
    private List<VideoMember> upload_video_list;
    @OneToMany(mappedBy = "upload_image_list")
    private List<ImageMember> upload_image_list;

    @OneToMany(mappedBy = "upload_link_list")
    private List<LinkMember> upload_link_list;

    @OneToMany(mappedBy = "users_allPlayList")
    private List<AllPlayListCall> allPlayListCall = new ArrayList<>();

//    @Column
//    private String accessToken;
    public Long user_id(){
        return user_id;
    }
    public OauthId oauthId(){
        return oauthId;
    }
    public String nickname(){
        return nickname;
    }
    public String picture(){
        return picture;
    }
    public String email(){
        return email;
    }
    public List<VideoMember> upload_video_list(){
        return upload_video_list;
    }
    public List<ImageMember> upload_image_list(){
        return upload_image_list;
    }

}
@Entity
class AllPlayListCall{
    @Id
    @GeneratedValue
    private  Long allplaylist_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private OauthMember users_allPlayList;

    @OneToMany
    @JoinColumn(name = "userplaylist_id") //참조할 콜룸 이름
    private List<UserPlayListMember> userplaylist_id;  //이 이름이 필드 이름 => mapped by에 사용할 값

    @OneToMany
    @JoinColumn(name = "playlist_id")
    private List<PlayListMember> playlist_id;
}
