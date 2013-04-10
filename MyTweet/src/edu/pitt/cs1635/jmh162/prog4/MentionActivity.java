package edu.pitt.cs1635.jmh162.prog4;

import java.util.ArrayList;
import java.util.List;

import com.twitterapime.rest.Timeline;
import com.twitterapime.search.Query;
import com.twitterapime.search.QueryComposer;
import com.twitterapime.search.SearchDeviceListener;
import com.twitterapime.search.Tweet;

import edu.pitt.cs1635.jmh162.prog4.TwitterActivity.TweetAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class MentionActivity extends TwitterActivity{


	private ImageButton refreshButton;
	private TweetAdapter tweetAdapter;
	private ListView listView;
	private Handler handler;
	private List<LocalTweet> newTweets;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		setupMenuBarButtons(this);
		setHighlight();
		
		newTweets = new ArrayList<LocalTweet>();
		handler = new Handler(){
			public void handleMessage(Message _){
				//commUtil.clearList();
				for(int i = 0; i < newTweets.size(); i++){
					commUtil.addToList(newTweets.get(i));
				}
				
				newTweets.clear();
				tweetAdapter.notifyDataSetChanged();
			}
		};
		commUtil.clearList();
		tweetAdapter = new TweetAdapter(this, R.layout.list_item, commUtil.getTweetList());
		downloadMentionTweets();
		
		listView = (ListView) findViewById(R.id.list_home);
		listView.setAdapter(tweetAdapter);

		
		initializeViewItems();
		initializeButtonListeners();
	}

	public void setHighlight() {
		// TODO Auto-generated method stub
		mentionsButton.setBackgroundColor(0xFF005CFF);
		
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
		refreshButton = (ImageButton) findViewById(R.id.refresh_home);
	}
	
	private boolean downloadMentionTweets(){
		commUtil.login();

		if(!commUtil.loggedIn()){
			Log.d("HOME FAILED","NOT LOGGED IN");
			return false;
		}
		
		Timeline timeline = Timeline.getInstance (commUtil.getAccntMgr());
		com.twitterapime.search.Query q = QueryComposer.count(20);
		SearchDeviceListener s = new SearchDeviceListener() {
				@Override public void searchCompleted() {
					Log.e("AHHH", "AHHHH");
					handler.sendMessage(new Message());
					
				}
				@Override public void searchFailed(Throwable arg0) {
					Log.e("AHHH", arg0.toString());
					
				}
				@Override
				public void tweetFound(final Tweet tweet) {
					LocalTweet local = new LocalTweet(tweet);
					newTweets.add(local);	

				}
			};
		timelineCall(q, s, timeline);
		return true;
	}
	
	public void timelineCall(Query q, SearchDeviceListener s, Timeline timeline){
		timeline.startGetMentions(q, s);
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
		tweetAdapter.notifyDataSetChanged();
	}
}
