package com.app.raffaellatran.tendencydetector.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DemographicDatum {

        @SerializedName("confidence_gender")
        @Expose
        private Double confidenceGender;
        @SerializedName("age")
        @Expose
        private String age;
        @SerializedName("confidence_age")
        @Expose
        private Double confidenceAge;
        @SerializedName("gender")
        @Expose
        private String gender;

        public Double getConfidenceGender() {
            return confidenceGender;
        }

        public void setConfidenceGender(Double confidenceGender) {
            this.confidenceGender = confidenceGender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public Double getConfidenceAge() {
            return confidenceAge;
        }

        public void setConfidenceAge(Double confidenceAge) {
            this.confidenceAge = confidenceAge;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

    }

