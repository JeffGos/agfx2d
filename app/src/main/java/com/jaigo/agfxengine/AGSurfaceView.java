package com.jaigo.agfxengine;
// AGSurfaceView
//
// Created by Jeff Gosling on 08/01/2015

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.jaigo.agfxengine.common.LogTags;

public class AGSurfaceView extends GLSurfaceView
{
	public AGRenderer renderer;

	public AGSurfaceView(Context activity, AttributeSet attribs)
	{
		super(activity, attribs);

		initialise();
	}

	private void initialise()
	{
		renderer = new AGRenderer(this);

		setEGLContextClientVersion(2);
		setEGLConfigChooser(8, 8, 8, 8, 0, 0); //must be called before setRenderer
		getHolder().setFormat(PixelFormat.TRANSLUCENT); //enable alpha
		setZOrderOnTop(true);
		setRenderer(renderer);
		//setPreserveEGLContextOnPause(true);
	}

	@Override
	public void onPause()
	{
		Log.d(LogTags.DEBUG, "===== AGSurfaceView.onPause =====");

		//deallocate memory intensive graphics objects here

		super.onPause();
	}

	@Override
	public void onResume()
	{
		super.onResume();

		Log.d(LogTags.DEBUG, "===== AGSurfaceView.onResume =====");

		//reallocate memory intensive graphics objects here
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int touchX = (int) event.getX();
		int touchY = (int) event.getY();

		AGEngine.ViewManager().onViewSurfaceTouched(touchX, touchY, event.getActionMasked());

		return true;
	}
}
