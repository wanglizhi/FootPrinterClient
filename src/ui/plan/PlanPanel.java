package ui.plan;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.PlanController;

import tools.MapUtils;
import vo.PlanEntertainmentVO;
import vo.PlanHotelVO;
import vo.PlanItemVO;
import vo.PlanRestaurantVO;
import vo.PlanShoppingMallVO;
import vo.PlanSpotVO;
import vo.PlanVO;

public class PlanPanel extends JPanel implements MouseMotionListener,
		MouseListener {
	/**
	 * @author clarkwong
	 */
	private static final long serialVersionUID = 1L;
	private int planBarNum;
	private PlanBar planBars[];

	private boolean isResizeTime;
	private boolean isInSpot;
	private boolean isDraggedInItem;
	private boolean isDraggedInGap;
	private boolean isInUpCtr;
	private boolean isInDownCtr;
	private boolean isShowFormerDay;
	private boolean isInFormerDay;
	private boolean isInAddDay;

	private int gapIndexX;
	private int gapIndexY;
	private int baseX;
	private int baseTime;

	private int spotDraggedIndex;

	private int itemIndexX;
	private int itemIndexY;

	private int formerIndexX;
	private int formerIndexY;
	private int laterIndexX;
	private int laterIndexY;

	private Selector selector;

	private ArrayList<PlanBar> allPlans;
	private int dayNum;
	private int currentDay;

	private ControlButton formerDay;
	private ControlButton addDay;

	private ThrillingButton exitButton;

	private Date startDate;
	private Date currentDate;
	private SimpleDateFormat myFormatter;
	private String dateStr;
	private JLabel dateLabel;
	private boolean isMoveInItem;

	private int itemIndexXMovedIn;
	private int itemIndexYMovedIn;

	private Image map;
	private Image background;
	private Color backcolor =
	// new Color(0, 120, 127);
	new Color(39, 90, 127);

	private PlanController planController;
	private int currentUserID;
	private int currentCityID;
	private int planID;
	private String title;

	public PlanPanel(int planBarNum, Date start, int currentUserID,
			int currentCityID, String title, PlanController planController) {
		this.setLayout(null);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		formerDay = new ControlButton(Constant.CTR_BUTTON_FIRST_X,
				Constant.CTR_BUTTON_FIRST_Y, "img/计划/-.png");
		addDay = new ControlButton(Constant.CTR_BUTTON_SECOND_X,
				Constant.CTR_BUTTON_SECOND_Y, "img/计划/+.png");
		exitButton = new ThrillingButton(Constant.PLAN_EXIT_X,
				Constant.PLAN_EXIT_Y, Constant.EXIT_LENGTH,
				Constant.EXIT_WIDTH, "退出", this);
		// selector = new Selector(this, s, h, r, e, sh, f);
		background = new ImageIcon("img/计划背景8.jpg").getImage();
		this.planController = planController;
		initNewPlan(Constant.PLANBAR_NUM, start, currentUserID, currentCityID,
				title);
	}

	public PlanPanel(int planBarNum, Date start, Date end,
			ArrayList<ArrayList<PlanItemVO>> planItems, int currentUserID,
			int currentCityID, int planID, String title,
			PlanController planController) {
		this.setLayout(null);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		formerDay = new ControlButton(Constant.CTR_BUTTON_FIRST_X,
				Constant.CTR_BUTTON_FIRST_Y, "img/计划/-.png");
		addDay = new ControlButton(Constant.CTR_BUTTON_SECOND_X,
				Constant.CTR_BUTTON_SECOND_Y, "img/计划/+.png");
		exitButton = new ThrillingButton(Constant.PLAN_EXIT_X,
				Constant.PLAN_EXIT_Y, Constant.EXIT_LENGTH,
				Constant.EXIT_WIDTH, "退出", this);
		// selector = new Selector(this, s, h, r, e, sh, f);
		background = new ImageIcon("img/计划背景8.jpg").getImage();
		this.planController = planController;
		initOldPlan(Constant.PLANBAR_NUM, start, end, planItems, currentUserID,
				currentCityID, planID, title);
	}

	public void initNewPlan(int planBarNum, Date start, int currentUserID,
			int currentCityID, String title) {
		this.currentUserID = currentUserID;
		this.currentCityID = currentCityID;
		this.planID = 0;
		this.title = title;
		initPlanBar(planBarNum);
		// selector.update(s, h, r, e, sh, f);
		dayNum = 1;
		currentDay = 1;
		allPlans = new ArrayList<PlanBar>();

		this.startDate = start;
		currentDate = startDate;
		myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		dateStr = myFormatter.format(currentDate);
		if (dateLabel == null) {
			dateLabel = new JLabel(dateStr);
		} else {
			dateLabel.setText(dateStr);
		}
		dateLabel.setForeground(Color.GRAY);
		dateLabel.setBounds(Constant.CTR_DATE_X, Constant.CTR_DATE_Y,
				Constant.CTR_DATE_LENGTH, Constant.CTR_DATE_WIDTH);
		dateLabel.setFont(Constant.DATE_FONT);
		this.add(dateLabel);

		updateMap();
	}

	public void initOldPlan(int planBarNum, Date start, Date end,
			ArrayList<ArrayList<PlanItemVO>> planItems, int currentUserID,
			int currentCityID, int planID, String title) {
		this.currentUserID = currentUserID;
		this.currentCityID = currentCityID;
		this.planID = planID;
		System.out.println("现在是plan" + planID);
		this.title = title;
		// dayNum = end.compareTo(start) + 1;
		dayNum = (int) (end.getTime() - start.getTime())
				/ (1000 * 60 * 60 * 24) + 1;
		currentDay = 1;
		allPlans = new ArrayList<PlanBar>();

		this.planBarNum = planBarNum;
		// planBars = new PlanBar[planBarNum];
		// // 把bars都建立起来
		// for (int i = 0; i < planBarNum; i++) {
		// planBars[i] = new PlanBar(i * Constant.EACH_BAR_TIME, (i + 1)
		// * Constant.EACH_BAR_TIME);
		// }
		// 开始按天进行初始化
		for (int day = 0; day < dayNum; day++) {
			ArrayList<PlanItemVO> currentDayItems = planItems.get(day);
			planBars = new PlanBar[planBarNum];
			// 把bars都建立起来
			for (int i = 0; i < planBarNum; i++) {
				planBars[i] = new PlanBar(i * Constant.EACH_BAR_TIME, (i + 1)
						* Constant.EACH_BAR_TIME);
			}
			for (PlanItemVO pv : currentDayItems) {
				PlanBarItem pbl = new PlanBarItem(pv.planItemID, pv.startTime,
						pv.endTime, pv.indexX, pv.indexY, this, pv.isUsed,
						pv.landmarkID, pv.landmarkType, pv.landmark, pv.comment);
				planBars[pv.indexX].addAnItem(pv.indexY, pbl);
			}
			// 把初始化好的bars加到allPlans里
			for (int k = 0; k < planBarNum; k++) {
				allPlans.add(planBars[k]);
			}

		}

		// selector.update(s, h, r, e, sh, f);
		this.startDate = start;
		currentDate = startDate;
		myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		dateStr = myFormatter.format(currentDate);
		if (dateLabel == null) {
			dateLabel = new JLabel(dateStr);
		} else {
			dateLabel.setText(dateStr);
		}
		dateLabel.setForeground(Color.GRAY);
		dateLabel.setBounds(Constant.CTR_DATE_X, Constant.CTR_DATE_Y,
				Constant.CTR_DATE_LENGTH, Constant.CTR_DATE_WIDTH);
		dateLabel.setFont(Constant.DATE_FONT);
		this.add(dateLabel);

		showPlanBarAtCurrentDay();
	}

	public void updateSelector(ArrayList<PlanSpotVO> s,
			ArrayList<PlanHotelVO> h, ArrayList<PlanRestaurantVO> r,
			ArrayList<PlanEntertainmentVO> e, ArrayList<PlanShoppingMallVO> sh,
			ArrayList<PlanItemVO> f) {
		if (selector == null) {
			selector = new Selector(this, s, h, r, e, sh, f);
		} else {
			selector.update(s, h, r, e, sh, f);
		}
	}

	class ShowMap implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			showMap();
			repaint();
		}

	}

	private void showMap() {
		ArrayList<String> markers = new ArrayList<String>();
		for (int i = 0; i < planBarNum; i++) {
			for (int j = 0; j < planBars[i].getPartNum(); j++) {
				if (planBars[i].getItemByIndex(j).isUsed()) {
					markers.add(planBars[i].getItemByIndex(j).getLandMark());
				}
			}
		}
		map = (MapUtils.getMap(markers, Constant.PLAN_MAP_LENGTH,
				Constant.PLAN_MAP_WIDTH)).getImage();
	}

	private void updateMap() {
		Thread t = new Thread(new ShowMap());
		t.start();
	}

	private void updateDate() {

		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.DATE, currentDay - 1);
		currentDate = c.getTime();
		dateStr = myFormatter.format(currentDate);
		dateLabel.setText(dateStr);

	}

	private void initPlanBar(int planBarNum) {
		if (planBars != null) {
			for (int k = 0; k < planBarNum; k++) {
				for (int j = 0; j < planBars[k].getPartNum(); j++) {
					PlanBarItem pbi = planBars[k].getItemByIndex(j);
					if (pbi.isShowComment()) {
						pbi.setComment(pbi.getCommentView().getCommentArea()
								.getText());
						pbi.setShowComment(false);
					}
				}
			}
		}
		this.planBarNum = planBarNum;
		planBars = new PlanBar[planBarNum];
		int partNum = 24 / planBarNum;
		for (int i = 0; i < planBarNum; i++) {
			planBars[i] = new PlanBar(i * Constant.EACH_BAR_TIME, (i + 1)
					* Constant.EACH_BAR_TIME, partNum, this);
		}
	}

	private void showPlanBarAtCurrentDay() {
		if (planBars != null) {
			for (int k = 0; k < planBarNum; k++) {
				for (int j = 0; j < planBars[k].getPartNum(); j++) {
					PlanBarItem pbi = planBars[k].getItemByIndex(j);
					if (pbi.isShowComment()) {
						pbi.setComment(pbi.getCommentView().getCommentArea()
								.getText());
						pbi.setShowComment(false);
					}
				}
			}
		}
		for (int i = 0; i < this.planBarNum; i++) {
			planBars[i] = allPlans.get((currentDay - 1) * this.planBarNum + i);
		}

		updateMap();
	}

	@Override
	public void paint(Graphics g) {
		g.setFont(Constant.ORIGIN_FONT);
		g.clearRect(0, 0, Constant.PANEL_LENGTH, Constant.PANEL_WIDTH);
		g.drawImage(background, 0, 0, Constant.PANEL_LENGTH,
				Constant.PANEL_WIDTH, null);
		// g.setColor(backcolor);
		// g.fillRect(0, 0, Constant.PANEL_LENGTH, Constant.PANEL_WIDTH);

		g.drawImage(map, Constant.PLAN_MAP_X, Constant.PLAN_MAP_Y,
				Constant.PLAN_MAP_LENGTH, Constant.PLAN_MAP_WIDTH, null);
		for (int i = 0; i < planBarNum; i++) {
			planBars[i].drawPlanBar(g);
		}
		if (selector != null) {
			selector.drawSelector(g);
		}
		if (isShowFormerDay) {
			formerDay.drawButton(g);
		}
		addDay.drawButton(g);
		exitButton.drawButton(g);
		this.paintComponents(g);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("My Plan");
		//
		// PlanSpotVO spot0 = new PlanSpotVO("img/计划/spot0.png", "栖霞山");
		// PlanSpotVO spot1 = new PlanSpotVO("img/计划/spot1.png", "西塘");
		// ArrayList<PlanSpotVO> spots = new ArrayList<PlanSpotVO>();
		// spots.add(spot0);
		// spots.add(spot1);
		//
		// PlanHotelVO hotel0 = new PlanHotelVO("img/计划/spot0.png", "丽江大酒店");
		// PlanHotelVO hotel1 = new PlanHotelVO("img/计划/spot0.png", "香格里拉大酒店");
		// PlanHotelVO hotel2 = new PlanHotelVO("img/计划/spot0.png", "norla大酒店");
		// ArrayList<PlanHotelVO> hotels = new ArrayList<PlanHotelVO>();
		// hotels.add(hotel0);
		// hotels.add(hotel1);
		// hotels.add(hotel2);
		//
		// PlanRestaurantVO res0 = new PlanRestaurantVO("img/计划/spot1.png",
		// "music bar");
		// PlanRestaurantVO res1 = new PlanRestaurantVO("img/计划/spot1.png",
		// "sad bar");
		// PlanRestaurantVO res2 = new PlanRestaurantVO("img/计划/spot1.png",
		// "happy bar");
		// PlanRestaurantVO res3 = new PlanRestaurantVO("img/计划/spot0.png",
		// "code bar");
		// ArrayList<PlanRestaurantVO> restaurants = new
		// ArrayList<PlanRestaurantVO>();
		// restaurants.add(res0);
		// restaurants.add(res1);
		// restaurants.add(res2);
		// restaurants.add(res3);
		//
		// PlanPanel planPanel = new PlanPanel(Constant.PLANBAR_NUM, new Date(),
		// spots, hotels, restaurants, null, null, null, 1, 1, "happy",
		// null);

		// frame.getContentPane().add(planPanel);
		// planPanel.setPreferredSize(new Dimension(Constant.PANEL_LENGTH,
		// Constant.PANEL_WIDTH));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	private void checkWheatherShowFormer() {
		if (currentDay > 1) {
			isShowFormerDay = true;
		} else {
			isShowFormerDay = false;
		}
	}

	private void addNew() {
		// 把已经制定好的计划存入allPlans之中
		System.out.print("new add into allPlans from " + allPlans.size());
		for (int i = 0; i < this.planBarNum; i++) {
			allPlans.add(planBars[i]);
		}
		System.out.println(" to " + (allPlans.size() - 1) + " dayNum " + dayNum
				+ " current " + " " + currentDay);
	}

	private void modifyOld() {
		// 把修改过的计划写入
		System.out.print("modify allPlans from " + (currentDay - 1)
				* this.planBarNum);
		for (int i = 0; i < this.planBarNum; i++) {
			allPlans.set((currentDay - 1) * this.planBarNum + i, planBars[i]);

		}
		System.out.println(" to "
				+ ((currentDay - 1) * this.planBarNum + planBarNum - 1)
				+ " dayNum " + dayNum + " current " + " " + currentDay);
	}

	public void addANewDay() {
		if (dayNum == currentDay) {
			dayNum++;
			if (allPlans.size() < currentDay * this.planBarNum) {
				addNew();
			} else {
				modifyOld();
			}
			currentDay++;
			initPlanBar(this.planBarNum);
			// checkWheatherShowFormer();
			updateDate();
			updateMap();
		} else {
			nextDay();
		}
	}

	public void nextDay() {
		if (currentDay < dayNum) {
			modifyOld();
			currentDay++;
			showPlanBarAtCurrentDay();
			// checkWheatherShowFormer();
			updateDate();
		}
	}

	public void formerDay() {
		if (currentDay > 1) {
			if (allPlans.size() < currentDay * this.planBarNum) {
				addNew();
			} else {
				modifyOld();
			}
			currentDay--;
			showPlanBarAtCurrentDay();
			// checkWheatherShowFormer();
			updateDate();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (isResizeTime) {
			// System.out.println(gapIndexX + "," + gapIndexY);
			PlanBar bar = planBars[gapIndexX];
			PlanBarItem former = bar.getItemByIndex(gapIndexY - 1);
			int offset = e.getX() - baseX;
			int offsetTime = offset / Constant.UNIT_LENGTH;
			former.setEndTime(baseTime + offsetTime);
			// System.out.print
			// ln(former.getEndTime());
			if (gapIndexY < 24 / Constant.PLANBAR_NUM) {
				PlanBarItem later = planBars[gapIndexX]
						.getItemByIndex(gapIndexY);
				later.setStartTime(former.getEndTime());

				int checkResult = -2;
				checkResult = former.check() + later.check();
				if (checkResult != -2) {
					checkResult++;
					// System.out.println("check结果" + checkResult);
					bar.proceedCombine(checkResult);
					if (checkResult == 0) {
						isResizeTime = false;
						later.setStartTime(bar.getStartTime());
					} else if (checkResult == bar.getPartNum()) {
						isResizeTime = false;
						former.setEndTime(bar.getEndTime());
					} else {
						if (former.check() != -1)
							gapIndexY--;
					}
				}
				repaint();
			}
		}

		if (isInSpot || isMoveInItem) {
			if (isInSpot) {
				selector.getItemByIndex(spotDraggedIndex).dragSpot(e.getX(),
						e.getY());
			}
			int result = -1;
			for (int i = 0; i < planBarNum; i++) {
				result = planBars[i].LocateItem(e.getX(), e.getY());
				if (result > -1) {
					break;
				}
			}
			if (result > -1) {
				int barIndex = 0;
				barIndex = (e.getY() - Constant.FIRST_BAR_Y)
						/ (Constant.BAR_WIDTH + Constant.BAR_GAP);
				// System.out.println(barIndex + "," + result);
				isDraggedInItem = true;
				itemIndexX = barIndex;
				itemIndexY = result;
				planBars[itemIndexX].getItemByIndex(itemIndexY).setDrawOutline(
						true);

			} else {
				isDraggedInItem = false;
				itemIndexX = -1;
				itemIndexY = -1;
			}

			if (isInSpot) {
				result = 0;
				for (int i = 0; i < planBarNum; i++) {
					result = planBars[i].LocateGap(e.getX(), e.getY());
					if (result > 0) {
						break;
					}
				}
				if (result > 0) {
					int barIndex = 0;
					barIndex = (e.getY() - Constant.FIRST_BAR_Y)
							/ (Constant.BAR_WIDTH + Constant.BAR_GAP);
					// System.out.println(barIndex + "," + result);
					PlanBar bar = planBars[barIndex];
					gapIndexX = barIndex;
					gapIndexY = result;
					PlanBarItem former = bar.getItemByIndex(gapIndexY - 1);
					this.formerIndexX = gapIndexX;
					this.formerIndexY = gapIndexY - 1;
					if (result < bar.getPartNum()) {
						this.isDraggedInGap = true;
						PlanBarItem later = bar.getItemByIndex(gapIndexY);
						former.setDrawLeftOutline(true);
						later.setDrawRightOutline(true);
						this.laterIndexX = gapIndexX;
						this.laterIndexY = gapIndexY;
					} else {
						if (barIndex + 1 < Constant.PLANBAR_NUM) {
							this.isDraggedInGap = true;
							bar = planBars[barIndex + 1];
							PlanBarItem later = bar.getItemByIndex(0);
							former.setDrawLeftOutline(true);
							later.setDrawRightOutline(true);
							this.laterIndexX = gapIndexX + 1;
							this.laterIndexY = 0;
						}
					}
				} else {
					this.isDraggedInGap = false;
					gapIndexX = 0;
					gapIndexY = 0;
				}
			}
			repaint();
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

		// 检查是否在gap中
		int result = 0;
		for (int i = 0; i < planBarNum; i++) {
			result = planBars[i].LocateGap(e.getX(), e.getY());
			if (result > 0) {
				break;
			}
		}
		if (result > 0) {
			int barIndex = 0;
			barIndex = (e.getY() - Constant.FIRST_BAR_Y)
					/ (Constant.BAR_WIDTH + Constant.BAR_GAP);
			// System.out.println(barIndex + "," + result);
			PlanBar bar = planBars[barIndex];
			if (result < bar.getPartNum()) {
				this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				isResizeTime = true;
				gapIndexX = barIndex;
				gapIndexY = result;
				PlanBarItem former = bar.getItemByIndex(gapIndexY - 1);
				baseX = former.getX() + former.getLength();
				baseTime = former.getEndTime();
			}
		} else {
			if ((!isInSpot) && (!isInUpCtr) && (!isInDownCtr)) {
				this.setCursor(Cursor.getDefaultCursor());
			}
			isResizeTime = false;
			gapIndexX = 0;
			gapIndexY = 0;
		}

		// 检查是否在item中
		result = -1;
		for (int i = 0; i < planBarNum; i++) {
			result = planBars[i].LocateItem(e.getX(), e.getY());
			if (result > -1) {
				break;
			}
		}
		if (result > -1 && !isMoveInItem) {
			int barIndex = 0;
			barIndex = (e.getY() - Constant.FIRST_BAR_Y)
					/ (Constant.BAR_WIDTH + Constant.BAR_GAP);
			System.out.println(barIndex + "," + result);
			isMoveInItem = true;
			itemIndexXMovedIn = barIndex;
			itemIndexYMovedIn = result;
			planBars[itemIndexXMovedIn].getItemByIndex(itemIndexYMovedIn)
					.moveInItem();
			repaint();

		} else if (result <= -1) {
			if (isMoveInItem) {
				planBars[itemIndexXMovedIn].getItemByIndex(itemIndexYMovedIn)
						.outItem();
				repaint();
				System.out.println("out item");
			}
			isMoveInItem = false;
			itemIndexXMovedIn = -1;
			itemIndexYMovedIn = -1;
		}

		// 检查是否在selector之中
		if (selector != null) {
			result = selector.inWhichSelector(e.getX(), e.getY());
		}
		if (result > -1) {
			// System.out.println(result);
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			this.isInSpot = true;
			this.spotDraggedIndex = result;
		} else {
			if ((!isResizeTime) && (!isInUpCtr) && (!isInDownCtr)) {
				this.setCursor(Cursor.getDefaultCursor());
			}
			this.isInSpot = false;
		}

		// 检查是否在selectorCtr之中
		if (selector != null) {
			result = selector.inWhichCtr(e.getX(), e.getY());
		}
		if (result > -1) {
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			if (result == 0)
				isInUpCtr = true;
			if (result == 1)
				isInDownCtr = true;
		} else {
			if ((!isResizeTime) && (!isInSpot)) {
				this.setCursor(Cursor.getDefaultCursor());
			}
			isInUpCtr = false;
			isInDownCtr = false;
		}

		if (formerDay.isEntered(e.getX(), e.getY()) && isShowFormerDay) {
			isInFormerDay = true;
		} else {
			isInFormerDay = false;
		}

		if (addDay.isEntered(e.getX(), e.getY())) {
			isInAddDay = true;
		} else {
			isInAddDay = false;
		}

		// 检查是否在exitButton中
		if (exitButton.isInButton(e.getX(), e.getY())) {
			exitButton.setThrilling(true);
		} else {
			exitButton.setThrilling(false);
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (isInUpCtr) {
			selector.clickUp();
		}
		if (isInDownCtr) {
			selector.clickDown();
		}
		if (isMoveInItem) {
			planBars[itemIndexXMovedIn].getItemByIndex(itemIndexYMovedIn)
					.proceedClick();
		}
		if (exitButton.isInButton(e.getX(), e.getY())) {
			exitButton.setThrilling(false);
			repaint();

			int result = JOptionPane.showConfirmDialog(null, "是否保存？");
			// 保存操作
			if (result == JOptionPane.OK_OPTION) {
				Date endDate;
				Calendar c = Calendar.getInstance();
				c.setTime(startDate);
				c.add(Calendar.DATE, dayNum - 1);
				endDate = c.getTime();
				System.out.println(endDate);
				// 检查最后一天有没有存入allPlans
				if (allPlans.size() < dayNum * Constant.PLANBAR_NUM) {
					for (int k = 0; k < Constant.PLANBAR_NUM; k++) {
						allPlans.add(planBars[k]);
					}
				}

				if (planID == 0) {

					PlanVO vo = new PlanVO(this.title, 1, planID,
							currentUserID, currentCityID, startDate, endDate);
					planController.saveNewPlan(vo);
					System.out.println("saveNew " + startDate + " " + endDate
							+ " " + allPlans.size());

				} else {

					PlanVO vo = new PlanVO(this.title, 1, planID,
							currentUserID, currentCityID, startDate, endDate);
					planController.saveOldPlan(vo);
					System.out.println("modifyOld " + startDate + " " + endDate
							+ " " + allPlans.size());
				}
				planController.toMyPlanPanel();
			}
			// 不保存操作
			else if (result == JOptionPane.NO_OPTION) {
				planController.toMyPlanPanel();
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (isInFormerDay && isShowFormerDay) {
			formerDay();
			formerDay.click();
			repaint();
		}
		if (isInAddDay) {
			addANewDay();
			addDay.click();
			repaint();
		}
		if (isMoveInItem) {
			// pressed之后重新定位
			int result = -1;
			for (int i = 0; i < planBarNum; i++) {
				result = planBars[i].LocateItem(e.getX(), e.getY());
				if (result > -1) {
					break;
				}
			}
			if (result > -1) {
				int barIndex = 0;
				barIndex = (e.getY() - Constant.FIRST_BAR_Y)
						/ (Constant.BAR_WIDTH + Constant.BAR_GAP);
				System.out.println(barIndex + "," + result);
				itemIndexXMovedIn = barIndex;
				itemIndexYMovedIn = result;
				planBars[itemIndexXMovedIn].getItemByIndex(itemIndexYMovedIn)
						.moveInItem();
				repaint();

			} else {
				isMoveInItem = false;
				itemIndexXMovedIn = -1;
				itemIndexYMovedIn = -1;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (isMoveInItem) {
			if (e.getX() > Constant.FIRST_BAR_X + Constant.BAR_LENGTH
					|| e.getX() < Constant.FIRST_BAR_X
					|| e.getY() < Constant.FIRST_BAR_Y
					|| e.getY() > Constant.FIRST_BAR_Y + Constant.BAR_WIDTH
							* Constant.PLANBAR_NUM + Constant.BAR_GAP
							* (Constant.PLANBAR_NUM - 1)) {
				System.out.println("do clear...");
				PlanBarItem origin = planBars[itemIndexXMovedIn]
						.getItemByIndex(itemIndexYMovedIn);
				origin.setUsed(false);
				origin.setLandMark(null);
				origin.setComment("在这里写备注");
				updateMap();
			}
		}

		if (isDraggedInItem) {

			isDraggedInItem = false;

			PlanBarItem selected = planBars[itemIndexX]
					.getItemByIndex(itemIndexY);
			if (isInSpot) {
				int currentIndex = selector.getCurrentHeaderIndex();
				if (currentIndex < 5) {
					selected.selected(selector.getItemByIndex(spotDraggedIndex)
							.getText(),
							selector.getItemByIndex(spotDraggedIndex)
									.getLandmarkID(),
							selector.getItemByIndex(spotDraggedIndex)
									.getLandmarkType());
				} else {
					selected.selected(selector.getItemByIndex(spotDraggedIndex)
							.getText(),
							selector.getItemByIndex(spotDraggedIndex)
									.getLandmarkID(),
							selector.getItemByIndex(spotDraggedIndex)
									.getLandmarkType(), selector
									.getItemByIndex(spotDraggedIndex)
									.getComment());
				}
			}
			if (isMoveInItem) {
				PlanBarItem origin = planBars[itemIndexXMovedIn]
						.getItemByIndex(itemIndexYMovedIn);
				selected.swap(origin);
			}
			selected.setShowComment(false);
			repaint();
			updateMap();
		}

		if (isDraggedInGap) {

			isDraggedInGap = false;
			PlanBarItem former = planBars[formerIndexX]
					.getItemByIndex(formerIndexY);
			PlanBarItem later = planBars[laterIndexX]
					.getItemByIndex(laterIndexY);
			if (laterIndexY == 0) {

				former.selected(selector.getItemByIndex(spotDraggedIndex)
						.getText(), selector.getItemByIndex(spotDraggedIndex)
						.getLandmarkID(),
						selector.getItemByIndex(spotDraggedIndex)
								.getLandmarkType());

				later.selected(selector.getItemByIndex(spotDraggedIndex)
						.getText(), selector.getItemByIndex(spotDraggedIndex)
						.getLandmarkID(),
						selector.getItemByIndex(spotDraggedIndex)
								.getLandmarkType());
			} else {
				former.selected(selector.getItemByIndex(spotDraggedIndex)
						.getText(), selector.getItemByIndex(spotDraggedIndex)
						.getLandmarkID(),
						selector.getItemByIndex(spotDraggedIndex)
								.getLandmarkType());
				former.setEndTime(later.getEndTime());
				planBars[formerIndexX].proceedCombine(laterIndexY);
			}
			former.setShowComment(false);
			later.setShowComment(false);
			former.updateLength();
			former.updateLocation();
			later.updateLength();
			later.updateLocation();
			repaint();
			updateMap();

		}

		if (isInSpot) {

			isInSpot = false;

			selector.getItemByIndex(spotDraggedIndex).setDrag(false);
			repaint();
		}

		if (isInFormerDay && isShowFormerDay) {
			formerDay.release();
			checkWheatherShowFormer();
			repaint();
		}

		if (isInAddDay) {
			addDay.release();
			checkWheatherShowFormer();
			repaint();
		}

		repaint();

	}

	public boolean isResizeTime() {
		return isResizeTime;
	}

	public void setResizeTime(boolean isResizeTime) {
		this.isResizeTime = isResizeTime;
	}

	public ArrayList<PlanBar> getAllPlans() {
		return allPlans;
	}

	public void setAllPlans(ArrayList<PlanBar> allPlans) {
		this.allPlans = allPlans;
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}
}
