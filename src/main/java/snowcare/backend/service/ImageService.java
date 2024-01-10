package snowcare.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.utils.StringUtils;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ImageService {
    private final S3Client s3Client;

    // Upload 하고자 하는 버킷의 이름
    private String bucketName = "snowcare-bucket";


    public String uploadImage(MultipartFile multipartFile) throws IOException {
        String key = UUID.randomUUID().toString(); // Storage에 저장될 파일 이름
        return this.putS3(multipartFile, key);
    }

    private RequestBody getFileRequestBody(MultipartFile file) throws IOException {
        return RequestBody.fromInputStream(file.getInputStream(), file.getSize());
    }


    // 실제 업로드 하는 메소드
    private String putS3(MultipartFile file, String key) throws IOException {
        String ext = file.getContentType();
        PutObjectRequest objectRequest =PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(ext)
                .contentLength(file.getSize())
                .build();

        RequestBody rb = getFileRequestBody(file);
        s3Client.putObject(objectRequest, rb);
        return key;
    }


    // 파일 다운로드
    public String processImage(String image) {
        if (StringUtils.isBlank(image)) {
            return null;
        }
        System.out.println(">>>>>> S3 Image uuid >>>>>> "+ image);
        return "https://snowcare-bucket.s3.ap-northeast-2.amazonaws.com/" + image;
    }
}
