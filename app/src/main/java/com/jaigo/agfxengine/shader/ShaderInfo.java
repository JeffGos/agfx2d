package com.jaigo.agfxengine.shader;
// ShaderInfo
//
// Created by Jeff on 11/01/2015

public class ShaderInfo
{
	public final int vertexShaderResourceId;
	public final int fragmentShaderResourceId;

	public ShaderInfo(int vertexShaderResourceId, int fragmentShaderResourceId)
	{
		this.vertexShaderResourceId = vertexShaderResourceId;
		this.fragmentShaderResourceId = fragmentShaderResourceId;
	}
}
