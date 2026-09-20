package com.eventhub.controller;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/uploads")
@CrossOrigin(origins = "*")
public class FileUploadController {

    private final String uploadDirectory = "uploads/events/";

    @PostMapping("/event-image")
    public ResponseEntity<String> uploadEventImage(
            @RequestParam("image") MultipartFile image)
            throws IOException {

        if (image == null || image.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Please select an image");
        }

        String contentType =
                image.getContentType();

        if (contentType == null ||
                !(contentType.equals("image/jpeg")
                || contentType.equals("image/png")
                || contentType.equals("image/webp"))) {

            return ResponseEntity.badRequest()
                    .body("Only JPG, PNG and WEBP images are allowed");
        }

        Path uploadPath =
                Paths.get(uploadDirectory);

        Files.createDirectories(uploadPath);

        String originalName =
                image.getOriginalFilename();

        String extension = "";

        if (originalName != null &&
                originalName.contains(".")) {

            extension =
                    originalName.substring(
                            originalName.lastIndexOf("."));
        }

        String fileName =
                UUID.randomUUID() + extension;

        Path filePath =
                uploadPath.resolve(fileName);

        Files.copy(
                image.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        String imageUrl =
                "/uploads/events/" + fileName;

        return ResponseEntity.ok(imageUrl);
    }
}