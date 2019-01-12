
package com.app.raffaellatran.tendencydetector.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quota {

    @SerializedName("remaining_credits")
    @Expose
    private Integer remainingCredits;
    @SerializedName("reset_time")
    @Expose
    private String resetTime;
    @SerializedName("explanation")
    @Expose
    private String explanation;
    @SerializedName("transaction_credits")
    @Expose
    private Integer transactionCredits;
    @SerializedName("renewal_date")
    @Expose
    private Integer renewalDate;
    @SerializedName("used_credits")
    @Expose
    private Integer usedCredits;
    @SerializedName("limit_credits")
    @Expose
    private Integer limitCredits;

    public Integer getRemainingCredits() {
        return remainingCredits;
    }

    public void setRemainingCredits(Integer remainingCredits) {
        this.remainingCredits = remainingCredits;
    }

    public String getResetTime() {
        return resetTime;
    }

    public void setResetTime(String resetTime) {
        this.resetTime = resetTime;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getTransactionCredits() {
        return transactionCredits;
    }

    public void setTransactionCredits(Integer transactionCredits) {
        this.transactionCredits = transactionCredits;
    }

    public Integer getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Integer renewalDate) {
        this.renewalDate = renewalDate;
    }

    public Integer getUsedCredits() {
        return usedCredits;
    }

    public void setUsedCredits(Integer usedCredits) {
        this.usedCredits = usedCredits;
    }

    public Integer getLimitCredits() {
        return limitCredits;
    }

    public void setLimitCredits(Integer limitCredits) {
        this.limitCredits = limitCredits;
    }

}
