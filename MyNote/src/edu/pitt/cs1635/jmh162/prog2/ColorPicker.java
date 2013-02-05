package edu.pitt.cs1635.jmh162.prog2;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ColorPicker extends Activity {
	private Button b_done;
	private RadioGroup r_colors;
	int color;
	int oldColor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_picker);
		color = 0;

		//initialize local objects
		initializeUIObjects();
		initializeButtonListeners();
	}
	
	public void initializeUIObjects()
	{
		//initialize buttons
		b_done = (Button) findViewById(R.id.done_button);
		r_colors = (RadioGroup) findViewById(R.id.color_group);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String oldColorString = extras.getString("oldColor");
		    oldColor = Integer.parseInt(oldColorString);
		}
		
		//mark the button representing the current color
		switch(oldColor)
		{
			case Color.RED:
				r_colors.check(R.id.red_radio);
				break;
			case Color.CYAN:
				r_colors.check(R.id.cyan_radio);
				break;
			case Color.YELLOW:
				r_colors.check(R.id.yellow_radio);
				break;
			case Color.GREEN:
				r_colors.check(R.id.green_radio);
				break;
			case Color.BLUE:
				r_colors.check(R.id.blue_radio);
				break;
			case Color.MAGENTA:
				r_colors.check(R.id.magenta_radio);
				break;
			case Color.BLACK:
				r_colors.check(R.id.black_radio);
				break;
		}
	}
	
	//--------------------------------------------------------------------------------------------------
	//Button Listeners
	//--------------------------------------------------------------------------------------------------
	
	private void initializeButtonListeners() 
	{
		b_done.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{				
				//grab the color ID of the button selected
				int radioSelection = r_colors.getCheckedRadioButtonId();
				if(radioSelection == R.id.red_radio) color = Color.RED;
				if(radioSelection == R.id.cyan_radio) color = Color.CYAN;
				if(radioSelection == R.id.yellow_radio) color = Color.YELLOW;
				if(radioSelection == R.id.green_radio) color = Color.GREEN;
				if(radioSelection == R.id.blue_radio) color = Color.BLUE;
				if(radioSelection == R.id.magenta_radio) color = Color.MAGENTA;
				if(radioSelection == R.id.black_radio) color = Color.BLACK;
			
				Intent resultData = new Intent();
				resultData.putExtra("color", ""+color);
				setResult(RESULT_OK, resultData);
				if(getParent()!=null) getParent().setResult(RESULT_OK, resultData);
				finish();
			}
		});		
	}
}