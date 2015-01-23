package com.jaigo.agfxenginedemo;
// MainActivity
//
// Created by Jeff Gosling on 07/01/2015

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.jaigo.agfx2d.R;
import com.jaigo.agfxengine.AGEngine;
import com.jaigo.agfxengine.AGEngineEventListener;
import com.jaigo.agfxengine.AGSurfaceView;
import com.jaigo.agfxengine.animation.Animation;
import com.jaigo.agfxengine.common.Color;
import com.jaigo.agfxengine.view.BaseView;
import com.jaigo.agfxengine.view.Button;
import com.jaigo.agfxengine.view.Image;
import com.jaigo.agfxengine.view.View;

public class MainActivity extends Activity implements AGEngineEventListener
{
	AGSurfaceView agSurfaceView;
	BaseView scene;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

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
		createScene();
	}

	private void createScene()
	{
		scene = new BaseView(1.0f, 1.0f);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.opengleslogo);
		final Image image = new Image(0.6f, 0.6f, bitmap);
		image.setCenter(0.5f, 0.9f);
		scene.addChild(image);

		View square = new View(0.5f, AGEngine.CoordinateSystem().widthPercentRelativeToHeight(0.5f));
		square.setColor(new Color(Color.BLUE));
		square.setDraggable(true);
		scene.addChild(square);

		final Image image2 = new Image(0.6f, 0.6f, bitmap);
		image2.setCenter(0.5f, 0.1f);
		scene.addChild(image2);

		Button btn = new Button(0.4f, 0.1f);
		btn.setCenter(0.5f, 0.3f);
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
