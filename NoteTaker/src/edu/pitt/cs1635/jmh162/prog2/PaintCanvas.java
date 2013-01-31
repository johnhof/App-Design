package edu.pitt.cs1635.jmh162.prog2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;

public class PaintCanvas extends View{
	private int color;
	
	public PaintCanvas(Context context) {
		super(context);
		color = Color.BLACK;
	}

	@Override
	protected void onDraw (Canvas canvas)
	{
		//NOTE: may not be the proper functionality
		super.onDraw(canvas);
		
		//initialize paint object
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(color);
		
		//make canvas white
	}
}
