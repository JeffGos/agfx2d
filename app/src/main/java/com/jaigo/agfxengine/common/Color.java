package com.jaigo.agfxengine.common;
// Color
//
// Created by Jeff Gosling on 21/01/2015
//

public class Color
{
	public static final int R = 0;
	public static final int G = 1;
	public static final int B = 2;
	public static final int A = 3;

	public static final float [] RED = new float [] { 1, 0, 0, 1};
	public static final float [] GREEN = new float [] { 0, 1, 0, 1};
	public static final float [] BLUE = new float [] { 0, 0, 1, 1};
	public static final float [] WHITE = new float [] { 1, 1, 1, 1};
	public static final float [] BLACK = new float [] { 0, 0, 0, 1};
	public static final float [] TRANSPARENT = new float [] { 0, 0, 0, 0};

	private float [] rgba;

	public Color()
	{
		this.rgba = new float [] { 0, 0, 0, 1 };
	}

	public Color(float [] rgba)
	{
		this.rgba = new float [4];
		System.arraycopy(rgba, 0, this.rgba, 0, 4);
	}

	public void set(float red, float green, float blue)
	{
		rgba[R] = red;
		rgba[G] = green;
		rgba[B] = blue;
	}

	public void setAlpha(float alpha)
	{
		rgba[A] = alpha;
	}

	public float [] getArray()
	{
		return rgba;
	}

	public void copy(Color copy)
	{
		float [] copyArray = copy.getArray();

		System.arraycopy(copyArray, 0, this.rgba, 0, 4);
	}
}
