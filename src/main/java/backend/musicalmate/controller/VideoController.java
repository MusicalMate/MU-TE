package backend.musicalmate.controller;

import backend.musicalmate.Member.OauthMember;
import backend.musicalmate.Member.VideoMember;
import backend.musicalmate.domain.dto.VideoUploadDto;
import backend.musicalmate.service.OauthService;
import backend.musicalmate.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class VideoController {
    private final OauthService oauthService;
    private final VideoService videoService;
//    @PostMapping("/api/upload/video")
//    ResponseEntity<String> uploadVideo(
//            @RequestPart("videos") List<MultipartFile> videos,
//            @RequestPart("metaData") String metaData
//    ){
//        VideoUploadDto videoUploadDto = new VideoUploadDto();
//        videoUploadDto.setVideos(videos);
//
//        long userId = 1L;
//        OauthMember oauthMember;
//        try{
//            oauthMember = oauthService.findUploadUser(userId);
//        }catch(Exception e){
//            return ResponseEntity.ofNullable("wrong user");
//        }
//        videoUploadDto.setOauthMember(oauthMember);
//
//        int videoNum = videos.size();
//        List<VideoMember> videoMembers = new ArrayList<>();
//        for(int i=0;i<videoNum;i++){
//            VideoMember videoMember = new VideoMember();
//            videoMember.setVideoTitle("test"+i);
//            videoMember.setUploadVideoList(oauthMember);
//            videoMembers.add(videoMember);
//        }
//        videoUploadDto.setVideoMembers(videoMembers);
//
//        List<String> urls = new ArrayList<>();
//        urls = videoService.uploadVideos(videoUploadDto);
//
//        return ResponseEntity.ok("ok");
//    }
//
//    @GetMapping("/api/download/video")
//    public ResponseEntity<String> testDown(
//            @RequestParam("title") String title
//    )throws IOException {
//
//        videoService.downloadVideo(title);
//        return ResponseEntity.ok("testOk");
//    }
}
