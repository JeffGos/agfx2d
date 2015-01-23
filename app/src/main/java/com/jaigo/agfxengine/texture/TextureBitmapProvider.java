package com.jaigo.agfxengine.texture;
// TextureBitmapProvider
//
// Created by Jeff Gosling on 11/01/2015

import android.graphics.Bitmap;

public interface TextureBitmapProvider
{
	public int getTextureWidth();
	public int getTextureHeight();
	public Bitmap getTextureBitmap();
}
