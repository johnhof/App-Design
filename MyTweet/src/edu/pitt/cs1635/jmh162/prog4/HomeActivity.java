package edu.pitt.cs1635.jmh162.prog4;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class HomeActivity extends TwitterActivity{

	private Button refreshButton;
	private TweetAdapter tweetAdapter;
	private ArrayList<Tweet> tweetList;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		setupMenuBarButtons(this);
		homeButton.setBackgroundColor(0xFF005CFF);
		
		commUtil.downloadHomeTweets();
		tweetList = commUtil.getTweetList();

		tweetAdapter = new TweetAdapter(this, R.layout.list_item, tweetList);
		listView = (ListView) findViewById(R.id.list_home);
		listView.setAdapter(tweetAdapter);
		
		initializeViewItems();
		initializeButtonListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	// -----------------------------------------------------------------------------------------------------------------------------
	// -- UTILITY FUNCTIONS
	// -----------------------------------------------------------------------------------------------------------------------------

	private void initializeViewItems() {
		refreshButton = (Button) findViewById(R.id.refresh_home);
	}

	private void initializeButtonListeners() {
		refreshButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				refreshList();
			}
		});
		
		listView.setOnItemClickListener(new ListView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> a, View v, int i, long l) {
	    		Log.d("----test","test");
	    		Intent intent = new Intent(HomeActivity.this, InfoActivity.class);
	    		commUtil.setSelectedTweet(i);
	    		startActivity(intent);
	        }
	    });
	}
	
	public void refreshList(){
		commUtil.downloadHomeTweets();
		tweetList = commUtil.getTweetList();
	}
}
