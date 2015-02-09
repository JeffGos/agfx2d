package com.jaigo.agfxengine.manager;
// FontManager
//
// Created by Jeff Gosling on 28/01/2015
//

import android.graphics.Typeface;

import com.jaigo.agfxengine.AGEngine;
import com.jaigo.agfxengine.common.Font;

import java.util.UUID;

public class FontManager
{
	public static final int FONT_TEXTURE_WIDTH = 1000;
	public static final int FONT_TEXTURE_HEIGHT = 1000;

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
