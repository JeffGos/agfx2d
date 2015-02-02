package com.jaigo.agfxengine.common;
// Font
//
// Created by Jeff Gosling on 28/01/2015
//

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import com.jaigo.agfxengine.common.LogTags;
import com.jaigo.agfxengine.texture.TextureBitmapProvider;
import com.jaigo.agfxengine.texture.TextureInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Font implements TextureBitmapProvider
{
	private Typeface typeface;
	private int fontSize;
	private Bitmap textureBitmap;
	private int textureWidth;
	private int textureHeight;
	private UUID textureId;
	private float widestCharacterWidth = 0;

	private HashMap<Character, TextureInfo> textureInfosByCharacterValue = new HashMap<Character, TextureInfo>();
	private TextureInfo fontTextureInfo = new TextureInfo();

	public Font(Typeface typeface, int fontSize, int textureWidth, int textureHeight)
	{
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.typeface = typeface;
		this.fontSize = fontSize;
	}

	public void setTypeface(Typeface typeface)
	{
		this.typeface = typeface;
	}

	public Typeface getTypeface()
	{
		return typeface;
	}

	public int getFontSize()
	{
		return fontSize;
	}

	public void setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
	}

	public ArrayList<TextureInfo> getTextureInfosForString(String value)
	{
		ArrayList<TextureInfo> result = new ArrayList<>();

		if (value == null)
		{
			return result;
		}

		for (int i = 0; i < value.length(); i++)
		{
			char currentChar = (char) value.getBytes()[i];

			Character character = new Character(currentChar);

			TextureInfo characterTextureInfo = textureInfosByCharacterValue.get(character);

			result.add(characterTextureInfo);
		}

		return result;
	}

	@Override
	public int getTextureWidth()
	{
		return 0;
	}

	@Override
	public int getTextureHeight()
	{
		return 0;
	}

	@Override
	public Bitmap getTextureBitmap()
	{
		textureBitmap = Bitmap.createBitmap(textureWidth, textureHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(textureBitmap);
		Paint paint = new Paint();
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(0xffffffff);
		paint.setTypeface(typeface);
		paint.setTextSize(fontSize);

		float totalWidth = 0;
		float totalHeight = fontSize;

		textureInfosByCharacterValue.clear();

		Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
		int heightPx = fontMetrics.bottom - fontMetrics.top;

		for (int i = 32; i <= 126; i++)
		{
			Character character = new Character((char)i);
			float width = paint.measureText(character.toString());

			if (width > widestCharacterWidth)
			{
				widestCharacterWidth = width;
			}

			Log.d(LogTags.DEBUG, "width of " + character.toString() + " = " + width);

			if (totalWidth + width > textureWidth)
			{
				totalHeight += heightPx;
				totalWidth = 0;
			}

			canvas.drawText(character.toString(), totalWidth, totalHeight, paint);

			TextureInfo characterTextureInfo = new TextureInfo();

			characterTextureInfo.setWidthPx((int) width);
			characterTextureInfo.setHeightPx(heightPx);
			characterTextureInfo.setxPx((int) totalWidth);
			characterTextureInfo.setyPx((int) (totalHeight - fontSize) + 1);
			characterTextureInfo.setTextureAtlasDimensionsPx(textureWidth, textureHeight);

			Log.d(LogTags.DEBUG, "Adding textureInfo for character " + character.toString());
			Log.d(LogTags.DEBUG, characterTextureInfo.toString());

			textureInfosByCharacterValue.put(character, characterTextureInfo);

			totalWidth += width;
		}

		//for (TextureInfo textureInfo : textureInfosByCharacterValue.values())
		{
			//textureInfo.setTextureAtlasDimensionsPx(textureWidth, (int) totalHeight);
		}



		return textureBitmap;
	}

	public TextureInfo getFontTextureInfo()
	{
		return fontTextureInfo;
	}

	public void setTextureId(UUID textureId)
	{
		this.textureId = textureId;
	}

	public UUID getTextureId()
	{
		return textureId;
	}

	public float getWidestCharacterWidth()
	{
		return widestCharacterWidth;
	}
}