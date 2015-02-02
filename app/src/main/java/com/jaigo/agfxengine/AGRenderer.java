package com.jaigo.agfxengine;
// AGRenderer
//
// Created by Jeff Gosling on 08/01/2015

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.jaigo.agfxengine.common.LogTags;

public class AGRenderer implements GLSurfaceView.Renderer
{
	public AGSurfaceView agSurfaceView;
	public AGRenderer(AGSurfaceView agSurfaceView)
	{
		this.agSurfaceView = agSurfaceView;
	}

	private int framesPerSecond = 0;
	private long lastFPSCheckTimeMs = 0;

	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig eglConfig)
	{
		Log.d(LogTags.DEBUG, "===== AGRenderer.onSurfaceCreated =====");

		AGEngine.Instance().onGLSurfaceCreated();
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height)
	{
		Log.d(LogTags.DEBUG, "===== AGRenderer.onSurfaceChanged =====");

		AGEngine.Instance().onGLSurfaceResized(width, height);
	}



	@Override
	public void onDrawFrame(GL10 unused)
	{
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

		AGEngine.AnimationManager().runAnimators();
		AGEngine.ViewManager().drawViews();

		updateFPS();
	}

	public int getFramesPerSecond()
	{
		Log.d(LogTags.OPEN_GL, "FPS = " + framesPerSecond);
		return framesPerSecond;
	}

	private void updateFPS()
	{
		framesPerSecond++;
		long currentFPSCheckTimeMs = System.currentTimeMillis();

		if (currentFPSCheckTimeMs - lastFPSCheckTimeMs >= 1000) {
			framesPerSecond = 0;
			lastFPSCheckTimeMs = currentFPSCheckTimeMs;
		}
	}
}
