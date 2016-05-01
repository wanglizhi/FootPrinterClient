package ui.plan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import vo.PlanEntertainmentVO;
import vo.PlanHotelVO;
import vo.PlanItemVO;
import vo.PlanRestaurantVO;
import vo.PlanSelectorVO;
import vo.PlanShoppingMallVO;
import vo.PlanSpotVO;

public class Selector {

	private int x;
	private int y;

	private SelectorHeaderLabel labels[];
	private PlanPanel planPanel;

	private SelectorItem selectors[];
	private int num;
	private int realNum;

	private Image upCtrImage;
	private Image downCtrImage;

	private int CtrCount;
	private int currentHeaderIndex;

	private boolean isShowContent;

	ArrayList<PlanSpotVO> spots;
	ArrayList<PlanHotelVO> hotels;
	ArrayList<PlanRestaurantVO> restaurants;
	ArrayList<PlanEntertainmentVO> entertainments;
	ArrayList<PlanShoppingMallVO> shoppingMalls;

	ArrayList<PlanItemVO> favourites;

	public Selector(PlanPanel planPanel, ArrayList<PlanSpotVO> s,
			ArrayList<PlanHotelVO> h, ArrayList<PlanRestaurantVO> r,
			ArrayList<PlanEntertainmentVO> e, ArrayList<PlanShoppingMallVO> sh,
			ArrayList<PlanItemVO> f) {
		this.x = Constant.SELECTORHEADER_X;
		this.y = Constant.SELECTORHEADER_Y;

		labels = new SelectorHeaderLabel[6];
		labels[0] = new SelectorHeaderLabel("景点", 0, this);
		labels[1] = new SelectorHeaderLabel("酒店", 1, this);
		labels[2] = new SelectorHeaderLabel("饭店", 2, this);
		labels[3] = new SelectorHeaderLabel("娱乐", 3, this);
		labels[4] = new SelectorHeaderLabel("商场", 4, this);
		labels[5] = new SelectorHeaderLabel("收藏", 5, this);

		this.planPanel = planPanel;
		for (int i = 0; i < 6; i++) {
			planPanel.add(labels[i]);
			labels[i].setForeground(Color.GRAY);
		}

		upCtrImage = new ImageIcon("img/计划/s.png").getImage();
		downCtrImage = new ImageIcon("img/计划/x.png").getImage();

		update(s, h, r, e, sh, f);
	}

	public void update(ArrayList<PlanSpotVO> s, ArrayList<PlanHotelVO> h,
			ArrayList<PlanRestaurantVO> r, ArrayList<PlanEntertainmentVO> e,
			ArrayList<PlanShoppingMallVO> sh, ArrayList<PlanItemVO> f) {
		CtrCount = 0;
		System.out.println("updating selector.....");
		this.spots = s;
		this.hotels = h;
		this.entertainments = e;
		this.restaurants = r;
		this.shoppingMalls = sh;
		this.favourites = f;
		currentHeaderIndex = 0;
		updateSelectors(spots);
	}

	private boolean updateSelectors(ArrayList<? extends PlanSelectorVO> vos) {
		this.num = vos.size();
		this.realNum = vos.size();
		if (this.num > 3) {
			this.num = 3;
		}
		selectors = new SelectorItem[num];
		for (int i = 0; i < num; i++) {
			selectors[i] = new SelectorItem(vos.get(i + CtrCount), i);
		}
		return true;
	}

	private boolean updateSelectors() {
		this.num = favourites.size();
		this.realNum = favourites.size();
		if (this.num > 3) {
			this.num = 3;
		}
		selectors = new SelectorItem[num];
		for (int i = 0; i < num; i++) {
			selectors[i] = new SelectorItem(favourites.get(i + CtrCount), i);
		}
		return true;
	}

	public void drawSelector(Graphics g) {
		if (isShowContent) {
			for (int i = 0; i < num; i++) {
				selectors[i].drawSpotItem(g);
			}
			g.drawImage(upCtrImage, Constant.SELECTOR_CTR_FIRST_X,
					Constant.SELECTOR_CTR_FIRST_Y,
					Constant.SELECTOR_CTR_LENGTH, Constant.SELECTOR_CTR_WIDTH,
					null);
			g.drawImage(downCtrImage, Constant.SELECTOR_CTR_FIRST_X,
					Constant.SELECTOR_CTR_FIRST_Y + Constant.SELECTOR_CTR_WIDTH
							+ Constant.SELECTOR_GAP,
					Constant.SELECTOR_CTR_LENGTH, Constant.SELECTOR_CTR_WIDTH,
					null);
		} else {
			g.drawString("正在载入该城市的景点哦=。=", Constant.FIRST_SPOT_X,
					Constant.FIRST_SPOT_Y+100);
		}

	}

	public int inWhichSelector(int ex, int ey) {
		int result = -1;
		for (int j = 0; j < num; j++) {
			result = selectors[j].isInSpot(ex, ey);
			if (result > -1)
				break;
		}
		return result;
	}

	public int inWhichCtr(int ex, int ey) {
		int result = -1;
		if (ex > Constant.SELECTOR_CTR_FIRST_X
				&& ex < Constant.SELECTOR_CTR_FIRST_X
						+ Constant.SELECTOR_CTR_LENGTH
				&& ey > Constant.SELECTOR_CTR_FIRST_Y
				&& ey < Constant.SELECTOR_CTR_FIRST_Y
						+ Constant.SELECTOR_CTR_WIDTH) {
			result = 0;
		}
		if (ex > Constant.SELECTOR_CTR_FIRST_X
				&& ex < Constant.SELECTOR_CTR_FIRST_X
						+ Constant.SELECTOR_CTR_LENGTH
				&& ey - Constant.SELECTOR_CTR_WIDTH - Constant.SELECTOR_GAP > Constant.SELECTOR_CTR_FIRST_Y
				&& ey - Constant.SELECTOR_CTR_WIDTH - Constant.SELECTOR_GAP < Constant.SELECTOR_CTR_FIRST_Y
						+ Constant.SELECTOR_CTR_WIDTH) {
			result = 1;
		}
		return result;
	}

	public void clickUp() {
		if (CtrCount > 0) {
			CtrCount--;
			switchType(currentHeaderIndex);
		}
	}

	public void clickDown() {
		if (CtrCount + 3 < realNum) {
			CtrCount++;
			switchType(currentHeaderIndex);
		}
	}

	public void switchType(int headerIndex) {
		if (headerIndex != this.currentHeaderIndex) {
			CtrCount = 0;
		}
		switch (headerIndex) {
		case 0:
			updateSelectors(spots);
			planPanel.repaint();
			break;
		case 1:
			updateSelectors(hotels);
			planPanel.repaint();
			break;
		case 2:
			updateSelectors(restaurants);
			planPanel.repaint();
			break;
		case 3:
			updateSelectors(entertainments);
			planPanel.repaint();
			break;
		case 4:
			updateSelectors(shoppingMalls);
			planPanel.repaint();
			break;
		case 5:
			updateSelectors();
			planPanel.repaint();
			break;
		default:
			break;

		}
	}

	public SelectorItem getItemByIndex(int index) {
		return selectors[index];
	}

	public int getCurrentHeaderIndex() {
		return currentHeaderIndex;
	}

	public void setCurrentHeaderIndex(int currentHeaderIndex) {
		this.currentHeaderIndex = currentHeaderIndex;
	}

	public boolean isShowContent() {
		return isShowContent;
	}

	public void setShowContent(boolean isShowContent) {
		this.isShowContent = isShowContent;
	}

}
