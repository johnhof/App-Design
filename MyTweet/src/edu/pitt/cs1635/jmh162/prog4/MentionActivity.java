package edu.pitt.cs1635.jmh162.prog4;

import java.util.ArrayList;

import edu.pitt.cs1635.jmh162.prog4.TwitterActivity.TweetAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MentionActivity extends TwitterActivity{


	private Button refreshButton;
	private TweetAdapter tweetAdapter;
	private ArrayList<LocalTweet> tweetList;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		setupMenuBarButtons(this);
		mentionsButton.setBackgroundColor(0xFF005CFF);

		commUtil.downloadMentionTweets();
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

		//TODO: set onview
		listView.setOnItemLongClickListener(new ListView.OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
	    		Intent intent = new Intent(MentionActivity.this, InfoActivity.class);
	    		commUtil.setSelectedTweet(arg2);
	    		startActivity(intent);
				return true;
			}
		});
		
		listView.setOnItemClickListener(new ListView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> a, View v, int i, long l) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
				
				//startActivity(browserIntent);
	        	
				//TODO get url
	        	//TODO menu to select from multiple
	        }
	    });
	}
	
	public void refreshList(){
		commUtil.downloadMentionTweets();
		tweetList = commUtil.getTweetList();
		Intent intent = new Intent(this, MentionActivity.class);
		startActivity(intent);		
	}
}
