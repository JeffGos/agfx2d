package com.jaigo.agfxengine.view;
// BaseView
//
// Created by Jeff Gosling on 08/01/2015

import android.view.MotionEvent;

import com.jaigo.agfxengine.view.listeners.OnClickListener;
import com.jaigo.agfxengine.view.listeners.OnDragListener;
import com.jaigo.agfxengine.view.listeners.OnReleaseListener;
import com.jaigo.agfxengine.view.listeners.OnTouchListener;

import java.util.concurrent.CopyOnWriteArrayList;

public class BaseView
{
	private BaseView parent;
	private CopyOnWriteArrayList<BaseView> children = new CopyOnWriteArrayList<BaseView>();

	private float widthPercent;
	private float heightPercent;
	private float centerPercentX = 0.5f;
	private float centerPercentY = 0.5f;

	private float rotateXAngle = 0.0f;
	private float rotateYAngle = 0.0f;
	private float rotateZAngle = 0.0f;

	private boolean isVisible = true;
	private boolean isEnabled = true;
	private boolean isDraggable = false;

	protected float startTouchPercentX;
	protected float startTouchPercentY;
	protected volatile float lastTouchedPercentX = 0.0f;
	protected volatile float lastTouchedPercentY = 0.0f;

	protected boolean isDragged = false;
	protected boolean isTouched = false;

	protected OnClickListener onClickListener;
	protected OnDragListener onDragListener;
	protected OnTouchListener onTouchListener;
	protected OnReleaseListener onReleaseListener;

	public BaseView()
	{}

	public BaseView(float widthPercent, float heightPercent)
	{
		setWidth(widthPercent);
		setHeight(heightPercent);
	}

	public boolean hasParent()
	{
		return parent != null;
	}

	public BaseView getParent()
	{
		return parent;
	}

	public BaseView setParent(BaseView p)
	{
		parent = p;
		return this;
	}

	public BaseView clearChildren()
	{
		children.clear();
		return this;
	}

	public CopyOnWriteArrayList<BaseView> getChildren()
	{
		return children;
	}

	public BaseView getChild(int index)
	{
		if (index < 0 || index >= children.size())
		{
			return null;
		}

		return children.get(index);
	}

	public int getChildCount()
	{
		return children.size();
	}

	public boolean hasChild(BaseView child)
	{
		return children.contains(child);
	}

	public BaseView removeChild(BaseView child)
	{
		children.remove(child);
		return this;
	}

	public BaseView addChild(BaseView child)
	{
		child.setParent(this);
		children.add(child);
		return this;
	}

	public BaseView addChildren(BaseView... newChildren)
	{
		for (BaseView child : newChildren)
		{
			child.setParent(this);
			children.add(child);
		}

		return this;
	}

	public final boolean isVisible()
	{
		return isVisible;
	}

	public BaseView setVisible(boolean visible)
	{
		isVisible = visible;

		for (BaseView child : children)
		{
			child.setVisible(visible);
		}

		return this;
	}

	public final boolean isEnabled()
	{
		return isEnabled;
	}

	public BaseView setEnabled(boolean enabled)
	{
		isEnabled = enabled;

		for (BaseView child : children)
		{
			child.setEnabled(isEnabled);
		}

		return this;
	}

	public boolean isDraggable()
	{
		return isDraggable;
	}

	public void setDraggable(boolean isDraggable)
	{
		this.isDraggable = isDraggable;
	}

	public final boolean onTouched(float touchPercentX, float touchPercentY, MotionEvent event)
	{
		startTouchPercentX = lastTouchedPercentX = touchPercentX;
		startTouchPercentY = lastTouchedPercentY = touchPercentY;

		isTouched = true;

		boolean handled = false;

		if (onTouchListener != null)
		{
			handled |= onTouchListener.onTouched(this);
		}

		handled |= onTouched(event);

		return handled;
	}

	protected boolean onTouched(MotionEvent event) { return false; }

	public final boolean onDragged(float touchPercentX, float touchPercentY, MotionEvent event)
	{
		if (!isTouched)
		{
			return false;
		}

		if (!isDragged)
		{
			float dragAmountXSinceStart = touchPercentX - startTouchPercentX;
			float dragAmountYSinceStart = touchPercentX - startTouchPercentY;
			final float DRAG_AMOUNT_THRESHOLD = 0.05f;

			if (Math.abs(dragAmountXSinceStart) > DRAG_AMOUNT_THRESHOLD || Math.abs(dragAmountYSinceStart) > DRAG_AMOUNT_THRESHOLD)
			{
				isDragged = true;
			}
		}

		boolean handled = false;

		if (isDragged)
		{
			float dragAmountX = touchPercentX - lastTouchedPercentX;
			float dragAmountY = touchPercentY - lastTouchedPercentY;

			if (onDragListener != null)
			{
				handled |= onDragListener.onDrag(this, dragAmountX, dragAmountY);
			}

			if (isDraggable)
			{
				moveBy(dragAmountX, dragAmountY);
			}

			handled |= onDragged(event);

			lastTouchedPercentX = touchPercentX;
			lastTouchedPercentY = touchPercentY;
		}

		return handled;
	}

