package dev.vorstu.services;


import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioService {

    @Value("${minio.buckek.name}")
    String defaultBucketName;



    @Autowired
    private MinioClient minioClient;

    public void uploadFile(String objectName, String filePath) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, ServerException, ErrorResponseException, InvalidResponseException, XmlParserException {
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(defaultBucketName)
                        .object(objectName)
                        .filename(filePath)
                        .build());
    }

    public InputStream downloadFile( String objectName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, ErrorResponseException, InternalException, ServerException, InvalidResponseException, XmlParserException {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(defaultBucketName)
                        .object(objectName)
                        .build());
    }
}