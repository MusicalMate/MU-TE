package backend.musicalmate.Member;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "UserPlayLists", uniqueConstraints = {@UniqueConstraint(name = "UserPlayList_unique",columnNames = {"userplaylist_id"}),})
public class UserPlayListMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userplaylist_id")
    private Long userplaylistId;

    @Column(name = "userplaylist_title", nullable = false)
    private String userplaylistTitle;

    @OneToMany(mappedBy = "userPlayLists")
    private List<VideoUserPlayListCall> videoUserPlayListCall = new ArrayList<>();

    @OneToMany(mappedBy = "userPlayLists")
    private  List<ImageUserPlayListCall> imageUserPlayListCall = new ArrayList<>();

    @OneToMany(mappedBy = "userPlayLists")
    private List<LinkUserPlayListCall> linkUserPlayListCall = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "allplaylist_id")
    private AllPlayListCall userPlayLists;

}
