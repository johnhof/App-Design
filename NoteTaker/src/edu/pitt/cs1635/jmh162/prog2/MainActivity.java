package edu.pitt.cs1635.jmh162.prog2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;


public class MainActivity extends Activity implements OnTouchListener{
	PaintCanvas canvas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//initialize local objects
		canvas = new PaintCanvas(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) 
	{
		//get the coordinates if the user engaged the canvas
		int action = arg1.getAction();
		if(action == MotionEvent.ACTION_MOVE || 
			action == MotionEvent.ACTION_DOWN || 
			action == MotionEvent.ACTION_UP)
		{
			append();
		}
		return false;
	}
	
	private void append()
	{
		
	}
	
	
}
