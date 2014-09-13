package com.cy.framework;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * ����ű� ���Ƹ��������еĵ��ˣ��ӵ�����Ч�����ܣ��Ի��� �л�SCREEN���л�����ű�
 */
public class MyWorldScript {
	/**
	 * �ű����е�frame��
	 */
	int elaspedframe = 0;
	GameWorld gWorld;
	XmlReader reader = new XmlReader();
	/**
	 * ���ڵ�, beginscriptʱ��ʼ��
	 */
	Element node;
	/**
	 * ��ǰԪ��
	 */
	Element currentElement;
	int currentno = 0;
	/**
	 * ��ǰԪ��
	 */
	int nodesum = 0;
	/**
	 * ��־
	 */
	boolean DIALOG;
	boolean WAIT;
	boolean WAITUNTIL;
	boolean UNTILCLEAR;
	boolean BORN;
	boolean NULL;
	public boolean isfinished=true;
	/**
	 * WAIT,UNTIL�ļ�ʱ��
	 */
	int counter = 0;
	// �õ�����Դ��·��
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
			// �ű�������ʲô����ִ�С�
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
				// �ű�ִ�����
				if (NULL) {
					break;
				}
			}

		}

	}

	/**
	 * ��ȡ��һ�ڵ㣬����Ѿ���β�ڵ㣬��NULL = true;
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
	 * ��ȡ��ǰ�ڵ� ����counter��ֵ ����ű����� WAIT �ȴ� DIALOG �Ի� UNTILCLEAR ֱ�����˱����� WAITUNTIL
	 * �ȵ��̶�ʱ�� BORN ��������
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
			gWorld.born(param, Float.parseFloat(px), Float.parseFloat(py),
					Float.parseFloat(direction));

		}
	}


	public void resetworld() {
	}

	/**
	 * readattribute ��ȡXML���ԣ�������Բ����ڣ��򷵻�"null"
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
