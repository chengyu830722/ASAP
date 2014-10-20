package com.cy.framework;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * 世界脚本 控制更新世界中的敌人，子弹，特效，技能，对话框 切换SCREEN，切换世界脚本
 */
public class MyWorldScript {
	/**
	 * 脚本运行的frame数
	 */
	int elaspedframe = 0;
	GameWorld gWorld;
	XmlReader reader = new XmlReader();
	/**
	 * 根节点, beginscript时初始化
	 */
	Element node;
	/**
	 * 当前元素
	 */
	Element currentElement;
	int currentno = 0;
	/**
	 * 当前元素
	 */
	int nodesum = 0;
	/**
	 * 标志
	 */
	boolean DIALOG;
	boolean WAIT;
	boolean WAITUNTIL;
	boolean UNTILCLEAR;
	boolean BORN;
	boolean NULL;
	public boolean isfinished=true;
	/**
	 * WAIT,UNTIL的计时器
	 */
	int counter = 0;
	// 用到的资源的路径
	String scriptpathString = "worldscript/";

	public MyWorldScript(GameWorld gWorld) {
		this.gWorld = gWorld;
	}

	public void beginscript(String scriptname) {
		isfinished = false;
		NULL = DIALOG = BORN = UNTILCLEAR = WAIT = false;
		elaspedframe = 0;
		counter = 0;
		FileHandle aFileHandle = Gdx.files.internal(scriptpathString
				+ scriptname);
		try {
			node = reader.parse(aFileHandle);
			nodesum = node.getChildCount();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentElement = node.getChild(0);
		try {
			readnode();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update1f() {
		elaspedframe++;
		if(isfinished)
		{
			return;
		}
		if (NULL) {
			// 脚本结束后，什么都不执行。
			// worldupdate();
		} else {
			while (true) {
				if (DIALOG) {
//					gWorld.dialogBox.update1f();
//					if (!gWorld.dialogBox.isalive) {
//						readnextnode();
//					}
					break;
				}
				if (BORN) {
					readnextnode();
				}
				if (UNTILCLEAR) {
//					worldupdate();
//					if (gWorld.isclear()) {
//						readnextnode();
//					}
					break;
				}
				if (WAIT) {
					counter--;
					if (counter == 0) {
						readnextnode();
					}
					break;
				}
				if (WAITUNTIL) {
					if (elaspedframe >= counter) {
						readnextnode();
					}
					break;
				}
				// 脚本执行完毕
				if (NULL) {
					break;
				}
			}

		}

	}

	/**
	 * 读取下一节点，如果已经是尾节点，则NULL = true;
	 */
	private void readnextnode() {
		if (currentno == nodesum - 1) {
			DIALOG = BORN = UNTILCLEAR = WAIT = WAITUNTIL = false;
			NULL = true;
			isfinished = true;
		} else {
			currentno++;
			currentElement = node.getChild(currentno);
			try {
				readnode();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				DIALOG = BORN = UNTILCLEAR = WAIT = WAITUNTIL = false;
				NULL = true;
				isfinished = true;
			}
		}
	}

	/**
	 * 读取当前节点 重置counter的值 世界脚本命令 WAIT 等待 DIALOG 对话 UNTILCLEAR 直到敌人被消灭 WAITUNTIL
	 * 等到固定时间 BORN 产生敌人
	 */
	private void readnode() throws UnsupportedEncodingException {
		counter = 0;
		NULL = DIALOG = BORN = UNTILCLEAR = WAIT = WAITUNTIL = false;
		String fun = readattribute("fun");
		String param = null;
		if (fun.equals("WAIT")) {
			WAIT = true;
			param = readattribute("param");
			counter = Integer.parseInt(param);
		}
		if (fun.equals("DIALOG")) {
			DIALOG = true;
			param = readattribute("param");
		}
		if (fun.equals("UNTILCLEAR")) {
			UNTILCLEAR = true;
		}
		if (fun.equals("WAITUNTIL")) {
			WAITUNTIL = true;
			param = readattribute("param");
			counter = Integer.parseInt(param);
		}
		if (fun.equals("BORN")) {
			BORN = true;
			param = readattribute("param");
			String px = readattribute("px");
			String py = readattribute("py");
			String direction = readattribute("direction");
			gWorld.bornEnemy(param, Float.parseFloat(px), Float.parseFloat(py),
					Float.parseFloat(direction));

		}
	}


	public void resetworld() {
	}

	/**
	 * readattribute 读取XML属性，如果属性不存在，则返回"null"
	 */
	String readattribute(String name) throws UnsupportedEncodingException {
		String temp;
		temp = currentElement.getAttribute(name, "null");
		if (!temp.equals("null")) {
			byte[] c = null;
			try {
				c = temp.getBytes("ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			temp = new String(c, "utf-8");
		}
		return temp;
	}
}
