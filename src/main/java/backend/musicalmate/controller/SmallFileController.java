package backend.musicalmate.controller;

import backend.musicalmate.Member.ImageMember;
import backend.musicalmate.Member.OauthMember;
import backend.musicalmate.Member.VideoMember;
import backend.musicalmate.domain.dto.SmallFileMetaDto;
import backend.musicalmate.domain.dto.ImageUploadDto;
import backend.musicalmate.domain.dto.VideoUploadDto;
import backend.musicalmate.service.SmallFileService;
import backend.musicalmate.service.OauthService;
import backend.musicalmate.service.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
public class SmallFileController {

    private Logger logger = LoggerFactory.getLogger(SmallFileController.class);
    private final SmallFileService smallFileService;
    private final OauthService oauthService;
    private final ObjectMapper objectMapper;

    @PostMapping("/api/upload/small/{type}")
    ResponseEntity<CompletableFuture<String>> uploadSmallFile(
            @PathVariable String type,
            @RequestPart("bigFiles")List<MultipartFile> bigFiles,
            @RequestPart("smallFiles") List<MultipartFile> smallFiles,
            @RequestPart("fileMeta") String fileMeta,
            @RequestHeader("userId") Long userId
            ) throws IOException {

        OauthMember uploadedUser = oauthService.findUploadUser(userId);
        if(uploadedUser==null){
            logger.info("user not found");
            return ResponseEntity.badRequest().build();
        }
        SmallFileMetaDto smallFileMetaDto = objectMapper.readValue(fileMeta, SmallFileMetaDto.class);
        List<CompletableFuture<String>> url =  new ArrayList<>();

        if(type.equals("image")){
            //image upload dto 설정
            ImageUploadDto imageUploadDto = new ImageUploadDto();
            imageUploadDto.setBigImageFiles(bigFiles);
            imageUploadDto.setSmallImageFiles(smallFiles);
            imageUploadDto.setOauthMember(uploadedUser);

            //image Member 설정
            ImageMember imageMember = new ImageMember();
            imageMember.setImageTitle(smallFileMetaDto.getFileTitle());
            imageMember.setImageDescription(smallFileMetaDto.getFileDescription());
            imageMember.setImageTime(smallFileMetaDto.getFileDate());
            imageMember.setUploadImageList(uploadedUser);

            imageUploadDto.setImageMember(imageMember);
            url = smallFileService.uploadImages(imageUploadDto);
        }
        else if(type.equals("video")){
            VideoUploadDto videoUploadDto = new VideoUploadDto();
            videoUploadDto.setVideos(bigFiles);
            videoUploadDto.setOauthMember(uploadedUser);
            videoUploadDto.setCoverImage(smallFiles);

            VideoMember videoMember = new VideoMember();
            videoMember.setVideoTitle(smallFileMetaDto.getFileTitle());
            videoMember.setVideoDescription(smallFileMetaDto.getFileDescription());
            videoMember.setVideoTime(smallFileMetaDto.getFileDate());
            videoMember.setUploadVideoList(uploadedUser);

            videoUploadDto.setVideoMember(videoMember);
            url = smallFileService.uploadVideos(videoUploadDto);
        }

        //연결될 actor, musical 가져오기
        List<Long> actorId = smallFileMetaDto.getActorId();
        Long musicalId = smallFileMetaDto.getMusicalId();

        //actor, musical, image db update

        return ResponseEntity.ok(url.get(0));
    }

    @PostMapping("/api/douwnload/image")
    ResponseEntity<CompletableFuture<InputStream>> sendSmall(
            @RequestParam("userId") Long userId
    ) throws IOException{
        OauthMember oauthMember = oauthService.findUploadUser(userId);
        CompletableFuture<InputStream> image = smallFileService.sendRawImage(oauthMember);

        return ResponseEntity.ok(image);
    }
}
