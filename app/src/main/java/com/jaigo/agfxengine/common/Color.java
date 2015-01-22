package com.jaigo.agfxengine.common;
// Color
//
// Created by Jeff Gosling on 21/01/2015
//

public class Color
{
	public static final float [] RED = new float [] { 1, 0, 0, 1};
	public static final float [] GREEN = new float [] { 0, 1, 0, 1};
	public static final float [] BLUE = new float [] { 0, 0, 1, 1};
	public static final float [] WHITE = new float [] { 1, 1, 1, 1};
	public static final float [] BLACK = new float [] { 0, 0, 0, 1};

	private float [] rgba;

	public Color()
	{
		this.rgba = new float [] { 0, 0, 0, 1 };
	}

	public Color(float [] rgba)
	{
		this.rgba = new float [4];
		System.arraycopy(rgba, 0, this.rgba, 0, 4);
		this.rgba = rgba;
	}

	public void set(float red, float green, float blue)
	{
		rgba[0] = red;
		rgba[1] = green;
		rgba[2] = blue;
	}

	public void setAlpha(float alpha)
	{
		rgba[3] = alpha;
	}

	public float [] getArray()
	{
		return rgba;
	}
}
