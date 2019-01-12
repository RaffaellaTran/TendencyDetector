package com.app.raffaellatran.tendencydetector.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SentimentModel {

    @SerializedName("sentiment")
    @Expose
    private Sentiment_Detail sentiment;
    @SerializedName("wordCount")
    @Expose
    private Integer wordCount;

    public Sentiment_Detail getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment_Detail sentiment) {
        this.sentiment = sentiment;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    @Override
    public String toString() {
        return "Sentiment{" +
                "sentiment='" + sentiment.toString() + '\'' +
                ", wordCount='" + wordCount +
                '}';
    }
}
