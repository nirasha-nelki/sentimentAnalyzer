package com.sentiment.sentimentAnalyzer.service;

import com.sentiment.sentimentAnalyzer.model.SentimentOutput;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Service
public class SentimentServiceImpl {
    private final StanfordCoreNLP pipeline;

    public SentimentServiceImpl() {
        // Set up Stanford CoreNLP pipeline properties
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");

        // Build the pipeline
        this.pipeline = new StanfordCoreNLP(props);
    }

    public SentimentOutput analyzeSentiment(String text) {

        int sentimentInt;
        String sentimentName;
        HashMap<String, Integer> sentimentMap = new HashMap<>();
        int totalSentiment = 0;
        int finalSentiment = 0;
        String finalSentimentName;

        // Create an Annotation object
        Annotation document = new Annotation(text);

        // Annotate the text
        pipeline.annotate(document);

        for(CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class))
        {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            sentimentInt = RNNCoreAnnotations.getPredictedClass(tree);
            sentimentName = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            sentimentMap.put(sentence.toString(), sentimentInt);
        }

        for (int value : sentimentMap.values()) {
            totalSentiment += value;
        }

        finalSentiment = totalSentiment / sentimentMap.size();
        System.out.println("Final Sentiment: " + finalSentiment);

        finalSentimentName = switch (finalSentiment+1) {
            case 1 -> "Very Negative";
            case 2 -> "Negative";
            case 3 -> "Neutral";
            case 4 -> "Positive";
            case 5 -> "Very Positive";
            default -> "";
        };

        return new SentimentOutput(sentimentMap, finalSentiment+1, finalSentimentName);

    }

}
