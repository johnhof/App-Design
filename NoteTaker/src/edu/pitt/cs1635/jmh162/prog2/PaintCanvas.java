package edu.pitt.cs1635.jmh162.prog2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class PaintCanvas extends View
{
	private int color;
	Paint paint;
	public ArrayList<Path> pathList;
	
	public PaintCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		/*
		color = Color.BLACK;
		paint = new Paint();
		*/
	}
	
	@Override
	protected void onDraw (Canvas canvas)
	{
		//NOTE: may not be the proper functionality
		super.onDraw(canvas);
		/*
		//initialize paint object
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(color);
		
        canvas.drawPaint(paint);
        
        paint.setStrokeWidth(3);
        paint.setPathEffect(null);
        
        for(int i = 0; i < pathList.size(); i++)
        {
        	canvas.drawPath(pathList.get(i), paint);	
        }*/
	}
}
