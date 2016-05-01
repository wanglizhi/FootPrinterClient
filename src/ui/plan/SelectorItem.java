package ui.plan;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import vo.PlanItemVO;
import vo.PlanSelectorVO;

public class SelectorItem {
	private int x;
	private int y;
	private int index;

	private Image picture;
	private Image defaultPic;
	private int landmarkID;
	private int landmarkType;
	private String text;
	private String comment;

	private boolean isDrag;
	private int dragX;
	private int dragY;

	public SelectorItem(String pictureURL, String text, int index) {
		this.index = index;
		ImageIcon i = new ImageIcon(pictureURL);
		picture = i.getImage();
		defaultPic=new ImageIcon("img/计划/默认.jpg").getImage();
		this.text = text;

		this.x = Constant.FIRST_SPOT_X;
		this.y = Constant.FIRST_SPOT_Y
				+ index
				* (Constant.SPOT_ITEM_WIDTH + Constant.SPOT_TEXT_WIDTH + Constant.SPOT_GAP);
	}

	public SelectorItem(PlanSelectorVO selectorVO, int index) {
		this.index = index;
		picture = selectorVO.pic;
		defaultPic=new ImageIcon("img/计划/默认.jpg").getImage();
		this.text = selectorVO.name;
		this.landmarkID=selectorVO.landmarkID;
		this.landmarkType=selectorVO.landmarkType;

		this.x = Constant.FIRST_SPOT_X;
		this.y = Constant.FIRST_SPOT_Y
				+ index
				* (Constant.SPOT_ITEM_WIDTH + Constant.SPOT_TEXT_WIDTH + Constant.SPOT_GAP);
	}

	public SelectorItem(PlanItemVO planItemVO, int index) {
		this.index = index;
		picture = new ImageIcon(planItemVO.picURL).getImage();
		defaultPic=new ImageIcon("img/计划/默认.jpg").getImage();
		this.text = planItemVO.landmark;
		this.landmarkID=planItemVO.landmarkID;
		this.landmarkType=planItemVO.landmarkType;
		this.comment = planItemVO.comment;

		this.x = Constant.FIRST_SPOT_X;
		this.y = Constant.FIRST_SPOT_Y
				+ index
				* (Constant.SPOT_ITEM_WIDTH + Constant.SPOT_TEXT_WIDTH + Constant.SPOT_GAP);
	}

	public void drawSpotItem(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Color origin=g2.getColor();
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(4.0f));
		g2.drawLine(x, y, x + Constant.SPOT_ITEM_LENGTH, y);
		g2.drawLine(x, y, x, y + Constant.SPOT_ITEM_WIDTH);
		g2.drawLine(x, y + Constant.SPOT_ITEM_WIDTH, x + Constant.SPOT_ITEM_LENGTH, y + Constant.SPOT_ITEM_WIDTH);
		g2.drawLine(x + Constant.SPOT_ITEM_LENGTH, y, x + Constant.SPOT_ITEM_LENGTH, y + Constant.SPOT_ITEM_WIDTH);
		g2.setColor(origin);
		g.drawImage(defaultPic, x, y, Constant.SPOT_ITEM_LENGTH,
				Constant.SPOT_ITEM_WIDTH, null);
		g.drawImage(picture, x, y, Constant.SPOT_ITEM_LENGTH,
				Constant.SPOT_ITEM_WIDTH, null);
		g.drawString(text, x + Constant.SPOT_ITEM_LENGTH
				+ Constant.SPOT_TEXT_GAP_X, y + Constant.SPOT_TEXT_GAP_Y);
		if (comment != null) {
			g.drawString(comment, x + Constant.SPOT_ITEM_LENGTH
					+ Constant.SPOT_TEXT_GAP_X, y + Constant.SPOT_TEXT_GAP_Y
					* 2);
		}
		if (isDrag) {

			BufferedImage bufferedImage = new BufferedImage(
					Constant.SPOT_ITEM_LENGTH, Constant.SPOT_ITEM_WIDTH,
					BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
			g2D.drawImage(picture, 0, 0, null);

			int alpha = 120;
			for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage
					.getHeight(); j1++) {
				for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage
						.getWidth(); j2++) {
					int rgb = bufferedImage.getRGB(j2, j1);
					rgb = (alpha << 24) | (rgb & 0x00ffffff);
					bufferedImage.setRGB(j2, j1, rgb);
				}
			}
			// AlphaComposite yuanac = (AlphaComposite) g2D.getComposite();
			// AlphaComposite ac = AlphaComposite.getInstance(
			// AlphaComposite.SRC_OVER, 0.3f);
			// g2D.setComposite(ac);
			// g.drawImage(bufferedImage, 0, 0, null);
			g.drawImage(bufferedImage, dragX, dragY, Constant.SPOT_ITEM_LENGTH,
					Constant.SPOT_ITEM_WIDTH, null);
			// g2D.setComposite(yuanac);
			isDrag = false;
		}
	}

	public int isInSpot(int x, int y) {
		if (x >= this.x && x <= this.x + Constant.SPOT_ITEM_LENGTH
				&& y >= this.y && y <= this.y + Constant.SPOT_ITEM_WIDTH) {
			return index;
		} else {
			return -1;
		}
	}

	public void dragSpot(int x, int y) {
		isDrag = true;
		this.dragX = x - Constant.SPOT_ITEM_LENGTH / 2;
		this.dragY = y - Constant.SPOT_ITEM_WIDTH / 2;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isDrag() {
		return isDrag;
	}

	public void setDrag(boolean isDrag) {
		this.isDrag = isDrag;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Image getPicture() {
		return picture;
	}

	public void setPicture(Image picture) {
		this.picture = picture;
	}

	public int getLandmarkID() {
		return landmarkID;
	}

	public void setLandmarkID(int landmarkID) {
		this.landmarkID = landmarkID;
	}

	public int getLandmarkType() {
		return landmarkType;
	}

	public void setLandmarkType(int landmarkType) {
		this.landmarkType = landmarkType;
	}
}
