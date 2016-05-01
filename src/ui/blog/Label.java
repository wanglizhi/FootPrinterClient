package ui.blog;

import java.awt.Graphics;

import javax.swing.ImageIcon;

//”√¿¥œ‘ æÕº∆¨
public class Label {
	public int leftupX;
	public int leftupY;
	public int width;
	public int height;
	public ImageIcon img;
	public int oleftupX;
	public int oleftupY;
	public int owidth;
	public int oheight;

	public Label(int leftupX, int leftupY, int width, int height, ImageIcon img) {
		super();
		this.oleftupX = this.leftupX = leftupX;
		this.oleftupY = this.leftupY = leftupY;
		this.owidth = this.width = width;
		this.oheight = this.height = height;
		this.img = img;
	}

	public void draw(Graphics g) {
		g.drawImage(img.getImage(), leftupX, leftupY, width, height, null);
	}

	public void getBigger() {
		leftupX = oleftupX - 5;
		leftupY = oleftupY - 5;
		width = owidth + 10;
		height = oheight + 10;
	}

	public void returnNormal() {
		leftupX = oleftupX;
		leftupY = oleftupY;
		width = owidth;
		height = oheight;
	}
}
