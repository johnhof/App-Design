package edu.pitt.cs1635.jmh162.prog4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.twitterapime.rest.Credential;
import com.twitterapime.rest.Timeline;
import com.twitterapime.rest.TweetER;
import com.twitterapime.rest.UserAccountManager;
import com.twitterapime.search.LimitExceededException;
import com.twitterapime.search.QueryComposer;
import com.twitterapime.search.SearchDevice;
import com.twitterapime.search.SearchDeviceListener;
import com.twitterapime.search.Tweet;
import com.twitterapime.xauth.Token;

import android.app.DownloadManager.Query;
import android.util.Log;


public class CommUtility {
	private Lock listLock = new ReentrantLock();
	private ArrayList<LocalTweet> tweetList = new ArrayList<LocalTweet>(50);
	private LocalTweet selectedTweet = null;
	
	private Token token;
	private Credential credential;
	private UserAccountManager accntMgr;
	private boolean loggedIn;

	private static CommUtility singleton;

	private CommUtility() {
	}

	public static synchronized CommUtility getInstance() {
		if (CommUtility.singleton == null) {
			singleton = new CommUtility();
			singleton.login();
			singleton.load();
		}
		return singleton;
	}
	
	public ArrayList<LocalTweet> getTweetList(){
		return tweetList;
	}
	
	public LocalTweet getSelectedTweet(){
		return selectedTweet;
	}
	
	public void setSelectedTweet(int index){
		selectedTweet = tweetList.get(index);
	}
	
	public void login(){
		token = new Token("1332139818-LdpljQTDL1pICxv0bcTH8iKxImKSq6bQPqaK7zE","3ipPBXu0TBgsIOZcTCeacDCoQJzdPKI2dcJSrYn3Ss");
		credential = new Credential("johnhofrichter", "QmzPlI0tyfHu1r0MHkBUsg", "wTObKWjEQqrti0ca38DQQtGVuBtXmTBmSFI3z8PLDgY", token);
		accntMgr = UserAccountManager.getInstance(credential);
		
		try {
			loggedIn = accntMgr.verifyCredential();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LimitExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(loggedIn){
			Log.d("---LOGIN", "SUCCESS");
		}
		else{
			Log.d("---LOGIN", "FAILURE");			
		}
	}
	private void load(){
		downloadHomeTweets();
	}
	
	public boolean post(String text){

		login();
		
		if(!loggedIn){
			Log.d("POST FAILED","NOT LOGGED IN");
			return false;
		}
		Tweet tweet = new Tweet(text);
		TweetER ter = TweetER.getInstance(accntMgr);
		try {
			tweet = ter.post(tweet);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LimitExceededException e) {
			e.printStackTrace();
		}
		
		//TODO: post to twitter
		Log.d("POST",text);
		return true;
	}
	
	public boolean downloadHomeTweets(){
    	login();

		if(!loggedIn){
			Log.d("HOME FAILED","NOT LOGGED IN");
			return false;
		}
		/*
		CommThread thread = new CommThread();
		thread.setThreadtype(CommThread.DL_HOME);
		thread.execute();
		*/
		
		Timeline timeline = Timeline.getInstance (accntMgr);
		com.twitterapime.search.Query q = QueryComposer.count(10);

		timeline.startGetHomeTweets(q, new SearchDeviceListener() {
			@Override public void searchCompleted() {}
			@Override public void searchFailed(Throwable arg0) {
				Log.d("ADDED","ugh");
				}
			@Override
			public void tweetFound(Tweet tweet) {
				addToList(tweet);				
				Log.d("ADDED",tweet.toString());
			}
		});
		
		Log.d("HOME TEST",tweetList.toString());
		
		return true;
	}
	
	public boolean downloadMentionTweets(){
		
		//TODO: retrieve from twitter
		
		tweetList = new ArrayList<LocalTweet>();
		//get your tweets
		SearchDevice sDevice = SearchDevice.getInstance();
		com.twitterapime.search.Query q = QueryComposer.containAny("@Johnhoffrichter @johnhoffrichter @johnHoffrichter");
		Tweet[] myMentions = null;
		try {
			myMentions = sDevice.searchTweets(q);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LimitExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		for(int i=0; i<myMentions.length; i ++ ){
			LocalTweet tweet = new LocalTweet(myMentions[i]);
			tweetList.add(tweet);
		}
		/*
		for(int i = 5; i < 15; i++)tweetList.add(new LocalTweet(""+i, ""+i, ""+i));*/
		return true;
	}

	public User getUserData(LocalTweet tweet) {
		
		// TODO: get user data and set it
		
		User user = new User();
		user.setUsername(tweet.getUser().toString());
		user.setName("name");
		user.setNumtweets(10);
		user.setNumFollowing(5);
		user.setNumFollowing(5);
		return user;
	}
	
	public boolean loggedIn(){
		return loggedIn;
	}
	
	public void clearList(){
		tweetList = new ArrayList<LocalTweet>();
	}
	
	public UserAccountManager getAccntMgr(){
		return accntMgr;
	}

	public void addToList(Tweet tweet) {
		tweetList.add(new LocalTweet(tweet));
	}
}
