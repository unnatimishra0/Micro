package com.nagarro.restClient;

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
public class WalmartClient {

    private final WebClient webClient;
@Autowired
    public WalmartClient(@Qualifier("walmart") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<DealResponse> walmartResponse(String categoryName) {
        String encodedCategoryName = URLEncoder.encode(categoryName.trim(), StandardCharsets.UTF_8);

        String url = "http://localhost:8083/backendserver3/walmart/deals/" + encodedCategoryName;
        log.info("Walmart response started for category: {}", categoryName);

        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    log.error("Client error: {}", response.statusCode());
                    return Mono.error(new RuntimeException("Client error while fetching Walmart deals"));
                })
                .onStatus(status -> status.is5xxServerError(), response -> {
                    log.error("Server error: {}", response.statusCode());
                    return Mono.error(new RuntimeException("Server error while fetching Walmart deals"));
                })
                .bodyToMono(DealResponse.class)
                .doOnError(error -> log.error("Error occurred: {}", error.getMessage()));
    }
}
