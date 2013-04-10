package edu.pitt.cs1635.jmh162.prog4;

import java.util.ArrayList;

import com.twitterapime.rest.Timeline;
import com.twitterapime.search.QueryComposer;
import com.twitterapime.search.SearchDeviceListener;
import com.twitterapime.search.Tweet;

import edu.pitt.cs1635.jmh162.prog4.TwitterActivity.TweetAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
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
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		setupMenuBarButtons(this);
		mentionsButton.setBackgroundColor(0xFF005CFF);

		commUtil.clearList();
		downloadMentionTweets();

		tweetAdapter = new TweetAdapter(this, R.layout.list_item, commUtil.getTweetList());
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
	
	private boolean downloadMentionTweets(){
		commUtil.login();

		if(!commUtil.loggedIn()){
			Log.d("HOME FAILED","NOT LOGGED IN");
			return false;
		}
		
		Timeline timeline = Timeline.getInstance (commUtil.getAccntMgr());
		com.twitterapime.search.Query q = QueryComposer.count(20);

		timeline.startGetMentions(q, new SearchDeviceListener() {
			@Override public void searchCompleted() {}
			@Override public void searchFailed(Throwable arg0) {}
			@Override
			public void tweetFound(final Tweet tweet) {
				LocalTweet local = new LocalTweet(tweet);
				commUtil.addToList(local);	

				MentionActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(tweetAdapter!=null)tweetAdapter.notifyDataSetChanged();
					}
				});
			}
		});
		return true;
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
	    		Intent intent = new Intent(MentionActivity.this, InfoActivity.class);
	    		commUtil.setSelectedTweet(i);
	    		startActivity(intent);
	        }
	    });
	}
	
	public void refreshList(){
		commUtil.clearList();
		downloadMentionTweets();
	}
}
