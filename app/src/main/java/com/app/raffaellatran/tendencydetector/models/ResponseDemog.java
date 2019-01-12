
package com.app.raffaellatran.tendencydetector.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDemog {

    @SerializedName("quota")
    @Expose
    private Quota quota;
    @SerializedName("data")

    @Expose
    private List<DemographicDatum> demographicData = null;
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

    public List<DemographicDatum> getDemographicData() {
        return demographicData;
    }

    public void setDemographicData(List<DemographicDatum> demographicData) {
        this.demographicData = demographicData;
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
