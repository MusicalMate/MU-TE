package backend.musicalmate.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="Hashtag", uniqueConstraints = {@UniqueConstraint(name = "hashtag_unique", columnNames = {"hashtag_id"}),})
public class HashtagMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long hashtagId;

    @OneToMany(mappedBy = "hashtagCalling")
    private List<HashtagCall> hashtagCalls = new ArrayList<>();

    @Column(name = "hashtag_name")
    private String hashtagName;
}

@Entity
@Getter
@Setter
class HashtagCall{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_call_id")
    private Long hashtagCallId;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageMember imageHashtagCalling;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private HashtagMember hashtagCalling;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private VideoMember videoHashtagCalling;

}
