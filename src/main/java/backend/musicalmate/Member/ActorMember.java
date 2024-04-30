package backend.musicalmate.Member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;


@Getter
@Setter
@Data
@Entity
@Table(name = "Actors", uniqueConstraints = {@UniqueConstraint(name = "Actor_unique",columnNames = {"actor_id"}),})
public class ActorMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Long actorId;

    @Column(name = "actor_name")
    private String actorName;

    @Column(name = "actor_description")
    private String actorDescription;

    @Column(columnDefinition = "json")
    private String filmography;

    private String image;
}

@Entity
@Table(name="HashTagActors", uniqueConstraints = {@UniqueConstraint(name = "htactor_unique", columnNames = {"actor_id"}),})
class HashTagActor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_actor_id")
    private Long hashtagActorId;

    @JoinColumn(name = "actor_id", nullable = false)
    private Long actorId;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private VideoMember hashTagActorsForVideo;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageMember hashTagActorsForImage;

    @Column(name = "actor_name")
    private String actorName;
}
