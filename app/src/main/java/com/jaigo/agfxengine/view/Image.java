package com.jaigo.agfxengine.view;
// Image
//
// Created by Jeff Gosling on 10/01/2015

import android.graphics.Bitmap;

public class Image extends TexturedView
{
	private Bitmap image;

	public Image(float widthPercent, float heightPercent, Bitmap image)
	{
		super(widthPercent, heightPercent);
		this.image = image;

		initTexture();
	}

	public Image(int widthPixels, int heightPixels, Bitmap bitmap)
	{
		super(widthPixels, heightPixels);
		image = bitmap;

		initTexture();
	}

	@Override
	public Bitmap getTextureBitmap()
	{
		return image;
	}
}
