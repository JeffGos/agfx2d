package com.jaigo.agfxengine.view;
// BaseView
//
// Created by Jeff Gosling on 08/01/2015

import com.jaigo.agfxengine.AGEngine;
import com.jaigo.agfxengine.view.listeners.OnClickListener;
import com.jaigo.agfxengine.view.listeners.OnDragListener;
import com.jaigo.agfxengine.view.listeners.OnReleaseListener;
import com.jaigo.agfxengine.view.listeners.OnTouchListener;

import java.util.concurrent.CopyOnWriteArrayList;

public class BaseView
{
	private BaseView parent;
	private CopyOnWriteArrayList<BaseView> children = new CopyOnWriteArrayList<BaseView>();

	private int widthPx;
	private int heightPx;
	private int centerXPx;
	private int centerYPx;

	private float widthPercent;
	private float heightPercent;
	private float centerXPercent;
	private float centerYPercent;

	private float rotateXAngle = 0.0f;
	private float rotateYAngle = 0.0f;
	private float rotateZAngle = 0.0f;

	private boolean isVisible = true;
	private boolean isEnabled = true;

	protected float startTouchXPercent;
	protected float startTouchYPercent;
	protected volatile float lastTouchedXPercent = 0.0f;
	protected volatile float lastTouchedYPercent = 0.0f;

	protected boolean isDragged = false;
	protected boolean isTouched = false;

	protected OnClickListener onClickListener;
	protected OnDragListener onDragListener;
	protected OnTouchListener onTouchListener;
	protected OnReleaseListener onReleaseListener;

	public BaseView(int widthPixels, int heightPixels)
	{
		setWidthPx(widthPixels);
		setHeightPx(heightPixels);
	}

