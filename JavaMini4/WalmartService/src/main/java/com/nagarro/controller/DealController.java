package com.nagarro.controller;

import com.nagarro.model.DealItem;
import com.nagarro.model.DealResponse;
import com.nagarro.repository.DealItemRepository; // Assuming you have a repository for DealItem
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DealController {

    private final DealItemRepository dealItemRepository;

    @Autowired
    public DealController(DealItemRepository dealItemRepository) {
        this.dealItemRepository = dealItemRepository;
    }

    @GetMapping("/backendserver3/walmart/deals/{categoryName}")
    public DealResponse getDealsByCategory(@PathVariable String categoryName) {
        // Fetching deal items based on category
        List<DealItem> dealItems = dealItemRepository.findByCategoryName(categoryName);
        
        // Return the response with the category name and the list of deal items
        return new DealResponse(categoryName, dealItems);
    }
}
