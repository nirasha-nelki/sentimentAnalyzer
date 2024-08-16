package com.sentiment.sentimentAnalyzer.model;

import lombok.*;

import java.util.HashMap;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SentimentOutput {

    private HashMap<String, Integer> sentimentMap;
    private int finalSentiment;
    private String finalSentimentName;
}
