package backend.musicalmate.Member;

import jakarta.persistence.*;

@Entity
@Table(name = "PlayLists", uniqueConstraints = {@UniqueConstraint(name = "PlayList_unique",columnNames = {"playlist_id"}),})
public class PlayListMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long playlistId;

    @Column(name = "playlist_title", nullable = false)
    private String playlistTitle;

    @Column(name = "which_playlist", nullable = false)
    private int whichPlaylist;

    @ManyToOne
    @JoinColumn(name = "allplaylist_id")
    private AllPlayListCall playLists;
}
