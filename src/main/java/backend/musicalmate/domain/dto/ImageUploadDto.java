package backend.musicalmate.domain.dto;

import backend.musicalmate.Member.ImageMember;
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
    private List<MultipartFile> multipartFiles = new ArrayList<>();
    private List<ImageMember> imageMembers = new ArrayList<>();
}
