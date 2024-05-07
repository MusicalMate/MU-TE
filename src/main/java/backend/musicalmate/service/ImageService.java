package backend.musicalmate.service;

import backend.musicalmate.Member.ImageMember;
import backend.musicalmate.Member.OauthMember;
import backend.musicalmate.domain.dto.ImageUploadDto;
import backend.musicalmate.domain.repository.ImageMemberRepository;

import com.amazonaws.auth.SdkClock;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ImageService {

    private String bucketName = "musicalmatemute/image";

    private final AmazonS3Client amazonS3Client;

    private final ImageMemberRepository imageMemberRepository;

    //img 여러개 처리
    public List<CompletableFuture<String>> uploadImages(ImageUploadDto imageUploadDto){
        List<CompletableFuture<String>> resultList = new ArrayList<>();

        int i=0;
        for(MultipartFile big: imageUploadDto.getMultipartFiles()){
            ImageMember image = imageUploadDto.getImageMembers().get(i);
            MultipartFile small = imageUploadDto.getSmallImageFiles().get(i);

            CompletableFuture<String> value = uploadImage(big, small, image);
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

            amazonS3Client.putObject(bucketName,key,bigImage,objectMetadata);

            String accessUrl = amazonS3Client.getUrl(bucketName,key).toString();
            image.setImageUrl(accessUrl);

            amazonS3Client.putObject(bucketName,smallKey,smallImage,objectMetadataSmall);
            String smallUrl = amazonS3Client.getUrl(bucketName,smallKey).toString();
            image.setSmallImageUrl(smallUrl);

        } catch (IOException e){

        }

        imageMemberRepository.save(image);

        return CompletableFuture.completedFuture(image.getImageUrl());
    }


    //db에서 꺼내서 보내주기
    @Async
    public CompletableFuture<InputStream> sendRawImage(OauthMember oauthMember) throws IOException{
        Long imageId = imageMemberRepository.findByUploadImageList(oauthMember).getImageId();
        String imageKey = imageMemberRepository.findByUploadImageList(oauthMember).getImageKey();

        S3Object s3Object = amazonS3Client.getObject(bucketName, imageKey);

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
