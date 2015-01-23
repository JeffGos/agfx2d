package com.jaigo.agfxengine;
// AGCoordinateSystem
//
// Created by Jeff Gosling on 08/01/2015
//
// View coords run from [0,0] in top left to [width,height] in bottom right
// GL   coords run from [-glWidth/2, -glHeight/2] to [glWidth/2, glHeight/2] in bottom right with [0,0] in center of screen

public class AGCoordinateSystem
{
	public enum AspectRatioRelativity {
		None,
		RelativeToWidth,
		RelativeToHeight
	}

	private AspectRatioRelativity aspectRatioRelativity = AspectRatioRelativity.None;

	//OpenGL coordinates
	private final float glCameraDistance;
	private final float glCameraWidth;
	private final float glCameraHeight;

	//Real world pixel coordinates
	private int viewWidthPx;
	private int viewHeightPx;

	public AGCoordinateSystem(float glCameraWidth, float glCameraHeight, float glCameraDistance)
	{
		this.glCameraWidth = glCameraWidth;
		this.glCameraHeight = glCameraHeight;
		this.glCameraDistance = glCameraDistance;
	}

	public void setAspectRatioRelativity(AspectRatioRelativity rel) { aspectRatioRelativity = rel; }

	public void setViewWidthPx(int widthPx)
	{
		viewWidthPx = widthPx;
	}

	public void setViewHeightPx(int heightPx)
	{
		viewHeightPx = heightPx;
	}

	public float getGlCameraDistance() { return glCameraDistance; }

	public int getViewWidthPx()
	{
		return viewWidthPx;
	}

	public int getViewHeightPx()
	{
		return viewHeightPx;
	}

	public float getGlCameraWidth() { return glCameraWidth; }
	public float getGlCameraHeight() { return glCameraHeight; }
	public float getGlCameraLeft() { return -getGlCameraWidth() / 2.0f; }
	public float getGlCameraRight() { return getGlCameraWidth() / 2.0f; }
	public float getGlCameraTop() { return getGlCameraHeight() / 2.0f; }
	public float getGlCameraBottom() { return -getGlCameraHeight() / 2.0f; }

	public float convertPercentageToGLValueX(float percent)
	{
		float result = percent - 0.5f;
		result *= glCameraWidth;

		return result;
	}

	public float convertPercentageToGLValueY(float percent)
	{
		float result = percent - 0.5f;
		result *= glCameraHeight;

		return result;
	}

	public float getWidthRatio()
	{
		if (aspectRatioRelativity == AspectRatioRelativity.RelativeToHeight) {
			return (float) viewWidthPx / (float) viewHeightPx;
		}

		return 1.0f;
	}

	public float getHeightRatio()
	{
		if (aspectRatioRelativity == AspectRatioRelativity.RelativeToWidth) {
			return (float) viewHeightPx / (float) viewWidthPx;
		}

		return 1.0f;
	}

	public float widthPercentRelativeToHeight(float widthPercent)
	{
		return (widthPercent * viewWidthPx) / (float) viewHeightPx;
	}

	public float heightPercentRelativeToHeight(float heightPercent)
	{
		return (heightPercent * viewHeightPx) / (float) viewWidthPx;
	}
}
