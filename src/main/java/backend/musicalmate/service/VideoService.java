package backend.musicalmate.service;

import backend.musicalmate.Member.VideoMember;
import backend.musicalmate.domain.dto.VideoUploadDto;
import backend.musicalmate.domain.repository.VideoMemberRepository;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoMemberRepository videoMemberRepository;
    private String bucketName = "musicalmatemute/video";
    private final AmazonS3Client amazonS3Client;

    @Transactional
    public List<String> uploadVideos(VideoUploadDto videos){
        int i=0;
        List<String> urls = new ArrayList<>();
        for(MultipartFile multipartFile : videos.getVideos()){
            VideoMember videoMember = videos.getVideoMembers().get(i);
            String url = uploadVideo(multipartFile,videoMember);
            urls.add(url);
            i++;
        }
        return urls;
    }

    @Transactional
    public String uploadVideo(MultipartFile multipartFile, VideoMember video){
        String title = video.getVideoTitle();

        try{
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getInputStream().available());

            amazonS3Client.putObject(bucketName, title, multipartFile.getInputStream().toString());

            String accessUrl = amazonS3Client.getUrl(bucketName,title).toString();
            video.setVideoUrl(accessUrl);

        } catch (IOException e){

        }

        videoMemberRepository.save(video);

        return video.getVideoUrl();
    }

}
