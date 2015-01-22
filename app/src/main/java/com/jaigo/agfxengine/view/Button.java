package com.jaigo.agfxengine.view;
// Button
//
// Created by Jeff Gosling on 10/01/2015

import com.jaigo.agfxengine.common.Color;

public class Button extends View
{
	private Color touchColor;

	public Button(float widthPercent, float heightPercent)
	{
		super(widthPercent, heightPercent);
	}

	public Button(int widthPixels, int heightPixels)
	{
		super(widthPixels, heightPixels);
	}

	public void setTouchColor(Color color)
	{
		touchColor = color;
	}

	@Override
	public Color getColor()
	{
		if (isTouched)
		{
			return touchColor;
		}

		return super.getColor();
	}
}
