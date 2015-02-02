package com.jaigo.agfxengine.manager;
// TextureManager
//
// Created by Jeff Gosling on 11/01/2015

import android.opengl.GLES20;
import android.util.Log;

import com.jaigo.agfxengine.common.LogTags;
import com.jaigo.agfxengine.texture.Texture;
import com.jaigo.agfxengine.texture.TextureBitmapProvider;
import com.jaigo.agfxengine.texture.TextureInfo;

import java.util.HashMap;
import java.util.UUID;

public class TextureManager
{
	private HashMap<UUID, TextureInfo> textureInfosById = new HashMap<UUID, TextureInfo>();
	private HashMap<UUID, Texture> texturesById = new HashMap<UUID, Texture>();

	private boolean initialised;

	public TextureManager()
	{
	}

	public void initialise()
	{
		Log.d(LogTags.OPEN_GL, "TextureManager.initialise()");

		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

		initialised = true;
	}

	public void destroy()
	{
		Log.d(LogTags.OPEN_GL, "TextureManager.destroy()");

		deleteTextures();

		textureInfosById.clear();
		texturesById.clear();

		initialised = false;
	}

	public UUID addTexture(TextureBitmapProvider textureBitmapProvider)
	{
		Log.d(LogTags.OPEN_GL, "TextureManager.addTexture()");

		int widthPx = textureBitmapProvider.getTextureWidth();
		int heightPx = textureBitmapProvider.getTextureHeight();

		TextureInfo textureInfo = new TextureInfo();
		textureInfo.setWidthPx(widthPx);
		textureInfo.setHeightPx(heightPx);
		textureInfo.setTextureAtlasDimensionsPx(widthPx, heightPx);
		textureInfo.setxPx(0);
		textureInfo.setyPx(0);

		Texture texture = new Texture(widthPx, heightPx);
		texture.setTextureBitmapProvider(textureBitmapProvider);
		texture.create();

		texturesById.put(texture.getId(), texture);

		return texture.getId();
	}

	public TextureInfo getTextureInfo(UUID textureId)
	{
		return textureInfosById.get(textureId);
	}

	public Texture getTexture(UUID id)
	{
		return texturesById.get(id);
	}

	public void deleteTextures()
	{
		for (Texture texture : texturesById.values())
		{
			texture.release();
		}
	}

	public void releaseTexture(TextureInfo textureInfo)
	{
		textureInfosById.remove(textureInfo.getId());
	}

	public boolean doesTextureExist(UUID id)
	{
		return textureInfosById.get(id) != null;
	}

	public void bindTextureForDrawing(UUID textureID)
	{
		Texture texture = texturesById.get(textureID);

		if (texture != null)
		{
			texture.bindToGL();
		}
		else
		{
			Log.e("TextureManager.bindTextureForDrawing", "Texture not found. textureId = " + textureID);
		}
	}
}
