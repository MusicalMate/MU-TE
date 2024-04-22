package backend.musicalmate.Member;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Actors", uniqueConstraints = {@UniqueConstraint(name = "Actor_unique",columnNames = {"actor_id"}),})
public class ActorMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actor_id;

    @Column
    private String actor_name;

    @Column
    private String actor_description;

    @Column
    private List<String> filmography;
}

@Entity
@Table(name="HashTagActors", uniqueConstraints = {@UniqueConstraint(name = "htactor_unique", columnNames = {"actor_id"}),})
class HashTagActor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtag_actor_id;

    @JoinColumn(name = "actor_id", nullable = false)
    private Long actor_id;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private VideoMember hashTagActorsForVideo;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageMember hashTagActorsForImage;

    @Column
    private String actor_name;
}
