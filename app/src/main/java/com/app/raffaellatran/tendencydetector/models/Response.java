
package com.app.raffaellatran.tendencydetector.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("quota")
    @Expose
    private Quota quota;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("summary")
    @Expose
    private Summary summary;

    public Quota getQuota() {
        return quota;
    }

    public void setQuota(Quota quota) {
        this.quota = quota;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

}
