package com.jaigo.agfxengine.manager;
// ViewManager
//
// Created by Jeff Gosling on 09/01/2015

import android.util.Log;
import android.view.MotionEvent;

import com.jaigo.agfxengine.common.LogTags;
import com.jaigo.agfxengine.view.BaseView;
import com.jaigo.agfxengine.view.TexturedView;

public class ViewManager
{
	private BaseView rootView = null;
	private BaseView touchedView = null;
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

	public void onViewSurfaceTouched(float touchPercentX, float touchPercentY, MotionEvent event)
	{
		int motionEventAction= event.getActionMasked();

		if (motionEventAction == MotionEvent.ACTION_MOVE)
		{
			if (touchedView != null)
			{
				if (!touchedView.isValueWithinView(touchPercentX, touchPercentY))
				{
					touchedView.onReleased(touchPercentX, touchPercentY, event);
					touchedView = null;
				}
				else
				{
					touchedView.onDragged(touchPercentX, touchPercentY, event);
				}
			}
		}
		else if (motionEventAction == (MotionEvent.ACTION_DOWN) || motionEventAction == (MotionEvent.ACTION_UP))
		{
			handleTouchEvent(rootView, touchPercentX, touchPercentY, event);
		}
	}

	private boolean handleTouchEvent(BaseView viewToCheck, float touchPercentX, float touchPercentY, MotionEvent event)
	{
		if (viewToCheck == null || !viewToCheck.isEnabled() || !viewToCheck.isVisible())
		{
			return false;
		}

		boolean handledByChild = false;

		for (int i = viewToCheck.getChildren().size() - 1; i >= 0; i--)
		{
			BaseView child = viewToCheck.getChild(i);

			if (child.isValueWithinView(touchPercentX, touchPercentY))
			{
				handledByChild = handleTouchEvent(child, touchPercentX, touchPercentY, event);
			}
		}

		if (!handledByChild)
		{
			handledByChild |= onViewTouchedEvent(viewToCheck, touchPercentX, touchPercentY, event);
		}

		return handledByChild;
	}

	private boolean onViewTouchedEvent(BaseView viewToCheck, float touchPercentX, float touchPercentY, MotionEvent event) {

		int motionEventAction = event.getActionMasked();

		//if no child handles the event, the parent will
		if (motionEventAction == (MotionEvent.ACTION_DOWN))
		{
			touchedView = viewToCheck;

			return viewToCheck.onTouched(touchPercentX, touchPercentY, event);
		}
		else if (motionEventAction == (MotionEvent.ACTION_UP))
		{
			boolean handled = false;

			if (touchedView != null)
			{
				if (touchedView == viewToCheck)
				{
					handled |= touchedView.onClicked(touchPercentX, touchPercentY, event);
				}

				handled |= touchedView.onReleased(touchPercentX, touchPercentY, event);
				touchedView = null;
			}

			handled |= viewToCheck.onReleased(touchPercentX, touchPercentY, event);

			return handled;
		}

		return false;
	}
}
