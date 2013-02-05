package edu.pitt.cs1635.jmh162.prog2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PaintView extends View implements OnTouchListener
{
	private int color;
	Paint userPaint;
	Paint clearPaint;
	public ArrayList<Path> pathList;
	
	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
		
		color = Color.BLACK;
		userPaint = new Paint();
		clearPaint = new Paint();
		pathList = new ArrayList<Path>();
		
		//initialize paint objects
		clearPaint.setStyle(Paint.Style.FILL);
		clearPaint.setColor(Color.WHITE);
		userPaint.setStyle(Paint.Style.STROKE);
		userPaint.setColor(color);
        userPaint.setStrokeWidth(3);
        userPaint.setPathEffect(null);
	}
	
	@Override
	protected void onDraw (Canvas canvas)
	{
		super.onDraw(canvas);
		
		//refresh userPaint objects
		userPaint.setStyle(Paint.Style.STROKE);
		userPaint.setColor(color);
		
        for(int i = 0; i < pathList.size(); i++)
        {
        	canvas.drawPath(pathList.get(i), userPaint);	
        }
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		int action = event.getAction();
		//if the event was a down event or add a new path to pathlist
		if(action == MotionEvent.ACTION_DOWN)
		{
			Path newPath = new Path();
			newPath.setLastPoint(event.getX(), event.getY());
			pathList.add(newPath);
		}
		if(action == MotionEvent.ACTION_MOVE)
		{
			pathList.get(pathList.size()-1).lineTo(event.getX(), event.getY());
		}
		invalidate();
				
		return true;
	}

	
	//empty path list and mark canvas invalid
	public void clear()
	{
		pathList = new ArrayList<Path>();
		invalidate();
	}
}
