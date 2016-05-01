package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import businessLogicService.PlanBLService;
import startup.ControllerFactory;
import startup.CurrentUserStatus;
import tools.Debugger;
import ui.plan.Constant;
import ui.plan.PlanBar;
import ui.plan.PlanBarItem;
import ui.plan.PlanPanel;
import vo.PlanEntertainmentVO;
import vo.PlanHotelVO;
import vo.PlanItemVO;
import vo.PlanRestaurantVO;
import vo.PlanShoppingMallVO;
import vo.PlanSpotVO;
import vo.PlanVO;

public class PlanController {

	private PlanPanel planPanel;
	private PlanBLService planBLService;
	private int cityID;

	public PlanController(PlanBLService pbs) {
		this.planBLService = pbs;
	}

	public PlanPanel getNewPlanPanel(PlanVO planVO) {
		if (planPanel == null) {
			creatNewPlanPanel(planVO);
		} else {
			initNewPlanPanel(planVO);
		}

		return planPanel;
	}

	public PlanPanel getOldPlanPanel(PlanVO planVO) {
		if (planPanel == null) {
			creatOldPlanPanel(planVO);
		} else {
			initOldPlanPanel(planVO);
		}

		return planPanel;
	}

	class LoadSelector implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ArrayList<PlanSpotVO> spots = new ArrayList<PlanSpotVO>();
			ArrayList<PlanHotelVO> hotels = new ArrayList<PlanHotelVO>();
			ArrayList<PlanRestaurantVO> restaurants = new ArrayList<PlanRestaurantVO>();
			ArrayList<PlanEntertainmentVO> entertainments = new ArrayList<PlanEntertainmentVO>();
			ArrayList<PlanShoppingMallVO> shoppingMalls = new ArrayList<PlanShoppingMallVO>();
			ArrayList<PlanItemVO> favourites = new ArrayList<PlanItemVO>();
			planPanel.updateSelector(spots, hotels, restaurants,
					entertainments, shoppingMalls, favourites);
			planPanel.getSelector().setShowContent(false);
			planPanel.repaint();
			spots = planBLService.getSpots(cityID);
			hotels = planBLService.getHotels(cityID);
			restaurants = planBLService.getRestaurants(cityID);
			entertainments = planBLService.getEntertainments(cityID);
			shoppingMalls = planBLService.getShoppingMalls(cityID);

			favourites = planBLService.getAllFavourite(CurrentUserStatus
					.getInstance().getCurrentUserID(), cityID);

