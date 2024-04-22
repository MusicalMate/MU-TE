package backend.musicalmate.Member;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Links", uniqueConstraints = {@UniqueConstraint(name = "Link_unique",columnNames = {"link_id"}),})
public class LinkMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long link_id;

    @Column(nullable = false)
    private String link_title;

    @Column
    private String link_description;

    @Column(nullable = false)
    private String link;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private OauthMember upload_link_list;

    @OneToMany(mappedBy = "links")
    private List<LinkUserPlayListCall> linkUserPlayListCalls = new ArrayList<>();
}

@Entity
class LinkUserPlayListCall{
    @Id
    @GeneratedValue
    private  Long lucall_id;

    @ManyToOne
    @JoinColumn(name = "link_id")
    private LinkMember links;

    @ManyToOne
    @JoinColumn(name = "userplaylist_id") //참조할 콜룸 이름
    private UserPlayListMember userPlayLists;  //이 이름이 필드 이름 => mapped by에 사용할 값
}
