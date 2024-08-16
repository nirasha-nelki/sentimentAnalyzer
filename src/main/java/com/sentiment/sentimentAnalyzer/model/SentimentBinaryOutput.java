package com.sentiment.sentimentAnalyzer.model;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SentimentBinaryOutput {
    private String text;
    private String sentiment;
    private String finalSentiment;
}
