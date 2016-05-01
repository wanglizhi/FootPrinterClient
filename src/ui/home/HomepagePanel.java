
package ui.home;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.Debugger;
import ui.foundation.BasePanel;
import ui.landmark.LandmarkItemPanel;
import vo.PlanVO;
import controller.HomepageController;

public class HomepagePanel extends BasePanel{
	
	private HomepageController homepageController;
	
	private JPanel leftupPanel;
	private JPanel leftdownPanel;
	private JLabel leftmiddleLabel;
	private JLabel leftupLabel;
	private JLabel copyrightLabel;
	private JLabel rightmiddleLabel;
	private JLabel rightupLabel;
	private JPanel rightdownPanel;
	private JPanel rightupPanel;
	
	
	private ArrayList<PlanVO> myPlanList = new ArrayList<PlanVO>();
	private ArrayList<PlanVO> recommandPlanList = new ArrayList<PlanVO>();
	private ArrayList<ShowBlogPanel> recommandBlogList = new ArrayList<ShowBlogPanel>();
	private ArrayList<LandmarkItemPanel> recommandLandmarkList = new ArrayList<LandmarkItemPanel>();

	public static final int DISPLAY_MYPLAN_NUM = 8;
	public static final int DISPLAY_RecommandPlan_NUM = 3;
	public static final int DISPLAY_BLOG_NUM = 6;
	public static final int DISPLAY_RECOMMAND_LANDMARK_NUM = 4;
	
	
	public HomepagePanel(HomepageController hpc){
		
		this.homepageController = hpc;
		this.setPreferredSize(new Dimension(1366,668));
		this.setLayout(null);
		this.setBackground(new java.awt.Color(255,255,255));
		{
			leftupPanel = new JPanel();
			BoxLayout leftupPanelLayout = new BoxLayout(leftupPanel, javax.swing.BoxLayout.Y_AXIS);
			this.add(leftupPanel);
			leftupPanel.setLayout(leftupPanelLayout);
			leftupPanel.setBounds(43, 65, 311, 228);
			leftupPanel.setBackground(new java.awt.Color(255,255,255));
			leftupPanel.setForeground(UIHelper.getGray());
		}
		{
			leftdownPanel = new JPanel();
			GridLayout leftdownPanelLayout = new GridLayout(DISPLAY_RecommandPlan_NUM+2,2,0,0);
			leftdownPanel.setLayout(leftdownPanelLayout);
			this.add(leftdownPanel);
			leftdownPanel.setBounds(43, 327, 311, 271);
			leftdownPanel.setBackground(new java.awt.Color(255,255,255));
		}
		{
			rightupPanel = createHorizontalPanel(false);
			this.add(rightupPanel);
			rightupPanel.setBounds(403, 64, 914, 228);
			rightupPanel.setBackground(new java.awt.Color(255,255,255));
		}
		{
			rightdownPanel = createHorizontalPanel(false);
			//BoxLayout rightdownPanelLayout = new BoxLayout(rightdownPanel, javax.swing.BoxLayout.X_AXIS);
			//rightdownPanel.setLayout(rightdownPanelLayout);
			this.add(rightdownPanel);
			rightdownPanel.setBounds(403, 327, 914, 271);
			rightdownPanel.setBackground(new java.awt.Color(255,255,255));
		}
		{
			rightupLabel = new JLabel();
			this.add(rightupLabel);
			rightupLabel.setText("你可能对这些内容感兴趣...");
			rightupLabel.setBounds(403, 39, 267, 20);
			rightupLabel.setForeground(UIHelper.getUnSelectedColor());
			rightupLabel.setFont(new java.awt.Font("微软雅黑",1,20));
		}
		{
			rightmiddleLabel = new JLabel();
			this.add(rightmiddleLabel);
			rightmiddleLabel.setText("我们为您推荐...");
			rightmiddleLabel.setBounds(403, 302, 181, 20);
			rightmiddleLabel.setForeground(UIHelper.getUnSelectedColor());
			rightmiddleLabel.setFont(new java.awt.Font("微软雅黑",1,20));
		}
		{
			leftmiddleLabel = new JLabel();
			this.add(leftmiddleLabel);
			leftmiddleLabel.setText("热门计划...");
			leftmiddleLabel.setBounds(43, 304, 121, 20);
			leftmiddleLabel.setFont(new java.awt.Font("微软雅黑",1,20));
			leftmiddleLabel.setForeground(UIHelper.getUnSelectedColor());
		}
		{
			copyrightLabel = new JLabel();
			this.add(copyrightLabel);
			copyrightLabel.setText("copyright@ firework nju 2013");
			copyrightLabel.setBounds(620, 604, 216, 20);
			copyrightLabel.setFont(new java.awt.Font("微软雅黑",0,12));
		}
		{
			leftupLabel = new JLabel();
			this.add(leftupLabel);
			leftupLabel.setText("我的计划");
			leftupLabel.setBounds(43, 41, 148, 20);
			leftupLabel.setFont(new java.awt.Font("微软雅黑",1,20));
			leftupLabel.setForeground(UIHelper.getUnSelectedColor());
		}
		addToPlanList();
		displayPlanList();
		
		addToRecommandLandmarkList();
		displayRecommandLandmarkList();
		
		addToRecommandPlanList();
		displayRecommandPlanList();
		
		addToRecommandBlogList();
		displayRecommandBlogList();
	}

	/**
 * 从数据库读入我的plan。 现在写的用于测试	
 */
	private void addToPlanList(){
		myPlanList = homepageController.addMyPlan();

	}
	
	/**
 * @author SongShuo
 * 从数据库读入推荐景点。现在用于测试
 */

