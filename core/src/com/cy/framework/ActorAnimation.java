package com.cy.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorAnimation extends Actor {

	private static final int FRAME_COLS = 10;
	private static final int FRAME_ROWS = 4;

	private Animation animation; // 动画
	private Texture Sheet; // 动画图片
	private TextureRegion[] Frames; // 动画帧数组
	private TextureRegion currentFrame; // 当前帧
	float stateTime; //

	public ActorAnimation() {
		super();
        this.setWidth(70); // 设置高度
        this.setHeight(70); // 设置宽度
		Sheet = new Texture(Gdx.files.internal("data/Animation.jpg"));
		TextureRegion[][] tmp = TextureRegion.split(Sheet, Sheet.getWidth()
				/ FRAME_COLS, Sheet.getHeight() / FRAME_ROWS);
		Frames = new TextureRegion[FRAME_COLS];// 获取第1行的4帧

		for (int j = 0; j < FRAME_COLS; j++) {
			Frames[j] = tmp[0][j];
		}

		animation = new Animation(0.01f, Frames);
		stateTime = 0f;
		
		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		stateTime += Gdx.graphics.getDeltaTime(); 
		currentFrame = animation.getKeyFrame(stateTime, true); // 获取当前关键帧
		batch.draw(currentFrame, this.getX(), this.getY(), this.getWidth(),
				this.getHeight());// 绘制
	}
}
