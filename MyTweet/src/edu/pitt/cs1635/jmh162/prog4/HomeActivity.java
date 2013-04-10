package edu.pitt.cs1635.jmh162.prog4;

import java.util.ArrayList;

import com.twitterapime.rest.Timeline;
import com.twitterapime.search.Query;
import com.twitterapime.search.QueryComposer;
import com.twitterapime.search.SearchDeviceListener;
import com.twitterapime.search.Tweet;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class HomeActivity extends MentionActivity{
	public void timelineCall(Query q, SearchDeviceListener s, Timeline timeline){
		timeline.startGetHomeTweets(q, s);
	}
	
	public void setHighlight() {
		// TODO Auto-generated method stub
		homeButton.setBackgroundColor(0xFF005CFF);
		
	}
}
