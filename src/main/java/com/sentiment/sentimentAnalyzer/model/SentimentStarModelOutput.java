package com.sentiment.sentimentAnalyzer.model;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SentimentStarModelOutput {
    private String text;
    private String sentimentJson;

    private int finalSentiment;

}
