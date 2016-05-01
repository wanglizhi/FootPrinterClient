package ui.plan;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class FavoriteButton {
	int x;
	int y;

	Image pic;
	BufferedImage[] pictures;
	int currentIndex;
	boolean isThrilling;

	CheckPlanPanel checkPlanPanel;

	public FavoriteButton(final CheckPlanPanel checkPlanPanel) {
		x = Constant.FAVOR_X;
		y = Constant.FAVOR_Y;

		pic = new ImageIcon("img/ÎÄ¼þ¼Ð.png").getImage();
		pictures = new BufferedImage[4];
		BufferedImage bi = new BufferedImage(Constant.FAVOR_LENGTH,
				Constant.FAVOR_WIDTH, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = bi.getGraphics();
		graphics.drawImage(pic, 0, 0, Constant.FAVOR_LENGTH,
				Constant.FAVOR_WIDTH, null);
		graphics.dispose();
		pictures[0] = bi;
		pictures[1] = rotateImage(bi, 20);
		pictures[2] = bi;
		pictures[3] = rotateImage(bi, -20);
		currentIndex = 0;

		this.checkPlanPanel = checkPlanPanel;

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					if (isThrilling) {
						if (currentIndex == 3) {
							currentIndex = 0;
						} else {
							currentIndex++;
						}

						checkPlanPanel.repaint();
					}

					try {
						Thread.sleep(60);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();

	}

	public void drawFavorButton(Graphics g) {
		g.drawImage(pictures[currentIndex], x, y, Constant.FAVOR_LENGTH,
				Constant.FAVOR_WIDTH, null);
	}

	public boolean isInButton(int ex, int ey) {
		return ex > x && ex < x + Constant.FAVOR_LENGTH && ey > y
				&& ey < y + Constant.FAVOR_WIDTH;
	}

	public void jump() {
		Thread t = new Thread(new jump());
		t.start();
	}

	class jump implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < 5; i++) {
				y -= (4 - i) * 3;
				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkPlanPanel.repaint();
			}
			for (int i = 0; i < 5; i++) {
				y += i * 3;
				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkPlanPanel.repaint();
			}
		}

	}

	public static BufferedImage rotateImage(final BufferedImage bufferedimage,
			final int degree) {
		int w = bufferedimage.getWidth();
		int h = bufferedimage.getHeight();
		int type = bufferedimage.getColorModel().getTransparency();
		BufferedImage img;
		Graphics2D graphics2d;
		(graphics2d = (img = new BufferedImage(w, h, type)).createGraphics())
				.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
		graphics2d.drawImage(bufferedimage, 0, 0, null);
		graphics2d.dispose();
		return img;
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
