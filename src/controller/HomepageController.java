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
	private UserManageController loginRegisterController;// ����������½��ע��frame
	private RecommandBLService recommandBLService;

	public HomepageController(UserManageBLService userManageBLService) {
		loginRegisterController = new UserManageController(userManageBLService);
		/* ��ʵ���룬������׮��ʱע���� */
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

	// ���ڵ��ĳ��item�����ת
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
 * ��ûʵ�֣�������
 * @author SongShuo
 * @param lip
 */
	public void recommandLandmarkItemClicked(LandmarkItemPanel lip) {
		int cityID = lip.place.cityID;
		ControllerFactory.getInstance().landmarkController.toCityPanel(cityID);
	}

	/**
	 * 
	 * @return ArrayList<MyPlanVO> ��blservice��ȡ�ҵļƻ���ת����VO Ŀǰ��׮
	 */
	public ArrayList<PlanVO> addMyPlan() {
		ArrayList<PlanVO> myPlanList = new ArrayList<PlanVO>();
		// /**ʵ�ʴ���
		
		// */
		// stub:
		/*
		 * myPlanList.add(new PlanVO("�����Լ���")); myPlanList.add(new
		 * PlanVO("����˹���Ĵ���")); myPlanList.add(new PlanVO("��ɽ֮��"));
		 * myPlanList.add(new PlanVO("������Ӱ����ػ���")); myPlanList.add(new
		 * PlanVO("����ʥĸԺ")); myPlanList.add(new PlanVO("����ͥԺ"));
		 * myPlanList.add(new PlanVO("���ڻ��ȳ�")); myPlanList.add(new
		 * PlanVO("�����Ӱ")); myPlanList.add(new PlanVO("޹�²ݵĹ�������֮��"));
		 * myPlanList.add(new PlanVO("޹�²ݵĹ�������֮��")); //end stub
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
	 * @return ArrayList<RecommandedPanel> ��blservice��ȡ�Ƽ��ص�����ݣ�ת����Сpanel��Ԫ�� Ŀǰ��׮
	 *         5.24
	 */
	public ArrayList<LandmarkItemPanel> addRecommandLandmark() {

		ArrayList<LandmarkItemPanel> recommandPanelList = new ArrayList<LandmarkItemPanel>();

		// /**ʵ�ʴ���
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
		 * recommandPanelList .add(new RecommandedPanel("÷��ɽ", true, 1800, "�Ͼ�",
		 * "���ⰻȻ")); recommandPanelList .add(new RecommandedPanel("÷��ɽ", true,
		 * 1800, "�Ͼ�", "���ⰻȻ")); recommandPanelList .add(new
		 * RecommandedPanel("÷��ɽ", true, 1800, "�Ͼ�", "���ⰻȻ"));
		 * recommandPanelList .add(new RecommandedPanel("÷��ɽ", true, 1800, "�Ͼ�",
		 * "���ⰻȻ")); recommandPanelList .add(new RecommandedPanel("÷��ɽ", true,
		 * 1800, "�Ͼ�", "���ⰻȻ")); //end stub
		 */
		return recommandPanelList;
	}

	/**
	 * 
	 * @return ArrayList<ShowBlogPanel> ��BLSERVICE ��ȡ�Ƽ�blog�����ݣ�ת����Сpanel
	 */
	public ArrayList<ShowBlogPanel> addRecommandBlog() {
		ArrayList<ShowBlogPanel> recommandBlogList = new ArrayList<ShowBlogPanel>();
		// /**ʵ�ʴ���
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
		 * recommandBlogList.add(new ShowBlogPanel("����֮��", 12390));
		 * recommandBlogList.add(new ShowBlogPanel("����֮��", 90));
		 * recommandBlogList.add(new ShowBlogPanel("����de����", 90));
		 * recommandBlogList.add(new ShowBlogPanel("����de����", 90));
		 * recommandBlogList.add(new ShowBlogPanel("����de����", 90)); //end stub
		 */
		return recommandBlogList;
	}

	/**
	 * ��blservice������ݣ�Ŀǰ��׮
	 * 
	 * @return
	 */
	public ArrayList<PlanVO> addRecommandPlan() {

		ArrayList<PlanVO> recommandPlanList = new ArrayList<PlanVO>();
		// /** ʵ�ʴ���
		recommandPlanList = recommandBLService.recommandPlan();
		// */

		/**
		 * stub: recommandPlanList.add(new PlanVO("̨��Ի�����", 14230));
		 * recommandPlanList.add(new PlanVO("�Ͼ��Ի�����", 13230));
		 * recommandPlanList.add(new PlanVO("�ձ��Ի�����", 12230));
		 * recommandPlanList.add(new PlanVO("�����Ի�����", 11230));
		 * recommandPlanList.add(new PlanVO("���ųԻ�����", 10230));
		 * recommandPlanList.add(new PlanVO("���ݳԻ�����", 9230));
		 * recommandPlanList.add(new PlanVO("���ĳԻ�����", 8230));
		 * recommandPlanList.add(new PlanVO("�ɶ��Ի�����", 7230));
		 * recommandPlanList.add(new PlanVO("����Ի�����", 6230));
		 * recommandPlanList.add(new PlanVO("�ػʵ��Ի�����", 5230)); /**end stub
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
	 *            �����õ�main
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
