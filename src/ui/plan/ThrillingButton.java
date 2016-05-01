package ui.plan;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ThrillingButton {
	int x;
	int y;
	int length;
	int width;
	String name;

	Image[] pic;
	int imageNum;
	int currentIndex;
	boolean isThrilling;

	JPanel fatherPanel;

	public ThrillingButton(int x, int y, int length, int width, String name,
			JPanel father) {
		this.x = x;
		this.y = y;
		this.length = length;
		this.width = width;
		this.name = name;
		this.fatherPanel = father;

		imageNum = 2;
		pic = new Image[imageNum];
		for (int i = 0; i < imageNum; i++) {
			pic[i] = new ImageIcon("img/¼Æ»®/" + name + (i + 1) + ".png")
					.getImage();
		}

		currentIndex = 0;

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					if (isThrilling) {
						if (currentIndex == imageNum - 1) {
							currentIndex = 0;
						} else {
							currentIndex++;
						}
						fatherPanel.repaint();
					} else {
						currentIndex = 0;
					}
					
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		t.start();
	}

	public void drawButton(Graphics g) {
		g.drawImage(pic[currentIndex], x, y, length, width, null);
	}

	public boolean isInButton(int ex, int ey) {
		return ex > x && ex < x + length && ey > y && ey < y + width;
	}
	
	public boolean isThrilling() {
		return isThrilling;
	}

	public void setThrilling(boolean isThrilling) {
		this.isThrilling = isThrilling;
		if (!isThrilling) {
			this.currentIndex = 0;
		}
	}

}
