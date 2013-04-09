package edu.pitt.cs1635.jmh162.prog4;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class TwitterActivity extends Activity{
	private static Context context;
	protected static Button postButton;
	protected static Button homeButton;
	protected static  Button mentionsButton;
	CommUtility commUtil = CommUtility.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TwitterActivity.context = getApplicationContext();
	}

	protected static void setupMenuBarButtons(final Activity activity) {
		postButton = (Button) activity.findViewById(R.id.post);
		homeButton = (Button) activity.findViewById(R.id.home);
		mentionsButton = (Button) activity.findViewById(R.id.mentions);

		postButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity, PostActivity.class);
				activity.startActivity(intent);
			}
		});

		homeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity, HomeActivity.class);
				activity.startActivity(intent);
			}
		});

		mentionsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity, MentionActivity.class);
				activity.startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public static Context getContext() {
		return TwitterActivity.context;
	}

	public class TweetAdapter extends ArrayAdapter<LocalTweet> {
	
		private ArrayList<LocalTweet> tweets;
		LocalTweet tweet;
	
		public TweetAdapter(Context context, int textViewResourceId,	ArrayList<LocalTweet> tweets) {
			super(context, textViewResourceId, tweets);
			this.tweets = tweets;
		}
	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
	
			View view = convertView;
			if (view == null) {
				LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflator.inflate(R.layout.list_item, null);
			}
	
			tweet = tweets.get(position);
			if (tweet != null) {
				// button creation
				TextView userField = (TextView) view.findViewById(R.id.user);
				TextView timeField = (TextView) view.findViewById(R.id.timestamp);
				TextView textField = (TextView) view.findViewById(R.id.tweet_text);
				
				userField.setText(tweet.getUser().toString());
				//timeField.setText(tweet.getTimestamp().toString());
				textField.setText(tweet.getText());
			}
			return view;
		}
	}		
}
