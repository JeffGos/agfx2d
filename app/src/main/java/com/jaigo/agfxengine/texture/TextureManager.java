package com.jaigo.agfxengine.texture;
// TextureManager
//
// Created by Jeff Gosling on 11/01/2015

import android.opengl.GLES20;
import android.util.Log;

import com.jaigo.agfxengine.common.LogTags;

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

		textureInfosById.clear();
		texturesById.clear();

		initialised = false;
	}

	public TextureInfo addTexture(int widthPx, int heightPx, TextureBitmapProvider textureBitmapProvider)
	{
		Log.d(LogTags.OPEN_GL, "TextureManager.addTexture()");

		Texture texture = new Texture(widthPx, heightPx);
		texture.setTextureBitmapProvider(textureBitmapProvider);
		texture.create();

		texturesById.put(texture.getId(), texture);

		TextureInfo textureInfo = new TextureInfo();

		textureInfo.setWidthPx(widthPx);
		textureInfo.setHeightPx(heightPx);
		textureInfo.setTextureId(texture.getId());
		textureInfo.setId(UUID.randomUUID());
		textureInfo.setTextureAtlasDimensionsPx(widthPx, heightPx);
		textureInfo.setxPx(0);
		textureInfo.setyPx(0);

		return textureInfo;
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

	public void bindTextureForDrawing(TextureInfo textureInfo)
	{
		Texture texture = texturesById.get(textureInfo.getTextureId());

		if (texture != null)
		{
			texture.bindToGL();
		}
		else
		{
			Log.e("TextureManager.bindTextureForDrawing", "Texture not found");
		}
	}
}
