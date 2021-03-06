package com.jaigo.agfxengine.view;
// Button
//
// Created by Jeff Gosling on 10/01/2015

import android.util.Log;
import android.view.MotionEvent;

import com.jaigo.agfxengine.animation.TimedAnimation;
import com.jaigo.agfxengine.common.Color;
import com.jaigo.agfxengine.common.LogTags;

public class Button extends View
{
	private static int FADE_COLOR_TIMEOUT_MS = 150;

	private Text text;
	private Color originalColor = new Color();
	private Color touchColor;
	private TimedAnimation colorAnimation = new TimedAnimation(FADE_COLOR_TIMEOUT_MS)
	{
		private float originalRed;
		private float originalGreen;
		private float originalBlue;
		private float targetRed;
		private float targetGreen;
		private float targetBlue;

		@Override
		public void start()
		{
			originalRed = originalColor.getArray()[Color.R];
			originalGreen = originalColor.getArray()[Color.G];
			originalBlue = originalColor.getArray()[Color.B];
			targetRed = touchColor.getArray()[Color.R];
			targetGreen = touchColor.getArray()[Color.G];
			targetBlue = touchColor.getArray()[Color.B];

			super.start();
		}

		@Override
		public void animate(float progressPercent)
		{
			float red = originalRed + (targetRed - originalRed) * (1.0f - progressPercent);
			float green = originalGreen + (targetGreen - originalGreen) * (1.0f - progressPercent);
			float blue = originalBlue + (targetBlue - originalBlue) * (1.0f - progressPercent);

			color.set(red, green, blue);
		}
	};

	public Button(float widthPercent, float heightPercent)
	{
		super(widthPercent, heightPercent);
	}

	public void setTouchColor(Color color)
	{
		touchColor = color;
	}

	@Override
	public View setColor(Color color)
	{
		originalColor.copy(color);
		return super.setColor(color);
	}

	public void setText(String value)
	{
		if (text == null) {
			text = new Text(getWidth(), getHeight());
			text.setColor(new Color(Color.TRANSPARENT));
			text.setTextColor(new Color(Color.WHITE));
			addChild(text);
		}

		text.setText(value);
	}

	@Override
	protected boolean onTouched(MotionEvent event)
	{
		colorAnimation.stop();
		color.copy(touchColor);

		Log.d(LogTags.DEBUG, "onTouched");

		return false;
	}

	@Override
	protected boolean onReleased(MotionEvent event)
	{
		colorAnimation.start();

		Log.d(LogTags.DEBUG, "onReleased");

		return false;
	}
}
