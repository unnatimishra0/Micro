package com.nagarro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.model.DealItem;

@Repository
public interface DealItemRepository extends JpaRepository<DealItem, String> {
    List<DealItem> findByCategoryName(String categoryName);
}