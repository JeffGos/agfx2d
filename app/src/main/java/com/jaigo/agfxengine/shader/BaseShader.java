package com.jaigo.agfxengine.shader;
// BaseShader
//
// Created by Jeff Gosling on 11/01/2015

import android.opengl.GLES20;
import android.util.Log;

import com.jaigo.agfxengine.common.CommonUtils;
import com.jaigo.agfxengine.common.LogTags;
import com.jaigo.agfxengine.AGEngine;

public class BaseShader
{
	protected final ShaderInfo shaderInfo;

	protected int programHandle;
	protected int vertexShaderHandle;
	protected int fragmentShaderHandle;

	protected float[] vpMatrix = null;
	protected boolean initialised;

	public BaseShader(ShaderInfo shaderInfo)
	{
		this.shaderInfo = shaderInfo;
	}

	public int initialise()
	{
		if (initialised)
		{
			return programHandle;
		}

		Log.d(LogTags.OPEN_GL, "BaseShader.initialise()");

		String vertexShaderCode = CommonUtils.readTextFileFromRawResource(AGEngine.Instance().getContext().getResources(), shaderInfo.vertexShaderResourceId);
		String fragmentShaderCode = CommonUtils.readTextFileFromRawResource(AGEngine.Instance().getContext().getResources(), shaderInfo.fragmentShaderResourceId);

		vertexShaderHandle = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		fragmentShaderHandle = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

		programHandle = GLES20.glCreateProgram();
		GLES20.glAttachShader(programHandle, vertexShaderHandle);
		GLES20.glAttachShader(programHandle, fragmentShaderHandle);
		GLES20.glLinkProgram(programHandle);
		CommonUtils.checkGLError("BaseShader.initialise - error attaching shaders and linking shader program ");

		activate();

		initShaderVariables();

		initialised = true;

		pushVPMatrix();

		Log.d(LogTags.OPEN_GL, "BaseShader.initialise() - programHandle = " + programHandle + " - vertexShaderHandle = " + vertexShaderHandle + " - fragmentShaderHandle = " + fragmentShaderHandle);

		return programHandle;
	}

	public void destroy()
	{
		Log.d(LogTags.OPEN_GL, "BaseShader.destroy()");

		initialised = false;
	}

	protected void initShaderVariables() {

	}

	public void activate()
	{
		GLES20.glUseProgram(programHandle);
		CommonUtils.checkGLError("BaseShader.activate - error using programHandle " + programHandle);
	}

	public void enable()
	{

	}

	public void disable()
	{

	}

	public void setViewProjectionMatrix(float[] vpMatrix) {

		this.vpMatrix = vpMatrix;//.clone();

		pushVPMatrix();
	}

	public void pushVPMatrix()
	{
		if (vpMatrix == null || !initialised)
		{
			return;
		}

		activate();

		int vpMatrixLocation = GLES20.glGetUniformLocation(programHandle, "vpMatrix");
		CommonUtils.checkGLError("BaseShader.pushVPMatrix - error fetching vpMatrix from shader");
		GLES20.glUniformMatrix4fv(vpMatrixLocation, 1, false, vpMatrix, 0);
		CommonUtils.checkGLError("BaseShader.pushVPMatrix - error pushing vpMatrix to shader");
	}

	public static int loadShader(int type, String shaderCode)
	{
		int shader = GLES20.glCreateShader(type);
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		CommonUtils.checkGLError("BaseShader.LoadShader - error loading shader");
		return shader;
	}

	public int getProgramHandle()
	{
		return programHandle;
	}
}
