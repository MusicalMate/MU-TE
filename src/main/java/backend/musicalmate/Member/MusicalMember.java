package backend.musicalmate.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    private String image;

    @OneToMany(mappedBy = "musicalImageCalling")
    private List<ImageMusicalCall> imageMusicalMembers = new ArrayList<>();

    @OneToMany(mappedBy = "musicalVideoCalling")
    private List<VideoMusicalCall> videoActorMembers = new ArrayList<>();
}


