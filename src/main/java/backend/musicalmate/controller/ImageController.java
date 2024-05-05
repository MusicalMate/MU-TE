package backend.musicalmate.controller;

import backend.musicalmate.Member.ImageMember;
import backend.musicalmate.Member.OauthMember;
import backend.musicalmate.domain.dto.ImageUploadDto;
import backend.musicalmate.service.ImageService;
import backend.musicalmate.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private Logger logger = LoggerFactory.getLogger(ImageController.class);
    private final ImageService imageService;
    private final OauthService oauthService;

    @PostMapping("/api/upload/image")
    ResponseEntity<String> uploadImage(
            @RequestPart("multipartFiles")List<MultipartFile> multipartFile,
            @RequestPart("imageMeta") String imageMeta
            ) throws IOException {

        Long userId = 1L;

        ImageUploadDto imageUploadDto = new ImageUploadDto();
        imageUploadDto.setMultipartFiles(multipartFile);

        OauthMember uploadedUser = oauthService.findUploadUser(userId);

        if(uploadedUser==null){
            logger.info("user not found");
            return ResponseEntity.badRequest().build();
        }

        imageUploadDto.setOauthMember(uploadedUser);

        logger.info(imageUploadDto.getImageMembers().toString());

        List<ImageMember> imageMembers = new ArrayList<>();
        for(int i=0;i<multipartFile.size();i++){
            ImageMember imageMember = new ImageMember();
            imageMember.setUploadImageList(uploadedUser);
            imageMember.setImageTitle(multipartFile.get(i).getName()+i);
            imageMembers.add(imageMember);

            logger.info(imageMember.getImageTitle());
        }

        imageUploadDto.setImageMembers(imageMembers);

        List<String> url = imageService.uploadImages(imageUploadDto);

        return ResponseEntity.ok(url.get(0));
    }
}
