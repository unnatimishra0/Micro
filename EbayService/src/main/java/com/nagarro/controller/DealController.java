package com.nagarro.controller;

import com.nagarro.model.DealItem;
import com.nagarro.model.DealResponse;
import com.nagarro.repository.DealItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/backendserver2/ebay")
public class DealController {

    private final DealItemRepository repository;

    public DealController(DealItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/deals/{categoryName}")
    public DealResponse getDealsByCategory(@PathVariable String categoryName) {
        List<DealItem> items = repository.findAll(); // Fetch all DealItems
        
        // Filter items based on the categoryName
        List<DealItem> filteredItems = items.stream()
            .filter(item -> item.getCategoryName().equalsIgnoreCase(categoryName))
            .toList();

        return new DealResponse(categoryName, filteredItems);
    }
}
