package com.app.raffaellatran.tendencydetector.view;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.app.raffaellatran.tendencydetector.R;
import com.app.raffaellatran.tendencydetector.observables.RxSearchObservable;
import com.app.raffaellatran.tendencydetector.services.APIService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class MainActivityTest {
    private MainActivity mainActivity;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private SearchView searchView;
    @Mock
    Button tweetButton;
    @Mock
    View view;
    @Mock
    RxSearchObservable rxSearchObservable;
    @Mock
    private Button ratingButton;

    @Mock
    private ImageButton profileImage;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        APIService apiService;

    }
    @Test
    public void initParameter() {
       // searchView = mainActivity.findViewById(R.id.enter_search_query);
        tweetButton.callOnClick();
        verify(tweetButton).callOnClick();
    }

    @Test
    public void configuration() {
    }

    @Test
    public void getStatus() {
    }

    @Test
    public void onOptionsItemSelected() {

       profileImage.setOnClickListener(v ->getUserTimeline());
        verify(profileImage).setOnClickListener(v -> getUserTimeline());
    }

    @Test
    public void getUserTimeline() {
    }

    @Test
    public void searchWord() {

        searchView.setQuery("berlin",true);
        verify(searchView).isSubmitButtonEnabled();

    }

    @Test
    public void dataFromNetwork() {
    }

    @Test
    public void searchSpecificTweet() {
        String search = "";

    }

    @Test
    public void logoutTwitter() {

        ratingButton.callOnClick();
        verify(ratingButton).callOnClick();
    }
}