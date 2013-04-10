package edu.pitt.cs1635.jmh162.prog4;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.twitterapime.model.Entity;
import com.twitterapime.model.MetadataSet;
import com.twitterapime.rest.UserAccount;
import com.twitterapime.search.Tweet;
import com.twitterapime.search.TweetEntity;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LocalTweet{
	private String userName;
	private String user;
	private String timestamp;
	private String text;
	private String followers;
	private String following;
	private String tweetCount;
	private Tweet tweet; 
	private TweetEntity entity;
	
	public LocalTweet(String newUserName, String newUser, String newTime, String newText){
		userName = newUserName;
		user = newUser;
		timestamp = newTime;
		text = newText;
	}
	
	public LocalTweet(Tweet newTweet) {
		tweet = newTweet;
		UserAccount fullUser = tweet.getUserAccount();
		userName = fullUser.getString("USERACCOUNT_USER_NAME");
		user = fullUser.getString("USERACCOUNT_NAME");
		text = tweet.getString("TWEET_CONTENT");
		followers = fullUser.getString("USERACCOUNT_FOLLOWERS_COUNT");
		following = fullUser.getString("USERACCOUNT_FRIENDS_COUNT");
		tweetCount = fullUser.getString("USERACCOUNT_TWEETS_COUNT");
		
		
		Date date = new Date(Long.parseLong(tweet.getString("TWEET_PUBLISH_DATE")));
		SimpleDateFormat displayDate = new SimpleDateFormat("EEE, d, MMM");
		timestamp = displayDate.format(date);
		
		entity = tweet.getEntity();
		
		if (entity != null) {
			TweetEntity[] urls = entity.getURLs();
			for (int i = 0; i < urls.length; i++) {
				Log.d("PRINTING URLS",urls[i].getString(MetadataSet.TWEETENTITY_URL));
			}
		}
		//int intTime = Integer.getInteger();
		//timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(intTime * 1000L));
	}

	public String getUserName(){
		return userName;
	}

	public String getUser(){
		return user;
	}
	
	public String getTimestamp(){
		return timestamp;
	}
	
	public String getText(){
		return text;
	}	
	
	public Entity getEntity(){
		return entity;
	}

	public String getNumtweets() {
		return tweetCount;
	}

	public String getNumFollowing() {
		return following;
	}

	public String getNumFollowers() {
		return followers;
	}
}
