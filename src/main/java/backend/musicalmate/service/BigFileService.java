package backend.musicalmate.service;

import backend.musicalmate.Member.ImageMember;
import backend.musicalmate.domain.dto.S3UploadDto;
import backend.musicalmate.domain.dto.multipart.*;
import backend.musicalmate.domain.repository.VideoMemberRepository;

import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import com.amazonaws.services.s3.AmazonS3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.*;


import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class BigFileService {
    private final VideoMemberRepository videoMemberRepository;
    private String bucketName = "musicalmatemute/video";
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final AmazonS3Client amazonS3Client;

    Logger logger = LoggerFactory.getLogger(BigFileService.class);


    /* Create a presigned URL to use in a subsequent PUT request */
    public String createPresignedPutUrl(String bucketName, String keyName, Map<String, String> metadata) {
        try (S3Presigner presigner = S3Presigner.create()) {

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .metadata(metadata)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))  // The URL expires in 10 minutes.
                    .putObjectRequest(objectRequest)
                    .build();


            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
            String myURL = presignedRequest.url().toString();
            logger.info("Presigned URL to upload a file to: [{}]", myURL);
            logger.info("HTTP method: [{}]", presignedRequest.httpRequest().method());

            return presignedRequest.url().toExternalForm();
        }
    }

    /* Create a pre-signed URL to download an object in a subsequent GET request. */
    public String createPresignedGetUrl(String bucketName, String keyName) {
        try (S3Presigner presigner = S3Presigner.create()) {

            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))  // The URL will expire in 10 minutes.
                    .getObjectRequest(objectRequest)
                    .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
            logger.info("Presigned URL: [{}]", presignedRequest.url().toString());
            logger.info("HTTP method: [{}]", presignedRequest.httpRequest().method());

            return presignedRequest.url().toExternalForm();
        }
    }












//    public S3UploadDto initialUpload(MultipartFile multipartFile, ImageMember imageMember){
//        String fileType = multipartFile.getContentType();
//        Instant now = Instant.now();
//
//        CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
//                .bucket(bucketName)
//                .key(imageMember.getImageTitle())
//                .acl(ObjectCannedACL.PUBLIC_READ)
//                .expires(now.plusSeconds(60*20))
//                .build();
//
//        CreateMultipartUploadResponse response = s3Client.createMultipartUpload(createMultipartUploadRequest);
//
//        return new S3UploadDto(response.uploadId(),imageMember.getImageTitle());
//    }
//
//    public S3PresignedUrlDto getUploadSignedUrl(S3UploadSignedUrlDto s3UploadSignedUrlDto,MultipartFile multipartFile, ImageMember image){
//
//        UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
//                .bucket(bucketName)
//                .key(image.getImageTitle())
//                .uploadId(s3UploadSignedUrlDto.getUploadId())
//                .partNumber(s3UploadSignedUrlDto.getPartNumber())
//                .build();
//
//        UploadPartPresignRequest uploadPartPresignRequest = UploadPartPresignRequest.builder()
//                .signatureDuration(Duration.ofMinutes(10))
//                .uploadPartRequest(uploadPartRequest)
//                .build();
//
//        PresignedUploadPartRequest presignedUploadPartRequest = s3Presigner.presignUploadPart(uploadPartPresignRequest);
//
//        return new S3PresignedUrlDto(presignedUploadPartRequest.url().toString());
//    }
//
//    public S3UploadResultDto completeUpload(S3UploadCompleteDto s3UploadCompleteDto, MultipartFile multipartFile, ImageMember image){
//        List<CompletedPart> completedParts = new ArrayList<>();
//
//        for(S3UploadPartsDetailDto partForm : s3UploadCompleteDto.getParts()){
//            CompletedPart part = CompletedPart.builder()
//                    .partNumber(partForm.getPartNumber())
//                    .eTag(partForm.getAwsETag())
//                    .build();
//            completedParts.add(part);
//        }
//
//        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
//                .parts(completedParts)
//                .build();
//        String fileName = s3UploadCompleteDto.getFileName();
//        CompleteMultipartUploadRequest completeMultipartUploadRequest = CompleteMultipartUploadRequest.builder()
//                .bucket(bucketName)
//                .key(image.getImageTitle())
//                .uploadId(s3UploadCompleteDto.getUploadId())
//                .multipartUpload(completedMultipartUpload)
//                .build();
//
//        CompleteMultipartUploadResponse completeMultipartUploadResponse = s3Client.completeMultipartUpload(completeMultipartUploadRequest);
//
//        String objectKey = completeMultipartUploadResponse.key();
//
//        String url = amazonS3Client.getUrl(bucketName,image.getImageTitle()).toString();
//        String bucket = completeMultipartUploadResponse.bucket();
//
//        long fileSize = getFileSizeFromS3Url(bucket,objectKey);
//
//        return S3UploadResultDto.builder()
//                .name(image.getImageTitle())
//                .url(url)
//                .size(fileSize)
//                .build();
//    }
//
//    public void abortUpload(S3UploadAbortDto s3UploadAbortDto, MultipartFile multipartFile, ImageMember imageMember){
//        AbortMultipartUploadRequest abortMultipartUploadRequest = AbortMultipartUploadRequest.builder()
//                .bucket(bucketName)
//                .key(imageMember.getImageTitle())
//                .uploadId(s3UploadAbortDto.getUploadId())
//                .build();
//        s3Client.abortMultipartUpload(abortMultipartUploadRequest);
//    }
//
//    private long getFileSizeFromS3Url(String bucket, String file){
//        GetObjectMetadataRequest metadataRequest = new GetObjectMetadataRequest(bucket, file);
//        ObjectMetadata objectMetadata = amazonS3Client.getObjectMetadata(metadataRequest);
//        return objectMetadata.getContentLength();
//    }


}
