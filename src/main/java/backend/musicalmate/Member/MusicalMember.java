package backend.musicalmate.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Musicals", uniqueConstraints = {@UniqueConstraint(name = "Musical_unique",columnNames = {"musical_id"}),})
public class MusicalMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musical_id")
    private Long musicalId;

    @Column(name = "musical_title")
    private String musicalTitle;

    @Column(name = "musical_time")
    private String musicalTime;

    @Column(name = "musical_description")
    private String musicalDescription;

    @Column(name = "actor_in_musical", columnDefinition = "json")
    private String actorInMusical;

}

@Entity
@Table(name="HashTagMusicals", uniqueConstraints = {@UniqueConstraint(name = "htmusical_unique", columnNames = {"musical_id"}),})
class HashTagMusical{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_musical_id")
    private Long hashtagMusicalId;

    @JoinColumn(name = "musical_id", nullable = false)
    private Long musicalId;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private VideoMember hashTagMusicalsForVideo;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageMember hashTagMusicalsForImage;

    @Column(name = "musical_title")
    private String musicalTitle;
}
