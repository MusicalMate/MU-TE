package backend.musicalmate.Member;

import jakarta.persistence.*;

@Entity
@Table(name = "PlayLists", uniqueConstraints = {@UniqueConstraint(name = "PlayList_unique",columnNames = {"playlist_id"}),})
public class PlayListMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlist_id;

    @Column(nullable = false)
    private String playlist_title;

    @Column(nullable = false)
    private int which_playlist;

    @ManyToOne
    @JoinColumn(name = "allplaylist_id")
    private PlayListMember playLists;
}
