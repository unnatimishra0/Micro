package com.nagarro.service;

import com.nagarro.model.DealItem;
import com.nagarro.model.DealResponse;
import com.nagarro.restClient.AmazonClient;
import com.nagarro.restClient.EbayClient;
import com.nagarro.restClient.WalmartClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DealService {

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    private EbayClient ebayClient;

    @Autowired
    private WalmartClient walmartClient;

    public Mono<List<DealResponse>> getDeals(String categoryName) {
        Mono<DealResponse> amazonResponse = amazonClient.amazonResponse(categoryName);
        Mono<DealResponse> ebayResponse = ebayClient.ebayResponse(categoryName);
        Mono<DealResponse> walmartResponse = walmartClient.walmartResponse(categoryName);

        return Mono.zip(amazonResponse, ebayResponse, walmartResponse)
                .flatMap(tuple -> {

                    System.out.println("Amazon Response: " + tuple.getT1());
                    System.out.println("eBay Response: " + tuple.getT2());
                    System.out.println("Walmart Response: " + tuple.getT3());

                    List<DealResponse> combinedResponses = List.of(tuple.getT1(), tuple.getT2(), tuple.getT3())
                            .stream()
                            .filter(response -> response != null && !response.getDealItems().isEmpty())
                            .collect(Collectors.toList());
                    System.out.println("Combined Responses: " + combinedResponses);

                    return Mono.just(filterAndSortDeals(combinedResponses));
                })
                .onErrorResume(e -> {

                    System.err.println("Error fetching deals: " + e.getMessage());
                    return Mono.just(List.of());
                });
    }


    private List<DealResponse> filterAndSortDeals(List<DealResponse> responses) {
        List<DealResponse> filteredDeals = responses.stream()
                .flatMap(response -> response.getDealItems().stream()
                        .filter(item -> {
                            boolean valid = isValidDealItem(item);
                            System.out.println("Deal Item: " + item + " is valid: " + valid);
                            return valid;
                        })
                        .map(item -> new DealResponse(response.getCategoryName(), List.of(item)))
                )
                .sorted(this::compareDeals)
                .collect(Collectors.toList());


        System.out.println("Filtered Deals: " + filteredDeals);

        return filteredDeals;
    }



    private int compareDeals(DealResponse deal1, DealResponse deal2) {
        DealItem item1 = deal1.getDealItems().get(0);
        DealItem item2 = deal2.getDealItems().get(0);


        int discountComparison = Double.compare(
                parseDouble(item2.getMarketingPrice().getDiscountPercentage()),
                parseDouble(item1.getMarketingPrice().getDiscountPercentage())
        );


        return discountComparison != 0 ? discountComparison : Double.compare(
                parseDouble(item1.getPrice().getValue()),
                parseDouble(item2.getPrice().getValue())
        );
    }

    private boolean isValidDealItem(DealItem item) {
        return item.getStock() > 0 && isDateInRange(item.getDealStartDate(), item.getDealEndDate());
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private boolean isDateInRange(String startDate, String endDate) {
        DateTimeFormatter formatterWithTimezone = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
        DateTimeFormatter formatterWithoutTimezone = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        ZonedDateTime now = ZonedDateTime.now();

        try {

            ZonedDateTime start = ZonedDateTime.parse(startDate, formatterWithTimezone);
            ZonedDateTime end = ZonedDateTime.parse(endDate, formatterWithTimezone);
            return !now.isBefore(start) && !now.isAfter(end);
        } catch (DateTimeParseException e1) {
            try {

                LocalDateTime start = LocalDateTime.parse(startDate, formatterWithoutTimezone);
                LocalDateTime end = LocalDateTime.parse(endDate, formatterWithoutTimezone);
                return !now.toLocalDateTime().isBefore(start) && !now.toLocalDateTime().isAfter(end);
            } catch (DateTimeParseException e2) {
                System.err.println("Failed to parse dates: " + startDate + " or " + endDate);
                System.err.println("Error: " + e2.getMessage());
                return false;
            }
        }
    }




}
