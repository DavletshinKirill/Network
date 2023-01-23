package dev.vorstu.controllers;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import dev.vorstu.adapter.MinioAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/storage")
@Slf4j
public class MinioStorageController {
	
	 @Autowired
	    MinioAdapter minioAdapter;

	    @GetMapping(path = "/buckets")
	    public List<Bucket> listBuckets() {
	        return minioAdapter.getAllBuckets();
	    }

	    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	    public Map<String, String> uploadFile(@RequestPart(value = "file", required = false) MultipartFile files) throws IOException {
	    	log.warn("Go fuck yourself");
	    	log.warn(files.getOriginalFilename());
	        minioAdapter.uploadFile(files.getOriginalFilename(), files.getBytes());
	        Map<String, String> result = new HashMap<>();
	        result.put("key", files.getOriginalFilename());
	        return result;
	    }

	    @GetMapping(path = "/download")
	    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value = "file") String file) throws IOException {
	        byte[] data = minioAdapter.getFile(file);
	        ByteArrayResource resource = new ByteArrayResource(data);

	        return ResponseEntity
	                .ok()
	                .contentLength(data.length)
	                .header("Content-type", "application/octet-stream")
	                .header("Content-disposition", "attachment; filename=\"" + file + "\"")
	                .body(resource);

	    }

}
