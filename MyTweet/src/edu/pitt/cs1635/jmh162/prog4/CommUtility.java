package edu.pitt.cs1635.jmh162.prog4;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.util.Log;


public class CommUtility {
	private Lock listLock = new ReentrantLock();
	private ArrayList<Tweet> tweetList = new ArrayList<Tweet>(50);
	private Tweet selectedTweet = null;

	private static CommUtility singleton;

	private CommUtility() {
	}

	public static synchronized CommUtility getInstance() {
		if (CommUtility.singleton == null) {
			singleton = new CommUtility();
			singleton.load();
		}
		return singleton;
	}
	
	public ArrayList<Tweet> getTweetList(){
		return tweetList;
	}
	
	public Tweet getSelected(){
		return selectedTweet;
	}
	
	public void setSelectedTweet(int index){
		selectedTweet = tweetList.get(index);
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
}
