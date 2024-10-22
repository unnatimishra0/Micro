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
            Optional<DealItem> existingItem1 = repository.findById("2345678767");
            if (existingItem1.isEmpty()) {
                repository.save(new DealItem(
                    "2345678767", 
                    "Blue American Eagle Jeans", 
                    "32", 
                    "American Eagle", 
                    "Jeans",  // Added category name
                    new DealItem.Image("https://i.ebayimg.com/images/g/~**********N/s-l225.jpg"),
                    new DealItem.MarketingPrice(
                        new DealItem.MarketingPrice.OriginalPrice("49.99", "USD"),
                        "50.0",
                        new DealItem.MarketingPrice.DiscountAmount("24.99", "USD"),
                        "LIST_PRICE"
                    ),
                    new DealItem.Price("24.99", "USD"),
                    4,
                    "2023-06-20T15:26:00Z",
                    "2023-12-20T14:59:59Z"
                ));
            }

            // Second DealItem
            Optional<DealItem> existingItem2 = repository.findById("4567822189");
            if (existingItem2.isEmpty()) {
                repository.save(new DealItem(
                    "4567822189", 
                    "Navy Blue Rare Rabbit Jeans", 
                    "32", 
                    "Rare Rabbit", 
                    "Jeans",  // Added category name
                    new DealItem.Image("https://i.ebayimg.com/images/g/~**********N/s-l225.jpg"),
                    new DealItem.MarketingPrice(
                        new DealItem.MarketingPrice.OriginalPrice("39.99", "USD"),
                        "25.0",
                        new DealItem.MarketingPrice.DiscountAmount("09.99", "USD"),
                        "LIST_PRICE"
                    ),
                    new DealItem.Price("29.99", "USD"),
                    0,
                    "2023-06-20T15:26:00Z",
                    "2023-12-20T14:59:59Z"
                ));
            }
        };
    }
}
