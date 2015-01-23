package com.jaigo.agfxengine.animation;
// TimedAnimation
//
// Created by Jeff Gosling on 12/01/2015

import android.os.SystemClock;

public abstract class TimedAnimation extends Animation
{
	protected long startTime;
	protected final long duration;

	public TimedAnimation(long duration)
	{
		this.duration = duration;
	}

	public void start()
	{
		if (isAnimating())
		{
			return;
		}

		startTime = SystemClock.elapsedRealtime();
		animate(0.0f);
		super.start();
	}

	@Override
	public void animate()
	{
		long currentTime = SystemClock.elapsedRealtime();
		long timeSinceStart = currentTime - startTime;

		if (timeSinceStart <= duration)
		{
			float progress = (float) timeSinceStart / (float) this.duration;
			animate(progress);
			return;
		}

		animate(1.0f);
		stop();
	}

	public abstract void animate(float progressPercent);
}
