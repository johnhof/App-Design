package edu.pitt.cs1635.jmh162.prog2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
	private PaintView paintView;
	private Button b_clearCanvas;
	private Button b_color;
	private Button b_submitLetter;
	private TextView tv_resultText;	
	String lastAnswer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//initialize local objects
		initializeUIObjects();
		initializeButtonListeners();
		
		lastAnswer = "";
	}
	
	public void initializeUIObjects()
	{
		//initialize buttons
		b_clearCanvas = (Button) findViewById(R.id.clear_canvas_button);
		b_color = (Button) findViewById(R.id.color_button);
		b_submitLetter = (Button) findViewById(R.id.submit_button);
		
		//initialize text field
		tv_resultText = (TextView) findViewById(R.id.result_text);
		
		//initialize canvas
		paintView = (PaintView) findViewById(R.id.paint_view);		
	}

//--------------------------------------------------------------------------------------------------
//Button Listeners
//--------------------------------------------------------------------------------------------------

	private void initializeButtonListeners() 
	{
		b_clearCanvas.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{		
				paintView.clear();
				tv_resultText.setText("Waiting...");
				
			}
		});		
		b_color.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{	
				int code = 0;
				Intent intent = new Intent(MainActivity.this, ColorPicker.class);
				intent.putExtra("oldColor", ""+PaintView.getColor());
				startActivityForResult(intent, code);
			}
		});
		b_submitLetter.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{		
				//only submit if the pointstring has values
				lastAnswer="";
				if(paintView.hasPoints())
				{
					try {
						submitLetter();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TimeoutException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{     
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) 
	    { 
	        //retrieve color
			Bundle extras = data.getExtras();
			if (extras != null) {
			    String newColorString = extras.getString("color");
			    paintView.setColor(Integer.parseInt(newColorString));
			}
	    }  
	} 
	
	private void submitLetter() throws InterruptedException, ExecutionException, TimeoutException
	{
		URL host = null;
		try
		{
			host = new URL("http://cwritepad.appspot.com/reco/usen");
		} catch (MalformedURLException e) {
		    	Log.w("error", e.toString());
		    	return;
		}
		new postTask().execute(host);
	}
	
	private class postTask extends AsyncTask<URL, Integer, Long> 
	{
		@Override
	     protected void onProgressUpdate(Integer... progress) {}

	     @Override
	     protected void onPostExecute(Long result) 
	     {
			tv_resultText.setText(lastAnswer);
		}

		@Override
		protected Long doInBackground(URL... params) 
		{
	 	   // Create a new HttpClient and Post Header
	 	    HttpClient httpclient = new DefaultHttpClient();
	 	    HttpPost httppost = null;
	 	    String url = params[0].toString();
			httppost = new HttpPost(url);

	 	    try 
	 	    {
	 	    	String test =  paintView.getFinalPointString();
	 	        //add and send data
	 	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	 	        nameValuePairs.add(new BasicNameValuePair("key", "11773edfd643f813c18d82f56a8104ed"));
	 	        nameValuePairs.add(new BasicNameValuePair("q", test));
	 	    	Log.w("error", test);
	 	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 	        
	 	        //EXCEPTION
	 	        //Execute HTTP Post Request
	 	        HttpResponse response = httpclient.execute(httppost);
	 	        HttpEntity httpEntity = response.getEntity();
	 	        lastAnswer = EntityUtils.toString(httpEntity);
				
	 	    } catch (ClientProtocolException e) {
	 	        // TODO Auto-generated catch block
	 	    } catch (IOException e) {
	 	        // TODO Auto-generated catch block
	 	    	Log.w("error", e.toString());
	 	    }	 	    
			return null;
		}
	 }
}

