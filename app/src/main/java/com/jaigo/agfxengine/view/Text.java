package com.jaigo.agfxengine.view;
// Texr
//
// Created by Jeff Gosling on 24/01/2015

import com.jaigo.agfxengine.AGEngine;
import com.jaigo.agfxengine.Font;
import com.jaigo.agfxengine.common.Color;
import com.jaigo.agfxengine.texture.TextureInfo;

import java.util.ArrayList;

public class Text extends View
{
	protected Font font;
	protected String text;

	public Text(float widthPercent, float heightPercent)
	{
		this(widthPercent, heightPercent, AGEngine.FontManager().getDefaultFont());
	}

	public Text(float widthPercent, float heightPercent, Font font)
	{
		super(widthPercent, heightPercent);
		setFont(font);
	}

	public void setFont(Font font)
	{
		this.font = font;
		initialiseCharacters();
	}

	public void setText(String text)
	{
		this.text = text;
		initialiseCharacters();
	}

	public void setTextColor(Color color)
	{
		for (BaseView view : getChildren())
		{
			((Character)view).setColor(color);
		}
	}

	private void initialiseCharacters()
	{
		if (font == null || text == null)
		{
			return;
		}

		ArrayList<TextureInfo> textureInfos = font.getTextureInfosForString(text);

		clearChildren();

		float totalWidth = 0;
		for (TextureInfo textureInfo : textureInfos)
		{
			Character character = new Character(getHeight(), font);
			character.moveBy(totalWidth * 3 , 0);
			character.setTextureInfo(textureInfo);
			totalWidth += (float)textureInfo.getWidthPx() / (float)AGEngine.CoordinateSystem().getViewWidthPx();
			addChild(character);
		}

		for (BaseView view : getChildren())
		{
			view.moveBy(- totalWidth * 3 / 2.0f, 0);
		}
	}

	private class Character extends TexturedView
	{
		protected Font font;
		protected float aspectRatio = 1.0f; //width relative to height

		public Character(float heightPercent, Font font)
		{
			super(0.1f, heightPercent);

			setFont(font);
		}

		public void setFont(Font font)
		{
			this.font = font;

			setAspectRatio(textureInfo.getWidthPx() / font.getWidestCharacterWidth());
			setTextureId(font.getTextureId());
		}

		@Override
		public BaseView setWidth(float widthPercent)
		{
			if (aspectRatio > 0.0f && aspectRatio <= 1.0f)
			{
				widthPercent *= aspectRatio;
			}

			return super.setWidth(widthPercent);
		}

		private void setAspectRatio(float aspectRatio)
		{
			this.aspectRatio = aspectRatio;
			setWidth(getWidth() * aspectRatio);
		}
	}
}