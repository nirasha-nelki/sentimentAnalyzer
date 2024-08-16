package com.sentiment.sentimentAnalyzer.api;

import com.sentiment.sentimentAnalyzer.dto.SentimentTextDto;
import com.sentiment.sentimentAnalyzer.model.SentimentOutput;
import com.sentiment.sentimentAnalyzer.model.SentimentStarModelOutput;
import com.sentiment.sentimentAnalyzer.service.SentimentModelServiceImpl;
import com.sentiment.sentimentAnalyzer.service.SentimentServiceImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("api/v1/sentiment")
public class SentimentController {


    @Autowired
    private SentimentServiceImpl sentimentService;

    @Autowired
    private SentimentModelServiceImpl sentimentModelService;

    @PostMapping("core")
    public ResponseEntity<?> getSentiment(@RequestBody SentimentTextDto dto){

           SentimentOutput sentimentMap =  sentimentService.analyzeSentiment(dto.getText());
           return ResponseEntity.ok(sentimentMap);

    }

    @PostMapping("/binary")
    public void getBinarySentiment(@RequestBody SentimentTextDto dto) throws IOException {
        System.out.println(dto.getText());
        sentimentModelService.analyzeSentiment(dto.getText());




    }

    @PostMapping("/stars")
    public ResponseEntity<?> sentimentByStars(@RequestBody SentimentTextDto dto) throws IOException {
        SentimentStarModelOutput response = sentimentModelService.analyzeSentimentStars(dto.getText());
        return ResponseEntity.ok(response);
    }
 }
