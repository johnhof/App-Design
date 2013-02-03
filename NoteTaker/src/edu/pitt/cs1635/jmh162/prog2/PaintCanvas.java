package edu.pitt.cs1635.jmh162.prog2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class PaintCanvas extends View
{
	private int color;
	Paint paint;
	
	public PaintCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		color = Color.BLACK;
		paint = new Paint();
	}
	
	@Override
	protected void onDraw (Canvas canvas)
	{
		//NOTE: may not be the proper functionality
		super.onDraw(canvas);
		
		//initialize paint object
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(color);
		
		
		//make canvas white
		
	}
}
