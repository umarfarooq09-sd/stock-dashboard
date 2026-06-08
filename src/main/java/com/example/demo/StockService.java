package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockService {

    @Value("${finnhub.api.key}")
    private String apiKey;

    public String getStockQuote(String symbol) {

        String url =
                "https://finnhub.io/api/v1/quote?symbol="
                        + symbol
                        + "&token="
                        + apiKey;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, String.class);
    }

    public Quote getQuoteObject(String symbol) {

        String url =
                "https://finnhub.io/api/v1/quote?symbol="
                        + symbol
                        + "&token="
                        + apiKey;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, Quote.class);
    }
}