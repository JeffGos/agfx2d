package com.jaigo.agfxengine.shader;
// TexturedShader
//
// Created by Jeff on 11/01/2015

import android.opengl.GLES20;

import com.jaigo.agfxengine.common.CommonUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class TexturedShader extends BaseShader
{
	private int vertexBufferId;
	private int textureCoordsBufferId;
	private int drawOrderBufferId;

	public TexturedShader(ShaderInfo shaderInfo)
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

		float[] textureCoords = new float[]
				{
						0.0f, 1.0f,
						1.0f, 1.0f,
						0.0f, 0.0f,
						1.0f, 0.0f,
				};

		short[] drawOrder = new short[] { 0, 1, 2, 3 };

		ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer vertexBuffer = bb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		bb = ByteBuffer.allocateDirect(textureCoords.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer textureBuffer = bb.asFloatBuffer();
		textureBuffer.put(textureCoords);
		textureBuffer.position(0);

		bb = ByteBuffer.allocateDirect(drawOrder.length * 2);
		bb.order(ByteOrder.nativeOrder());
		ShortBuffer drawOrderBuffer = bb.asShortBuffer();
		drawOrderBuffer.put(drawOrder);
		drawOrderBuffer.position(0);

		int[] bufferIds = new int[3];
		GLES20.glGenBuffers(3, bufferIds, 0);

		vertexBufferId = bufferIds[0];
		textureCoordsBufferId = bufferIds[1];
		drawOrderBufferId = bufferIds[2];

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertices.length * 4, vertexBuffer, GLES20.GL_STATIC_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, textureCoordsBufferId);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, textureCoords.length * 4, textureBuffer, GLES20.GL_STATIC_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, drawOrderBufferId);
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, drawOrder.length * 2, drawOrderBuffer, GLES20.GL_STATIC_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
		CommonUtils.checkGLError("TexturedShader.initShaderVariables - error binding buffers");

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
		int vHandle = GLES20.glGetAttribLocation(programHandle, "vPosition");
		GLES20.glEnableVertexAttribArray(vHandle);
		GLES20.glVertexAttribPointer(vHandle, 3, GLES20.GL_FLOAT, false, 4 * 3, 0);
		CommonUtils.checkGLError("TexturedShader.initShaderVariables - error in glVertexAttribPointer for vPosition");

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, textureCoordsBufferId);
		int tCoordHandle = GLES20.glGetAttribLocation(programHandle, "tCoordIn");
		GLES20.glEnableVertexAttribArray(tCoordHandle);
		GLES20.glVertexAttribPointer(tCoordHandle,  2, GLES20.GL_FLOAT, false, 4 * 2, 0);
		CommonUtils.checkGLError("TexturedShader.initShaderVariables - error in glVertexAttribPointer for texture");

		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, drawOrderBufferId);
	}
}
