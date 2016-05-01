package ui.plan;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PlanBarItem {
	private int planItemID;
	private int startTime;
	private int endTime;
	private boolean isUsed;
	private int length;
	private int bar_width;
	private int landmarkID;
	private int landmarkType;
	private String landMark;
	private Color background;
	private Color usedColor;

	private int x;
	private int y;
	private int barIndex;
	private int index;

	private boolean isDrawOutline;
	private boolean isDrawLeftOutline;
	private boolean isDrawRightOutline;
	private boolean isMoveIn;
	private boolean isShowComment;

	private CommentView commentView;
	private JPanel fatherPanel;

	public PlanBarItem(int startTime, int endTime, int barIndex, int index,
			JPanel p) {
		this.startTime = startTime;
		this.endTime = endTime;

		background =
		// new Color(146, 249, 255);
		new Color(147, 210, 255);
		usedColor =
		// new Color(0, 192, 204);
		new Color(1, 119, 204);
		this.barIndex = barIndex;
		int barStartTime = barIndex * Constant.EACH_BAR_TIME;
		this.x = Constant.FIRST_BAR_X + Constant.UNIT_LENGTH
				* (startTime - barStartTime);
		this.y = barIndex * (bar_width + Constant.BAR_GAP)
				+ Constant.FIRST_BAR_Y;
		this.index = index;
		this.bar_width = Constant.BAR_WIDTH;
		updateLength();
		updateLocation();
		commentView = new CommentView(p, this);
		this.fatherPanel = p;
	}

	// 用来给planPanel的
	public PlanBarItem(int startTime, int endTime, int barIndex, int index,
			PlanPanel p, boolean isUsed, String landMark, String comment) {
		this(startTime, endTime, barIndex, index, p);
		this.isUsed = isUsed;
		this.landMark = landMark;

		this.setComment(comment);
	}

	// 用来给planPanel加载过去plan的
	public PlanBarItem(int planItemID, int startTime, int endTime,
			int barIndex, int index, PlanPanel p, boolean isUsed,
			int landmarkID, int landmarkType, String landMark, String comment) {
		this(startTime, endTime, barIndex, index, p);
		this.planItemID = planItemID;
		this.isUsed = isUsed;
		this.landmarkID = landmarkID;
		this.landmarkType = landmarkType;
		this.landMark = landMark;

		this.setComment(comment);
	}

	// 用来给checkpanel的
	public PlanBarItem(int planItemID, int startTime, int endTime,
			int barIndex, int index, CheckPlanPanel c, boolean isUsed,
			int landmarkID, int landmarkType, String landMark, String comment) {
		this(startTime, endTime, barIndex, index, c);
		this.planItemID = planItemID;
		this.isUsed = isUsed;
		this.landmarkID = landmarkID;
		this.landmarkType = landmarkType;
		this.landMark = landMark;

		this.setComment(comment);
	}

	public void drawPlanBarItem(Graphics g) {
		if (fatherPanel instanceof PlanPanel
				&& ((PlanPanel) fatherPanel).isResizeTime()) {
			updateLength();
			updateLocation();
		}
		if (!isUsed) {
			g.setColor(background);
		} else {
			g.setColor(usedColor);
		}
		g.fillRect(x, y, length, bar_width);
		String end = Constant.toNormalTime(endTime);
		g.setColor(Color.GRAY);
		g.drawString(end, x + length + Constant.ITEM_GAP - Constant.TIME_LENGTH
				/ 2, y - Constant.TIME_WIDTH);

		if (isUsed) {

			Font f = g.getFont();
			FontMetrics fm = g.getFontMetrics();
			if (landMark != null) {
				int strWidth = fm.stringWidth(landMark);
				int width = length;
				int high = bar_width;
				g.setColor(Color.LIGHT_GRAY);
				g.drawString(landMark, this.x + (width - strWidth) / 2, this.y
						+ (high - f.getSize()) / 2 + f.getSize());
				g.setColor(Color.GRAY);
			}
		}

		if (isDrawOutline) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.GRAY);
			g2.setStroke(new BasicStroke(4.0f));
			g2.drawLine(x, y, x + length, y);
			g2.drawLine(x, y, x, y + bar_width);
			g2.drawLine(x, y + bar_width, x + length, y + bar_width);
			g2.drawLine(x + length, y, x + length, y + bar_width);
			isDrawOutline = false;
			g2.setColor(Color.black);
		}

		if (isDrawLeftOutline) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.GRAY);
			g2.setStroke(new BasicStroke(4.0f));
			g2.drawLine(x, y, x + length + Constant.ITEM_GAP, y);
			g2.drawLine(x, y, x, y + bar_width);
			g2.drawLine(x, y + bar_width, x + length + Constant.ITEM_GAP, y
					+ bar_width);
			isDrawLeftOutline = false;
			g2.setColor(Color.black);
		}

		if (isDrawRightOutline) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.GRAY);
			g2.setStroke(new BasicStroke(4.0f));
			g2.drawLine(x, y, x + length, y);
			g2.drawLine(x, y + bar_width, x + length, y + bar_width);
			g2.drawLine(x + length, y, x + length, y + bar_width);
			isDrawRightOutline = false;
			g2.setColor(Color.black);
		}

		if (isShowComment) {
			commentView.drawComment(g);
		}
	}

	public int check() {
		if (startTime >= endTime) {
			return index;
		} else
			return -1;
	}

	public void selected(String landmark, int landmarkID, int landmarkType) {
		this.landMark = landmark;
		this.landmarkID = landmarkID;
		this.landmarkType = landmarkType;
		this.isUsed = true;
	}

	public void selected(String landmark, int landmarkID, int landmarkType,
			String comment) {
		this.landMark = landmark;
		this.landmarkID = landmarkID;
		this.landmarkType = landmarkType;
		this.setComment(comment);
		this.isUsed = true;
	}

	public void swap(PlanBarItem origin) {
		int swapLandMarkID = origin.getLandmarkID();
		int swapLandMarkType = origin.getLandmarkType();
		String swapLandMark = origin.getLandMark();
		String swapComment = origin.getComment();
		boolean swapUsed = origin.isUsed();

		origin.setLandmarkID(landmarkID);
		origin.setLandmarkType(landmarkType);
		origin.setLandMark(landMark);
		origin.setComment(commentView.getComment());
		origin.setUsed(isUsed);

		this.setLandmarkID(swapLandMarkID);
		this.setLandmarkType(swapLandMarkType);
		this.setLandMark(swapLandMark);
		this.setComment(swapComment);
		this.setUsed(swapUsed);
	}

	public void setComment(String comment) {
		// TODO Auto-generated method stub
		commentView.setComment(comment);
	}

	public String getComment() {
		// TODO Auto-generated method stub
		return commentView.getComment();
	}

	public void updateLength() {
		this.length = (Constant.UNIT_LENGTH) * (endTime - startTime);

		this.length = length - Constant.ITEM_GAP;

		if (isShowComment) {
			commentView.setLength(length);
			commentView.setRealLength(length);
			commentView.setCommentAreaLocation(x, y, length);
		}
	}

	public void updateLocation() {
		int barStartTime = barIndex * Constant.EACH_BAR_TIME;
		this.x = Constant.FIRST_BAR_X + Constant.UNIT_LENGTH
				* (startTime - barStartTime);
		this.y = barIndex * (bar_width + Constant.BAR_GAP)
				+ Constant.FIRST_BAR_Y;

		if (isShowComment) {
			commentView.setX(x);
			commentView.setY(y);
			commentView.setCommentAreaLocation(x, y, length);
		}
	}

	public int LocateGap(int x) {
		if (x >= this.x + length && x <= this.x + length + Constant.ITEM_GAP)
			return index + 1;
		else
			return 0;
	}

	public int LocateItem(int x) {
		if (x > this.x && x < this.x + length)
			return index;
		else
			return -1;
	}

	public void moveInItem() {
		this.isMoveIn = true;

	}

	public void outItem() {
		this.isMoveIn = false;

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isDrawOutline() {
		return isDrawOutline;
	}

	public void setDrawOutline(boolean isDrawOutline) {
		this.isDrawOutline = isDrawOutline;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public boolean isDrawLeftOutline() {
		return isDrawLeftOutline;
	}

	public void setDrawLeftOutline(boolean isDrawLeftOutline) {
		this.isDrawLeftOutline = isDrawLeftOutline;
	}

	public boolean isDrawRightOutline() {
		return isDrawRightOutline;
	}

	public void setDrawRightOutline(boolean isDrawRightOutline) {
		this.isDrawRightOutline = isDrawRightOutline;
	}

	public void proceedClick() {
		// TODO Auto-generated method stub
		if (!isShowComment) {
			setShowComment(true);
			commentView.showView(this.x, this.y, this.length);
		} else {
			commentView.concealView();
			// isShowComment = false;
		}
		System.out.println(isShowComment);
	}

	public boolean isShowComment() {
		return isShowComment;
	}

	public void setShowComment(boolean isShowComment) {
		this.isShowComment = isShowComment;
		commentView.setCommentAreaVisible(isShowComment);
	}

	public CommentView getCommentView() {
		return commentView;
	}

	public void setCommentView(CommentView commentView) {
		this.commentView = commentView;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public int getBarIndex() {
		return barIndex;
	}

	public void setBarIndex(int barIndex) {
		this.barIndex = barIndex;
	}

	public int getPlanItemID() {
		return planItemID;
	}

	public void setPlanItemID(int planItemID) {
		this.planItemID = planItemID;
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
