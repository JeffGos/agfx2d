package com.jaigo.agfxengine.view;
// Image
//
// Created by Jeff Gosling on 10/01/2015

import android.graphics.Bitmap;

public class Image extends TexturedView
{
	private Bitmap image;
	private float imageAspectRatio = 1.0f;

	public Image(float widthPercent, float heightPercent, Bitmap image)
	{
		super(widthPercent, heightPercent);
		this.image = image;

		imageAspectRatio = ((float)image.getHeight()) / ((float)image.getWidth());

		setHeight(heightPercent * imageAspectRatio);

		initTexture();
	}

	@Override
	public Bitmap getTextureBitmap()
	{
		return image;
	}

	@Override
	public int getTextureWidth()
	{
		return image.getWidth();
	}

	@Override
	public int getTextureHeight()
	{
		return image.getHeight();
	}
}
