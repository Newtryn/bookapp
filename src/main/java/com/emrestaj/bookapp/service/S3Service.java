package com.emrestaj.bookapp.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3Service {

    private static final Logger logger = LogManager.getLogger(S3Service.class);
    private final S3Client s3Client;
    private final String bucketName = "emre-bookapp-files-2025";

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadFile(MultipartFile file) throws IOException {
        String key = file.getOriginalFilename();
        logger.info("Uploading to S3: {}", key);

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );

        logger.info("Successfully uploaded file: {}", key);
    }

    public byte[] downloadFile(String key) throws IOException {
        logger.info("Downloading from S3: {}", key);

        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build()
        );

        byte[] data = response.readAllBytes();
        logger.info("Successfully downloaded file: {}", key);
        return data;
    }

    public List<String> listAllFiles() {
        logger.info("Listing all files in S3 bucket: {}", bucketName);

        ListObjectsV2Response response = s3Client.listObjectsV2(
                ListObjectsV2Request.builder()
                        .bucket(bucketName)
                        .build()
        );

        List<String> fileNames = response.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());

        logger.info("Found {} files", fileNames.size());
        return fileNames;
    }
}
