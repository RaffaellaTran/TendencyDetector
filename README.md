# TENDENCY DETECTOR MOBILE APP

by Raffaella Tran

I implemented the app from the 1st to the 9th of September for a job interview.

**IDEA** 

The app allows the user to logs into Twitter with his or her personal account, visualize his or her Tweets, type word(s) and search for a specific set of Tweets. 
Moreover, the user can visualise a sentiment and demographic analysis of the Tweets.

**USAGE** 

The user logs into the app using his or her Twitter account.

The home screen displays on top his or her profile image, username and the logs out button. At any time, if the user touches his or her profile image, the screen shows his or her personal Tweets. Then, there is a search bar and a "rating" button. This button shows analyses of the Tweets with sentimental and demographic algorithm through APIs in the next view.

In the centre of the home screen, the app shows user tweets by default. If the user types a word, it will change the view of the researched Tweets.

When the user presses the "rating" button, the activity sends the researched Tweets to the next screen. The display shows a summary title and 3 graphs. The first represents the positiveness of the Tweets, the second the gender of the users and the third the age range of the users.

**API** 

For retrieving the Tweets I used Twitter Kit. For the analysis, I utilised the sentiment and demographic APIs. In the beginning, I tried to use Google and Microsoft APIs, but they kept rejecting my credit cards. Therefore after testing other free APIs, I decided to used the following because they offer good results.

**TECHNOLOGIES USED** 

* For the implementation of the app, I used Android Studio 3.0, Rxjava2. For Twitter, I used Twitter Kit.

* For the APIs, I used retrofit2, okhttp3 and gson.

* For the charts, I used MPAndroidChart and SpeedView.

* For the unit test, I used Mockito.

* For reducing boilerplate, I used Lombok.

IMPROVEMENTS

- Implementation of new functionalities with the API data for the rating screen. For example the analysis of the personal Tweets.

- Fix bugs in the search bar.

- Improve the performance of the application because it is slow in the uploading and to analyse the data.

- Clean the text before sends to the rating activity. - Do more unit testing. Some functions required the authentication.
