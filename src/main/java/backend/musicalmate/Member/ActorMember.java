package backend.musicalmate.Member;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "actorImageCalling")
    private List<ImageActorCall> imageActorMembers = new ArrayList<>();

    @OneToMany(mappedBy = "actorVideoCalling")
    private List<VideoActorCall> videoActorMembers = new ArrayList<>();
}


