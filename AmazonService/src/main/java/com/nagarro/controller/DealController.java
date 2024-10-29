package com.nagarro.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.model.DealItem;
import com.nagarro.model.DealResponse; // Import the DealResponse
import com.nagarro.repository.DealItemRepository;

@RestController
@RequestMapping("/backendserver1/amazon/deals")
public class DealController {

    private final DealItemRepository dealItemRepository;

    public DealController(DealItemRepository dealItemRepository) {
        this.dealItemRepository = dealItemRepository;
    }

    @GetMapping("/{categoryName}")
    public DealResponse getDealsByCategory(@PathVariable String categoryName) {
        List<DealItem> dealItems = dealItemRepository.findByCategoryName(categoryName);
        return new DealResponse(categoryName, dealItems);
    }
}
