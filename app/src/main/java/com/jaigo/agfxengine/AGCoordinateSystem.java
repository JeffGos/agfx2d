package com.jaigo.agfxengine;
// AGCoordinateSystem
//
// Created by Jeff Gosling on 08/01/2015

public class AGCoordinateSystem
{
	private enum AspectRatioRelativity {
		RelativeToWidth,
		RelativeToHeight
	}

	private AspectRatioRelativity aspectRatioRelativity = AspectRatioRelativity.RelativeToWidth;

	//OpenGL coordinates
	public final float glCameraLeft;
	public final float glCameraRight;
	public final float glCameraTop;
	public final float glCameraBottom;
	public final float glCameraDistance;
	public final float glCameraWidth;
	public final float glCameraHeight;

	//Real world pixel coordinates
	private int widthPx;
	private int heightPx;

	public AGCoordinateSystem(float glCameraLeft, float glCameraRight, float glCameraTop, float glCameraBottom, float glCameraDistance)
	{
		this.glCameraLeft = glCameraLeft;
		this.glCameraRight = glCameraRight;
		this.glCameraTop = glCameraTop;
		this.glCameraBottom = glCameraBottom;
		this.glCameraDistance = glCameraDistance;

		glCameraWidth = glCameraRight - glCameraLeft;
		glCameraHeight = glCameraTop - glCameraBottom;
	}

	public void setWidthPx(int widthPx)
	{
		this.widthPx = widthPx;
	}

	public void setHeightPx(int heightPx)
	{
		this.heightPx = heightPx;
	}

	public int getWidthPx()
	{
		return widthPx;
	}

	public int getHeightPx()
	{
		return heightPx;
	}

	public float convertPixelValueToPercentageValue(int pixelValue)
	{
		int divisor = widthPx;

		if (aspectRatioRelativity == AspectRatioRelativity.RelativeToHeight) {
			divisor = heightPx;
		}

		return (float) pixelValue / (float) divisor;
	}

	public int convertPercentageValueToPixelValue(float percent)
	{
		int relativeValue = widthPx;

		if (aspectRatioRelativity == AspectRatioRelativity.RelativeToHeight) {
			relativeValue = heightPx;
		}

		return Math.round(percent * relativeValue);
	}

	public float convertPercentageValueToGLValueX(float percent)
	{
		float relativeValue = glCameraWidth;

		if (aspectRatioRelativity == AspectRatioRelativity.RelativeToHeight) {
			relativeValue = glCameraHeight;
		}

		float percentOfGLViewport = relativeValue * percent;

		return glCameraLeft * getWidthRatio() + (percentOfGLViewport * glCameraWidth);
	}

	public float convertPercentageValueToGLValueY(float percent)
	{
		float relativeValue = glCameraWidth;

		if (aspectRatioRelativity == AspectRatioRelativity.RelativeToHeight) {
			relativeValue = glCameraHeight;
		}

		float percentOfGLViewport = relativeValue * percent;

		return glCameraBottom * getHeightRatio() + (percentOfGLViewport * glCameraHeight);
	}

	public float convertPixelValueToGLValueX(int pixelValue)
	{
		float percentValue = convertPixelValueToPercentageValue(pixelValue);

		return convertPercentageValueToGLValueX(percentValue);
	}

	public float convertPixelValueToGLValueY(int pixelValue)
	{
		float percentValue = convertPixelValueToPercentageValue(pixelValue);

		return convertPercentageValueToGLValueY(percentValue);
	}

	public float getWidthRatio() {
		if (aspectRatioRelativity == AspectRatioRelativity.RelativeToHeight) {
			return (float) widthPx / (float) heightPx;
		}

		return 1.0f;
	}

	public float getHeightRatio() {

		if (aspectRatioRelativity == AspectRatioRelativity.RelativeToWidth) {
			return (float) heightPx / (float) widthPx;
		}

		return 1.0f;
	}
}
