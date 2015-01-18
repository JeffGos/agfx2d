package com.jaigo.agfxengine.texture;
// TextureInfo
//
// Created by Jeff Gosling on 11/01/2015

import java.util.UUID;

public class TextureInfo
{
	private UUID id;
	//which texture atlas this belongs to
	private UUID textureId;

	//this texture could be a portion of a larger texture
	private int totalTextureWidthPx;
	private int totalTextureHeightPx;

	//the x (left) and y (top) positions of the texture
	private int xPx;
	private int yPx;

	//the width and height in pixels of the texture on the atlas
	private int widthPx;
	private int heightPx;

	private float [] glTextureOffset = new float[] {0, 0};
	private float [] glTextureScale = new float[] {1.0f, 1.0f };

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public UUID getTextureId()
	{
		return textureId;
	}

	public void setTextureId(UUID textureId)
	{
		this.textureId = textureId;
	}

	public void setTextureAtlasDimensionsPx(int textureAtlasWidthPx, int textureAtlasHeightPx)
	{
		this.totalTextureWidthPx = textureAtlasWidthPx;
		this.totalTextureHeightPx = textureAtlasHeightPx;
		calculateGlTextureOffset();
		calculateGlTextureScale();
	}

	public int getxPx()
	{
		return xPx;
	}

	public void setxPx(int xPx)
	{
		this.xPx = xPx;
		calculateGlTextureOffset();
	}

	public int getyPx()
	{
		return yPx;
	}

	public void setyPx(int yPx)
	{
		this.yPx = yPx;
		calculateGlTextureOffset();
	}

	public int getWidthPx()
	{
		return widthPx;
	}

	public void setWidthPx(int widthPx)
	{
		this.widthPx = widthPx;
		calculateGlTextureOffset();
	}

	public int getHeightPx()
	{
		return heightPx;
	}

	public void setHeightPx(int heightPx)
	{
		this.heightPx = heightPx;
		calculateGlTextureOffset();
	}

	public float[] getGlTextureOffset()
	{
		return glTextureOffset;
	}

	public float[] getGlTextureScale()
	{
		return glTextureScale;
	}

	private void calculateGlTextureOffset()
	{
		if (totalTextureWidthPx > 0)
		{
			glTextureOffset[0] = (float) xPx / (float) totalTextureWidthPx;
		}

		if (totalTextureHeightPx > 0)
		{
			glTextureOffset[1] = (float) yPx / (float) totalTextureHeightPx;
		}
	}

	private void calculateGlTextureScale()
	{
		if (totalTextureWidthPx > 0)
		{
			glTextureScale[0] = (float) widthPx / (float) totalTextureWidthPx;
		}

		if (totalTextureHeightPx > 0)
		{
			glTextureScale[1] = (float) heightPx / (float) totalTextureHeightPx;
		}
	}
}
