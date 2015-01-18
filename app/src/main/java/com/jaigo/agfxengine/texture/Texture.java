package com.jaigo.agfxengine.texture;
// Texture
//
// Created by Jeff on 11/01/2015

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.util.Log;

import com.jaigo.agfxengine.common.CommonUtils;
import com.jaigo.agfxengine.common.LogTags;

import java.util.UUID;

public class Texture
{
	private UUID id;
	private int glTextureHandle;
	private TextureBitmapProvider textureBitmapProvider;

	//the width of the texture openGL
	private final int widthPx;
	private final int heightPx;

	public Texture(int widthPx, int heightPx)
	{
		this.widthPx = widthPx;
		this.heightPx = heightPx;

		id = UUID.randomUUID();
		int [] textureHandle = new int [1];

		GLES20.glGenTextures(1, textureHandle, 0);
		CommonUtils.checkGLError("Texture - error in glGenTextures");

		glTextureHandle = textureHandle[0];

		Log.d(LogTags.OPEN_GL, "New texture created. handle = " + glTextureHandle);
	}

	public void create()
	{
		Bitmap textureBitmap = null;

		if (textureBitmapProvider != null)
		{
			textureBitmap = textureBitmapProvider.getTextureBitmap();

			if (textureBitmap == null)
			{
				Log.e(LogTags.OPEN_GL, "Texture.create - Bitmap is null");
			}
			else if (textureBitmap.isRecycled())
			{
				Log.e(LogTags.OPEN_GL, "Texture.create - Bitmap is recycled");
			}
		}

		bindToGL();

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		CommonUtils.checkGLError("Texture.create - error setting texture properties");

		if (textureBitmap == null)
		{
			//create the texture on the GL context
			GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, widthPx, heightPx, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
		}
		else
		{
			//upload texture bitmap
			android.opengl.GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, textureBitmap, 0);
		}

		CommonUtils.checkGLError("Texture.create - error in texImage2D");
	}

	public void uploadImage(Bitmap textureBitmap, int xOffsetIntoTexture, int yOffsetIntoTexture)
	{
		if (textureBitmap == null)
		{
			Log.e(LogTags.OPEN_GL, "Texture.uploadImage - Bitmap is null");
		}
		else if (textureBitmap.isRecycled())
		{
			Log.e(LogTags.OPEN_GL, "Texture.uploadImage - Bitmap is recycled");
		}

		bindToGL();
		android.opengl.GLUtils.texSubImage2D(GLES20.GL_TEXTURE_2D, 0, xOffsetIntoTexture, yOffsetIntoTexture, textureBitmap);
		CommonUtils.checkGLError("Texture.uploadImage - error in texSubImage2D");
	}

	public void bindToGL()
	{
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, glTextureHandle);
		CommonUtils.checkGLError("Texture.create - error in glBindTexture. Handle = " + glTextureHandle);
	}

	public void setTextureBitmapProvider(TextureBitmapProvider textureBitmapProvider)
	{
		this.textureBitmapProvider = textureBitmapProvider;
	}

	public UUID getId()
	{
		return id;
	}

	public void release()
	{
		GLES20.glDeleteTextures(1, new int [] { glTextureHandle }, 0);
	}
}