	private void addToRecommandLandmarkList(){
	//调用Hompagecontroller addRecommandPlan
	recommandLandmarkList = homepageController.addRecommandLandmark();
}

	
	private void addToRecommandPlanList(){
		recommandPlanList = homepageController.addRecommandPlan();
	}

	private void addToRecommandBlogList(){
		recommandBlogList = homepageController.addRecommandBlog();
	}
	/**
	 * 
	 * @return 显示了多少个plan，有可能所有的plan不能全部显示出来,最多可以显示5个
	 */
	
	private int displayPlanList(){
		int showNum = 0;
		if(myPlanList==null){
			Debugger.log("!!!!!!!!!!recommandplanlist==null!!!!!!!!!!");
		}
		else{
		showNum = Math.min(myPlanList.size(),DISPLAY_MYPLAN_NUM);
//**********ATTENTION 要在匿名类里面使用，要讲其设为final
		final Font planFont = UIHelper.getUnderlinedFont(18,"微软雅黑");
		final Font planSelectedFont = UIHelper.getUnderlinedFont(22, "幼圆");
		
		for(int i = 0; i< showNum; i++){
			String title = myPlanList.get(i).title;
			final JLabel tempLabel = new JLabel(title);
			final int mark = i;
			tempLabel.setFont(planFont);
			tempLabel.setForeground(UIHelper.getGray());
			tempLabel.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					tempLabel.setFont(planSelectedFont);
					homepageController.myPlanItemClicked(myPlanList.get(mark));
					HomepagePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					tempLabel.setFont(planSelectedFont);	
					HomepagePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					tempLabel.setFont(planFont);
					HomepagePanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					tempLabel.setFont(planSelectedFont);
					HomepagePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					tempLabel.setFont(planFont);
					HomepagePanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				
			});
			leftupPanel.add(tempLabel);
		}
		}
		return showNum;
	}

	private int displayRecommandPlanList(){
		int showNum = Math.min(recommandPlanList.size(),DISPLAY_RecommandPlan_NUM);
		final Font planFont = UIHelper.getUnderlinedFont(18,"微软雅黑");
		final Font planSelectedFont = UIHelper.getUnderlinedFont(22, "幼圆");
		final Font RecommandFont =new  Font("微软雅黑",Font.PLAIN,14);
		
		for(int i = 0; i< showNum; i++){
			String title = recommandPlanList.get(i).title ;
			String like = "热度:"+recommandPlanList.get(i).applauseNum;
			final JLabel titleLabel = new JLabel(title);
			final JLabel likeLabel = new JLabel(like);
			final int mark = i;
			titleLabel.setFont(planFont);
			titleLabel.setForeground(UIHelper.getGray());
			likeLabel.setFont(RecommandFont);
			likeLabel.setForeground(UIHelper.getGray());
			
			titleLabel.addMouseListener(new MouseListener(){
	
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					titleLabel.setFont(planSelectedFont);
					HomepagePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
					homepageController.recommandPlanItemClicked(recommandPlanList.get(mark));
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
					titleLabel.setFont(planSelectedFont);	
					HomepagePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseReleased(MouseEvent e) {
					titleLabel.setFont(planFont);
					HomepagePanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
	
				@Override
				public void mouseEntered(MouseEvent e) {
					titleLabel.setFont(planSelectedFont);
					HomepagePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					titleLabel.setFont(planFont);
					HomepagePanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				
			});
			leftdownPanel.add(titleLabel);
			leftdownPanel.add(likeLabel);
			
		}

	
		return showNum;
	}

	private int displayRecommandBlogList(){
		int showNum = Math.min(recommandBlogList.size(), DISPLAY_BLOG_NUM);
		for(int i = 0; i<showNum;i++){
			final ShowBlogPanel temp = recommandBlogList.get(i);
			final int mark = i;
			rightupPanel.add(temp);
			temp.addMouseListener(new MouseListener(){
				
				@Override
				public void mouseClicked(MouseEvent e) {
					temp.changeClickState(true);
					
					homepageController.recommandBlogItemClicked(recommandBlogList.get(mark).getBlogVO());
					HomepagePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
					temp.changeClickState(true);
					HomepagePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseReleased(MouseEvent e) {
					temp.changeClickState(false);
					HomepagePanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
	
				@Override
				public void mouseEntered(MouseEvent e) {
					temp.changeClickState(true);
					HomepagePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					temp.changeClickState(false);
					HomepagePanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				
			});
	
		}
		return showNum;
	}

	private int displayRecommandLandmarkList(){
		int showNum = Math.min(recommandLandmarkList.size(), DISPLAY_RECOMMAND_LANDMARK_NUM);
		
		for(int i = 0;i<showNum;i++){
			final LandmarkItemPanel temp = recommandLandmarkList.get(i);
			/*
			final int panelWidth = temp.getWidth();
			final int panelHeight = temp.getHeight();
			final Dimension clickedDimension = new Dimension((int)(panelWidth*1.1),(int)(panelHeight*1.1));
			final Dimension oldDimension = new Dimension(panelWidth,panelHeight);
			*/
			final int mark = i;
			temp.addMouseListener(new MouseListener(){
	
				@Override
				public void mouseClicked(MouseEvent e) {
//					temp.changeClickState(true);
					
					homepageController.recommandLandmarkItemClicked(recommandLandmarkList.get(mark));
					HomepagePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
//					temp.changeClickState(true);
					HomepagePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseReleased(MouseEvent e) {
//					temp.changeClickState(false);
					HomepagePanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
	
				@Override
				public void mouseEntered(MouseEvent e) {
//					temp.changeClickState(true);
					HomepagePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
//					temp.changeClickState(false);
					HomepagePanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				
			});
			rightdownPanel.add(temp);
			
		}
		return showNum;
	}
}
