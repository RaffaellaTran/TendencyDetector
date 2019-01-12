package com.app.raffaellatran.tendencydetector.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.app.raffaellatran.tendencydetector.R;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetui.TweetUi;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {

    TwitterLoginButton loginButton;
    TwitterSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        TweetUi.getInstance();
        setContentView(R.layout.activity_login);
        session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        if (session != null) {
            login(session);
        } else {
            loginTwitter();
        }
    }

    void loginTwitter() {
        loginButton = findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                login(session);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void login(TwitterSession session) {

        String username;
        long idUser;
        if (session != null) {

            TwitterAuthToken authToken = session.getAuthToken();
            String token = authToken.token;
            String secret = authToken.secret;
            username = session.getUserName();
            idUser = session.getUserId();
            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();

            Call<User> call = twitterApiClient.getAccountService().verifyCredentials(true, false, true);

            call.enqueue(new Callback<User>() {
                @Override
                public void success(Result<User> result) {
                    User user = result.data;
                    String imageProfileUrl = user.profileImageUrl;

                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                    mainActivity.putExtra("token", token);
                    mainActivity.putExtra("secret", secret);
                    mainActivity.putExtra("username", username);
                    mainActivity.putExtra("idUser", idUser);
                    mainActivity.putExtra("imageProfile", imageProfileUrl);

                    startActivity(mainActivity);
                    finish();
                }

                @Override
                public void failure(TwitterException exception) {
                    Toast.makeText(LoginActivity.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //if user is not authenticated first ask user to do authentication
            Toast.makeText(this, "First to Twitter auth to Verify Credentials.", Toast.LENGTH_SHORT).show();
        }
    }
}
