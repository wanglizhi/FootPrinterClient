package controller;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import startup.ControllerFactory;
import startup.CurrentUserStatus;
import tools.Debugger;
import ui.plan.MyPlanPanel;
import ui.plan.PlanHomePanel;
import vo.PlanIntroductionVO;
import vo.PlanVO;
import businessLogicService.PlanHomeBLService;

public class PlanHomeController {

	int currentPage;
	int allPageNum;

	PlanHomeBLService planHomeBLService;

	PlanHomePanel planHomePanel;
	MyPlanPanel myPlanPanel;

	// ControllerFactory controllers;

	public PlanHomeController(PlanHomeBLService planHomeBLService) {

		this.currentPage = 1;
		this.allPageNum = 1;

		this.planHomeBLService = planHomeBLService;
		
//		Debugger.log(" planhome : " + (planHomeBLService == null));

		// controllers = ControllerFactory.getInstance();
	}

	public ArrayList<PlanIntroductionVO> search(String key) {
		
		planHomeBLService.search(key);
		this.currentPage = 1;
		this.allPageNum = planHomeBLService.getPagesNum();
		return planHomeBLService.getPage(1);
	}

	public ArrayList<PlanIntroductionVO> nextPage() {
		return planHomeBLService.getPage(++currentPage);
	}

	public ArrayList<PlanIntroductionVO> prevPage() {
		return planHomeBLService.getPage(--currentPage);
	}

	public int getPagesNum() {
		return allPageNum;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public ArrayList<PlanIntroductionVO> searchByDate(Date date) {
		planHomeBLService.searchByDate(date);
		this.currentPage = 1;
		this.allPageNum = planHomeBLService.getPagesNum();
		return planHomeBLService.getPage(1);
	}

	public ImageIcon getMap() {
		return planHomeBLService.getMap(currentPage);
	}

	public ImageIcon getMap(String marker) {
		return planHomeBLService.getMap(marker);
	}

	// myPlan

	public ArrayList<PlanIntroductionVO> searchByUser(int userID) {
		planHomeBLService.searchByUser(userID);
		return planHomeBLService.getAllPages();
	}

	public ArrayList<PlanIntroductionVO> getAllPlanIntroductions() {
		return planHomeBLService.getAllPages();
	}

	public JPanel getPlanHomePanel() {
		if (planHomePanel == null)
			planHomePanel = new PlanHomePanel(this);
		return planHomePanel;
	}

	public MyPlanPanel getMyPlanPanel() {
		if (myPlanPanel == null)
			myPlanPanel = new MyPlanPanel(this);
		updateMyPlans();
		return myPlanPanel;
	}

	// 新建计划
	public void toNewPlanPanel(PlanVO planVO) {
		ControllerFactory.getInstance().homepageController.setTab(3,
				ControllerFactory.getInstance().planController.getNewPlanPanel(planVO));
	}

	// 查看自己的计划
	public void toOldPlanPanel(PlanVO planVO) {
		ControllerFactory.getInstance().homepageController.setTab(3,
				ControllerFactory.getInstance().planController.getOldPlanPanel(planVO));

	}

	// 查看别人的计划
	public void toCheckPlanPanel(PlanVO planVO) {
		ControllerFactory.getInstance().homepageController.setTab(3,
				ControllerFactory.getInstance().checkPlanController.getCheckPlanPanel(planVO));
	}

	public void updateMyPlans(){
		myPlanPanel.refreshMyPlanResults(searchByUser(CurrentUserStatus.getInstance().getCurrentUserID()));
	}
	
	public void deletePlan(int planID){
		planHomeBLService.delete(planID);
		updateMyPlans();
	}
}
