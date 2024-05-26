package com.jpozarycki.springaifunctions.services;

import com.jpozarycki.springaifunctions.model.WeatherRequest;
import com.jpozarycki.springaifunctions.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

@RequiredArgsConstructor
public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {
    private static final String WEATHER_API_URL = "https://api.api-ninjas.com/v1/weather";
    private final String apiKey;

    @Override
    public WeatherResponse apply(WeatherRequest weatherRequest) {
        RestClient restClient = RestClient.builder()
                .baseUrl(WEATHER_API_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-Api-Key", apiKey);
                    httpHeaders.set("Content-Type", "application/json");
                    httpHeaders.set("Accept", "application/json");
                })
                .build();
        return restClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.queryParam("city", weatherRequest.location());
                    if (weatherRequest.country() != null && !weatherRequest.country().isEmpty()) {
                        uriBuilder.queryParam("country", weatherRequest.country());
                    }
                    return uriBuilder.build();
                })
                .retrieve()
                .body(WeatherResponse.class);
    }
}
