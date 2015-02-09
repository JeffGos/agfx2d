package com.jaigo.agfxengine.view;
// Scroller
//
// Created by Jeff Gosling on 02/02/2015
//

import android.view.MotionEvent;

public class Scroller extends View
{
	private boolean dragEnabled;
	private boolean zoomEnabled;
	private boolean rotateEnabled;

	public Scroller(float widthPercent, float heightPercent)
	{
		super(widthPercent, heightPercent);
	}

	public void setDragEnabled(boolean dragEnabled)
	{
		this.dragEnabled = dragEnabled;
	}

	public void setZoomEnabled(boolean zoomEnabled)
	{
		this.zoomEnabled = zoomEnabled;
	}

	public void setRotateEnabled(boolean rotateEnabled)
	{
		this.rotateEnabled = rotateEnabled;
	}

	@Override
	protected boolean onTouched(MotionEvent event)
	{
		return super.onTouched(event);
	}

	@Override
	protected boolean onDragged(MotionEvent event)
	{
		super.onDragged(event);

		if (event.getPointerCount() > 1)
		{
			scaleBy(-0.01f, -0.01f);
		}
		else
		{
			scaleBy(0.01f, 0.01f);
		}

		return true;
	}

	@Override
	protected boolean onReleased(MotionEvent event)
	{
		return super.onReleased(event);
	}
}
