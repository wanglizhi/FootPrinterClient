package controller;

import java.awt.Dimension;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import dataService.PlanDataService;

import startup.BLServiceFactory;
import startup.ClientNetworkInit;
import startup.ControllerFactory;
import startup.CurrentUserStatus;
import tools.Debugger;
import ui.home.HomepageFrame;
import ui.home.HomepagePanel;
import ui.home.RecommandedPanel;
import ui.home.ShowBlogPanel;
import ui.landmark.LandmarkHomePanel;
import ui.landmark.LandmarkItemPanel;
import ui.space.SpacePanel;
import vo.BlogVO;
import vo.LandmarkVO;
import vo.PlaceVO;
import vo.PlanVO;
import bean.PlanPO;
import businessLogicService.RecommandBLService;
import businessLogicService.UserManageBLService;
import businessLogicService_imp.PlanBLService_imp;
import businessLogicService_imp.PlanHomeBLService_imp;
import businessLogicService_imp.RecommandBLService_imp;

public class HomepageController {

	private HomepageFrame homepageFrame;
	private UserManageController loginRegisterController;// 用来触发登陆和注册frame
	private RecommandBLService recommandBLService;

	public HomepageController(UserManageBLService userManageBLService) {
		loginRegisterController = new UserManageController(userManageBLService);
		/* 真实代码，现在是桩暂时注销掉 */
		recommandBLService = BLServiceFactory.getInstance().recommandBLService;

	}

	public void initHomeTab(final HomepageFrame homepageFrame) {
		final ControllerFactory controllers = ControllerFactory.getInstance();
		final HomepagePanel hp = new HomepagePanel(this);
////		SpacePanel sp = controllers.spaceController.getSpacePanel();
////		LandmarkHomePanel lp = controllers.landmarkController
////				.getLandmarkHomePanel();
////		JPanel pp = controllers.planHomeController.getPlanHomePanel();
////		JPanel bp = controllers.blogHomeController.getBlogHomePanel();
//		
//		SpacePanel sp = new JPanel();
//		LandmarkHomePanel lp = new JPanel();
//		JPanel pp = controllers.planHomeController.getPlanHomePanel();
//		JPanel bp = controllers.blogHomeController.getBlogHomePanel();
		
		final ArrayList<JPanel> tabPaneList = new ArrayList<JPanel>();
		tabPaneList.add(hp);
//		tabPaneList.add(sp);
//		tabPaneList.add(lp);
//		tabPaneList.add(pp);
//		tabPaneList.add(bp);
		for (int i = 0; i < 4; i++)
			tabPaneList.add(new JPanel());
		homepageFrame.setTabs(tabPaneList);

		final JTabbedPane tabbedPane = homepageFrame.getTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int index = tabbedPane.getSelectedIndex();
//				homepageFrame.setTab(index, tabPaneList.get(index));
//				if (index == 2)
//					ControllerFactory.getInstance().landmarkController
//							.getLandmarkHomePanel().refreshHome();
				Debugger.log("" + (tabPaneList.get(0) == null));
				switch(tabbedPane.getSelectedIndex()){
					case 0 : homepageFrame.setTab(0, tabPaneList.get(0)); addMyPlan(); break;
					case 1 : homepageFrame.setTab(1, controllers.spaceController.getSpacePanel()); break;
					case 2 : homepageFrame.setTab(2, controllers.landmarkController.getLandmarkHomePanel()); controllers.landmarkController.getLandmarkHomePanel().refreshHome(); break;
					case 3 : homepageFrame.setTab(3, controllers.planHomeController.getPlanHomePanel()); break;
					case 4 : homepageFrame.setTab(4, controllers.blogHomeController.getBlogHomePanel()); break;
				}
			}
		});

		tabbedPane.setSelectedIndex(0);
	}

	public void loginButtonClicked() {
		loginRegisterController.createLoginFrame();
	}

	public void userNameLabelClicked() {
		loginRegisterController.createModifyUserFrame();
	}

	public void registerButtonClicked() {

		loginRegisterController.createRegisterFrame();
	}

	public void myPlanButtonClicked() {
		homepageFrame.setTab(3,
				ControllerFactory.getInstance().planHomeController
						.getMyPlanPanel());
	}

	public void myBlogButtonClicked() {
		homepageFrame.setTab(4,
				ControllerFactory.getInstance().blogHomeController
						.getMyBlogPanel());
	}

	// 用于点击某个item后的跳转
	public void myPlanItemClicked(PlanVO planVO) {
		setTab(3,
				ControllerFactory.getInstance().planController
						.getOldPlanPanel(planVO));
	}

	public void recommandPlanItemClicked(PlanVO planVO) {
		Debugger.log("startDate:" + (planVO.startDate == null));
		setTab(3,
				ControllerFactory.getInstance().checkPlanController
						.getCheckPlanPanel(planVO));
	}

	public void recommandBlogItemClicked(BlogVO blogVO) {
		setTab(3, ControllerFactory.getInstance().blogShowController.getPanel(
				blogVO.blogID, blogVO.userID));
	}
