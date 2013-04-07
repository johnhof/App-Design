package edu.pitt.cs1635.jmh162.prog4;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Tweet{
	private String user;
	private String timestamp;
	private String text;
	
	public Tweet(String newUser, String newTime, String newText){
		user = newUser;
		timestamp = newTime;
		text = newText;
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
}
