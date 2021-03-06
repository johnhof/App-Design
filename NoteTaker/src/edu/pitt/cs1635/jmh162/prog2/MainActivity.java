package edu.pitt.cs1635.jmh162.prog2;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Path;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends Activity implements OnTouchListener{
	private PaintCanvas canvas;
	private Button b_clearCanvas;
	private Button b_options;
	private Button b_submitLetter;
	private TextView tv_resultText;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//initialize local objects
		//initializeUIObjects();
		//initializeButtonListeners();
	}
	
	public void initializeUIObjects()
	{
		//initialize buttons
		b_clearCanvas = (Button) findViewById(R.id.clear_canvas_button);
		b_options = (Button) findViewById(R.id.options_button);
		b_submitLetter = (Button) findViewById(R.id.submit_button);
		
		//initialize text field
		tv_resultText = (TextView) findViewById(R.id.result_text);
		
		//initialize canvas
		canvas = (PaintCanvas) findViewById(R.id.paint_canvas);		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
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
				//TODO: Clear Canvas
			}
		});		
		b_options.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{		
				//TODO: open color Selector
			}
		});
		b_submitLetter.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{		
				//TODO: request letters from server
			}
		});
	}

//--------------------------------------------------------------------------------------------------
//Canvas listener and utility functions
//--------------------------------------------------------------------------------------------------
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) 
	{
		/*
		int action = arg1.getAction();
		//if the event was a down event or add a new path to pathlist
		if(action == MotionEvent.ACTION_DOWN || canvas.pathList.size()==0)
		{
			Path newPath = new Path();
			newPath.setLastPoint(arg1.getX(), arg1.getY());
			canvas.pathList.add(newPath);
		}
		else if(action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_UP)
		{
			canvas.pathList.get(canvas.pathList.size()-1).lineTo(arg1.getX(), arg1.getY());
		}*/
		return true;
	}
	
	private void append()
	{
		
	}
}