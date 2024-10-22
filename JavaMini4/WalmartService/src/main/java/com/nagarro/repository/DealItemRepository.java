package com.nagarro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.model.DealItem;


public interface DealItemRepository extends JpaRepository<DealItem, String> {
    List<DealItem> findByCategoryName(String categoryName);
}