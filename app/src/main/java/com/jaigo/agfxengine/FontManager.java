package com.jaigo.agfxengine;
// FontManager
//
// Created by Jeff Gosling on 28/01/2015
//

import android.graphics.Typeface;

import java.util.UUID;

public class FontManager
{
	public static final int FONT_TEXTURE_WIDTH = 250;
	public static final int FONT_TEXTURE_HEIGHT = 250;

	private Font defaultFont;
	private UUID textureId;

	public Font createFont(Typeface typeface, int fontSize) {

		Font result = new Font(typeface, fontSize, FONT_TEXTURE_WIDTH, FONT_TEXTURE_HEIGHT);

		defaultFont = result;
		textureId = AGEngine.TextureManager().addTexture(result);
		defaultFont.setTextureId(textureId);

		return result;
	}

	public Font getDefaultFont()
	{
		return defaultFont;
	}
}
