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
                DealItem item1 = DealItem.builder()
                        .itemid("2345678767")
                        .productTitle("Blue American Eagle Jeans")
                        .size("32")
                        .brand("American Eagle")
                        .categoryName("Jeans")  // Added category name
                        .image(DealItem.Image.builder()
                                .imageUrl("https://i.ebayimg.com/images/g/~**********N/s-l225.jpg")
                                .build())
                        .marketingPrice(DealItem.MarketingPrice.builder()
                                .originalPrice(DealItem.MarketingPrice.OriginalPrice.builder()
                                        .value("49.99")
                                        .currency("USD")
                                        .build())
                                .discountPercentage("50.0")
                                .discountAmount(DealItem.MarketingPrice.DiscountAmount.builder()
                                        .value("24.99")
                                        .currency("USD")
                                        .build())
                                .priceTreatment("LIST_PRICE")
                                .build())
                        .price(DealItem.Price.builder()
                                .value("24.99")
                                .currency("USD")
                                .build())
                        .stock(4)
                        .dealStartDate("2024-06-20T15:26:00Z")
                        .dealEndDate("2024-12-20T14:59:59Z")
                        .build();

                repository.save(item1);
            }

            // Second DealItem
            Optional<DealItem> existingItem2 = repository.findById("4567822189");
            if (existingItem2.isEmpty()) {
                DealItem item2 = DealItem.builder()
                        .itemid("4567822189")
                        .productTitle("Navy Blue Rare Rabbit Jeans")
                        .size("32")
                        .brand("Rare Rabbit")
                        .categoryName("Jeans")  // Added category name
                        .image(DealItem.Image.builder()
                                .imageUrl("https://i.ebayimg.com/images/g/~**********N/s-l225.jpg")
                                .build())
                        .marketingPrice(DealItem.MarketingPrice.builder()
                                .originalPrice(DealItem.MarketingPrice.OriginalPrice.builder()
                                        .value("39.99")
                                        .currency("USD")
                                        .build())
                                .discountPercentage("25.0")
                                .discountAmount(DealItem.MarketingPrice.DiscountAmount.builder()
                                        .value("09.99")
                                        .currency("USD")
                                        .build())
                                .priceTreatment("LIST_PRICE")
                                .build())
                        .price(DealItem.Price.builder()
                                .value("29.99")
                                .currency("USD")
                                .build())
                        .stock(0)
                        .dealStartDate("2023-06-20T15:26:00Z")
                        .dealEndDate("2023-12-20T14:59:59Z")
                        .build();

                repository.save(item2);
            }
        };
    }
}
