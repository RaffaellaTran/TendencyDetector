package com.app.raffaellatran.tendencydetector.view;

import android.annotation.SuppressLint;
import android.app.usage.NetworkStats;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.raffaellatran.tendencydetector.R;
import com.app.raffaellatran.tendencydetector.models.Const;
import com.app.raffaellatran.tendencydetector.models.Demographic;
import com.app.raffaellatran.tendencydetector.models.RequestJSON;
import com.app.raffaellatran.tendencydetector.models.Datum;
import com.app.raffaellatran.tendencydetector.models.SentimentModel;
import com.app.raffaellatran.tendencydetector.models.SentimentStatus;
import com.app.raffaellatran.tendencydetector.services.APIService;
import com.app.raffaellatran.tendencydetector.services.ApiUtils;
import com.app.raffaellatran.tendencydetector.services.RetrofitClient;

import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.github.anastr.speedviewlib.TubeSpeedometer;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class RatingTweetsActivity extends AppCompatActivity {

    ///
    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    ///
    private APIService APIServiceSentiment;
    private APIService APIServiceDemographic;
    TextView mResponseTv;
    PointerSpeedometer pointerSpeedometer;
    PieChart pieChart;
    PieChart donutChart;
    TextView sentenceShowView;
    Map<String, Double> setMapValuesToView = new HashMap<>();
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.rating_tweets);
        // put the value from the string.xml

        res = getResources();
        String apiKey = res.getString(R.string.api_key);
       /*
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/
        // get value from the previous activity
        HashMap<String, String> myList = (HashMap<String, String>) getIntent().getSerializableExtra("hashmap");

        //Initialize map

        setMapValuesToView.put("positive", 0.5);
        setMapValuesToView.put("negative", 0.5);
        setMapValuesToView.put("female", 5.0);
        setMapValuesToView.put("male", 5.0);
        setMapValuesToView.put("12-30", 5.0);
        setMapValuesToView.put("31-55", 5.0);
        setMapValuesToView.put("+56", 5.0);

        pointerSpeedometer = (PointerSpeedometer) findViewById(R.id.pointerSpeedometer);

        pieChart = (PieChart) findViewById(R.id.pie_chart);
        donutChart = (PieChart) findViewById(R.id.donut_chart);

        // SetGauge();
        Toolbar toolbar = (Toolbar) findViewById(R.id.rating_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        APIServiceSentiment = ApiUtils.getSentimentAPIService();
        APIServiceDemographic = ApiUtils.getDemographycAPIService();
        for (Map.Entry<String, String> entry : myList.entrySet()) {
            Log.d("DEMOOOO", entry.getKey() +"/"+entry.getValue());
            calculateSentiment(setRequestJSON(apiKey, entry.getValue(), entry.getKey()));
            calculateDemography(setRequestJSON(apiKey, entry.getValue(), entry.getKey()));
        }
        SetGauge();
    }

    //I couldn't use JSONObject because the body request from the API required this structure
    public String setRequestJSON(String apiKey, String text, String username) {
        return "request={" +
                "   \"data\":{" +
                "      \"api_key\":\"" + apiKey + "\"," +
                "      \"call\":{" +
                "         \"sentiment_classifier\":\"default\"," +
                "         \"data\":[" +
                "            {" +
                "               \"text\":\"" + text + "\"," +
                "               \"language_iso\":\"eng\"," +
                "               \"user\":\"" + username + "\"" +
                "            }" +
                "         ]" +
                "      }" +
                "   }" +
                "}";
    }

    public void calculateSentiment(String requestJSON) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), requestJSON);
        mCompositeDisposable.add(
                APIServiceSentiment.postSentimentAnalysis(requestBody)
                        .retry(5)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map((SentimentStatus datum) -> {
                            if (datum.getResponse().getDescription().contains("ERROR")) {
                                return "ERROR";
                            } else {
                                return datum.getResponse().getData().get(0).getSentimentClass();
                            }
                        }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("DEMO", s);
                        if (setMapValuesToView.containsKey(s)) {
                            setMapValuesToView.put(s, setMapValuesToView.get(s) + 1.0);
                        }

                    }

                    public void onError() {
                        Log.d("DEMO", "ERRORE");
                    }
                })
        );
    }
    public void calculateDemography(String requestJSON) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), requestJSON);
        mCompositeDisposable.add(
                APIServiceDemographic.postDemographicAnalysis(requestBody)
                .retry(5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map((Demographic datum) ->{
                    if (datum.getResponseDemog().getDescription().contains("ERROR")) {
                        return "ERROR";
                    } else {
                        String value =datum.getResponseDemog().getDemographicData().get(0).getGender();
                        if (setMapValuesToView.containsKey(value)) {
                            setMapValuesToView.put(value, setMapValuesToView.get(value) + 1.0);
                        }
                        String valueAge = datum.getResponseDemog().getDemographicData().get(0).getAge();
                        /*if (setMapValuesToView.containsKey(valueAge)) {
                            setMapValuesToView.put(valueAge, setMapValuesToView.get(valueAge) + 1.0);
                        }*/
