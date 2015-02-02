package com.jaigo.agfxenginedemo;
// MainActivity
//
// Created by Jeff Gosling on 07/01/2015

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;

import com.jaigo.agfx2d.R;
import com.jaigo.agfxengine.AGEngine;
import com.jaigo.agfxengine.AGEngineEventListener;
import com.jaigo.agfxengine.AGSurfaceView;
import com.jaigo.agfxengine.animation.Animation;
import com.jaigo.agfxengine.common.Color;
import com.jaigo.agfxengine.view.*;

public class MainActivity extends Activity implements AGEngineEventListener
{
	AGSurfaceView agSurfaceView;
	BaseView scene;
	Typeface defaultFont;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		defaultFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Condensed.ttf");

		agSurfaceView = new AGSurfaceView(this, null);
		setContentView(agSurfaceView);

		AGEngine.Create(getApplicationContext());
		AGEngine.Instance().setEventListener(this);
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		agSurfaceView.onPause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		agSurfaceView.onResume();
	}

	@Override
	public void onGraphicsEngineInitialised()
	{
		AGEngine.FontManager().createFont(defaultFont, 32);

		createScene();

		createDebugScene();
	}

	private void createDebugScene()
	{
		scene = new BaseView(1.0f, 1.0f);

		Text text = new Text(0.5f, 0.1f);
		text.setText("Hello World");
		text.setColor(new Color(Color.RED));
		text.setTextColor(new Color(Color.WHITE));
		scene.addChild(text);

		//View v = new View(0.5f, 0.5f);
		//v.setColor(new Color(Color.BLUE));
		//scene.addChild(v);

		AGEngine.ViewManager().addView(scene);
	}

	private void createScene()
	{
		float width = 0.1f;
		float widthAsHeight = AGEngine.CoordinateSystem().widthPercentRelativeToHeight(width);

		scene = new BaseView(1.0f, 1.0f);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.opengleslogo);
		final Image image = new Image(0.6f, 0.6f, bitmap);
		image.setCenter(0.5f, 0.9f);
		scene.addChild(image);

		float halfScreenWidthAsHeight = AGEngine.CoordinateSystem().widthPercentRelativeToHeight(0.5f);

		View square = new View(0.5f, halfScreenWidthAsHeight);
		square.setColor(new Color(Color.BLUE));
		square.setDraggable(true);
		scene.addChild(square);

		final Image image2 = new Image(0.6f, 0.6f, bitmap);
		image2.setCenter(0.5f, 0.1f);
		scene.addChild(image2);

		Button btn = new Button(0.4f, 0.1f);
		btn.setCenter(0.5f, 0.25f);
		btn.setColor(new Color(Color.RED));
		btn.setTouchColor(new Color(Color.BLACK));
		scene.addChild(btn);

		Animation spinRepeat = new Animation()
		{
			int timer = 0;
			float scaleIncrement = 0.01f;
			float rotateIncrement = 5f;

			@Override
			public void animate()
			{
				image.scaleBy(scaleIncrement, scaleIncrement);
				image.rotateZByAngle(rotateIncrement);
				timer++;

				if (timer >= 200) {
					scaleIncrement *= -1;
					rotateIncrement *= -1;
					timer = 0;
				}
			}
		};

		spinRepeat.start();

		AGEngine.ViewManager().addView(scene);
	}
}
