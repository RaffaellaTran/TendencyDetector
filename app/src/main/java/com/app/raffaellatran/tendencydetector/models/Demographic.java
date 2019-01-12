package com.app.raffaellatran.tendencydetector.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Demographic {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("response")
    @Expose
    private ResponseDemog response;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ResponseDemog getResponseDemog() {
        return response;
    }

    public void setResponse(ResponseDemog response) {
        this.response = response;
    }
}
