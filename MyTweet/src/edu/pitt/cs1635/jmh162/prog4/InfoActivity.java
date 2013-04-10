package edu.pitt.cs1635.jmh162.prog4;

import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends TwitterActivity{
	private LocalTweet tweet;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		setupMenuBarButtons(this);
		
		tweet = commUtil.getSelectedTweet();
		user = commUtil.getUserData(tweet);

		TextView usernameText = (TextView) findViewById(R.id.username);
		TextView nameText = (TextView) findViewById(R.id.name);
		TextView tweetNumText = (TextView) findViewById(R.id.tweet_number);
		TextView followerText = (TextView) findViewById(R.id.followers);
		TextView followsText = (TextView) findViewById(R.id.following);
		TextView tweetText = (TextView) findViewById(R.id.tweet_text);

		usernameText.setText(tweet.getUserName());
		nameText.setText(tweet.getUser());
		tweetNumText.setText("Tweeted "+user.getNumtweets()+" times");
		followerText.setText("Following "+user.getNumFollowing());
		followsText.setText("Followers "+user.getNumFollowers());
		tweetText.setText(tweet.getText());
		
		//SEAN THIS BETTER FUCKING WORK
		Linkify.addLinks(tweetText, Linkify.ALL);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