	public BaseView(float widthPercent, float heightPercent)
	{
		setWidthPercent(widthPercent);
		setHeightPercent(heightPercent);
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
		//child.parent = null;
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

	public final void onTouched(float touchX, float touchY)
	{
		startTouchXPercent = lastTouchedXPercent = touchX;
		startTouchYPercent = lastTouchedYPercent = touchY;

		isTouched = true;

		if (onTouchListener != null)
		{
			onTouchListener.onTouched(this);
		}

		onTouched();
	}

	protected void onTouched() {}

	public final void onDragged(float touchX, float touchY)
	{
		if (!isTouched)
		{
			return;
		}

		float dragAmountX = touchX - lastTouchedXPercent;
		float dragAmountY = touchY - lastTouchedYPercent;

		if (!isDragged)
		{
			float dragAmountXSinceStart = touchX - startTouchXPercent;
			float dragAmountYSinceStart = touchX - startTouchYPercent;
			final float DRAG_AMOUNT_THRESHOLD = 0.15f;

			if (Math.abs(dragAmountXSinceStart) > DRAG_AMOUNT_THRESHOLD || Math.abs(dragAmountYSinceStart) > DRAG_AMOUNT_THRESHOLD)
			{
				isDragged = true;
			}
		}

		lastTouchedXPercent = touchX;
		lastTouchedYPercent = touchY;

		if (onDragListener != null)
		{
			onDragListener.onDrag(this, dragAmountX, dragAmountY);
		}

		onDragged();
	}

	protected void onDragged() {}

	public final void onReleased(float touchX, float touchY)
	{
		isTouched = false;
		isDragged = false;

		if (onReleaseListener != null)
		{
			onReleaseListener.onRelease(this);
		}

		onReleased();
	}

	public void onReleased() {}

	public final void onClicked(float touchX, float touchY)
	{
		if (onClickListener != null)
		{
			onClickListener.onClick(this);
		}
	}

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

	public BaseView setDimensionsPx(int widthPx, int heightPx) {
		setWidthPx(widthPx);
		setHeightPx(widthPx);

		return this;
	}

	public BaseView setDimensionsPercent(float widthPercent, float heightPercent) {
		setWidthPercent(widthPercent);
		setHeightPercent(heightPercent);

		return this;
	}

	public BaseView setWidthPx(int widthPx)
	{
		this.widthPx = widthPx;
		this.widthPercent = AGEngine.Instance().getCoordinateSystem().convertPixelValueToPercentageValue(widthPx);

		onDimensionsChanged();

		return this;
	}

	public BaseView setWidthPercent(float widthPercent)
	{
		this.widthPercent = widthPercent;
		this.widthPx = AGEngine.Instance().getCoordinateSystem().convertPercentageValueToPixelValue(widthPercent);

		onDimensionsChanged();

		return this;
	}

	public BaseView setHeightPx(int heightPx)
	{
		this.heightPx = heightPx;
		this.heightPercent = AGEngine.Instance().getCoordinateSystem().convertPixelValueToPercentageValue(heightPx);

		onDimensionsChanged();

		return this;
	}

	public BaseView setHeightPercent(float heightPercent)
	{
		this.heightPercent = heightPercent;
		this.heightPx =  AGEngine.Instance().getCoordinateSystem().convertPercentageValueToPixelValue(heightPercent);

		onDimensionsChanged();

		return this;
	}

	public BaseView moveByPx(float xChangePixel, float yChangePixel)
	{
		setCenterPx(centerXPx + Math.round(xChangePixel), centerYPx + Math.round(yChangePixel));

		onPositionChanged();

		return this;
	}

	public BaseView scaleByPercent(float widthChangePercent, float heightChangePercent)
	{
		setWidthPercent(widthPercent + widthChangePercent);
		setHeightPercent(heightPercent + heightChangePercent);

		return this;
	}

	public BaseView setCenterPx(int xPixels, int yPixels)
	{
		centerXPx = xPixels;
		centerYPx = yPixels;

		centerXPercent = AGEngine.Instance().getCoordinateSystem().convertPixelValueToPercentageValue(centerXPx);
		centerYPercent = AGEngine.Instance().getCoordinateSystem().convertPixelValueToPercentageValue(centerYPx);

		onPositionChanged();

		return this;
	}

	public BaseView setCenterPercent(float xPercent, float yPercent)
	{
		centerXPercent = xPercent;
		centerYPercent = yPercent;

		centerXPx = AGEngine.Instance().getCoordinateSystem().convertPercentageValueToPixelValue(centerXPercent);
		centerYPx = AGEngine.Instance().getCoordinateSystem().convertPercentageValueToPixelValue(centerYPercent);

		onPositionChanged();

		return this;
	}

	public int getWidthPx()
	{
		return widthPx;
	}

	public float getWidthPercent()
	{
		return widthPercent;
	}

	public int getHeightPx()
	{
		return heightPx;
	}

	public float getHeightPercent()
	{
		return heightPercent;
	}

	public int getCenterXPx()
	{
		return centerXPx;
	}

	public int getCenterYPx()
	{
		return centerYPx;
	}

	public float getCenterXPercent()
	{
		return centerXPercent;
	}

	public float getCenterYPercent()
	{
		return centerYPercent;
	}

	public float [] getCenterPxVector() {
		return new float[] {centerXPx, centerYPx};
	}

	public float [] getCenterPercentVector() {
		return new float[] {centerXPercent, centerYPercent};
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

	public boolean isValuePercentWithinView(float xPercent, float yPercent)
	{
		float[] absoluteCenter = getAbsoluteCenterPercentVector();

		boolean result = Math.abs(xPercent - absoluteCenter[0]) <= widthPercent / 2.0f;
		result &= Math.abs(yPercent - absoluteCenter[1]) <= heightPercent / 2.0f;

		return result;
	}

	public boolean isValuePixelsWithinView(int x, int y)
	{
		float[] absoluteCenterPx = getAbsoluteCenterPxVector();

		boolean result = Math.abs(x - absoluteCenterPx[0]) <= widthPx / 2.0f;
		result &= Math.abs(y - absoluteCenterPx[1]) <= heightPx / 2.0f;

		return result;
	}

	public float[] getAbsoluteCenterPxVector()
	{
		float[] result = new float[2];

		if (getParent() != null)
		{
			result[0] = centerXPx + getParent().getCenterXPx();
			result[1] = centerYPx + getParent().getCenterYPx();

			return result;
		}
		else
		{
			result = getCenterPxVector();
		}

		return result;
	}

	public float[] getAbsoluteCenterPercentVector()
	{
		float[] result = new float[2];

		if (getParent() != null)
		{
			result[0] = centerXPercent + getParent().getCenterXPercent();
			result[1] = centerYPercent + getParent().getCenterYPercent();

			return result;
		}
		else
		{
			result = getCenterPercentVector();
		}

		return result;
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
