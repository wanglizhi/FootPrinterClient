package ui.plan;

import java.awt.Color;
import java.awt.Graphics;

public class PlanBar {
	private int startTime; // minute
	private int endTime;
	private int partNum;
	private int index;

	private int x;
	private int y;

	private PlanBarItem Items[];
	private PlanPanel planPanel;
	private CheckPlanPanel checkPlanPanel;

	public PlanBar(int startTime, int endTime, int partNum, PlanPanel p) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.partNum = partNum;
		this.index = startTime / Constant.EACH_BAR_TIME;
		this.x = Constant.FIRST_BAR_X;
		this.y = index * (Constant.BAR_WIDTH + Constant.BAR_GAP)
				+ Constant.FIRST_BAR_Y;

		this.Items = new PlanBarItem[partNum];
		int eachItemTime = (endTime - startTime) / partNum;
		this.planPanel = p;
		for (int i = 0; i < partNum; i++) {
			Items[i] = new PlanBarItem(startTime + i * eachItemTime, startTime
					+ (i + 1) * eachItemTime, index, i, p);
		}
	}

	public PlanBar(int startTime, int endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.index = startTime / Constant.EACH_BAR_TIME;
		this.x = Constant.FIRST_BAR_X;
		this.y = index * (Constant.BAR_WIDTH + Constant.BAR_GAP)
				+ Constant.FIRST_BAR_Y;

		int maxPart = Constant.EACH_BAR_TIME / 60;
		this.Items = new PlanBarItem[maxPart];
		this.partNum = 0;
	}

	public void addAnItem(int index, PlanBarItem pbl) {
		Items[index] = pbl;
		partNum++;
	}

	public PlanBarItem getItemByIndex(int index) {
		return Items[index];
	}

	public void drawPlanBar(Graphics g) {
		String start = Constant.toNormalTime(startTime);
		g.setColor(Color.GRAY);
		g.drawString(start, x - Constant.TIME_LENGTH / 2, y
				- Constant.TIME_WIDTH);
		for (int i = 0; i < partNum; i++) {
			Items[i].drawPlanBarItem(g);
		}

	}

	public void proceedCombine(int index) {
		partNum--;
		for (int i = index; i < partNum; i++) {
			Items[i] = Items[i + 1];
			Items[i].setIndex(Items[i].getIndex() - 1);
		}
	}

	public int LocateGap(int x, int y) {
		if (y > this.y && y < this.y + Constant.BAR_WIDTH) {
			int result = 0;
			for (int i = 0; i < partNum; i++) {
				result = Items[i].LocateGap(x);
				if (result > 0)
					return result;
			}
		}
		return 0;
	}

	public int LocateItem(int x, int y) {
		if (y > this.y && y < this.y + Constant.BAR_WIDTH) {
			int result = -1;
			for (int i = 0; i < partNum; i++) {
				result = Items[i].LocateItem(x);
				if (result > -1)
					return result;
			}
		}
		return -1;
	}

	public int getPartNum() {
		return partNum;
	}

	public void setPartNum(int partNum) {
		this.partNum = partNum;
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

}
