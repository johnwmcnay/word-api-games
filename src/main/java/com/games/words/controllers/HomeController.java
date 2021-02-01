package com.games.words.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @Value("${rapid.api.key}")
    private String key;

    @Value("${rapid.api.host}")
    private String host;


    @GetMapping("/")
    public String homePage(Model model) throws IOException, InterruptedException {
//        AsyncHttpClient client = new DefaultAsyncHttpClient();
//        client.prepare("GET", "https://wordsapiv1.p.rapidapi.com/words/?letterPattern=%5E%5BcefCEF%5D&lettersmin=3&lettersMax=10&limit=100&page=1&frequencymin=4")
//                .setHeader("x-rapidapi-key", key)
//                .setHeader("x-rapidapi-host", host)
//                .execute()
//                .toCompletableFuture()
//                .thenAccept(System.out::println)
//                .join();
//
//        client.close();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://wordsapiv1.p.rapidapi.com/words/?random=true"))
                .header("x-rapidapi-key", key)
                .header("x-rapidapi-host", host)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        Map<String,Object> result = new ObjectMapper().readValue(response.body(), HashMap.class);
        System.out.println(result.get("word"));
        model.addAttribute("word", result.get("word"));
        return "index";
    }

}
