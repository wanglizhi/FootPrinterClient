package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import businessLogicService.PlanBLService;
import startup.ControllerFactory;
import startup.CurrentUserStatus;
import ui.plan.CheckPlanPanel;
import ui.plan.Constant;
import vo.PlanItemVO;
import vo.PlanVO;

public class CheckPlanController {

	private CheckPlanPanel checkPlanPanel;
	private PlanBLService planBLService;
	// private int currentUserID;
	private int planID;

	public CheckPlanController(PlanBLService pbs) {
		this.planBLService = pbs;
		// currentUserID = CurrentUserStatus.getInstance().getCurrentUserID();
	}

	public CheckPlanPanel getCheckPlanPanel(PlanVO planVO) {
		if (checkPlanPanel == null) {
			creatCheckPlanPanel(planVO);
		} else {
			initCheckPlanPanel(planVO);
		}

		return checkPlanPanel;
	}

	public void creatCheckPlanPanel(PlanVO planVO) {
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

		checkPlanPanel = new CheckPlanPanel(Constant.PLANBAR_NUM,
				planVO.startDate, planVO.endDate, allItems, CurrentUserStatus
						.getInstance().getCurrentUserID(), this);
		planID = planVO.planID;
	}

	public void initCheckPlanPanel(PlanVO planVO) {
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

		checkPlanPanel.initOldPlan(Constant.PLANBAR_NUM, planVO.startDate,
				planVO.endDate, allItems, CurrentUserStatus.getInstance()
						.getCurrentUserID());
		planID = planVO.planID;
	}

	public boolean addFavourite(int userID, int planItemID) {
		if (planBLService.checkFavourite(userID, planItemID)) {
			return false;
		} else {
			return planBLService.addFavourite(userID, planItemID);
		}
	}

	public void setCheckPlanPanelVisible(boolean b) {
		checkPlanPanel.setVisible(b);
	}

	public void toPlanHomePanel() {
		ControllerFactory.getInstance().homepageController.setTab(3,
				ControllerFactory.getInstance().planHomeController
						.getPlanHomePanel());
	}

	public boolean checkApplause() {
		return planBLService.checkApplause(CurrentUserStatus.getInstance()
				.getCurrentUserID(), planID);
	}

	public boolean addApplause() {
		return planBLService.addApplause(CurrentUserStatus.getInstance()
				.getCurrentUserID(), planID);
	}

	public boolean deleteApplause() {
		return planBLService.deleteApplause(CurrentUserStatus.getInstance()
				.getCurrentUserID(), planID);
	}
}
