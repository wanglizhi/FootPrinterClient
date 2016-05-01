package ui.plan;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class ControlButton {
	private int x;
	private int y;
	private int width;
	private int length;

	private ArrayList<Image> pics;
	private Image currentPic;

	public ControlButton(int x, int y, String url) {
		this(x, y, Constant.CTR_BUTTON_WIDTH, Constant.CTR_BUTTON_LENGTH, url);
	}

	public ControlButton(int x, int y, int width, int length, String url) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.length = length;
		pics = new ArrayList<Image>();
		Image pic = new ImageIcon(url).getImage();
		pics.add(pic);

		currentPic = pics.get(0);
	}

	public void drawButton(Graphics g) {
		g.drawImage(currentPic, x, y, length, width, null);
	}

	public boolean isEntered(int x, int y) {
		return x > this.x && x < this.x + this.length && y > this.y
				&& y < this.y + this.width;
	}

	public void click() {
		this.x += Constant.CTR_BUTTON_SHRINK;
		this.y += Constant.CTR_BUTTON_SHRINK;
		this.width -= 2 * Constant.CTR_BUTTON_SHRINK;
		this.length -= 2 * Constant.CTR_BUTTON_SHRINK;
	}

	public void release() {
		this.x -= Constant.CTR_BUTTON_SHRINK;
		this.y -= Constant.CTR_BUTTON_SHRINK;
		this.width += 2 * Constant.CTR_BUTTON_SHRINK;
		this.length += 2 * Constant.CTR_BUTTON_SHRINK;
	}

	public void addAPic(String url) {
		Image pic = new ImageIcon(url).getImage();
		pics.add(pic);
	}

	public void changePic(int picIndex) {
		currentPic = pics.get(picIndex);
	}
}
