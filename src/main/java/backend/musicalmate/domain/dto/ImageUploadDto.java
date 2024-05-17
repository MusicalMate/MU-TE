package backend.musicalmate.domain.dto;

import backend.musicalmate.Member.ImageMember;
import backend.musicalmate.Member.OauthMember;
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
public class ImageUploadDto {
    private List<MultipartFile> bigImageFiles = new ArrayList<>();
    private List<MultipartFile> smallImageFiles = new ArrayList<>();
    private ImageMember imageMember;
    private OauthMember oauthMember;
}
