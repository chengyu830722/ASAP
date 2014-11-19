package com.cy.framework.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cy.framework.GlobalVal;
import com.cy.framework.MainGame;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
public class SelectStageScreen implements Screen, ICallBack {
	private MainGame game;
	private Stage stage;
	private Image bgimage;

	private Window dialog; // 对话框
	private Button btn_OK; // 对话框 确定按钮
	private Button btn_Cancel; // 对话框 确定按钮
	private Label lbl_Tilte; // 关卡介绍 标题
	private Image infoImage; // 填坑用

	InputMultiplexer multiplexer = new InputMultiplexer();
	
	//
	float speedx;
	float speedy;
	private GestureDetector detector;
	//计时器
	float timer;
	private Image testImage;

	public SelectStageScreen(MainGame mainGame) {
		this.game = mainGame;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		// 惯性
		if (!Gdx.input.isTouched()) {
			if ((Math.abs(speedx) > 0.001f) || (Math.abs(speedy) > 0.001f)) {
				moveCamera(speedx, speedy);
				speedx = speedx * 0.95f;
				speedy = speedy * 0.95f;
			}
		}
		// DELAY
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer++;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		stage = new Stage(new FitViewport(480, 800));
		bgimage = new Image(GlobalVal.manager.get("data/SelStageBG.png",
				Texture.class));
		// bgimage增加手势监听的功能。-- 移动camera会造成抖动。
//		bgimage.addListener(new ActorGestureListener() {
//			@Override
//			public void fling (InputEvent event, float velocityX, float velocityY, int button) {
//				speedx = velocityX / 100.0f;
//				speedy = velocityY / 100.0f;
//			}
//			public void pan (InputEvent event, float x, float y, float deltaX, float deltaY) {
//				moveCamera(deltaX, deltaY);
//			}
//		});
		
		stage.addActor(bgimage);
		Texture tex1 = GlobalVal.manager.get("data/SelStageBtn.png",
				Texture.class);
		Texture tex2 = GlobalVal.manager.get("data/SelStageBtn2.png",
				Texture.class);
		TextureRegion region1 = new TextureRegion(tex1);
		TextureRegion region2 = new TextureRegion(tex2);
		for (int i = 0; i < GlobalVal.stagelist.length; i++) {
			int x = GlobalVal.stagelist[i].x;
			int y = GlobalVal.stagelist[i].y;
			int no = GlobalVal.stagelist[i].stageno;
			SelStageButton temp = new SelStageButton(new TextureRegionDrawable(
					region1), new TextureRegionDrawable(region1),
					new TextureRegionDrawable(region2));
			temp.setStageInfo(x, y, no, this);
			stage.addActor(temp);
		}
		
//		testImage = new Image(tex2);
//		testImage.setPosition(100, 100);
//		stage.addActor(testImage);

//		detector = new GestureDetector(this);
//		multiplexer.addProcessor(detector); // 设置手势监听
		multiplexer.addProcessor(stage); // 设置点击监听
		Gdx.input.setInputProcessor(multiplexer);

	}

	@Override
	public void hide() {
		this.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();

	}


	private void moveCamera(float deltaX, float deltaY) {
		stage.getViewport().getCamera().position.x -= deltaX;
		stage.getViewport().getCamera().position.y += deltaY;
		if (stage.getViewport().getCamera().position.x < 240) {
			stage.getViewport().getCamera().position.x = 240;
		}
		if (stage.getViewport().getCamera().position.x > bgimage.getWidth() - 240) {
			stage.getViewport().getCamera().position.x = bgimage.getWidth() - 240;
		}
		if (stage.getViewport().getCamera().position.y < 400) {
			stage.getViewport().getCamera().position.y = 400;
		}
		if (stage.getViewport().getCamera().position.y > bgimage.getHeight() - 400) {
			stage.getViewport().getCamera().position.y = bgimage.getHeight() - 400;
		}
	}

	@Override
	// 实现选关方法回调
	public void doSelectStage(String StageNo) {
		BitmapFont bitmapFont = GlobalVal.manager.get("font/chn.fnt",
				BitmapFont.class);
		TextureRegion txr = new TextureRegion(GlobalVal.manager.get(
				"data/WindowBG.png", Texture.class));
		TextureRegionDrawable txrregion = new TextureRegionDrawable(txr);
		dialog = new Window("dialog", new Window.WindowStyle(bitmapFont,
				new Color(), txrregion));
		// Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		// dialog =new Window("你选择了第"+ StageNo+"关", new
		// WindowStyle().stageBackground());

		float width = 400;
		float height = 350;
		// todo:设置位置 √
		dialog.setWidth(width);
		dialog.setHeight(height);
		dialog.setKeepWithinStage(false);
		// 为了让Window保持居中
		Vector3 camerapos = stage.getViewport().getCamera().position;
		float x = camerapos.x - width / 2;
		float y = camerapos.y - height / 2;
		dialog.setPosition(x, y);

		// dialog.setModal(true);
		// 一个图片 一段文字
		lbl_Tilte = new Label("你选择了第" + StageNo + "关", new LabelStyle(
				bitmapFont, Color.RED));
		infoImage = new Image(new Texture(Gdx.files.internal("badlogic.jpg")));
		Texture texOK = GlobalVal.manager.get("data/OK.png", Texture.class);
		Texture texCancel = GlobalVal.manager.get("data/Cancel.png",
				Texture.class);
		TextureRegion regionOK = new TextureRegion(texOK);
		TextureRegion regioCancel = new TextureRegion(texCancel);
		btn_OK = new Button(new TextureRegionDrawable(regionOK));
		btn_Cancel = new Button(new TextureRegionDrawable(regioCancel));
		btn_OK.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(game.dialogscreen); // 开始游戏 对话框
				return false;
			}
		});
		btn_Cancel.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				dialog.remove(); // 干掉 这window
				multiplexer.addProcessor(detector); // 设置手势监听
				return false;
			}
		});
		//dialog 布局
		dialog.add(lbl_Tilte).expand().top().padLeft(50);
		dialog.row();
		dialog.add(infoImage).padLeft(60);
		dialog.row();
		dialog.add(btn_OK).padLeft(0); // 添加OK 按钮
		dialog.add(btn_Cancel).padLeft(0);// 添加Cancel按钮
		dialog.pack();
		//dialog 增加action
		dialog.setY(y+height/2);
		dialog.setScaleY(0);
		//action的持续时间
		float duration=0.2f;
		//可以增加差值算法，使ACTION更生动
		Interpolation alpha=Interpolation.bounceOut;
		dialog.addAction(parallel(scaleTo(1, 1, duration,alpha),moveTo(x, y, duration,alpha)));
		stage.addActor(dialog);
		multiplexer.removeProcessor(detector);
	}
}
