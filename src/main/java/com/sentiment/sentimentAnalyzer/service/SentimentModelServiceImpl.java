package com.sentiment.sentimentAnalyzer.service;

import com.sentiment.sentimentAnalyzer.model.SentimentBinaryOutput;
import com.sentiment.sentimentAnalyzer.model.SentimentStarModelOutput;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Service
public class SentimentModelServiceImpl {

    private static final String API_URL = "https://api-inference.huggingface.co/models/distilbert-base-uncased-finetuned-sst-2-english";
    private static final String STAR_API_URL = "https://api-inference.huggingface.co/models/nlptown/bert-base-multilingual-uncased-sentiment";

    @Value("${api.token}")
    private String API_TOKEN;

    public SentimentBinaryOutput analyzeSentiment(String text) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setHeader("Authorization", "Bearer " + API_TOKEN);
            httpPost.setHeader("Content-Type", "application/json");

            JSONObject json = new JSONObject();
            json.put("inputs", text);

            StringEntity entity = new StringEntity(json.toString());
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println(responseBody);

                try {
                    JSONArray outerArray = new JSONArray(responseBody);
                    JSONArray innerArray = outerArray.getJSONArray(0);
                    JSONObject innerObj = innerArray.getJSONObject(0);

                    String sentiment = innerObj.getString("label");
                    System.out.println("Sentiment: " + sentiment);

                    return new SentimentBinaryOutput(text, responseBody, sentiment);

                } catch (JSONException e) {
                    throw new RuntimeException("Failed to parse the response JSON.", e);
                }
            }
        } catch (IOException e) {
            throw new IOException("An error occurred while calling the sentiment analysis API.", e);
        }
    }

    public SentimentStarModelOutput analyzeSentimentStars(String text) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            System.out.println("Text: " + text);
            HttpPost httpPost = new HttpPost(STAR_API_URL);
            httpPost.setHeader("Authorization", "Bearer " + API_TOKEN);
            httpPost.setHeader("Content-Type", "application/json");

            JSONObject json = new JSONObject();
            json.put("inputs", text);

            StringEntity entity = new StringEntity(json.toString());
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println(responseBody);

                try {
                    JSONArray outerArray = new JSONArray(responseBody);
                    JSONArray innerArray = outerArray.getJSONArray(0);
                    JSONObject innerObj = innerArray.getJSONObject(0);

                    String starScore = innerObj.getString("label");
                    System.out.println("Star Score: " + starScore);

                    return new SentimentStarModelOutput(text, responseBody, Integer.parseInt(String.valueOf(starScore.charAt(0))));
                } catch (JSONException e) {
                    throw new RuntimeException("Failed to parse the response JSON.", e);
                }
            }
        } catch (IOException e) {
            throw new IOException("An error occurred while calling the sentiment analysis API.", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Failed to parse star rating from the response.", e);
        }
    }
}
