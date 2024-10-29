package com.nagarro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealItem {
    @Id
    private String itemid;
    private String productTitle;
    private String size;
    private String brand;
    private String categoryName;  // Add this line


    @Embedded
    private Image image;

    @Embedded
    private MarketingPrice marketingPrice;

    @Embedded
    private Price price;

    private int stock;
    private String dealStartDate;
    private String dealEndDate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class Image {
        private String imageUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class MarketingPrice {
        @Embedded
        @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "original_price_value")),
            @AttributeOverride(name = "currency", column = @Column(name = "original_price_currency"))
        })
        private OriginalPrice originalPrice;
        
        private String discountPercentage;
        
        @Embedded
        @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "discount_amount_value")),
            @AttributeOverride(name = "currency", column = @Column(name = "discount_amount_currency"))
        })
        private DiscountAmount discountAmount;
        
        private String priceTreatment;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Embeddable
        public static class OriginalPrice {
            private String value;
            private String currency;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Embeddable
        public static class DiscountAmount {
            private String value;
            private String currency;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class Price {
        private String value;
        private String currency;
    }
}
