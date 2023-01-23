package dev.vorstu.adapter;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

@Service
@Slf4j
public class MinioAdapter {
    @Autowired
    MinioClient minioClient;

    @Value("${minio.buckek.name}")
    String defaultBucketName;

    @Value("${minio.default.folder}")
    String defaultBaseFolder;

    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


    public void uploadFile(String name, byte[] content) {
    	log.warn("Upload File");
    	File file = new File("/tmp/" + name);
    	// file.canWrite();
    	// file.canRead();
    	try {
    	// ByteArrayInputStream bais = new ByteArrayInputStream(file.toString().getBytes("UTF-8"));
    	// FileOutputStream iofs = new FileOutputStream(file);
    	// iofs.write(content);
    	// minioClient.uploadObject(
    	// UploadObjectArgs.builder()
    	// .bucket(defaultBucketName)
    	// .object(defaultBaseFolder)
    	// .filename(file.getName())
    	// .contentType("image/jpg")
    	// .build());
    	// bais.close();
    	String bucketName = "java-demo-bucket";
    	String objectName = "image.png";
    	String contentType = "image/png";
    	//String filename = "/tmp/demo/im.png";
    	UploadObjectArgs uArgs = UploadObjectArgs.builder()
    	.bucket(defaultBucketName)
    	.object(name)
    	.filename(file.getName())
    	.contentType(contentType)
    	.build();
    	// ObjectWriteResponse resp = minioClient.uploadObject(uArgs);
    	} catch (Exception e) {
    	throw new RuntimeException(e.getMessage());
    	}

    	}

    public byte[] getFile(String key) {
    	log.warn("getFile");
    	try {
    	InputStream obj = minioClient.getObject(
    	GetObjectArgs.builder().bucket(defaultBucketName).object(defaultBaseFolder + "/" + key).build());

    	byte[] content = IOUtils.toByteArray(obj);
    	obj.close();
    	return content;
    	} catch (Exception e) {
    	e.printStackTrace();
    	}
    	return null;
    	}

    @PostConstruct
    public void init() {
    }
}
