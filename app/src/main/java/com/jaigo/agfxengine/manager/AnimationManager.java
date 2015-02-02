package com.jaigo.agfxengine.manager;
// AnimationManager
//
// Created by Jeff Gosling on 12/01/2015

import com.jaigo.agfxengine.animation.Animation;

import java.util.concurrent.CopyOnWriteArraySet;

public class AnimationManager
{
	private CopyOnWriteArraySet<Animation> animations = new CopyOnWriteArraySet<Animation>();

	public void runAnimators()
	{
		Object[] animatorsArray = animations.toArray();
		for (int i = 0; i < animations.size(); ++i)
		{
			((Animation)animatorsArray[i]).animate();
		}
	}

	public void addAnimator(Animation animation)
	{
		animations.add(animation);
	}

	public boolean removeAnimator(Animation animation)
	{
		return animations.remove(animation);
	}
}
