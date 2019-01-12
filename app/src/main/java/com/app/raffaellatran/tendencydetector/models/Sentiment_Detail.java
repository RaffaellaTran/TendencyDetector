package com.app.raffaellatran.tendencydetector.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sentiment_Detail {
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("positive")
    @Expose
    private Double positive;
    @SerializedName("negative")
    @Expose
    private Double negative;
    @SerializedName("neutral")
    @Expose
    private Double neutral;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getPositive() {
        return positive;
    }

    public void setPositive(Double positive) {
        this.positive = positive;
    }

    public Double getNegative() {
        return negative;
    }

    public void setNegative(Double negative) {
        this.negative = negative;
    }

    public Double getNeutral() {
        return neutral;
    }

    public void setNeutral(Double neutral) {
        this.neutral = neutral;
    }
    @Override
    public String toString() {
        return "Sentiment_detail{" +
                "label='" + label + '\'' +
                ", positive='" + positive + '\'' +
                ", negative=" + negative +
                ", neutral=" + neutral +
                '}';
    }
}
