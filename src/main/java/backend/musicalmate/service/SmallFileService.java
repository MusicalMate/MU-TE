package backend.musicalmate.service;

import backend.musicalmate.Member.ImageMember;
import backend.musicalmate.Member.OauthMember;
import backend.musicalmate.Member.VideoMember;
import backend.musicalmate.domain.dto.ImageUploadDto;
import backend.musicalmate.domain.dto.VideoUploadDto;
import backend.musicalmate.domain.repository.ImageMemberRepository;

import backend.musicalmate.domain.repository.VideoMemberRepository;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.IOException;
import java.io.InputStream;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SmallFileService {

    private String iniBucketName = "musicalmatemute";
    String bucketNameImage = iniBucketName+"/image";
    String bucketNameVideo = iniBucketName+"/video";
    private final AmazonS3Client amazonS3Client;

    private final ImageMemberRepository imageMemberRepository;
    private final VideoMemberRepository videoMemberRepository;

    //img 여러개 처리
    public List<CompletableFuture<String>> uploadImages(ImageUploadDto imageUploadDto){
        List<CompletableFuture<String>> resultList = new ArrayList<>();

        int i=0;
        for(MultipartFile big: imageUploadDto.getBigImageFiles()){
            ImageMember image = imageUploadDto.getImageMember();
            MultipartFile small = imageUploadDto.getSmallImageFiles().get(i);

            CompletableFuture<String> value = uploadImage(big, small, image);
            resultList.add(value);
            i++;
        }

        return resultList;
    }
    public List<CompletableFuture<String>> uploadVideos(VideoUploadDto videoUploadDto){
        List<CompletableFuture<String>> resultList = new ArrayList<>();

        int i=0;
        for(MultipartFile big: videoUploadDto.getVideos()){
            VideoMember video = videoUploadDto.getVideoMember();
            MultipartFile small = videoUploadDto.getCoverImage().get(i);

            CompletableFuture<String> value = uploadVideo(big, small, video);
            resultList.add(value);
            i++;
        }

        return resultList;
    }

    //img 1개
    @Transactional
    @Async
    public CompletableFuture<String> uploadImage(MultipartFile big, MultipartFile small, ImageMember image){
        String title = image.getImageTitle();
        //key = 사용자 입력 title + 저장 시간 -> 이걸로 불러오기 때문에 중복을 피하기 위해서
        Instant now = Instant.now();
        String key = title.concat(now.toString());
        image.setImageKey(key);

        String smallKey = key.concat("small");
        image.setSmallImageKey(smallKey);

        try{
            InputStream bigImage = big.getInputStream();
            InputStream smallImage = small.getInputStream();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(big.getContentType());
            objectMetadata.setContentLength(big.getSize());

            ObjectMetadata objectMetadataSmall = new ObjectMetadata();
            objectMetadataSmall.setContentType(small.getContentType());
            objectMetadataSmall.setContentLength(small.getSize());

            amazonS3Client.putObject(bucketNameImage,key,bigImage,objectMetadata);

            String accessUrl = amazonS3Client.getUrl(bucketNameImage,key).toString();
            image.setImageUrl(accessUrl);

            amazonS3Client.putObject(bucketNameImage,smallKey,smallImage,objectMetadataSmall);
            String smallUrl = amazonS3Client.getUrl(bucketNameImage,smallKey).toString();
            image.setSmallImageUrl(smallUrl);

        } catch (IOException e){

        }

        imageMemberRepository.save(image);

        return CompletableFuture.completedFuture(image.getImageUrl());
    }


    @Transactional
    @Async
    public CompletableFuture<String> uploadVideo(MultipartFile big, MultipartFile small, VideoMember video){
        String title = video.getVideoTitle();
        //key = 사용자 입력 title + 저장 시간 -> 이걸로 불러오기 때문에 중복을 피하기 위해서
        Instant now = Instant.now();
        String key = title.concat(now.toString());
        video.setVideoKey(key);

        String smallKey = key.concat("small");
        video.setCoverImageKey(smallKey);

        try{
            InputStream bigVideo = big.getInputStream();
            InputStream smallVideo = small.getInputStream();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(big.getContentType());
            objectMetadata.setContentLength(big.getSize());

            ObjectMetadata objectMetadataSmall = new ObjectMetadata();
            objectMetadataSmall.setContentType(small.getContentType());
            objectMetadataSmall.setContentLength(small.getSize());

            amazonS3Client.putObject(bucketNameVideo,key,bigVideo,objectMetadata);

            String accessUrl = amazonS3Client.getUrl(bucketNameVideo,key).toString();
            video.setVideoUrl(accessUrl);

            amazonS3Client.putObject(bucketNameVideo,smallKey,smallVideo,objectMetadataSmall);
            String smallUrl = amazonS3Client.getUrl(bucketNameVideo,smallKey).toString();
            video.setCoverImageUrl(smallUrl);

        } catch (IOException e){

        }

        videoMemberRepository.save(video);

        return CompletableFuture.completedFuture(video.getVideoUrl());
    }


    //db에서 꺼내서 보내주기
    @Async
    public CompletableFuture<InputStream> sendRawImage(OauthMember oauthMember) throws IOException{
        Long imageId = imageMemberRepository.findByUploadImageList(oauthMember).getImageId();
        String imageKey = imageMemberRepository.findByUploadImageList(oauthMember).getImageKey();

        S3Object s3Object = amazonS3Client.getObject(bucketNameImage, imageKey);

        InputStream objectData = s3Object.getObjectContent();

        //File localFile = new File("C:/computer_capston/test.mp4");
//        OutputStream outputStream = new FileOutputStream(localFile);
//
//        byte[] buffer = new byte[1024];
//        int bytesRead;
//        while ((bytesRead = objectData.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, bytesRead);
//        }
//
//        objectData.close();
//        outputStream.close();

        return CompletableFuture.completedFuture(objectData);
    }
}
