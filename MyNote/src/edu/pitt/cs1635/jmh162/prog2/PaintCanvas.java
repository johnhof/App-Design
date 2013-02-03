package edu.pitt.cs1635.jmh162.prog2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class PaintCanvas extends View{
	private int color;
	Paint paint;
	
	public PaintCanvas(Context context) {
		super(context);
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
