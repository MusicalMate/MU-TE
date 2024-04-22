package backend.musicalmate.Member;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Musicals", uniqueConstraints = {@UniqueConstraint(name = "Musical_unique",columnNames = {"musical_id"}),})
public class MusicalMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musical_id;

    @Column
    private String video_title;

    @Column
    private String musical_description;

    @Column
    private List<String> actor_in_musical;

}

@Entity
@Table(name="HashTagMusicals", uniqueConstraints = {@UniqueConstraint(name = "htmusical_unique", columnNames = {"musical_id"}),})
class HashTagMusical{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtag_musical_id;

    @JoinColumn(name = "musical_id", nullable = false)
    private Long musical_id;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private VideoMember hashTagMusicalsForVideo;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageMember hashTagMusicalsForImage;

    @Column
    private List<String> video_title;

    @Column
    private List<String> image_title;

    @Column
    private List<String> musical_title;
}
