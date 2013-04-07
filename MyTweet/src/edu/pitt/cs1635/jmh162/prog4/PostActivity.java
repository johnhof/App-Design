package edu.pitt.cs1635.jmh162.prog4;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class PostActivity extends TwitterActivity{
	private Button postbutton;
	private EditText textEntry;
	private String postText;
	
	//toast tools
	private CharSequence text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		setupMenuBarButtons(this);
		postButton.setBackgroundColor(0xFF005CFF);
		initializeViewItems();
		initializeButtonListeners();
	}
	// -----------------------------------------------------------------------------------------------------------------------------
	// -- UTILITY FUNCTIONS
	// -----------------------------------------------------------------------------------------------------------------------------

	private void initializeViewItems() {
		postButton = (Button) findViewById(R.id.post_button);
		textEntry = (EditText)findViewById(R.id.text_entry);
		InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(textEntry.getWindowToken(), 0);
	}

	private void initializeButtonListeners() {

		//relic, still deciding on its fate -john 3/30
		postButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				postText = textEntry.getText().toString();
				
				if(commUtil.post(postText)){
					text = "Tweet Posted: '"+postText+"'";
					textEntry.clearComposingText();
				}
				else text = "Failed to Post Tweet: '"+postText+"'";
				Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
				toast.show();
				
				Intent intent = new Intent(PostActivity.this, HomeActivity.class);
				startActivity(intent);
			}
		});
		
		textEntry.setOnEditorActionListener(new OnEditorActionListener() {

	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	            if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
	                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

	                in.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
	                return true;
	            }
	            return false;
	        }
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
