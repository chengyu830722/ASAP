package com.cy.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

public class GlobalVal {
	public static final float M2P=30;
	public static final boolean DEBUG = true;
	
	public static final float BOUND = 40f;
	public static final float WIDTH = 480f;
	public static final float HEIGHT = 800f;
	public static final float STAGE=1;
	public static AssetManager manager;
	public static final float factorx = 480.0f / Gdx.graphics.getWidth();
	public static final float factory = 800.0f / Gdx.graphics.getHeight();
}
