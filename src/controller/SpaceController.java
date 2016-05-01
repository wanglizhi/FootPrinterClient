package controller;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import startup.ControllerFactory;
import ui.space.SpacePanel;
import vo.BlogIntroductionVO;
import vo.BlogVO;
import vo.PlanIntroductionVO;
import vo.PlanVO;
import businessLogicService.SpaceBLService;

public class SpaceController {
	
	private SpacePanel spacePanel;

	private SpaceBLService spaceBLService;

	public SpaceController(SpaceBLService spaceBLService) {
		this.spaceBLService = spaceBLService;
	}

	public ArrayList<BlogIntroductionVO> getBlogs() {
		return spaceBLService.getBlogs();
	}

	public ArrayList<PlanIntroductionVO> getPlans() {
		return spaceBLService.getPlans();
	}

	public ImageIcon getMap(int zoom) {
		return spaceBLService.getMap(zoom);
	}
	
	public void changeCenter(String center, int zoom){
		spaceBLService.changeCenter(center, zoom);
		return;
	}
	
	public SpacePanel getSpacePanel(){
		if (spacePanel == null)
			spacePanel = new SpacePanel(this);
		spacePanel.updateSpace();
		return spacePanel;
	}
	
	public void changeMapType(String type, int zoom){
		spaceBLService.changeMapType(type, zoom);
		return;
	}
	
	public void toMyPlanPanel(){
		ControllerFactory.getInstance().homepageController.setTab(3, ControllerFactory.getInstance().planHomeController.getMyPlanPanel());
	}
	
	public void toMyBlogPanel(){
		ControllerFactory.getInstance().homepageController.setTab(4, ControllerFactory.getInstance().blogHomeController.getMyBlogPanel());
	}

	// 查看计划
	public void toCheckPlanPanel(PlanVO planVO){
		ControllerFactory.getInstance().homepageController.setTab(3, ControllerFactory.getInstance().planController.getNewPlanPanel(planVO));
	}

	// 查看博客
	public void toCheckBlogPanel(BlogVO blogVO){
		ControllerFactory.getInstance().homepageController.setTab(3, ControllerFactory.getInstance().blogController.getPanel(blogVO));
	}
}
