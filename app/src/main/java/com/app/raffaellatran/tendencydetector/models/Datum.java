package com.app.raffaellatran.tendencydetector.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("confidence_sentiment")
    @Expose
    private Double confidenceSentiment;
    @SerializedName("topic_sentiments")
    @Expose
    private TopicSentiments topicSentiments;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("confidence_language")
    @Expose
    private Double confidenceLanguage;
    @SerializedName("sentiment_class")
    @Expose
    private String sentimentClass;
    @SerializedName("text_labels")
    @Expose
    private List<Object> textLabels = null;
    @SerializedName("language_iso")
    @Expose
    private String languageIso;
    @SerializedName("language_eng_name")
    @Expose
    private String languageEngName;
    @SerializedName("user")
    @Expose
    private String user;

    public String showSentiment(){
        return "Sentiment= "+ sentimentClass
                +"\n language= " + languageIso
                +"\n confidenceSentiment= " + confidenceSentiment
                +"\n text= " + text;
    }

    public Double getConfidenceSentiment() {
        return confidenceSentiment;
    }

    public void setConfidenceSentiment(Double confidenceSentiment) {
        this.confidenceSentiment = confidenceSentiment;
    }

    public TopicSentiments getTopicSentiments() {
        return topicSentiments;
    }

    public void setTopicSentiments(TopicSentiments topicSentiments) {
        this.topicSentiments = topicSentiments;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getConfidenceLanguage() {
        return confidenceLanguage;
    }

    public void setConfidenceLanguage(Double confidenceLanguage) {
        this.confidenceLanguage = confidenceLanguage;
    }

    public String getSentimentClass() {
        return sentimentClass;
    }

    public void setSentimentClass(String sentimentClass) {
        this.sentimentClass = sentimentClass;
    }

    public List<Object> getTextLabels() {
        return textLabels;
    }

    public void setTextLabels(List<Object> textLabels) {
        this.textLabels = textLabels;
    }

    public String getLanguageIso() {
        return languageIso;
    }

    public void setLanguageIso(String languageIso) {
        this.languageIso = languageIso;
    }

    public String getLanguageEngName() {
        return languageEngName;
    }

    public void setLanguageEngName(String languageEngName) {
        this.languageEngName = languageEngName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
