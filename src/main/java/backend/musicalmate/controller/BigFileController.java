package backend.musicalmate.controller;

import backend.musicalmate.Member.ImageMember;
import backend.musicalmate.Member.OauthMember;
import backend.musicalmate.domain.dto.FileMetaDto;
import backend.musicalmate.domain.dto.ImageUploadDto;
import backend.musicalmate.service.OauthService;
import backend.musicalmate.service.BigFileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@Slf4j
public class BigFileController {
    private final OauthService oauthService;
    private final BigFileService bigFileService;
    private final ObjectMapper objectMapper;
    private Logger logger = LoggerFactory.getLogger(BigFileController.class);


    @PostMapping("/api/upload/big/{type}")
    ResponseEntity<List<CompletableFuture<String>>> uploadBigFile(
            @PathVariable String type,
            @RequestPart("fileTitles") List<String> fileTitles,
            @RequestPart("smallFiles") List<MultipartFile> smallFiles,
            @RequestPart("fileMeta") String fileMeta,
            @RequestHeader("userId") Long userId
    ) throws IOException {

        OauthMember uploadedUser = oauthService.findUploadUser(userId);
        if(uploadedUser==null){
            logger.info("user not found");
            return ResponseEntity.badRequest().build();
        }
        FileMetaDto fileMetaDto = objectMapper.readValue(fileMeta, FileMetaDto.class);

        //연결될 actor, musical 가져오기
        List<Long> actorId = fileMetaDto.getActorId();
        Long musicalId = fileMetaDto.getMusicalId();

        //key 만들기
        List<String> keys = new ArrayList<>();
        Instant now = Instant.now();
        for(int i=0;i<fileTitles.size();i++){
            String key = fileTitles.get(i).concat(now.toString());
            key.concat(Integer.toString(i));
            keys.add(key);
        }

        List<CompletableFuture<String>> preSignedUrls = new ArrayList<>();

        if(type.equals("image")){
            //image upload dto 설정
            ImageUploadDto imageUploadDto = new ImageUploadDto();
            imageUploadDto.setSmallImageFiles(smallFiles);
            imageUploadDto.setOauthMember(uploadedUser);

            //image Member 설정
            ImageMember imageMember = new ImageMember();
            imageMember.setImageTitle(fileMetaDto.getFileTitle());
            imageMember.setImageDescription(fileMetaDto.getFileDescription());
            imageMember.setImageTime(fileMetaDto.getFileDate());
            imageMember.setUploadImageList(uploadedUser);

            imageUploadDto.setImageMember(imageMember);

            //smallImage 저장하고 DB 업데이트
            bigFileService.updateImageDB(imageUploadDto,keys);
            //url 받아오기
            preSignedUrls = bigFileService.createPresignedPutUrls(keys);
        }


        //fileMeta -> 백에서 저장/ 이 경우에는 link 저장하지 않기
        //title의 경우 백에서 만든 후 link와 함께 주기 => 이게 나중에 다운 받기 위한 key!
        //original title로 요청이 오면 이거를 key로 만들어서 다운 받을 파일을 찾는 것
        //현재 key = title + time (small의 경우 small 추가)

        return ResponseEntity.ok(preSignedUrls);
    }
}