/*
                       if(valueAge.contains("12")||valueAge.contains("30")){
                           setMapValuesToView.put("12-30", setMapValuesToView.get(value) + 1.0);
                       }else if(valueAge.contains("31")||valueAge.contains("55")){
                           setMapValuesToView.put("31-55", setMapValuesToView.get(value) + 1.0);
                       }else if(valueAge.contains("56")){
                           setMapValuesToView.put("+56", setMapValuesToView.get(value) + 1.0);
                       }*/
                     return value;
                    }})
                        . subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String s) throws Exception {

                    }

                    public void onError() {
                        Log.d("DEMO", "ERRORE");
                    }

                })
        );
    }

    //set the parameters in the views
    public void SetGauge() {

        for (Map.Entry<String, Double> entry : setMapValuesToView.entrySet())
        {
            System.out.println("GGGGGG "+entry.getKey() + "/" + entry.getValue());
        }
        //  setMapValuesToView."
        Double female = setMapValuesToView.get("female");
        Double male = setMapValuesToView.get("male");
        Double positive = setMapValuesToView.get("positive");
        Double negative = setMapValuesToView.get("negative");
        Double young = setMapValuesToView.get("12-30");
        Double adult = setMapValuesToView.get("31-55");
        Double elder = setMapValuesToView.get("+56");
        Double sum = positive + negative;
        Double rating = 0.0;
        rating = positive * sum * 100.0;
        pointerSpeedometer.speedTo(rating.floatValue());
        pointerSpeedometer.setWithTremble(false);

        final int[] MY_COLORS = {Color.rgb(192, 0, 0), Color.rgb(255, 0, 0), Color.rgb(255, 192, 0),
                Color.rgb(127, 127, 127), Color.rgb(146, 208, 80), Color.rgb(0, 176, 80), Color.rgb(79, 129, 189)};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : MY_COLORS) colors.add(c);

        List<PieEntry> entriesPie = new ArrayList<>();

        entriesPie.add(new PieEntry(female.floatValue(), "Female"));
        entriesPie.add(new PieEntry(male.floatValue(), "Male"));

        pieChart.setUsePercentValues(true);
        pieChart.setCenterTextRadiusPercent(0);
        pieChart.setHoleRadius(0);
        pieChart.setTransparentCircleRadius(0);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);

        PieDataSet setPie = new PieDataSet(entriesPie, "Gender");
        setPie.setColors(colors);
        PieData dataPie = new PieData(setPie);
        pieChart.setData(dataPie);

        List<PieEntry> entriesDonut = new ArrayList<>();

        entriesDonut.add(new PieEntry(setMapValuesToView.get("12-30").floatValue(), "12-30 years"));
        entriesDonut.add(new PieEntry(setMapValuesToView.get("31-55").floatValue(), "31-55 years"));
        entriesDonut.add(new PieEntry(setMapValuesToView.get("+56").floatValue(), "+56 years"));

        PieDataSet setDonut = new PieDataSet(entriesDonut, "Age");
        setDonut.setColors(colors);
        PieData dataDonut = new PieData(setDonut);
        donutChart.setData(dataDonut);
        donutChart.getDescription().setEnabled(false);
        donutChart.getLegend().setEnabled(false);


        //sentence of the view
        String word = "";
        String gender = "female";
        gender = female >= male ? "females" : "males";
        String age = "both men and women";
        String ratingWord = "positive";
        ratingWord = positive >= negative ? "positive" : "negative";
        String usernameAccount = getIntent().getStringExtra("username");

        String sentence = "";

        word = getIntent().getStringExtra("value");
        sentenceShowView = findViewById(R.id.rating_text);

        if (young > adult && young > elder) {
            age = "young";
        } else if (elder > young && elder > adult) {
            age = "elder";
        } else if (adult > young && adult > elder) {
            age = "adult";
        }
        res = getResources();
        sentence = res.getString(R.string.sentence, word, ratingWord, age, gender);

        if (word.equals(usernameAccount)) {
            sentenceShowView.setText(R.string.personal_analysis);
        } else {
            sentenceShowView.setVisibility(View.VISIBLE);
            sentenceShowView.setText(sentence);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
