package com.cy.VisualEffect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cy.framework.GameWorld;

public abstract class VisualEffect {
	public GameWorld gameWorld;
	public boolean alive=true;
	public abstract void render1f(SpriteBatch batch);
	public abstract void update1f();
	public boolean getAlive() {
		return alive;
	}
}
