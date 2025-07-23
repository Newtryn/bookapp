package com.emrestaj.bookapp.Controllers;

import com.emrestaj.bookapp.service.S3Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    private static final Logger logger = LogManager.getLogger(S3Controller.class);
    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        logger.info("POST /api/s3/upload - Uploading file: {}", file.getOriginalFilename());
        try {
            s3Service.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            logger.error("Upload failed for file {}: {}", file.getOriginalFilename(), e.getMessage());
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable String filename) {
        logger.info("GET /api/s3/download/{} - Downloading file", filename);
        try {
            byte[] content = s3Service.downloadFile(filename);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + filename)
                    .body(content);
        } catch (Exception e) {
            logger.error("Download failed for file {}: {}", filename, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<String>> listFiles() {
        logger.info("GET /api/s3/files - Listing all files in bucket");
        try {
            List<String> files = s3Service.listAllFiles();
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            logger.error("Failed to list files: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
