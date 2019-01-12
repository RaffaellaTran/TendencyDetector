package com.app.raffaellatran.tendencydetector.services;
import com.app.raffaellatran.tendencydetector.models.Datum;
import com.app.raffaellatran.tendencydetector.models.Demographic;
import com.app.raffaellatran.tendencydetector.models.SentimentModel;
import com.app.raffaellatran.tendencydetector.models.SentimentStatus;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface APIService {
    @POST(".")
    Single<SentimentStatus> postSentimentAnalysis(@Body RequestBody body);
    @POST(".")
    Single<Demographic> postDemographicAnalysis(@Body RequestBody body);

}
