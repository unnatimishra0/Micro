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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AmazonClient {

    private final WebClient webClient;

    @Autowired
    public AmazonClient(@Qualifier("amazon") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<DealResponse> amazonResponse(String categoryName) {
        String encodedCategoryName = URLEncoder.encode(categoryName.trim(), StandardCharsets.UTF_8);
        String url = "http://localhost:8085/backendserver1/amazon/deals/" + encodedCategoryName;
        System.out.println("Calling Amazon API: " + url);
        log.info("Amazon response started for category: {}", categoryName);

        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    log.error("Client error: {}", response.statusCode());
                    return Mono.error(new RuntimeException("Client error while fetching Amazon deals"));
                })
                .onStatus(status -> status.is5xxServerError(), response -> {
                    log.error("Server error: {}", response.statusCode());
                    return Mono.error(new RuntimeException("Server error while fetching Amazon deals"));
                })
                .bodyToMono(String.class)
                .doOnNext(responseBody -> {
                    log.info("Raw Amazon API response: {}", responseBody);
                })
                .map(responseBody -> {
                    DealResponse dealResponse = parseResponse(responseBody);
                    log.info("Parsed Amazon API response: {}", dealResponse);
                    return dealResponse;
                })
                .doOnError(error -> log.error("Error occurred: {}", error.getMessage()));
    }

    // Example method to parse JSON string into DealResponse
    private DealResponse parseResponse(String responseBody) {
        // Use your preferred JSON library (e.g., Jackson, Gson) to convert the responseBody to DealResponse
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(responseBody, DealResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse response: {}", e.getMessage());
            throw new RuntimeException("Failed to parse Amazon API response");
        }
    }
}
