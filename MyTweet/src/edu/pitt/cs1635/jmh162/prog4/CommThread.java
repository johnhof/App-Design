package edu.pitt.cs1635.jmh162.prog4;

import java.net.URL;

import com.twitterapime.rest.Timeline;
import com.twitterapime.search.QueryComposer;
import com.twitterapime.search.SearchDeviceListener;
import com.twitterapime.search.Tweet;

import android.os.AsyncTask;
import android.util.Log;

public class CommThread extends AsyncTask<URL, Integer, Long> {

	CommUtility commUtil = CommUtility.getInstance();
	int threadType = 0;
	
	public final static int DL_HOME = 1;
	public final static int DL_MENTION = 2;
	public final static int DL_USER = 3;
	
	public void setThreadtype(int type){
		threadType = type;
	}
	
    protected Long doInBackground(URL... urls) {
		
		Timeline timeline = Timeline.getInstance (commUtil.getAccntMgr());
		com.twitterapime.search.Query q = QueryComposer.count(10);

		timeline.startGetHomeTweets(q, new SearchDeviceListener() {
			@Override public void searchCompleted() {}
			@Override public void searchFailed(Throwable arg0) {}
			@Override
			public void tweetFound(Tweet tweet) {
				commUtil.addToList(tweet);				
			}
		});
        return (long) 0;
    }

    protected void onProgressUpdate(Integer... progress) { }

    protected void onPostExecute(Long result) {}
}
