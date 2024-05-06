package backend.musicalmate.service;

import backend.musicalmate.Member.ImageMember;
import backend.musicalmate.domain.dto.ImageUploadDto;
import backend.musicalmate.domain.repository.ImageMemberRepository;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private String bucketName = "musicalmatemute/image";

    private final AmazonS3Client amazonS3Client;

    private final ImageMemberRepository imageMemberRepository;

    //img 여러개 처리
    public List<String> uploadImages(ImageUploadDto imageUploadDto){
        List<String> resultList = new ArrayList<>();

        int i=0;
        for(MultipartFile multipartFile: imageUploadDto.getMultipartFiles()){
            ImageMember image = imageUploadDto.getImageMembers().get(i);

            String value = uploadImage(multipartFile, image);
            resultList.add(value);
            i++;
        }

        return resultList;
    }


    //img 1개
    @Transactional
    public String uploadImage(MultipartFile multipartFile, ImageMember image){
        String title = image.getImageTitle();

        try{
            InputStream is = multipartFile.getInputStream();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());

            amazonS3Client.putObject(bucketName,title,is,objectMetadata);

            String accessUrl = amazonS3Client.getUrl(bucketName,title).toString();
            image.setImageUrl(accessUrl);

        } catch (IOException e){

        }

        imageMemberRepository.save(image);

        return image.getImageUrl();
    }
}
