package com.nagarro.repository;

import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.nagarro.model.DealItem;
import com.nagarro.model.DealItem.Image;
import com.nagarro.model.DealItem.MarketingPrice;
import com.nagarro.model.DealItem.MarketingPrice.DiscountAmount;
import com.nagarro.model.DealItem.MarketingPrice.OriginalPrice;
import com.nagarro.model.DealItem.Price;

@Component
public class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(DealItemRepository repository) {
        return args -> {
            // First DealItem
            if (repository.findById("123456789").isEmpty()) {
                DealItem dealItem1 = createDealItem(
                        "123456789", "Blue Levis Jeans", "30", "Levis", "Jeans",
                        "https://i.ebayimg.com/images/g/~**********N/s-l225.jpg",
                        29.99, "USD", 17.0, 5.0, "USD",
                        "LIST_PRICE", 24.99, "USD", 4,
                        LocalDateTime.of(2022, 6, 20, 0, 0),
                        LocalDateTime.of(2022, 12, 20, 23, 59)
                );
                // Validate dates before saving (uncomment when the method is implemented)
                // dealItem1.validateDates();
                repository.save(dealItem1);
            }

            // Second DealItem
            if (repository.findById("981235678").isEmpty()) {
                DealItem dealItem2 = createDealItem(
                        "981235678", "Black Men Slim Fit Mid Rise Jeans", "32",
                        "Louis Philippe Jeans", "Jeans",
                        "https://i.ebayimg.com/images/g/~**********N/s-l225.jpg",
                        29.99, "USD", 50.0, 14.99, "USD",
                        "LIST_PRICE", 14.99, "USD", 3,
                        LocalDateTime.of(2024, 6, 20, 0, 0),
                        LocalDateTime.of(2024, 12, 20, 23, 59)
                );
                // Validate dates before saving (uncomment when the method is implemented)
                // dealItem2.validateDates();
                repository.save(dealItem2);
            }
        };
    }

    private DealItem createDealItem(
            String itemId, String productTitle, String size, String brand, String categoryName,
            String imageUrl, double originalPriceValue, String originalPriceCurrency,
            double discountPercentage, double discountAmountValue, String discountAmountCurrency,
            String priceTreatment, double priceValue, String priceCurrency, int stock,
            LocalDateTime dealStartDate, LocalDateTime dealEndDate) {

        return DealItem.builder()
                .itemid(itemId)
                .categoryName(categoryName)
                .productTitle(productTitle)
                .size(size)
                .brand(brand)
                .image(Image.builder()
                        .imageUrl(imageUrl)
                        .build())
                .marketingPrice(MarketingPrice.builder()
                        .originalPrice(OriginalPrice.builder()
                                .value(String.valueOf(originalPriceValue))
                                .currency(originalPriceCurrency)
                                .build())
                        .discountPercentage(String.valueOf(discountPercentage))
                        .discountAmount(DiscountAmount.builder()
                                .value(String.valueOf(discountAmountValue))
                                .currency(discountAmountCurrency)
                                .build())
                        .priceTreatment(priceTreatment)
                        .build())
                .price(Price.builder()
                        .value(String.valueOf(priceValue))
                        .currency(priceCurrency)
                        .build())
                .stock(stock)
                .dealStartDate(String.valueOf(dealStartDate)) // Store as LocalDateTime directly
                .dealEndDate(String.valueOf(dealEndDate)) // Store as LocalDateTime directly
                .build();
    }
}
