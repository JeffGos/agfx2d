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
import com.jaigo.agfxengine.view.BaseView;
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

		/*
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.opengleslogo);
		final Image image = new Image(600, 210, bitmap);
		scene.addChild(image);

		final Image image2 = new Image(600, 210, bitmap);
		image2.setCenterPercent(0, 0.4f);
		scene.addChild(image2);
*/
		//View square = new View(0.5f, 0.5f);
		//square.setCenterPercent(0, -0.3f);
		//scene.addChild(square);

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.opengleslogo);
		final Image image = new Image(600, 210, bitmap);
		scene.addChild(image);

		final Image image2 = new Image(600, 210, bitmap);
		image2.setCenterPercent(0, 0.4f);
		scene.addChild(image2);

		AGEngine.ViewManager().addView(scene);

		Animation spinRepeat = new Animation()
		{
			int timer = 0;
			float scaleIncrement = 0.01f;
			float rotateIncrement = 5f;

			@Override
			public void animate()
			{
				//image.scaleByPercent(scaleIncrement, scaleIncrement);
				//image.rotateZByAngle(rotateIncrement);
				timer++;

				if (timer >= 200) {
					scaleIncrement *= -1;
					rotateIncrement *= -1;
					timer = 0;
				}
			}
		};

		AGEngine.AnimationManager().addAnimator(spinRepeat);
	}
}
