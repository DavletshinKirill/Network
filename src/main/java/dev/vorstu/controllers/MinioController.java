package dev.vorstu.controllers;

import dev.vorstu.services.MinioService;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RestController
public class MinioController {

	@Autowired
	MinioService minioService;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			// Save the uploaded file to a temporary location
			Path tempFile = Files.createTempFile("temp", file.getOriginalFilename());
			file.transferTo(tempFile);

			// Upload the file to MinIO
			minioService.uploadFile( file.getOriginalFilename(), tempFile.toString());

			// Delete the temporary file
			Files.delete(tempFile);

			return ResponseEntity.ok("File uploaded successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
		}
	}



	@GetMapping(path = "/download")
	public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value = "file") String file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
		byte[] data = minioService.downloadFile(file).readAllBytes();
		ByteArrayResource resource = new ByteArrayResource(data);

		return ResponseEntity
				.ok()
				.contentLength(data.length)
				.header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + file + "\"")
				.body(resource);
	}
}