			planPanel.updateSelector(spots, hotels, restaurants,
					entertainments, shoppingMalls, favourites);
			planPanel.getSelector().setShowContent(true);
			planPanel.repaint();
		}

	}

	public void creatNewPlanPanel(PlanVO planVO) {
		Debugger.log("" + (planBLService));
		this.cityID = planVO.cityID;
		planPanel = new PlanPanel(Constant.PLANBAR_NUM, planVO.startDate,
				planVO.userID, planVO.cityID, planVO.title, this);
		Thread t = new Thread(new LoadSelector());
		t.start();
	}

	public void setPlanPanelVisible(boolean b) {
		planPanel.setVisible(b);
	}

	public void initNewPlanPanel(PlanVO planVO) {
		this.cityID = planVO.cityID;
		planPanel.initNewPlan(Constant.PLANBAR_NUM, planVO.startDate,
				planVO.userID, planVO.cityID, planVO.title);
		Thread t = new Thread(new LoadSelector());
		t.start();
	}

	public void creatOldPlanPanel(PlanVO planVO) {
		this.cityID = planVO.cityID;
		ArrayList<ArrayList<PlanItemVO>> allItems = new ArrayList<ArrayList<PlanItemVO>>();
		Date start = planVO.startDate;
		Date end = planVO.endDate;
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		// int dayNum = end.compareTo(start) + 1;
		int dayNum = (int) (end.getTime() - start.getTime())
				/ (1000 * 60 * 60 * 24) + 1;
		for (int i = 0; i < dayNum; i++) {
			Date d = c.getTime();
			c.add(Calendar.DATE, 1);
			ArrayList<PlanItemVO> currentDayitems = planBLService.getPlanItems(
					planVO.planID, d);
			allItems.add(currentDayitems);
		}

		planPanel = new PlanPanel(Constant.PLANBAR_NUM, start, end, allItems,
				planVO.userID, planVO.cityID, planVO.planID, planVO.title, this);
		Thread t = new Thread(new LoadSelector());
		t.start();
	}

	public void initOldPlanPanel(PlanVO planVO) {
		this.cityID = planVO.cityID;
		ArrayList<ArrayList<PlanItemVO>> allItems = new ArrayList<ArrayList<PlanItemVO>>();
		Date start = planVO.startDate;
		Date end = planVO.endDate;
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		// int dayNum = end.compareTo(start) + 1;
		int dayNum = (int) (end.getTime() - start.getTime())
				/ (1000 * 60 * 60 * 24) + 1;
		for (int i = 0; i < dayNum; i++) {
			Date d = c.getTime();
			c.add(Calendar.DATE, 1);
			ArrayList<PlanItemVO> currentDayitems = planBLService.getPlanItems(
					planVO.planID, d);
			allItems.add(currentDayitems);
		}

		planPanel.initOldPlan(Constant.PLANBAR_NUM, start, end, allItems,
				planVO.userID, planVO.cityID, planVO.planID, planVO.title);
		Thread t = new Thread(new LoadSelector());
		t.start();
	}

	// 这里的vo不要包含planID
	public void saveNewPlan(PlanVO planVO) {
		int planID = planBLService.savePlan(planVO);

		Date start = planVO.startDate;
		Date end = planVO.endDate;
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		// int dayNum = end.compareTo(start) + 1;
		int dayNum = (int) (end.getTime() - start.getTime())
				/ (1000 * 60 * 60 * 24) + 1;
		System.out.println("inSaveNewPlan" + dayNum);
		// 得到所有的planbar
		ArrayList<PlanBar> allPlans = planPanel.getAllPlans();

		// 对于每一天循环
		for (int i = 0; i < dayNum; i++) {
			Date d = c.getTime();
			c.add(Calendar.DATE, 1);
			// 对于每一个planbar循环
			for (int j = 0; j < Constant.PLANBAR_NUM; j++) {
				PlanBar thisBar = allPlans.get(i * Constant.PLANBAR_NUM + j);
				// 对于每一个planbaritem循环
				for (int k = 0; k < thisBar.getPartNum(); k++) {
					PlanBarItem thisItem = thisBar.getItemByIndex(k);
					PlanItemVO vo = new PlanItemVO(thisItem);
					planBLService.savePlanItem(planID, vo, d);
				}
			}
		}
	}

	// 这里的vo要包含planID
	public void saveOldPlan(PlanVO planVO) {
		int planID = planVO.planID;

		Date start = planVO.startDate;
		Date end = planVO.endDate;
		System.out.println("in saveOldPlan " + end);
		planBLService.modifyPlan(planVO);
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		// int dayNum = end.compareTo(start) + 1;
		int dayNum = (int) (end.getTime() - start.getTime())
				/ (1000 * 60 * 60 * 24) + 1;
		// 得到所有的planbar
		ArrayList<PlanBar> allPlans = planPanel.getAllPlans();

		// 对于每一天循环
		for (int i = 0; i < dayNum; i++) {
			Date d = c.getTime();
			c.add(Calendar.DATE, 1);
			// 对于每一个planbar循环
			for (int j = 0; j < Constant.PLANBAR_NUM; j++) {
				PlanBar thisBar = allPlans.get(i * Constant.PLANBAR_NUM + j);
				// 对于每一个planbaritem循环
				for (int k = 0; k < thisBar.getPartNum(); k++) {
					PlanBarItem thisItem = thisBar.getItemByIndex(k);
					PlanItemVO vo = new PlanItemVO(thisItem);
					System.out.println(planID + " " + vo.planItemID);
					if (vo.planItemID == 0) {
						planBLService.savePlanItem(planID, vo, d);
					} else {
						planBLService.modifyPlanItem(planID, vo, d);
					}
				}
			}
		}
	}

	public void toMyPlanPanel() {
		ControllerFactory.getInstance().homepageController.setTab(3,
				ControllerFactory.getInstance().planHomeController
						.getMyPlanPanel());
	}
}