package com.easylocate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for handling web-related configurations.
 * This class specifically configures the serving of static resources (uploaded images)
 * through a specific URL pattern.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    /**
     * Configures the resource handlers for serving static files.
     * Maps the /images/** URL pattern to the physical file location where images are stored.
     * 
     * @param registry The ResourceHandlerRegistry used to register resource handlers
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }
} 
