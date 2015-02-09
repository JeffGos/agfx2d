package com.jaigo.agfxengine.view;
// Texr
//
// Created by Jeff Gosling on 24/01/2015

import com.jaigo.agfxengine.AGEngine;
import com.jaigo.agfxengine.common.Font;
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

	@Override
	public BaseView setCenter(float xPercent, float yPercent)
	{
		float diffX = getCenterX() - xPercent;
		float diffY = getCenterY() - yPercent;

		for (BaseView view : getChildren())
		{
			view.moveBy(diffX, diffY);
		}

		return super.setCenter(xPercent, yPercent);
	}

	@Override
	public BaseView moveBy(float movePercentX, float movePercentY)
	{
		for (BaseView view : getChildren())
		{
			view.moveBy( movePercentX, movePercentY);
		}

		return super.moveBy(movePercentX, movePercentY);
	}

	private void initialiseCharacters()
	{
		if (font == null || text == null)
		{
			return;
		}

		clearChildren();

		float totalWidth = 0;

		for (int i = 0; i < text.length(); i++)
		{
			Character character = new Character(text.charAt(i), font);
			character.moveBy(totalWidth + character.getWidth() / 2.0f, 0);
			totalWidth += character.getWidth();
			addChild(character);
		}

		for (BaseView view : getChildren())
		{
			view.moveBy( - totalWidth / 2.0f, 0);
		}
	}

	private class Character extends TexturedView
	{
		protected char character;
		protected Font font;

		public Character(char character, Font font)
		{
			this.character = character;
			this.font = font;

			setTextureId(font.getTextureId());
			setTextureInfo(font.getTextureInfosForCharacter(character));

			float widestCharacter = font.getWidestCharacterWidth();
			float characterHeight = font.getFontSize();
			float characterWidth = textureInfo.getWidthPx();

			setWidth(characterWidth / AGEngine.CoordinateSystem().getViewWidthPx());
			setHeight(characterHeight / AGEngine.CoordinateSystem().getViewHeightPx());
			//scaleBy(3.0f, 3.0f);
			initialise();
		}
	}
}