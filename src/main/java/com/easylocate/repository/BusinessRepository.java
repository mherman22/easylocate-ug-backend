package com.easylocate.repository;

import com.easylocate.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    List<Business> findBusinessByCategoryId(Long categoryId);
}