	protected boolean onDragged(MotionEvent event) { return false; }

	public final boolean onReleased(float touchPercentX, float touchPercentY, MotionEvent event)
	{
		isTouched = false;
		isDragged = false;

		boolean handled = false;

		if (onReleaseListener != null)
		{
			handled |= onReleaseListener.onRelease(this);
		}

		handled |= onReleased(event);

		return handled;
	}

	protected boolean onReleased(MotionEvent event) { return false; }

	public final boolean onClicked(float touchPercentX, float touchPercentY, MotionEvent event)
	{
		boolean handled = false;

		if (onClickListener != null)
		{
			handled |= onClickListener.onClick(this);
		}

		handled |= onClicked(event);

		return handled;
	}

	protected boolean onClicked(MotionEvent event) { return false; }

	public BaseView setOnClickListener(OnClickListener listener)
	{
		onClickListener = listener;

		return this;
	}

	public BaseView setOnDragListener(OnDragListener listener)
	{
		onDragListener = listener;

		return this;
	}

	public BaseView setOnTouchListener(OnTouchListener listener)
	{
		onTouchListener = listener;

		return this;
	}

	public BaseView setOnReleaseListener(OnReleaseListener listener)
	{
		onReleaseListener = listener;

		return this;
	}

	public void draw()
	{
		for (int i = 0; i < getChildren().size(); ++i)
		{
			getChildren().get(i).draw();
		}
	}

	public BaseView setDimensions(float widthPercent, float heightPercent) {
		setWidth(widthPercent);
		setHeight(heightPercent);

		return this;
	}

	public BaseView setWidth(float widthPercent)
	{
		this.widthPercent = widthPercent;

		onDimensionsChanged();

		return this;
	}

	public BaseView setHeight(float heightPercent)
	{
		this.heightPercent = heightPercent;

		onDimensionsChanged();

		return this;
	}

	public BaseView moveBy(float movePercentX, float movePercentY)
	{
		setCenter(centerPercentX + movePercentX, centerPercentY + movePercentY);

		return this;
	}

	public BaseView scaleBy(float widthChangePercent, float heightChangePercent)
	{
		setWidth(widthPercent + widthChangePercent);
		setHeight(heightPercent + heightChangePercent);

		return this;
	}

	public float getWidth()
	{
		return widthPercent;
	}

	public float getHeight()
	{
		return heightPercent;
	}

	public float getCenterX()
	{
		return centerPercentX;
	}

	public float getCenterY()
	{
		return centerPercentY;
	}

	public float [] getCenterVector() {
		return new float[] {centerPercentX, centerPercentY};
	}

	public BaseView setCenter(float xPercent, float yPercent)
	{
		centerPercentX = xPercent;
		centerPercentY = yPercent;

		onPositionChanged();

		return this;
	}

	public boolean isValueWithinView(float xPercent, float yPercent)
	{
		float[] absoluteCenter = getAbsoluteCenterVector();

		boolean result = Math.abs(xPercent - absoluteCenter[0]) <= widthPercent / 2.0f;
		result &= Math.abs(yPercent - absoluteCenter[1]) <= heightPercent / 2.0f;

		return result;
	}

	public float[] getAbsoluteCenterVector()
	{
		float[] result = new float[2];

		if (getParent() != null)
		{
			result[0] = centerPercentX + (getParent().getCenterX() - 0.5f);
			result[1] = centerPercentY + (getParent().getCenterY() - 0.5f);

			return result;
		}
		else
		{
			result = getCenterVector();
		}

		return result;
	}

	public void resetRotation() {
		this.rotateXAngle = 0;
		this.rotateYAngle = 0;
		this.rotateZAngle = 0;

		onOrientationChanged();
	}

	public float getRotateXAngle()
	{
		return rotateXAngle;
	}

	public void setRotateXAngle(float rotateXAngle)
	{
		this.rotateXAngle = rotateXAngle;
		onOrientationChanged();
	}

	public float getRotateYAngle()
	{
		return rotateYAngle;
	}

	public void setRotateYAngle(float rotateYAngle)
	{
		this.rotateYAngle = rotateYAngle;
		onOrientationChanged();
	}

	public float getRotateZAngle()
	{
		return rotateZAngle;
	}

	public void setRotateZAngle(float rotateZAngle)
	{
		this.rotateZAngle = rotateZAngle;
		onOrientationChanged();
	}

	public void rotateXByAngle(float angle) {
		rotateZAngle += angle;
		onOrientationChanged();
	}

	public void rotateYByAngle(float angle) {
		rotateZAngle += angle;
		onOrientationChanged();
	}

	public void rotateZByAngle(float angle) {
		rotateZAngle += angle;
		onOrientationChanged();
	}

	protected void onDimensionsChanged()
	{
	}

	protected void onPositionChanged()
	{
	}

	protected void onOrientationChanged()
	{
	}
}
