package com.cy.framework;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.assets.AssetManager;

public class GlobalVal {
	public static final float M2P = 30;
	public static final boolean DEBUG = true;

	public static final float BOUND = 40f;
	public static final float WIDTH = 480f;
	public static final float HEIGHT = 800f;

	public static float STAGE = 1;
	public static AssetManager manager;
	public static final float factorx = 480.0f / Gdx.graphics.getWidth();
	public static final float factory = 800.0f / Gdx.graphics.getHeight();

	// 局数
	// 138,538;746,673;1156,200;1368,770;1756,712
	public static StageInfo[] stagelist = { new StageInfo(138, 538, 1, ""),
			new StageInfo(746, 673, 2, ""), new StageInfo(1156, 200, 3, ""),
			new StageInfo(1368, 770, 4, ""), new StageInfo(1756, 712, 5, ""), };
}
