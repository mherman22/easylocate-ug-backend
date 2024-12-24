package com.easylocate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@SuppressWarnings("unused")
public class FileStorageConfig implements CommandLineRunner {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Override
    public void run(String... args) throws Exception {
        createDirectory(uploadDir);
    }

    private void createDirectory(String dir) throws Exception {
        Path path = Paths.get(dir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}
