
package com.app.raffaellatran.tendencydetector.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Summary {

    @SerializedName("topic_summary")
    @Expose
    private List<Object> topicSummary = null;
    @SerializedName("general_summary")
    @Expose
    private GeneralSummary generalSummary;

    public String print(){
        return "sentiment: "+ generalSummary.getSentimentClass();
    }

    public List<Object> getTopicSummary() {
        return topicSummary;
    }

    public void setTopicSummary(List<Object> topicSummary) {
        this.topicSummary = topicSummary;
    }

    public GeneralSummary getGeneralSummary() {
        return generalSummary;
    }

    public void setGeneralSummary(GeneralSummary generalSummary) {
        this.generalSummary = generalSummary;
    }

}
