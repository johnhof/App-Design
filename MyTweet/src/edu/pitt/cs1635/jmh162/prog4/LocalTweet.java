package edu.pitt.cs1635.jmh162.prog4;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.twitterapime.model.Entity;
import com.twitterapime.rest.UserAccount;
import com.twitterapime.search.Tweet;

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
	private Tweet tweet; 
	private Entity entity;
	
	public LocalTweet(String newUserName, String newUser, String newTime, String newText){
		userName = newUserName;
		user = newUser;
		timestamp = newTime;
		text = newText;
	}
	
	public LocalTweet(Tweet newTweet) {
		userName = newTweet.getString("TWEET_AUTHOR_USERNAME");
		user = newTweet.getString("TWEET_AUTHOR_NAME");
		text = newTweet.getString("TWEET_CONTENT");
		timestamp = newTweet.getString("TWEET_PUBLISH_DATE");
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
}
