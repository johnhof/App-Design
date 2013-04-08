package edu.pitt.cs1635.jmh162.prog4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.twitterapime.rest.Credential;
import com.twitterapime.rest.UserAccountManager;
import com.twitterapime.search.LimitExceededException;
import com.twitterapime.xauth.Token;

import android.util.Log;


public class CommUtility {
	private Lock listLock = new ReentrantLock();
	private ArrayList<Tweet> tweetList = new ArrayList<Tweet>(50);
	private Tweet selectedTweet = null;
	
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
	
	public ArrayList<Tweet> getTweetList(){
		return tweetList;
	}
	
	public Tweet getSelectedTweet(){
		return selectedTweet;
	}
	
	public void setSelectedTweet(int index){
		selectedTweet = tweetList.get(index);
	}
	
	private void login(){
		/*
		token = new Token("332139818-FGBxr0ikpNlxUiUbsYLwK5RNoCgGqb9gemUXx0h","lrx5HCXoxBJv1CccvT2ksEUet1p5We9ONUb776lnU");
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
			Log.d("---LOGin", "SUCCESS");
		}
		else{
			Log.d("---LOGin", "FAILURE");			
		}*/
	}
	private void load(){
		downloadHomeTweets();
	}
	
	public boolean post(String text){
		//TODO: post to twitter
		Log.d("POST",text);
		return true;
	}
	public boolean downloadHomeTweets(){
		
		//TODO: retrieve from twitter
		
		tweetList = new ArrayList<Tweet>();
		for(int i = 0; i < 15; i++)tweetList.add(new Tweet(""+i, ""+i, ""+i));
		return true;
	}
	
	public boolean downloadMentionTweets(){
		
		//TODO: retrieve from twitter
		
		tweetList = new ArrayList<Tweet>();
		for(int i = 5; i < 15; i++)tweetList.add(new Tweet(""+i, ""+i, ""+i));
		return true;
	}

	public User getUserData(Tweet tweet) {
		
		// TODO: get user data and set it
		
		User user = new User();
		user.setUsername(tweet.getUser());
		user.setName("name");
		user.setNumtweets(10);
		user.setNumFollowing(5);
		user.setNumFollowing(5);
		return user;
	}
}
