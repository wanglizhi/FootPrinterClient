package ui.plan;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.CheckPlanController;

import tools.FileUtils;
import tools.MapUtils;
import vo.PlanHotelVO;
import vo.PlanItemVO;
import vo.PlanRestaurantVO;
import vo.PlanSpotVO;
import vo.PlanVO;

public class CheckPlanPanel extends JPanel implements MouseListener,
		MouseMotionListener {

	/**
	 * @author clarkwong
	 */
	private static final long serialVersionUID = 1L;
	private int planBarNum;
	private PlanBar planBars[];

	private boolean isShowFormerDay;
	private boolean isShowNextDay;
	private boolean isInFormerDay;
	private boolean isInNextDay;
	private boolean isInApplause;
	private boolean isMoveInItem;
	private boolean isDragItem;

	private int itemIndexXMovedIn;
	private int itemIndexYMovedIn;
	private int dragX;
	private int dragY;

	private ArrayList<PlanBar> allPlans;
	private int dayNum;
	private int currentDay;

	private ControlButton formerDay;
	private ControlButton nextDay;

	private ControlButton applause;

	private ThrillingButton exitButton;

	private Date startDate;
	private Date currentDate;
	private SimpleDateFormat myFormatter;
	private String dateStr;
	private JLabel dateLabel;
	private FavoriteButton favoriteButton;
	private Image map;
	private Image background;

	private Color backcolor =
	// new Color(0, 120, 127);
	new Color(39, 90, 127);

	private CheckPlanController checkPlanController;
	private int currentUserID;

	public CheckPlanPanel(int planBarNum, Date start, Date end,
			ArrayList<ArrayList<PlanItemVO>> planItems, int currentUserID,
			CheckPlanController checkPlanController) {
		this.setLayout(null);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		formerDay = new ControlButton(Constant.CTR_BUTTON_FIRST_X,
				Constant.CTR_BUTTON_FIRST_Y, "img/计划/-.png");
		nextDay = new ControlButton(Constant.CTR_BUTTON_SECOND_X,
				Constant.CTR_BUTTON_SECOND_Y, "img/计划/+.png");
		applause = new ControlButton(Constant.APPLAUSE_X, Constant.APPLAUSE_Y,
				Constant.APPLAUSE_LENGTH, Constant.APPLAUSE_WIDTH,
				"chan/like.png");
		applause.addAPic("chan/like2.png");
		if (checkPlanController.checkApplause()) {
			applause.changePic(1);
		}
		exitButton = new ThrillingButton(Constant.PLAN_EXIT_X,
				Constant.PLAN_EXIT_Y, Constant.EXIT_LENGTH,
				Constant.EXIT_WIDTH, "退出", this);
		favoriteButton = new FavoriteButton(this);
		background = new ImageIcon("img/计划背景8.jpg").getImage();
		this.checkPlanController = checkPlanController;
		initOldPlan(Constant.PLANBAR_NUM, start, end, planItems, currentUserID);
	}

	public void initOldPlan(int planBarNum, Date start, Date end,
			ArrayList<ArrayList<PlanItemVO>> planItems, int currentUserID) {
		this.currentUserID = currentUserID;
		// dayNum = end.compareTo(start) + 1;
		dayNum = (int) (end.getTime() - start.getTime())
				/ (1000 * 60 * 60 * 24) + 1;
		currentDay = 1;
		allPlans = new ArrayList<PlanBar>();

		if (dayNum > 1) {
			isShowNextDay = true;
		}
		if (checkPlanController.checkApplause()) {
			applause.changePic(1);
		}
		this.planBarNum = planBarNum;
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
		map = (MapUtils
				.getMap(markers, Constant.MAP_LENGTH, Constant.MAP_WIDTH))
				.getImage();
	}

	private void updateDate() {

		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.DATE, currentDay - 1);
		currentDate = c.getTime();
		dateStr = myFormatter.format(currentDate);
		dateLabel.setText(dateStr);

	}

	private void showPlanBarAtCurrentDay() {
		if (planBars != null) {
			for (int k = 0; k < planBarNum; k++) {
				for (int j = 0; j < planBars[k].getPartNum(); j++) {
					PlanBarItem pbi = planBars[k].getItemByIndex(j);
					if (pbi != null && pbi.isShowComment()) {
						pbi.setShowComment(false);
					}
				}
			}
		}
		for (int i = 0; i < this.planBarNum; i++) {
			planBars[i] = allPlans.get((currentDay - 1) * this.planBarNum + i);
		}

		Thread t = new Thread(new ShowMap());
		t.start();
	}

	public void paint(Graphics g) {
		g.setFont(Constant.ORIGIN_FONT);
		g.clearRect(0, 0, Constant.PANEL_LENGTH, Constant.PANEL_WIDTH);
		// g.setColor(backcolor);
		// g.fillRect(0, 0, Constant.PANEL_LENGTH, Constant.PANEL_WIDTH);
		g.drawImage(background, 0, 0, null);
		g.drawImage(map, Constant.MAP_X, Constant.MAP_Y, Constant.MAP_LENGTH,
				Constant.MAP_WIDTH, null);

		for (int i = 0; i < planBarNum; i++) {
			planBars[i].drawPlanBar(g);
		}

		if (isShowFormerDay) {
			formerDay.drawButton(g);
		}
		if (isShowNextDay) {
			nextDay.drawButton(g);
		}
		applause.drawButton(g);
		exitButton.drawButton(g);
		favoriteButton.drawFavorButton(g);

		if (isDragItem) {
			drawDraggedItem(g, dragX, dragY);
		}
		this.paintComponents(g);
	}

	private void checkWheatherShowFormer() {
		if (currentDay > 1) {
			isShowFormerDay = true;
		} else {
			isShowFormerDay = false;
		}
	}

	private void checkWheatherShowNext() {
		if (currentDay == dayNum) {
			isShowNextDay = false;
		} else {
			isShowNextDay = true;
		}
	}

	public void nextDay() {
		if (currentDay < dayNum) {
			currentDay++;
			showPlanBarAtCurrentDay();
			//checkWheatherShowFormer();
			//checkWheatherShowNext();
			updateDate();
		}
	}

	public void formerDay() {
		currentDay--;
		showPlanBarAtCurrentDay();
		//checkWheatherShowFormer();
		//checkWheatherShowNext();
		updateDate();
	}

	public void drawDraggedItem(Graphics g, int x, int y) {
		Color c = new Color(0, 255, 255, 60);
		BufferedImage image = new BufferedImage(Constant.SPOT_ITEM_LENGTH,
				Constant.SPOT_ITEM_WIDTH, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(c);
		graphics.fillRect(0, 0, Constant.SPOT_ITEM_LENGTH,
				Constant.SPOT_ITEM_WIDTH);
		graphics.dispose();

		g.drawImage(image, x - Constant.SPOT_ITEM_LENGTH / 2, y
				- Constant.SPOT_ITEM_WIDTH / 2, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (isMoveInItem) {
			planBars[itemIndexXMovedIn].getItemByIndex(itemIndexYMovedIn)
					.proceedClick();
		}
		if (exitButton.isInButton(e.getX(), e.getY())) {
			exitButton.setThrilling(false);
			repaint();

			int result = JOptionPane.showConfirmDialog(null, "是否退出？");
			// 退出操作
			if (result == JOptionPane.OK_OPTION) {
				checkPlanController.toPlanHomePanel();
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (isInFormerDay && isShowFormerDay) {
			formerDay();
			formerDay.click();
			repaint();
		}
		if (isInNextDay && isShowNextDay) {
			nextDay();
			nextDay.click();
			repaint();
		}
		if (isInApplause) {
			if (checkPlanController.checkApplause()) {
				checkPlanController.deleteApplause();
				applause.changePic(0);
			} else {
				checkPlanController.addApplause();
				applause.changePic(1);
			}
			applause.click();
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
		if (isInFormerDay && isShowFormerDay) {
			formerDay.release();
			checkWheatherShowFormer();
			checkWheatherShowNext();
			repaint();
		}

		if (isInNextDay && isShowNextDay) {
			nextDay.release();
			checkWheatherShowFormer();
			checkWheatherShowNext();
			repaint();
		}

		if (isInApplause) {
			applause.release();
			repaint();
		}

		if (isDragItem) {
			isDragItem = false;
			if (favoriteButton.isInButton(e.getX(), e.getY())) {
				System.out.println("收藏了"
						+ planBars[itemIndexXMovedIn].getItemByIndex(
								itemIndexYMovedIn).getLandMark());
				favoriteButton.setThrilling(false);
				favoriteButton.jump();
				PlanBarItem selected = planBars[itemIndexXMovedIn]
						.getItemByIndex(itemIndexYMovedIn);
				boolean result = checkPlanController.addFavourite(
						currentUserID, selected.getPlanItemID());
				if (!result) {
					JOptionPane.showMessageDialog(null, "重复收藏=。=");
				}
			}
			repaint();
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
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (isMoveInItem
				&& planBars[itemIndexXMovedIn]
						.getItemByIndex(itemIndexYMovedIn).isUsed()) {
			isDragItem = true;
			dragX = e.getX();
			dragY = e.getY();
			if (favoriteButton.isInButton(e.getX(), e.getY())) {
				favoriteButton.setThrilling(true);
			} else {
				favoriteButton.setThrilling(false);
			}
			repaint();
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		// 检查是否在item中
		int result = -1;
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
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
				this.setCursor(Cursor.getDefaultCursor());
			}
			isMoveInItem = false;
			itemIndexXMovedIn = -1;
			itemIndexYMovedIn = -1;
		}

		// 检查是否在former和next两个按钮中
		if (formerDay.isEntered(e.getX(), e.getY()) && isShowFormerDay) {
			isInFormerDay = true;
		} else {
			isInFormerDay = false;
		}

		if (nextDay.isEntered(e.getX(), e.getY()) && isShowNextDay) {
			isInNextDay = true;
		} else {
			isInNextDay = false;
		}

		// 检查是否在applause中
		if (applause.isEntered(e.getX(), e.getY())) {
			isInApplause = true;
		} else {
			isInApplause = false;
		}

		// 检查是否在exitButton中
		if (exitButton.isInButton(e.getX(), e.getY())) {
			exitButton.setThrilling(true);
		} else {
			exitButton.setThrilling(false);
			repaint();
		}

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("My Plan");

		// ArrayList<ArrayList<PlanItemVO>> allPlans = new
		// ArrayList<ArrayList<PlanItemVO>>();
		//
		// ArrayList<PlanItemVO> dayplan = new ArrayList<PlanItemVO>();
		// PlanItemVO vo1 = new PlanItemVO("九龙", 0, 240, "huhu", 0, 0, true);
		// PlanItemVO vo2 = new PlanItemVO(null, 240, 300, null, 1, 0, false);
		// PlanItemVO vo3 = new PlanItemVO("新界", 300, 480, "锻炼身体", 1, 1, true);
		// PlanItemVO vo4 = new PlanItemVO("selei hotel", 480, 660, "吃饭", 2, 0,
		// true);
		// PlanItemVO vo5 = new PlanItemVO(null, 660, 720, null, 2, 1, false);
		// PlanItemVO vo6 = new PlanItemVO("victory", 720, 960, "看大黄鸭", 3, 0,
		// true);
		// PlanItemVO vo7 = new PlanItemVO(null, 960, 1020, null, 4, 0, false);
		// PlanItemVO vo8 = new PlanItemVO("迪士尼", 1020, 1200, "happy time", 4,
		// 1,
		// true);
		// PlanItemVO vo9 = new PlanItemVO("红馆", 1200, 1440, "eason演唱会", 5, 0,
		// true);
		// dayplan.add(vo1);
		// dayplan.add(vo2);
		// dayplan.add(vo3);
		// dayplan.add(vo4);
		// dayplan.add(vo5);
		// dayplan.add(vo6);
		// dayplan.add(vo7);
		// dayplan.add(vo8);
		// dayplan.add(vo9);
		//
		// allPlans.add(dayplan);
		//
		// ArrayList<PlanItemVO> dayplan2 = new ArrayList<PlanItemVO>();
		// PlanItemVO vo12 = new PlanItemVO("重庆大厦", 0, 240, "睡觉", 0, 0, true);
		// PlanItemVO vo22 = new PlanItemVO(null, 240, 360, null, 1, 0, false);
		// PlanItemVO vo32 = new PlanItemVO("太平山", 360, 480, "锻炼身体", 1, 1,
		// true);
		// PlanItemVO vo42 = new PlanItemVO("中环", 480, 660, "购买gucci", 2, 0,
		// true);
		// PlanItemVO vo52 = new PlanItemVO(null, 660, 720, null, 2, 1, false);
		// PlanItemVO vo62 = new PlanItemVO("维多利亚港", 720, 960, "看大黄鸭", 3, 0,
		// true);
		// PlanItemVO vo72 = new PlanItemVO(null, 960, 1080, null, 4, 0, false);
		// PlanItemVO vo82 = new PlanItemVO("迪士尼乐园", 1080, 1200, "happy time",
		// 4,
		// 1, true);
		// PlanItemVO vo92 = new PlanItemVO("红馆", 1200, 1440, "eason演唱会", 5, 0,
		// true);
		// dayplan2.add(vo12);
		// dayplan2.add(vo22);
		// dayplan2.add(vo32);
		// dayplan2.add(vo42);
		// dayplan2.add(vo52);
		// dayplan2.add(vo62);
		// dayplan2.add(vo72);
		// dayplan2.add(vo82);
		// dayplan2.add(vo92);
		//
		// allPlans.add(dayplan2);
		//
		// Date startDate = new Date();
		// Date endDate = new Date();
		// Calendar c = Calendar.getInstance();
		// c.setTime(endDate);
		// c.add(Calendar.DATE, 1);
		// endDate = c.getTime();
		// System.out.println(endDate.compareTo(startDate) + 1);
		//
		// CheckPlanPanel checkPlanPanel = new CheckPlanPanel(
		// Constant.PLANBAR_NUM, startDate, endDate, allPlans, 1, null);
		//
		// frame.getContentPane().add(checkPlanPanel);
		// checkPlanPanel.setPreferredSize(new Dimension(Constant.PANEL_LENGTH,
		// Constant.PANEL_WIDTH));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
