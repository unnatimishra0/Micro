package com.nagarro.repository;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.nagarro.model.DealItem;

@Component
public class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(DealItemRepository repository) {
        return args -> {

            // First DealItem
            Optional<DealItem> existingItem1 = repository.findById("777654321");
            if (existingItem1.isEmpty()) {
                repository.save(new DealItem(
                    "777654321", 
                    "Men Black Regular Jeans", 
                    "32", 
                    "H&M", 
                    "Jeans",  // Added category name
                    new DealItem.Image("https://i.ebayimg.com/images/g/~**********N/s-l225.jpg"),
                    new DealItem.MarketingPrice(
                        new DealItem.MarketingPrice.OriginalPrice("29.99", "USD"),
                        "40.0",
                        new DealItem.MarketingPrice.DiscountAmount("11.99", "USD"),
                        "LIST_PRICE"
                    ),
                    new DealItem.Price("17.99", "USD"),
                    4,
                    "2023-06-20T15:26:00Z",
                    "2023-12-20T14:59:59Z"
                ));
            }

            // Second DealItem
            Optional<DealItem> existingItem2 = repository.findById("55666778231");
            if (existingItem2.isEmpty()) {
                repository.save(new DealItem(
                    "55666778231", 
                    "Light Fade Clean Look Stretchable Jeans", 
                    "32", 
                    "Louis Philippe Jeans", 
                    "Jeans",  // Added category name
                    new DealItem.Image("https://i.ebayimg.com/images/g/~**********N/s-l225.jpg"),
                    new DealItem.MarketingPrice(
                        new DealItem.MarketingPrice.OriginalPrice("39.99", "USD"),
                        "25.0",
                        new DealItem.MarketingPrice.DiscountAmount("09.99", "USD"),
                        "LIST_PRICE"
                    ),
                    new DealItem.Price("29.99", "USD"),
                    3,
                    "2023-06-20T15:26:00Z",
                    "2023-12-20T14:59:59Z"
                ));
            }
        };
    }
}
