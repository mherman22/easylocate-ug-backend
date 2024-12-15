package com.easylocate.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Service class responsible for handling image upload operations.
 * This service manages the storage of images in the file system and provides
 * URLs for accessing the uploaded images.
 */
@Service
public class ImageUploadService {
    
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;
    
    /**
     * Uploads an image file to the server's file system.
     * 
     * @param file The MultipartFile containing the image data to be uploaded
     * @return String The relative URL path where the image can be accessed
     * @throws IOException If there's an error reading the file or writing to the filesystem
     * @throws IllegalArgumentException If the file is empty or has an invalid extension
     */
    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Failed to store empty file");
        }
        
        validateFileExtension(file.getOriginalFilename());
        
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString() + "." + extension;
        
        Path path = Paths.get(uploadDir, newFileName);
        Files.write(path, file.getBytes());
        
        return "/images/" + newFileName;
    }
    
    /**
     * Validates the file extension to ensure only image files are uploaded.
     * 
     * @param filename The original filename to validate
     * @throws IllegalArgumentException If the file extension is not allowed
     */
    private void validateFileExtension(String filename) {
        String extension = FilenameUtils.getExtension(filename).toLowerCase();
        if (!extension.matches("jpg|jpeg|png|gif")) {
            throw new IllegalArgumentException("Only image files (jpg, jpeg, png, gif) are allowed");
        }
    }
}
