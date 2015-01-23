package com.jaigo.agfxengine.view;
// View
//
// Created by Jeff Gosling on 09/01/2015

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.jaigo.agfxengine.AGEngine;
import com.jaigo.agfxengine.common.Color;
import com.jaigo.agfxengine.shader.BaseShader;

public class View extends BaseView
{
	private final float[] glScale = new float [] { 1.0f, 1.0f, 0, 0};
	private final float[] glPreRotationTranslation = new float [] { 0, 0, 0, 0};
	private final float[] glRotationMatrix = new float [16];
	private final float[] glTranslation = new float [] { 0, 0, 0, 0};

	private int numberOfVertices = 4;

	protected BaseShader shader;

	protected Color color = new Color(new float [] {1.0f, 0.0f, 1.0f, 1.0f});

	public View(float widthPercent, float heightPercent)
	{
		super(widthPercent, heightPercent);

		initialise();
	}

	public void initialise()
	{
		resetRotation();
		onPositionChanged();
		onOrientationChanged();
		onDimensionsChanged();

		shader = AGEngine.ShaderManager().getNonTexturedShader();
	}

	public void draw()
	{
		int programHandle = shader.getProgramHandle();
		shader.activate();

		GLES20.glUniform4fv(GLES20.glGetUniformLocation(programHandle, "scale"), 1, glScale, 0);
		GLES20.glUniform4fv(GLES20.glGetUniformLocation(programHandle, "preRotationTranslation"), 1, glPreRotationTranslation, 0);
		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(programHandle, "rotation"), 1, false, glRotationMatrix, 0);
		GLES20.glUniform4fv(GLES20.glGetUniformLocation(programHandle, "translation"), 1, glTranslation, 0);
		GLES20.glUniform4fv(GLES20.glGetUniformLocation(programHandle, "color"), 1, getColor().getArray(), 0);

		shader.enable();
		GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, getNumberOfVertices(), GLES20.GL_UNSIGNED_SHORT, 0);
		shader.disable();

		super.draw();
	}

	public Color getColor()
	{
		return color;
	}

	public View setColor(Color color)
	{
		this.color = color;
		return this;
	}

	public int getNumberOfVertices()
	{
		return numberOfVertices;
	}

	public View setNumberOfVertices(int count)
	{
		numberOfVertices = count;
		return this;
	}

	public void setGlPreRotationTranslation(float[] glPreRotationTranslation)
	{
		for (int i = 3; i >= 0; --i)
		{
			glPreRotationTranslation[i] = glPreRotationTranslation[i];
		}
	}

	@Override
	protected void onDimensionsChanged()
	{
		if (glScale == null)
		{
			return;
		}

		glScale[0] = getWidth();
		glScale[1] = getHeight();
		glScale[2] = 1.0f;
		glScale[3] = 1.0f;
	}

	@Override
	protected void onPositionChanged()
	{
		if (glTranslation == null)
		{
			return;
		}

		glTranslation[0] = AGEngine.Instance().getCoordinateSystem().convertPercentageToGLValueX(getCenterX());
		glTranslation[1] = AGEngine.Instance().getCoordinateSystem().convertPercentageToGLValueY(getCenterY());
		glTranslation[2] = 0.0f;
		glTranslation[3] = 0.0f;

		if (hasParent())
		{
			float parentCenterXPercent = getParent().getAbsoluteCenterVector()[0];
			float parentCenterYPercent = getParent().getAbsoluteCenterVector()[1];

			glTranslation[0] += AGEngine.Instance().getCoordinateSystem().convertPercentageToGLValueX(parentCenterXPercent);;
			glTranslation[1] += AGEngine.Instance().getCoordinateSystem().convertPercentageToGLValueY(parentCenterYPercent);;
		}

		for (BaseView child : getChildren()) {
			child.onPositionChanged();
		}
	}

	@Override
	protected void onOrientationChanged()
	{
		if (glRotationMatrix == null)
		{
			return;
		}

		if (getRotateXAngle() != 0) {
			Matrix.setRotateM(glRotationMatrix, 0, getRotateXAngle(), 1, 0, 0);
		}
		else if (getRotateYAngle() != 0) {
			Matrix.setRotateM(glRotationMatrix, 0, getRotateYAngle(), 0, 1, 0);
		}
		else if (getRotateZAngle() != 0) {
			Matrix.setRotateM(glRotationMatrix, 0, getRotateZAngle(), 0, 0, 1);
		}
		else
		{
			Matrix.setIdentityM(glRotationMatrix, 0);
		}
	}

}
