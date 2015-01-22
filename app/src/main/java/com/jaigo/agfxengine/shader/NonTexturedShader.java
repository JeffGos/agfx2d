package com.jaigo.agfxengine.shader;
// NonTexturedShader
//
// Created by Jeff on 11/01/2015

import android.opengl.GLES20;

import com.jaigo.agfxengine.common.CommonUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class NonTexturedShader extends BaseShader
{
	private int vertexArrayHandle;
	private int vertexBufferId;
	private int drawOrderBufferId;

	public NonTexturedShader(ShaderInfo shaderInfo)
	{
		super(shaderInfo);
	}

	protected void initShaderVariables()
	{
		float[] vertices = new float[]
				{
						-1.0f, -1.0f, 0.0f,
						1.0f, -1.0f, 0.0f,
						-1.0f, 1.0f, 0.0f,
						1.0f, 1.0f, 0.0f
				};

		short[] drawOrder = new short[] { 0, 1, 2, 3 };

		ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer vertexBuffer = bb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		bb = ByteBuffer.allocateDirect(drawOrder.length * 2);
		bb.order(ByteOrder.nativeOrder());
		ShortBuffer drawOrderBuffer = bb.asShortBuffer();
		drawOrderBuffer.put(drawOrder);
		drawOrderBuffer.position(0);

		int[] bufferIds = new int[2];
		GLES20.glGenBuffers(2, bufferIds, 0);
		vertexBufferId = bufferIds[0];
		drawOrderBufferId = bufferIds[1];

		vertexArrayHandle = GLES20.glGetAttribLocation(programHandle, "vPosition");

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertices.length * 4, vertexBuffer, GLES20.GL_STATIC_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		CommonUtils.checkGLError("NonTexturedShader.initShaderVariables - error pushing vertex buffer");

		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, drawOrderBufferId);
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, drawOrder.length * 2, drawOrderBuffer, GLES20.GL_STATIC_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
		CommonUtils.checkGLError("NonTexturedShader.initShaderVariables - error pushing draw order buffer");

		super.initShaderVariables();
	}

	@Override
	public void activate()
	{
		super.activate();

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
		GLES20.glVertexAttribPointer(vertexArrayHandle, 3, GLES20.GL_FLOAT, false, 4 * 3, 0);

		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, drawOrderBufferId);
	}

	@Override
	public void enable()
	{
		super.enable();

		GLES20.glEnableVertexAttribArray(vertexArrayHandle);
	}

	@Override
	public void disable()
	{
		GLES20.glDisableVertexAttribArray(vertexArrayHandle);
		super.disable();
	}
}
