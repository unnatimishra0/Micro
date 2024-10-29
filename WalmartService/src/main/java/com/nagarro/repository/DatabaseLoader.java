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
                DealItem item1 = DealItem.builder()
                        .itemid("777654321")
                        .productTitle("Men Black Regular Jeans")
                        .size("32")
                        .brand("H&M")
                        .categoryName("Jeans")  // Category name
                        .image(new DealItem.Image("https://i.ebayimg.com/images/g/~**********N/s-l225.jpg"))
                        .marketingPrice(new DealItem.MarketingPrice(
                                new DealItem.MarketingPrice.OriginalPrice("29.99", "USD"),
                                "40.0",
                                new DealItem.MarketingPrice.DiscountAmount("11.99", "USD"),
                                "LIST_PRICE"
                        ))
                        .price(new DealItem.Price("17.99", "USD"))
                        .stock(4)
                        .dealStartDate("2024-06-20T15:26:00Z")
                        .dealEndDate("2024-12-20T14:59:59Z")
                        .build();

                repository.save(item1);
            }

            // Second DealItem
            Optional<DealItem> existingItem2 = repository.findById("55666778231");
            if (existingItem2.isEmpty()) {
                DealItem item2 = DealItem.builder()
                        .itemid("55666778231")
                        .productTitle("Light Fade Clean Look Stretchable Jeans")
                        .size("32")
                        .brand("Louis Philippe Jeans")
                        .categoryName("Jeans")  // Category name
                        .image(new DealItem.Image("https://i.ebayimg.com/images/g/~**********N/s-l225.jpg"))
                        .marketingPrice(new DealItem.MarketingPrice(
                                new DealItem.MarketingPrice.OriginalPrice("39.99", "USD"),
                                "25.0",
                                new DealItem.MarketingPrice.DiscountAmount("09.99", "USD"),
                                "LIST_PRICE"
                        ))
                        .price(new DealItem.Price("29.99", "USD"))
                        .stock(3)
                        .dealStartDate("2023-06-20T15:26:00Z")
                        .dealEndDate("2023-12-20T14:59:59Z")
                        .build();

                repository.save(item2);
            }
        };
    }
}
