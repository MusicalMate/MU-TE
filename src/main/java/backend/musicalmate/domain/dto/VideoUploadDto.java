package backend.musicalmate.domain.dto;

import backend.musicalmate.Member.OauthMember;
import backend.musicalmate.Member.VideoMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VideoUploadDto {
    private List<MultipartFile> videos = new ArrayList<>();
    private List<VideoMember> videoMembers = new ArrayList<>();
    private OauthMember oauthMember;
}
