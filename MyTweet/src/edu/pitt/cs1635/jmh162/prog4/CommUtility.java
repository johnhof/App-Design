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

	public void addToList(LocalTweet tweet) {
		tweetList.add(tweet);
	}
}
