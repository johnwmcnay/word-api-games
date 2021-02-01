package com.games.words.controllers;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HomeController {

    @Value("${rapid.api.key}")
    private String key;

    @Value("${rapid.api.host}")
    private String host;


    @GetMapping("/")
    public String homePage() throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", "https://wordsapiv1.p.rapidapi.com/words/?letterPattern=%5E%5BcefCEF%5D&lettersmin=3&lettersMax=10&limit=100&page=1&frequencymin=4")
                .setHeader("x-rapidapi-key", key)
                .setHeader("x-rapidapi-host", host)
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();

        return "index";
    }

}
