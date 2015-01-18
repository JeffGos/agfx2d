package com.jaigo.agfxengine.view;
// TexturedView
//
// Created by Jeff Gosling on 10/01/2015

import android.graphics.Bitmap;
import android.opengl.GLES20;

import com.jaigo.agfxengine.AGEngine;
import com.jaigo.agfxengine.texture.TextureBitmapProvider;
import com.jaigo.agfxengine.texture.TextureInfo;

public class TexturedView extends View implements TextureBitmapProvider
{
	protected TextureInfo textureInfo = new TextureInfo();

	public TexturedView(int widthPixels, int heightPixels)
	{
		super(widthPixels, heightPixels);
	}

	public TexturedView(float widthPercent, float heightPercent)
	{
		super(widthPercent, heightPercent);
	}

	@Override
	public void initialise()
	{
		super.initialise();

		shader = AGEngine.ShaderManager().getTexturedShader();
	}

	public void initTexture()
	{
		textureInfo = AGEngine.TextureManager().addTexture(getWidthPx(), getHeightPx(), this);
	}

	public Bitmap getTextureBitmap()
	{
		return null;
	}

	@Override
	public void draw()
	{
		int programHandle = shader.getProgramHandle();

		AGEngine.TextureManager().bindTextureForDrawing(textureInfo);
		GLES20.glUniform2fv(GLES20.glGetUniformLocation(programHandle, "tCoordScale"), 1, textureInfo.getGlTextureScale(), 0);
		GLES20.glUniform2fv(GLES20.glGetUniformLocation(programHandle, "tCoordOffset"), 1, textureInfo.getGlTextureOffset(), 0);

		super.draw();
	}
}
