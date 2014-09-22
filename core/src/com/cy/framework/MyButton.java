package com.cy.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MyButton {
	Rectangle button;
	boolean buttontouched;
	public Image buttonImage;
	public boolean ispressed = false;
	private Texture tex;
	
	public MyButton(float x, float y, float width, float height, String icon) {
		button = new Rectangle(x, y, width, height);
		tex = new Texture(Gdx.files.internal(icon));
		tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		buttonImage = new Image(tex);
		buttonImage.setBounds(button.x, button.y, button.width, button.height);
		buttonImage.setOrigin(button.width / 2, button.height / 2);
	}

	public MyButton(float x, float y, float width, float height, String icon,
			float degrees) {
		this(x, y, width, height, icon);
		buttonImage.setRotation(degrees);
	}

	public void update1f() {
		if (ispressed()) {
			ispressed = true;
			onpressed();
		} else {
			ispressed = false;
		}
	}

	/**
     * 
     */
	public void onpressed() {
		// TODO Auto-generated method stub

	}

	/**
	 * 检查此按钮是否被按下，如果为true，则被按下。false则没有按下。
	 */
	public boolean ispressed() {
		boolean rslt = false;
		if (Gdx.input.justTouched()) {
			float touchpointx = (int) (Gdx.input.getX() * GlobalVal.factorx);
			float touchpointy = 800 - (int) (Gdx.input.getY() * GlobalVal.factory);
			if (button.contains(touchpointx, touchpointy)) {
				rslt = true;
			}
			if (Gdx.input.isTouched(1)) {
				// 对应多触点
				touchpointx = (int) (Gdx.input.getX() * GlobalVal.factorx);
				touchpointy = 800 - (int) (Gdx.input.getY() * GlobalVal.factory);
				if (button.contains(touchpointx, touchpointy)) {
					rslt = true;
				}
			}
		}
		return rslt;
	}

	public void draw(SpriteBatch batch) {
		buttonImage.draw(batch, 1.0f);
	}

	public void draw(SpriteBatch batch, float alpha) {
		buttonImage.draw(batch, alpha);
	}

	public void debug(SpriteBatch batch) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		ShapeRenderer sRenderer = new ShapeRenderer();
		sRenderer.begin(ShapeType.Filled);
		sRenderer.setColor(0, 1, 0, 1f);
		sRenderer.rect(button.x, button.y, button.width, button.height);
		sRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	public void dispose() {
		tex.dispose();
	}

}
