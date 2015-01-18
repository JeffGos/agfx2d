package com.jaigo.agfxengine.shader;
// ShaderManager
//
// Created by Jeff on 11/01/2015

import android.util.Log;

import com.jaigo.agfx2d.R;
import com.jaigo.agfxengine.common.LogTags;

import java.util.ArrayList;

public class ShaderManager
{
	private ArrayList<BaseShader> shaders = new ArrayList<BaseShader>();
	private boolean initialised;

	private BaseShader nonTexturedShader;
	private BaseShader texturedShader;

	public ShaderManager() {
		nonTexturedShader = new TexturedShader(new ShaderInfo(R.raw.simple_vs, R.raw.simple_fs));
		texturedShader = new TexturedShader(new ShaderInfo(R.raw.textured_vs, R.raw.textured_fs));
	}

	public void initialise()
	{
		Log.d(LogTags.OPEN_GL, "ShaderManager.initialise()");

		nonTexturedShader.initialise();
		texturedShader.initialise();

		initialised = true;
	}

	public void destroy()
	{
		Log.d(LogTags.OPEN_GL, "ShaderManager.destroy()");

		nonTexturedShader.destroy();
		texturedShader.destroy();

		initialised = false;
	}

	public BaseShader getShader(ShaderInfo shaderInfo)
	{
		return loadShader(shaderInfo);
	}

	public BaseShader loadShader(ShaderInfo shaderInfo)
	{
		BaseShader result = new BaseShader(shaderInfo);

		result.initialise();
		shaders.add(result);

		return result;
	}

	public BaseShader getNonTexturedShader()
	{
		return nonTexturedShader;
	}

	public BaseShader getTexturedShader()
	{
		return texturedShader;
	}

	public BaseShader getShader(int index)
	{
		return shaders.get(index);
	}


	public void setViewProjectionMatrix(float[] vpMatrix)
	{
		texturedShader.setViewProjectionMatrix(vpMatrix);
		nonTexturedShader.setViewProjectionMatrix(vpMatrix);
	}
}
