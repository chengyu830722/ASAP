package com.cy.framework;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.cy.tool.MyMath;

/**
 * 显示对话,使用WORLD中的字体
 */
public class MyDialogBox {
	int elapsedframe;
	BitmapFont bitmapFont;
	XmlReader reader = new XmlReader();
	/**
	 * 根节点, beginscript时初始化
	 */
	Element node;
	// 当前元素
	Element currentElement;

	String cutin;
	String name;
	String data;
	String music;
	String picture;
	NinePatch borderNinePatch;
	/**
	 * 对话框背景
	 */
	Texture textbackgroundTexture;
	/**
	 * 对话背景
	 */
	Texture backgroundTexture;
	Texture cutinTexture;
	/**
	 * 脚本中对话个数, beginscript时初始化
	 */
	int pagesnum = 0;
	// 当前显示的脚本页数
	int currentpage = 0;
	// 已经读过的脚本页数
	int readpage = 0;
	// 是否激活
	public boolean isalive = false;

	// skip 略过对话
	// back 显示上一个对话
	MyButton skip;
	MyButton back;

	// 防止点击过快
	int CD = 10;
	int cdtimer;
	// 第一次加载动画效果
	int animedur = 30;
	int animetimer = 0;
	// 播放音乐所在PAGE
	int musicpage = -1;
	String skipmusic = null;

	// 定义dialogbox中用到的资源的路径
	String musicpathString = "music/";
	String picpathString = "dialogbox/pic/";
	String cutinpathString = "dialogbox/cutin/";
	String scriptpathString = "dialogbox/dialogscript/";

	MyDialogBox() {
		reader = new XmlReader();
		// 加载字体
		bitmapFont = GlobalVal.manager.get("font/chn.fnt", BitmapFont.class);
		// 加载背景，对话框边界
		textbackgroundTexture = new Texture(
				Gdx.files.internal("dialogbox/dialogboxbackground.png"));
		borderNinePatch = new NinePatch(new Texture(
				Gdx.files.internal("dialogbox/border.png")), 9, 9, 9, 9);
		// SKIP,BACK按钮
//		skip = new MyButton(0, 360, 100, 100, "dialogbox/SKIP.png", 90);
//		back = new MyButton(0, 20, 100, 100, "dialogbox/BACK.png", 90);
		skip = new MyButton(20, 0, 100, 100, "dialogbox/SKIP.png", 0);
		back = new MyButton(360, 0, 100, 100, "dialogbox/BACK.png", 0);
	}

