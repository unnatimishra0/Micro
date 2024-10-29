package com.nagarro.controller;

import com.nagarro.model.DealResponse;
import com.nagarro.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/deals")
public class DealController {

    @Autowired
    private DealService dealService;

    /**
     * Get filtered and sorted deals based on category name.
     * @param categoryName the name of the category to filter the deals.
     * @return a Mono containing the list of DealResponse.
     */
    @GetMapping
    public Mono<List<DealResponse>> getDeals(@RequestParam String categoryName) {
        return dealService.getDeals(categoryName);
    }
}
