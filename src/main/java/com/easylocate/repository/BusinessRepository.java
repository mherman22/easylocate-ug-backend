package com.easylocate.repository;

import com.easylocate.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    List<Business> findByCategory(String category);

    @Query("SELECT DISTINCT LOWER(b.category) AS category FROM Business b")
    List<String> findAllCategories();

    List<Business> findByCategoryId(Long categoryId);
}
