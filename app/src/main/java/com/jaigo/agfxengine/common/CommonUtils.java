package com.jaigo.agfxengine.common;
// CommonUtils
//
// Created by Jeff Gosling on 08/01/2015

import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommonUtils
{
	public static void checkGLError(String glOperation)
	{
		int error;
		String errorMessage = "";

		while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR)
		{
			errorMessage += glOperation + ": glError " + error;
		}

		if (!errorMessage.isEmpty())
		{
			Log.e(LogTags.OPEN_GL, errorMessage);
		}
	}

	public static String readTextFileFromRawResource(final Resources res, final int resourceId)
	{
		final InputStream inputStream = res.openRawResource(resourceId);
		final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String nextLine;
		final StringBuilder body = new StringBuilder();

		try
		{
			while ((nextLine = bufferedReader.readLine()) != null)
			{
				body.append(nextLine);
				body.append('\n');
			}
		}
		catch (IOException e)
		{
			return null;
		}

		return body.toString();
	}
}
