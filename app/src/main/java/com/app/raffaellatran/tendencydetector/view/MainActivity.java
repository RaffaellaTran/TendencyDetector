package com.app.raffaellatran.tendencydetector.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.app.raffaellatran.tendencydetector.R;
import com.app.raffaellatran.tendencydetector.observables.RxSearchObservable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

//javac -Xlint MainActivity.java
public class MainActivity extends AppCompatActivity {

    private String username, valueToPass;

    private ImageButton uImageView;
    private long idUser;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ImageButton logoutButton;
    private SearchView searchQuery;
    private View view;
    Button tweetButton;

    twitter4j.Twitter config;
    HashMap<String, String> stringMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String key = this.getString(R.string.com_twitter_sdk_android_CONSUMER_KEY);
        String secret = this.getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String accessToken = getIntent().getStringExtra("token");
        String accessTokenSecret = getIntent().getStringExtra("secret");

        initParameter();
        config = Configuration(key, secret, accessToken, accessTokenSecret);
        //GET USER TWEET
        getUserTimeline(view);
        touchRatingButton();
        //GET SEARCH TWEET

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchQuery = findViewById(R.id.enter_search_query);
        searchWord();

    }

    public void initParameter() {

        view = this.findViewById(android.R.id.content);

        username = getIntent().getStringExtra("username");
        TextView unameTextView = findViewById(R.id.username);
        unameTextView.setText(username);

        Toolbar toolbar = findViewById(R.id.title_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        idUser = getIntent().getLongExtra("idUser", 0);
        String imageProfile = getIntent().getStringExtra("imageProfile");

        uImageView = findViewById(R.id.image_button);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);

        Glide.with(this).load(imageProfile).apply(options).into(uImageView);

        logoutButton = findViewById(R.id.logout_button);

         tweetButton = findViewById(R.id.rating_tweet_button);

    }

    public void touchRatingButton(){
        tweetButton.setOnClickListener(v -> {

            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                Log.d("DEMOOOO", entry.getKey() +"/"+entry.getValue());
            }

            Intent intent = new Intent(this, RatingTweetsActivity.class);
            intent.putExtra("value", valueToPass);
            intent.putExtra("username", username);
            intent.putExtra("hashmap", (Serializable) stringMap);
            Log.d("image", String.valueOf(stringMap.size()));

            if (stringMap.size() != 0) {

                startActivity(intent);
            }
        });
    }
    public twitter4j.Twitter Configuration(String key, String secret, String accessToken, String accessTokenSecret) {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(key);
        builder.setOAuthConsumerSecret(secret);
        builder.setOAuthAccessToken(accessToken);
        builder.setOAuthAccessTokenSecret(accessTokenSecret);
        Configuration configuration = builder.build();
        TwitterFactory factory = new TwitterFactory(configuration);

        return factory.getInstance();
    }

    public void getStatus(twitter4j.Twitter mUnauthenticatedTwitter, String value) {

        new Thread(() -> {
            try {
                if (value.equals(username)) {
                    Log.d("MESSAGE", value);
                    stringMap.clear();
                    List<Status> statuses = mUnauthenticatedTwitter.getUserTimeline(value);
                    for (Status st : statuses) {
                        //   st.getUser().getName();
                        String tweet = st.getText();
                        String usernameTweet = tweet.substring(tweet.indexOf("@") + 1, tweet.indexOf(":"));
                        String textTweet = tweet.substring(tweet.indexOf(":") + 1);
                        stringMap.put(usernameTweet, textTweet);
                        Log.d("MESSAGE333", tweet.substring(tweet.indexOf(":") + 1));

                    }

                } else {String textTweet = null;
                    String usernameTweet = "unknown";
                    Query query = new Query();
                    query.setLang("en");
                    query.setQuery(value);
                    QueryResult result = mUnauthenticatedTwitter.search(query);
                    String tweet = "";
                    List<String> list = result.getTweets().stream()
                            .map(Status::getText)
                            .collect(Collectors.toList());

                    if (list.size() != 0) {
                        stringMap.clear();

                        for (int i=0;i<list.size(); i++) {
                            tweet = list.get(i);
                            Log.d("MESSAGE-"+i, tweet);
//&& !tweet.contains("")
                            if (tweet.contains("@") && tweet.contains(":") ) {
                                usernameTweet = tweet.substring(tweet.indexOf("@") + 1, tweet.indexOf(":"));
                                textTweet = tweet.substring(tweet.indexOf(":") + 1);
                                stringMap.put(usernameTweet, textTweet);
                                Log.d("QQQQQ", usernameTweet+"/"+textTweet+"\n");
                            }else{
                                stringMap.put(usernameTweet, tweet);
                                Log.d("QQQQQ", usernameTweet+"/"+textTweet+"\n");
                            }
                            Log.d("BBBBBB", usernameTweet+"/"+textTweet+"\n");

                        }

                    }
                }
            } catch (TwitterException | twitter4j.TwitterException e) {
                e.printStackTrace();
            }
        }).start();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.image_button:

                uImageView.setOnClickListener(view1 -> getUserTimeline(view1));
                break;

            case R.id.logout_button:

                logoutButton.setOnClickListener(v -> {
                    Toast.makeText(getApplication(), "Log out", Toast.LENGTH_LONG).show();
                    logoutTwitter(v);
                });
                break;
        }
        return true;
    }


    public void getUserTimeline(View view) {

        final RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserTimeline userTimeline = new UserTimeline.Builder()
                .userId(idUser)//User ID of the user to show tweets for
                .screenName(username)//screen name of the user to show tweets for
                .includeReplies(true)//Whether to include replies. Defaults to false.
                .includeRetweets(true)//Whether to include re-tweets. Defaults to true.
                .maxItemsPerRequest(20)//Max number of items to return per request
                .build();

        final TweetTimelineRecyclerViewAdapter adapter =
                new TweetTimelineRecyclerViewAdapter.Builder(this)
                        .setTimeline(userTimeline)
                        .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                        .build();

        recyclerView.setAdapter(adapter);

        valueToPass = username;
        stringMap.clear();
        getStatus(config,username);

    }

    @SuppressLint("CheckResult")
    public void searchWord() {

        RxSearchObservable.fromView(searchQuery)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(text -> !text.isEmpty())
                .distinctUntilChanged()
                .switchMap((Function<String, ObservableSource<String>>) this::dataFromNetwork)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::searchSpecificTweet
                );
    }

    public Observable<String> dataFromNetwork(final String query) {
        return Observable.just(true)
                .delay(1, TimeUnit.SECONDS)
                .map(value -> query);
    }

    public void searchSpecificTweet(String search) {
        final RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query(search)
                .languageCode("en")
                .maxItemsPerRequest(20)
                .build();

        final TweetTimelineRecyclerViewAdapter adapter =
                new TweetTimelineRecyclerViewAdapter.Builder(this)
                        .setTimeline(searchTimeline)
                        .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                        .build();
        recyclerView.setAdapter(adapter);
        valueToPass= search;
        stringMap.clear();
        getStatus(config, search);
    }

    public void logoutTwitter(View view) {
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (twitterSession != null) {

            disposable.clear();
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
            TwitterCore.getInstance().getSessionManager().clearActiveSession();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }


}
