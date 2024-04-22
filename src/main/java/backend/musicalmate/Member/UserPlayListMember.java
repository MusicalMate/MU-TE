package backend.musicalmate.Member;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "UserPlayLists", uniqueConstraints = {@UniqueConstraint(name = "UserPlayList_unique",columnNames = {"userplaylist_id"}),})
public class UserPlayListMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userplaylist_id;

    @Column(nullable = false)
    private String userplaylist_title;

    @OneToMany(mappedBy = "userPlayLists")
    private List<VideoUserPlayListCall> videoUserPlayListCall = new ArrayList<>();

    @OneToMany(mappedBy = "userPlayLists")
    private  List<ImageUserPlayListCall> imageUserPlayListCall = new ArrayList<>();

    @OneToMany(mappedBy = "userPlayLists")
    private List<LinkUserPlayListCall> linkUserPlayListCall = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "allplaylist_id")
    private UserPlayListMember userPlayLists;

}
