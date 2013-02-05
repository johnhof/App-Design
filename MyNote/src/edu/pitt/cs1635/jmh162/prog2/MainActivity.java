package edu.pitt.cs1635.jmh162.prog2;

import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//initialize local objects
		initializeUIObjects();
		initializeButtonListeners();
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
			}
		});		
		b_color.setOnClickListener(new OnClickListener() 
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
		});/*
		paintView.setOnTouchListener(new View.OnTouchListener() 
		{
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				return paintView.touchHandler(v, event);
			}
		});*/
	}
}