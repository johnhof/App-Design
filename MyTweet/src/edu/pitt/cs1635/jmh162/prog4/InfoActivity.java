package edu.pitt.cs1635.jmh162.prog4;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends TwitterActivity{
	private Tweet tweet;
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
		

		usernameText.setText(user.getUsername());
		nameText.setText(user.getName());
		tweetNumText.setText("Tweeted "+user.getNumtweets()+" times");
		followerText.setText("Following "+user.getNumFollowing());
		followsText.setText("Followers "+user.getNumFollowers());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
