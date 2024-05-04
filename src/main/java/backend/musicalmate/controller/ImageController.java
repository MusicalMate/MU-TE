package backend.musicalmate.controller;

import backend.musicalmate.Member.ImageMember;
import backend.musicalmate.domain.dto.ImageUploadDto;
import backend.musicalmate.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/api/upload/image")
    ResponseEntity<List<String>> uploadImage(
            @RequestPart("multipartFiles") ImageUploadDto imageUploadDto,
            @RequestPart("imageMeta") String imageMeta
            ) throws IOException {

        int i=0;
        for(ImageMember imageMember : imageUploadDto.getImageMembers()){
            imageMember.setImageTitle("test");
            imageMember.setImageDescription("test description");
            i++;
        }
        List<String> url = imageService.uploadImages(imageUploadDto);

        return ResponseEntity.ok(url);
    }
}
