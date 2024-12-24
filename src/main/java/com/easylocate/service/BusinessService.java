package com.easylocate.service;

import com.easylocate.model.Business;
import com.easylocate.model.Category;
import com.easylocate.repository.BusinessRepository;
import com.easylocate.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {
    
    @Autowired
    private BusinessRepository businessRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(BusinessService.class);
    
    public List<Business> getAllBusinesses() {
        return businessRepository.findAll();
    }
    
    public Optional<Business> getBusinessById(Long id) {
        return businessRepository.findById(id);
    }

    public Business saveBusiness(Business business) {
        if (business.getCategory() != null && business.getCategory().getId() != null) {
            Category fullCategory = categoryRepository.findById(business.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            business.setCategory(fullCategory);
        }
        return businessRepository.save(business);
    }
    
    public void deleteBusiness(Long id) {
        businessRepository.deleteById(id);
    }
    
    public List<Category> getAllCategories() {
        logger.info("Fetching all categories");
        return categoryRepository.findAll();
    }
}
