package ui.blog;

import java.awt.Graphics;

public abstract class Item {
	public int width;
	public int toLeft;// 距左边像素数,创建后确定一个值，除非后来的插入删除
	public int height;

	Item(String code, int cursor) {
		width = 0;
		toLeft = cursor;
		height = Constant.SHOWHEIGHT;
	}

	public abstract void draw(int left, Graphics g);

	public int getWidth() {
		return width;
	}
	@Override
	public abstract String toString() ;
	public abstract void click() ;
}
