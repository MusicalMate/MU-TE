package backend.musicalmate.service;

import backend.musicalmate.Member.ImageMember;
import backend.musicalmate.domain.dto.S3UploadDto;
import backend.musicalmate.domain.dto.multipart.*;
import backend.musicalmate.domain.repository.VideoMemberRepository;

import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import com.amazonaws.services.s3.AmazonS3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedUploadPartRequest;
import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest;


import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoMemberRepository videoMemberRepository;
    private String bucketName = "musicalmatemute/video";
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final AmazonS3Client amazonS3Client;


    public S3UploadDto initialUpload(MultipartFile multipartFile, ImageMember imageMember){
        String fileType = multipartFile.getContentType();
        Instant now = Instant.now();

        CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(imageMember.getImageTitle())
                .acl(ObjectCannedACL.PUBLIC_READ)
                .expires(now.plusSeconds(60*20))
                .build();

        CreateMultipartUploadResponse response = s3Client.createMultipartUpload(createMultipartUploadRequest);

        return new S3UploadDto(response.uploadId(),imageMember.getImageTitle());
    }

    public S3PresignedUrlDto getUploadSignedUrl(S3UploadSignedUrlDto s3UploadSignedUrlDto,MultipartFile multipartFile, ImageMember image){

        UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                .bucket(bucketName)
                .key(image.getImageTitle())
                .uploadId(s3UploadSignedUrlDto.getUploadId())
                .partNumber(s3UploadSignedUrlDto.getPartNumber())
                .build();

        UploadPartPresignRequest uploadPartPresignRequest = UploadPartPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .uploadPartRequest(uploadPartRequest)
                .build();

        PresignedUploadPartRequest presignedUploadPartRequest = s3Presigner.presignUploadPart(uploadPartPresignRequest);

        return new S3PresignedUrlDto(presignedUploadPartRequest.url().toString());
    }

    public S3UploadResultDto completeUpload(S3UploadCompleteDto s3UploadCompleteDto, MultipartFile multipartFile, ImageMember image){
        List<CompletedPart> completedParts = new ArrayList<>();

        for(S3UploadPartsDetailDto partForm : s3UploadCompleteDto.getParts()){
            CompletedPart part = CompletedPart.builder()
                    .partNumber(partForm.getPartNumber())
                    .eTag(partForm.getAwsETag())
                    .build();
            completedParts.add(part);
        }

        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
                .parts(completedParts)
                .build();
        String fileName = s3UploadCompleteDto.getFileName();
        CompleteMultipartUploadRequest completeMultipartUploadRequest = CompleteMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(image.getImageTitle())
                .uploadId(s3UploadCompleteDto.getUploadId())
                .multipartUpload(completedMultipartUpload)
                .build();

        CompleteMultipartUploadResponse completeMultipartUploadResponse = s3Client.completeMultipartUpload(completeMultipartUploadRequest);

        String objectKey = completeMultipartUploadResponse.key();

        String url = amazonS3Client.getUrl(bucketName,image.getImageTitle()).toString();
        String bucket = completeMultipartUploadResponse.bucket();

        long fileSize = getFileSizeFromS3Url(bucket,objectKey);

        return S3UploadResultDto.builder()
                .name(image.getImageTitle())
                .url(url)
                .size(fileSize)
                .build();
    }

    public void abortUpload(S3UploadAbortDto s3UploadAbortDto, MultipartFile multipartFile, ImageMember imageMember){
        AbortMultipartUploadRequest abortMultipartUploadRequest = AbortMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(imageMember.getImageTitle())
                .uploadId(s3UploadAbortDto.getUploadId())
                .build();
        s3Client.abortMultipartUpload(abortMultipartUploadRequest);
    }

    private long getFileSizeFromS3Url(String bucket, String file){
        GetObjectMetadataRequest metadataRequest = new GetObjectMetadataRequest(bucket, file);
        ObjectMetadata objectMetadata = amazonS3Client.getObjectMetadata(metadataRequest);
        return objectMetadata.getContentLength();
    }

//    @Transactional
//    public List<String> uploadVideos(VideoUploadDto videos){
//        int i=0;
//        List<String> urls = new ArrayList<>();
//        for(MultipartFile multipartFile : videos.getVideos()){
//            VideoMember videoMember = videos.getVideoMembers().get(i);
//            String url = uploadVideo(multipartFile,videoMember);
//            urls.add(url);
//            i++;
//        }
//        return urls;
//    }
//
//    @Transactional
//    public String uploadVideo(MultipartFile multipartFile, VideoMember video){
//        String title = video.getVideoTitle();
//
//        try{
//            InputStream is = multipartFile.getInputStream();
//
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentType(multipartFile.getContentType());
//            objectMetadata.setContentLength(multipartFile.getSize());
//
//            amazonS3Client.putObject(new PutObjectRequest(bucketName,title,is,objectMetadata));
//
//            String accessUrl = amazonS3Client.getUrl(bucketName,title).toString();
//            video.setVideoUrl(accessUrl);
//
//        } catch (IOException e){
//
//        }
//
//        videoMemberRepository.save(video);
//
//        return video.getVideoUrl();
//    }
//
//    //download test code
//    public String downloadVideo(String title) throws IOException {
//        AmazonS3 s3Client = amazonS3Client;
//
//        S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, title));
//
//        InputStream objectData = s3Object.getObjectContent();
//
//        File localFile = new File("C:/computer_capston/test.mp4");
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
//
//        return "test";
//    }

}
