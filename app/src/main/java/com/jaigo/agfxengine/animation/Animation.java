package com.jaigo.agfxengine.animation;
// Animation
//
// Created by Jeff Gosling on 12/01/2015

import com.jaigo.agfxengine.AGEngine;

public abstract class Animation
{
	private boolean isAnimating = false;

	public void stop()
	{
		AGEngine.AnimationManager().removeAnimator(this);
		setIsAnimating(false);
	}

	public void start()
	{
		setIsAnimating(true);
		AGEngine.AnimationManager().addAnimator(this);
	}

	public boolean isAnimating()
	{
		return isAnimating;
	}
	protected void setIsAnimating(boolean isAnimating)
	{
		this.isAnimating = isAnimating;
	}

	public abstract void animate();
}