	public void beginscript(String scriptname) {
		elapsedframe = 0;
		bitmapFont.setColor(new Color(1, 1, 1, 1));
		bitmapFont.setScale(0.8f);
		animetimer = animedur;
		isalive = true;
		currentpage = 0;
		readpage = 0;
		musicpage = -1;
		cutin = "null";
		data = "null";
		music = "null";
		name = "null";
		picture = "null";
		cutinTexture = null;
		backgroundTexture = null;

		FileHandle aFileHandle = Gdx.files.internal(scriptpathString
				+ scriptname);
		try {
			node = reader.parse(aFileHandle);
			pagesnum = node.getChildCount();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 读取skipmusic
		currentElement = node.getChild(0);
		try {
			skipmusic = readattribute("skipmusic");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		readpage();
	}

	private void readpage() {
		currentElement = node.getChild(currentpage);
		try {
			cutin = readattribute("cutin");
			data = readattribute("data");
			music = readattribute("music");
			name = readattribute("name");
			picture = readattribute("pic");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 读取背景
		if (!picture.equals("null")) {
			if (picture.equals("stop")) {
				backgroundTexture = null;
			} else {
				backgroundTexture = new Texture(
						Gdx.files.internal(picpathString + picture));
			}
		}
		// 读取CUTIN
		if (!cutin.equals("null")) {
			if (cutin.equals("stop")) {
				cutinTexture = null;
			} else {
				cutinTexture = new Texture(Gdx.files.internal(cutinpathString
						+ cutin));
			}
		}
		// 因为可能back回翻，保证已经播放过的音乐不再次播放
//		if ((currentpage >= readpage) && (currentpage != musicpage)) {
//			playmusic(music);
//		}
	}

	public void update1f() {
		elapsedframe++;
		if (Gdx.input.justTouched() && (animetimer == 0) && (cdtimer == 0)) {
			skip.update1f();
			back.update1f();
			// 略过
			if (skip.ispressed) {
//				playmusic(skipmusic);
				finish();
			}
			// 回退
			else if (back.ispressed) {
				currentpage = Math.max(currentpage - 1, 0);
				readpage();
			}
			// 下一页
			else {
				if (currentpage == pagesnum - 1)
					finish();
				if (currentpage < readpage) {
					currentpage++;
				} else {
					readpage = Math.min(readpage + 1, pagesnum - 1);
					currentpage = readpage;
				}
				readpage();
			}
			cdtimer = CD;
		}
		cdtimer = Math.max(0, cdtimer - 1);
		animetimer = Math.max(0, animetimer - 1);
	}

	/**
	 * pictureTexture null 保持原图像不变 stop 不再绘制背景 name 用新贴图替换pictureTexture
	 */
	public void draw(SpriteBatch batch) {
		//batch.setProjectionMatrix(gWorld.camera.combined);
		// 绘制背景图
		if (backgroundTexture != null) {
			batch.draw(backgroundTexture, 0, 160, 480, 640);
		}
		// 绘制CUTIN
		if (cutinTexture != null) {
			// 淡入效果
			if (animetimer != 0) {
				batch.setColor(1, 1, 1, 1 - (animedur - animetimer) / animedur);
				batch.draw(cutinTexture, 0 - animetimer, 160);
				batch.setColor(1, 1, 1, 1);
			} else {
				batch.draw(cutinTexture, 0, 160);
			}

		}
		// 绘制对话框
		batch.draw(textbackgroundTexture, 0, 0, 480, 160);
		borderNinePatch.draw(batch, 0, 0, 480, 160);
		// 绘制文字
		if (currentpage < readpage) {
			bitmapFont.setColor(new Color(0, 1, 0, 0.5f));
		}
		if (name.equals("null")) {
			bitmapFont.drawWrapped(batch, data, 20, 140, 440);
		} else {
			bitmapFont.drawWrapped(batch, name + "\n" + data, 20, 140, 440);
		}

		//batch.setProjectionMatrix(gWorld.camera.combined);
		// 绘制按钮
		float alpha = MyMath.linearinterval(elapsedframe, 150, 0.2f, 1.0f,
				MyMath.PINGPONG);
		skip.draw(batch, alpha);
		back.draw(batch, alpha);
		bitmapFont.setColor(new Color(1, 1, 1, 1));
	}

	/**
	 * @music "stop" 停止播放音乐 "null" 什么都不做 其他为音乐路径，进行播放
	 */
//	public void playmusic(String music) {
//		if (!music.equals("null")) {
//			if (music.equals("stop")) {
//				if (gWorld.mainGame.Music != null) {
//					gWorld.mainGame.Music.dispose();
//				}
//			} else {
//				if (gWorld.mainGame.Music != null) {
//					gWorld.mainGame.Music.dispose();
//				}
//				musicpage = currentpage;
//				gWorld.mainGame.Music = Gdx.audio.newMusic(Gdx.files
//						.internal(musicpathString + music));
//				gWorld.mainGame.Music.setVolume(gWorld.mainGame.musicvolume);
//				gWorld.mainGame.Music.setLooping(true);
//				gWorld.mainGame.Music.play();
//			}
//		}
//
//	}

	/**
	 * readattribute 读取XML属性，如果属性不存在，则返回"null"
	 */
	String readattribute(String name) throws UnsupportedEncodingException {
		String temp;
		temp = currentElement.getAttribute(name, "null");
		if (!temp.equals("null")) {
//			byte[] c = null;
//			try {
//				c = temp.getBytes("ISO8859-1");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//			temp = new String(c, "utf-8");
			if(temp.equals(new String(temp.getBytes("iso8859-1"), "iso8859-1")))
			{
				temp=new String(temp.getBytes("iso8859-1"),"utf-8");
			}
		}
		return temp;
	}

	//
	void finish() {
		isalive = false;
		if (backgroundTexture != null) {
			backgroundTexture.dispose();
			backgroundTexture = null;
		}

		if (cutinTexture != null) {
			cutinTexture.dispose();
			cutinTexture = null;
		}
		skip.dispose();
		back.dispose();
	}
}
