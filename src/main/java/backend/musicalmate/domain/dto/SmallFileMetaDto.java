package backend.musicalmate.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SmallFileMetaDto {
    private String fileTitle;
    private String fileDescription;
    private Long musicalId;
    private List<Long> actorId;
    private String fileDate;
    private List<String> hashtag;
}
