package com.nagarro.restClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.model.DealResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class EbayClient {

    private final WebClient webClient;
    private final ObjectMapper objectMapper; // Added ObjectMapper for parsing

    @Autowired
    public EbayClient(@Qualifier("ebay") WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper; // Initialize ObjectMapper
    }

    public Mono<DealResponse> ebayResponse(String categoryName) {
        String encodedCategoryName = URLEncoder.encode(categoryName.trim(), StandardCharsets.UTF_8);
        String url = "http://localhost:8082/backendserver2/ebay/deals/" + encodedCategoryName;
        log.info("eBay response started for category: {}", categoryName);
        System.out.println("Calling eBay API: " + url);

        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    log.error("Client error: {}", response.statusCode());
                    return Mono.error(new RuntimeException("Client error while fetching eBay deals"));
                })
                .onStatus(status -> status.is5xxServerError(), response -> {
                    log.error("Server error: {}", response.statusCode());
                    return Mono.error(new RuntimeException("Server error while fetching eBay deals"));
                })
                .bodyToMono(String.class)
                .doOnNext(responseBody -> {
                    log.info("Raw eBay API response: {}", responseBody);
                })
                .map(responseBody -> {
                    DealResponse dealResponse = parseResponse(responseBody);
                    log.info("Parsed eBay API response: {}", dealResponse);
                    return dealResponse;
                })
                .doOnError(error -> log.error("Error occurred: {}", error.getMessage()));
    }

    // Method to parse JSON string into DealResponse
    private DealResponse parseResponse(String responseBody) {
        // Use your preferred JSON library (e.g., Jackson, Gson) to convert the responseBody to DealResponse
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(responseBody, DealResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse response: {}", e.getMessage());
            throw new RuntimeException("Failed to parse Ebay API response");
        }
    }
}
