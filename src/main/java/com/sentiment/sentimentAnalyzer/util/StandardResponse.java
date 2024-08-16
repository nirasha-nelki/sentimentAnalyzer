package com.sentiment.sentimentAnalyzer.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StandardResponse {

    private HashMap<String, Integer> sentimentMap;
    private int finalSentiment;
}
