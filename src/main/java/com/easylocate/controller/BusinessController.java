package com.easylocate.controller;

import com.easylocate.model.Business;
import com.easylocate.model.Category;
import com.easylocate.service.BusinessService;
import com.easylocate.service.ImageUploadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * REST Controller for managing business-related operations.
 * Provides endpoints for CRUD operations on businesses and handles image uploads.
 */
@RestController
@RequestMapping("/api/businesses")
@CrossOrigin(origins = "*")
@SuppressWarnings("unused")
public class BusinessController {
    
    @Autowired
    private BusinessService businessService;
    
    @Autowired
    private ImageUploadService imageUploadService;
    
    @GetMapping
    public List<Business> getAllBusinesses() {
        return businessService.getAllBusinesses();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Business>> getBusinessesByCategory(@PathVariable Long categoryId) {
        List<Business> businesses = businessService.getBusinessesByCategory(categoryId);
        if (businesses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(businesses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Business> getBusinessById(@PathVariable Long id) {
        return businessService.getBusinessById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Creates a new business with an uploaded image.
     * This endpoint handles multipart form data containing both the business information
     * and an image file.
     *
     * @param image The image file uploaded for the business
     * @param businessJson The business data in JSON format
     * @return The created business entity with the image URL
     * @throws RuntimeException If there's an error processing the image upload
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Business createBusiness(
            @RequestPart(value = "image", required = true) MultipartFile image,
            @RequestPart(value = "business", required = true) String businessJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Business business = objectMapper.readValue(businessJson, Business.class);

            String imageUrl = imageUploadService.uploadImage(image);
            business.setImageUrl(imageUrl);

            return businessService.saveBusiness(business);
        } catch (IOException e) {
            throw new RuntimeException("Failed to process request", e);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Business> updateBusiness(@PathVariable Long id, @RequestBody Business business) {
        return businessService.getBusinessById(id)
                .map(existingBusiness -> {
                    business.setId(id);
                    return ResponseEntity.ok(businessService.saveBusiness(business));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id) {
        return businessService.getBusinessById(id)
                .map(business -> {
                    businessService.deleteBusiness(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = businessService.getAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }
}
