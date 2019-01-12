
package com.app.raffaellatran.tendencydetector.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralSummary {

    @SerializedName("confidence_sentiment")
    @Expose
    private Integer confidenceSentiment;
    @SerializedName("sentiment_class")
    @Expose
    private String sentimentClass;

    public String print(){
        return "sentiment: "+ sentimentClass;
    }

    public Integer getConfidenceSentiment() {
        return confidenceSentiment;
    }

    public void setConfidenceSentiment(Integer confidenceSentiment) {
        this.confidenceSentiment = confidenceSentiment;
    }

    public String getSentimentClass() {
        return sentimentClass;
    }

    public void setSentimentClass(String sentimentClass) {
        this.sentimentClass = sentimentClass;
    }

}