/**
 * 还没实现！！！！
 * @author SongShuo
 * @param lip
 */
	public void recommandLandmarkItemClicked(LandmarkItemPanel lip) {
		int cityID = lip.place.cityID;
		ControllerFactory.getInstance().landmarkController.toCityPanel(cityID);
	}

	/**
	 * 
	 * @return ArrayList<MyPlanVO> 从blservice获取我的计划，转换成VO 目前是桩
	 */
	public ArrayList<PlanVO> addMyPlan() {
		ArrayList<PlanVO> myPlanList = new ArrayList<PlanVO>();
		// /**实际代码
		
		// */
		// stub:
		/*
		 * myPlanList.add(new PlanVO("西藏自驾游")); myPlanList.add(new
		 * PlanVO("普罗斯旺的春天")); myPlanList.add(new PlanVO("香山之秋"));
		 * myPlanList.add(new PlanVO("桨声灯影里的秦淮河")); myPlanList.add(new
		 * PlanVO("巴黎圣母院")); myPlanList.add(new PlanVO("苏州庭院"));
		 * myPlanList.add(new PlanVO("深圳华侨城")); myPlanList.add(new
		 * PlanVO("月球光影")); myPlanList.add(new PlanVO("薰衣草的故乡浪漫之旅"));
		 * myPlanList.add(new PlanVO("薰衣草的故乡浪漫之旅")); //end stub
		 */
		
		PlanDataService planDataService = ClientNetworkInit.planDataService;
		ArrayList<PlanPO> pos = new ArrayList<PlanPO>();
		try {
			pos = planDataService.searchByUser(CurrentUserStatus.getInstance().getCurrentUserID());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Debugger.log("userID:" + CurrentUserStatus.getInstance().getCurrentUserID() + " pos.size():" + pos.size());
		
		for (int i = 0; i < pos.size(); i++){
			if (i >= 6) break;
			myPlanList.add(PlanHomeBLService_imp.toIntroduction(pos.get(i)).planVO);
		}
		
		Debugger.log("myPlabList:" + myPlanList.size());
//		Debugger.log("NULL :" + (myPlanList.get(0).startDate == null));
		
		return myPlanList;

	}

	/**
	 * 
	 * @return ArrayList<RecommandedPanel> 从blservice获取推荐地点的数据，转换成小panel单元， 目前是桩
	 *         5.24
	 */
	public ArrayList<LandmarkItemPanel> addRecommandLandmark() {

		ArrayList<LandmarkItemPanel> recommandPanelList = new ArrayList<LandmarkItemPanel>();

		// /**实际代码
		ArrayList<PlaceVO> recommandVOList = recommandBLService
				.recommandLandmark();
		for(int i = 0; i<recommandVOList.size() ; i++){
			LandmarkItemPanel lipanel = new LandmarkItemPanel(recommandVOList.get(i));
			lipanel.setPreferredSize(new Dimension(LandmarkItemPanel.WIDTH,LandmarkItemPanel.HEIGHT));
			recommandPanelList.add(lipanel);
		}
		Debugger.log(recommandPanelList.size()+"!!recommandpanellist size");
		// */
		// stub
		/*
		 * recommandPanelList .add(new RecommandedPanel("梅花山", true, 1800, "南京",
		 * "春意盎然")); recommandPanelList .add(new RecommandedPanel("梅花山", true,
		 * 1800, "南京", "春意盎然")); recommandPanelList .add(new
		 * RecommandedPanel("梅花山", true, 1800, "南京", "春意盎然"));
		 * recommandPanelList .add(new RecommandedPanel("梅花山", true, 1800, "南京",
		 * "春意盎然")); recommandPanelList .add(new RecommandedPanel("梅花山", true,
		 * 1800, "南京", "春意盎然")); //end stub
		 */
		return recommandPanelList;
	}

	/**
	 * 
	 * @return ArrayList<ShowBlogPanel> 从BLSERVICE 获取推荐blog的数据，转换成小panel
	 */
	public ArrayList<ShowBlogPanel> addRecommandBlog() {
		ArrayList<ShowBlogPanel> recommandBlogList = new ArrayList<ShowBlogPanel>();
		// /**实际代码
		ArrayList<BlogVO> blogVOList = recommandBLService.recommandBlog();
		if (blogVOList == null) {
			Debugger.log("recommandlist null");
		} else {
			for (int i = 0; i < blogVOList.size(); i++) {
				recommandBlogList.add(VOToPanel(blogVOList.get(i)));
			}
		}
		// */
		// stub:
		/*
		 * recommandBlogList.add(new ShowBlogPanel("西藏之旅", 12390));
		 * recommandBlogList.add(new ShowBlogPanel("西藏之旅", 90));
		 * recommandBlogList.add(new ShowBlogPanel("西藏de旅行", 90));
		 * recommandBlogList.add(new ShowBlogPanel("西藏de旅行", 90));
		 * recommandBlogList.add(new ShowBlogPanel("西藏de旅行", 90)); //end stub
		 */
		return recommandBlogList;
	}

	/**
	 * 从blservice获得数据，目前是桩
	 * 
	 * @return
	 */
	public ArrayList<PlanVO> addRecommandPlan() {

		ArrayList<PlanVO> recommandPlanList = new ArrayList<PlanVO>();
		// /** 实际代码
		recommandPlanList = recommandBLService.recommandPlan();
		// */

		/**
		 * stub: recommandPlanList.add(new PlanVO("台湾吃货攻略", 14230));
		 * recommandPlanList.add(new PlanVO("南京吃货攻略", 13230));
		 * recommandPlanList.add(new PlanVO("日本吃货攻略", 12230));
		 * recommandPlanList.add(new PlanVO("西安吃货攻略", 11230));
		 * recommandPlanList.add(new PlanVO("厦门吃货攻略", 10230));
		 * recommandPlanList.add(new PlanVO("苏州吃货攻略", 9230));
		 * recommandPlanList.add(new PlanVO("宁夏吃货攻略", 8230));
		 * recommandPlanList.add(new PlanVO("成都吃货攻略", 7230));
		 * recommandPlanList.add(new PlanVO("重庆吃货攻略", 6230));
		 * recommandPlanList.add(new PlanVO("秦皇岛吃货攻略", 5230)); /**end stub
		 */
		
		RecommandBLService recommandBLService = BLServiceFactory.getInstance().recommandBLService;
		recommandPlanList = recommandBLService.recommandPlan();
		
		return recommandPlanList;
	}

	public HomepageFrame getHomepageFrame() {
		if (homepageFrame == null)
			homepageFrame = new HomepageFrame(this);
		return homepageFrame;
	}

	public void setHomepageFrame(HomepageFrame homepageFrame) {
		this.homepageFrame = homepageFrame;

	}

	public void setTab(int index, JComponent p) {
		homepageFrame.setTab(index, p);
	}

	private ShowBlogPanel VOToPanel(BlogVO bVO) {
		ShowBlogPanel sbp = new ShowBlogPanel(bVO);
		return sbp;
	}

	private RecommandedPanel VOToPanel(LandmarkVO lmv) {
		RecommandedPanel rp = new RecommandedPanel(lmv);

		return rp;
	}

	/**
	 * 
	 * @param args
	 *            测试用的main
	 */
	public static void main(String args[]) {

		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
		} catch (Exception e) {

		}

		ControllerFactory.getInstance().homepageController.getHomepageFrame();
	}

}
