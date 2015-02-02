package com.jaigo.agfxengine;
// AGEngine
//
// Created by Jeff Gosling on 08/01/2015

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.jaigo.agfxengine.animation.AnimationManager;
import com.jaigo.agfxengine.common.LogTags;
import com.jaigo.agfxengine.view.ViewManager;
import com.jaigo.agfxengine.shader.ShaderManager;
import com.jaigo.agfxengine.texture.TextureManager;

public class AGEngine
{
	public static AGEngine Create(Context context)
	{
		if (instance == null) {
			instance = new AGEngine(context);
		}

		return instance;
	}

	public static AGEngine Instance()
	{
		return instance;
	}

	public static AGCoordinateSystem CoordinateSystem() { return coordinateSystem; }
	public static TextureManager TextureManager() { return textureManager; }
	public static ShaderManager ShaderManager() { return shaderManager; }
	public static ViewManager ViewManager() { return viewManager; }
	public static AnimationManager AnimationManager() { return animationManager; }
	public static FontManager FontManager() { return fontManager; }

	private static AGCoordinateSystem coordinateSystem = new AGCoordinateSystem(2.0f, 2.0f, 5.0f);
	private static AGEngine instance;
	private static TextureManager textureManager;
	private static ShaderManager shaderManager;
	private static ViewManager viewManager;
	private static AnimationManager animationManager;
	private static FontManager fontManager;

	private Context context;
	private boolean initialised;

	private AGEngineEventListener eventListener;

	private AGEngine(Context context)
	{
		this.context = context;

		textureManager = new TextureManager();
		shaderManager = new ShaderManager();
		viewManager = new ViewManager();
		animationManager = new AnimationManager();
		fontManager = new FontManager();
	}

	public void onGLSurfaceCreated()
	{
		Log.d(LogTags.OPEN_GL, "AGEngine.onGLSurfaceCreated");

		if (initialised)
		{
			destroy();
		}

		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glCullFace(GLES20.GL_BACK);
	}

	public void onGLSurfaceResized(int width, int height)
	{
		Log.d(LogTags.OPEN_GL, "AGEngine.onGLSurfaceResized. w = " + width + " h = " + height);

		coordinateSystem.setViewWidthPx(width);
		coordinateSystem.setViewHeightPx(height);

		//set up the camera and projection
		GLES20.glViewport(0, 0, width, height);

		float[] projMatrix = new float[16];
		float[] viewMatrix = new float[16];
		float[] vpMatrix = new float[16];

		Matrix.frustumM(projMatrix, 0, 		//m, offset,
				-coordinateSystem.getGlCameraLeft(),
				-coordinateSystem.getGlCameraRight(),
				coordinateSystem.getGlCameraBottom(),
				coordinateSystem.getGlCameraTop(),
				coordinateSystem.getGlCameraDistance(), 		// near,
				coordinateSystem.getGlCameraDistance() * 1.5f); 		// far

		// Set the camera position (View matrix)
		Matrix.setLookAtM(viewMatrix, 0, // rm, rmOffset,
				0, 0, -coordinateSystem.getGlCameraDistance(), // eyeX, eyeY, eyeZ,
				0f, 0f, 0f, // centerX, centerY, centerZ,
				0f, 1.0f, 0.0f); // upX, upY, upZ

		Matrix.multiplyMM(vpMatrix, 0, projMatrix, 0, viewMatrix, 0);

		shaderManager.setViewProjectionMatrix(vpMatrix);

		initialise();
	}

	public void initialise()
	{
		if (initialised)
		{
			return;
		}

		Log.d(LogTags.OPEN_GL, "AGEngine.initialise");
		initialised = true;

		shaderManager.initialise();
		textureManager.initialise();
		viewManager.initialise();

		if (eventListener != null) {
			eventListener.onGraphicsEngineInitialised();
		}
	}

	public void createFont(String fontName, int fontSize)
	{

	}

	public void destroy()
	{
		Log.d(LogTags.OPEN_GL, "AGEngine.destroy()");

		shaderManager.destroy();
		textureManager.destroy();
		viewManager.destroy();

		initialised = false;
	}

	public void setEventListener(AGEngineEventListener eventListener)
	{
		this.eventListener = eventListener;
	}

	public Context getContext()
	{
		return context;
	}

	public AGCoordinateSystem getCoordinateSystem()
	{
		return coordinateSystem;
	}
}
