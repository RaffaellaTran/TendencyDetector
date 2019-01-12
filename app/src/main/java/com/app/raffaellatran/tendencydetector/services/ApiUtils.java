package com.app.raffaellatran.tendencydetector.services;

import com.app.raffaellatran.tendencydetector.models.Const;

public class ApiUtils {

    private ApiUtils() {}

    public static APIService getSentimentAPIService() {

        return RetrofitClient.getClient(Const.BASE_URL_SENTIMENTAL).create(APIService.class);
    }
    public static APIService getDemographycAPIService() {

        return RetrofitClient.getClient(Const.BASE_URL_DEMOGRAPHY).create(APIService.class);
    }
}
