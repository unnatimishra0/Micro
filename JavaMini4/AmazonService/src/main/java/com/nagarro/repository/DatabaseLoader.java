package com.nagarro.repository;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.nagarro.model.DealItem;
import com.nagarro.model.DealItem.MarketingPrice;

@Component
public class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(DealItemRepository repository) {
        return args -> {
            // First DealItem
            Optional<DealItem> existingItem1 = repository.findById("123456789");
            if (existingItem1.isEmpty()) {
                repository.save(new DealItem(
                    "123456789",                              // Item ID
                    "Blue Levis Jeans",                       // Product Title
                    "30",                                     // Size
                    "Levis",                                  // Brand
                    "Jeans",                                  // Category Name
                    new DealItem.Image("https://i.ebayimg.com/images/g/~**********N/s-l225.jpg"), // Image URL
                    new DealItem.MarketingPrice(
                        new DealItem.MarketingPrice.OriginalPrice("29.99", "USD"), // Original Price
                        "17.0", // Discount Percentage
                        new DealItem.MarketingPrice.DiscountAmount("5.0", "USD"), // Discount Amount
                        "LIST_PRICE"
                    ),
                    new DealItem.Price("24.99", "USD"),      // Price
                    4,                                        // Stock
                    "2022-06-20T15:26:00Z",                  // Deal Start Date
                    "2022-12-20T14:59:59Z"                   // Deal End Date
                ));
            }

            // Second DealItem
            Optional<DealItem> existingItem2 = repository.findById("981235678");
            if (existingItem2.isEmpty()) {
                repository.save(new DealItem(
                    "981235678",                              // Item ID
                    "Black Men Slim Fit Mid Rise Jeans",     // Product Title
                    "32",                                     // Size
                    "Louis Philippe Jeans",                   // Brand
                    "Jeans",                                  // Category Name
                    new DealItem.Image("https://i.ebayimg.com/images/g/~**********N/s-l225.jpg"), // Image URL
                    new DealItem.MarketingPrice(
                        new DealItem.MarketingPrice.OriginalPrice("29.99", "USD"), // Original Price
                        "50.0", // Discount Percentage
                        new DealItem.MarketingPrice.DiscountAmount("14.99", "USD"), // Discount Amount
                        "LIST_PRICE"
                    ),
                    new DealItem.Price("14.99", "USD"),      // Price
                    3,                                        // Stock
                    "2023-06-20T15:26:00Z",                  // Deal Start Date
                    "2023-12-20T14:59:59Z"                   // Deal End Date
                ));
            }
        };
    }
}
