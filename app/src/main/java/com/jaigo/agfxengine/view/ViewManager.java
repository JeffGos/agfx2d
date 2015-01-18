package com.jaigo.agfxengine.view;
// ViewManager
//
// Created by Jeff Gosling on 09/01/2015

import android.util.Log;
import android.view.MotionEvent;

import com.jaigo.agfxengine.common.LogTags;

public class ViewManager
{
	private BaseView rootView = null;
	private BaseView touchedView = null;
	private BaseView draggedView = null;
	private boolean initialised;

	public void initialise()
	{
		rootView = new BaseView(1.0f, 1.0f);
		initialised = true;
	}

	public void destroy()
	{
		Log.d(LogTags.OPEN_GL, "ViewManager.destroy()");

		initialised = false;
	}

	public void drawViews()
	{
		if (!initialised)
		{
			return;
		}

		for (int i = 0; i <  rootView.getChildren().size(); ++i)
		{
			BaseView glView = rootView.getChild(i);
			glView.draw();
		}
	}

	public void addView(BaseView view)
	{
		rootView.addChild(view);
	}

	public void removeView(TexturedView view)
	{
		rootView.removeChild(view);
	}

	public void clearViews()
	{
		rootView.clearChildren();
	}

	public void onViewSurfaceTouched(int touchX, int touchY, int motionEventAction)
	{
		if (motionEventAction == MotionEvent.ACTION_MOVE)
		{
			if (draggedView != null)
			{
				draggedView.onDragged(touchX, touchY);
			}
		}
		else if (motionEventAction == (MotionEvent.ACTION_DOWN) || motionEventAction == (MotionEvent.ACTION_UP))
		{
			handleTouchEvent(rootView, touchX, touchY, motionEventAction);
		}
	}

	private void handleTouchEvent(BaseView viewToCheck, int touchX, int touchY, int motionEventAction)
	{
		if (viewToCheck == null || !viewToCheck.isEnabled() || !viewToCheck.isVisible())
		{
			return;
		}

		boolean handledByChild = false;

		for (int i = viewToCheck.getChildren().size() - 1; i >= 0; i--)
		{
			BaseView child = viewToCheck.getChild(i);

			if (child.isValuePixelsWithinView(touchX, touchY))
			{
				handleTouchEvent(child, touchX, touchY, motionEventAction);
				handledByChild = true;
			}
		}

		if (!handledByChild)
		{
			//if no child handles the event, the parent will
			if (motionEventAction == (MotionEvent.ACTION_DOWN))
			{
				touchedView = viewToCheck;

				viewToCheck.onTouched(touchX, touchY);
			}
			else if (motionEventAction == (MotionEvent.ACTION_UP))
			{
				if (viewToCheck == touchedView) {
					viewToCheck.onClicked(touchX, touchY);
				}

				touchedView = null;
				viewToCheck.onReleased(touchX, touchY);
			}
		}
	}
}
