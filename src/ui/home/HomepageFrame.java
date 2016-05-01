package ui.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import businessLogicService_imp.SpaceBLService_imp;

import tools.Config;
import tools.Debugger;
import ui.landmark.LandmarkHomePanel;
import ui.plan.PlanHomePanel;
import ui.space.SpacePanel;
import controller.HomepageController;
import controller.SpaceController;

public class HomepageFrame extends JFrame {

	JPanel rootPanel;

	public HeadPanel headPanel;// 为了方便界面跳转，将其设为public
	public JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	private HomepagePanel homepagePanel;
	private JLabel registerLabel;
	private String[] tabNameString = { "   主 页   ", "   空 间   ", "   景 点   ",
			"   计 划   " ,"   记 录   "};
	private ArrayList<JPanel> tabPanelList = new ArrayList<JPanel>();// 可以从别的面板注册到这个面板的arraylist里
	private HomepageController homepageController;

	public HomepageFrame(HomepageController homepageController) {
		this.homepageController = homepageController;

		rootPanel = new JPanel();
		rootPanel.setLayout(null);
		rootPanel.setBackground(Color.white);

		Font tabFont = new Font("微软雅黑", Font.PLAIN, 28);
		tabbedPane.setFont(tabFont);
		tabbedPane.setForeground(UIHelper.getSelectedColot());

		for (int i = 0; i < tabNameString.length; i++){
			tabPanelList.add(new JPanel());
			tabbedPane.addTab(tabNameString[i], tabPanelList.get(i));
		}
		
		this.homepageController.initHomeTab(this);
		
		headPanel = new HeadPanel(homepageController);
		headPanel.setBounds(0, 0, Config.PANEL_WIDTH, Config.HEAD_PANEL_HEIGHT);
		rootPanel.add(headPanel);
		headPanel.setBackground(new java.awt.Color(255, 255, 255));

		tabbedPane.setBounds(0, Config.HEAD_PANEL_HEIGHT, Config.PANEL_WIDTH, Config.PANEL_HEIGHT);

		rootPanel.add(tabbedPane);
		this.add(rootPanel);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//this.setDefaultCloseOperation(ERROR);
		this.setUndecorated(true);
		this.setVisible(true);
		this.setBackground(Color.white);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		
	}

	public void setTabs(ArrayList<JPanel> panelList){
		
		for (int i = 0; i < tabNameString.length; i++){
			Debugger.log("before tab"+i+"init"+Calendar.getInstance().getTimeInMillis());
			setTab(i, panelList.get(i));
			Debugger.log("after tab"+i+"init"+Calendar.getInstance().getTimeInMillis());
		}
	}
	
	public void setTab(int index, JComponent p){
		tabbedPane.setSelectedIndex(index);
		tabPanelList.get(index).removeAll();
		tabPanelList.get(index).revalidate();
		tabPanelList.get(index).repaint();
		tabPanelList.get(index).setLayout(null);
		p.setBounds(0, 0, Config.PANEL_WIDTH, Config.PANEL_HEIGHT - 80 );
		tabPanelList.get(index).add(p);
		tabPanelList.get(index).revalidate();
		tabPanelList.get(index).repaint();
	}
	
	public JTabbedPane getTabbedPane(){
		return tabbedPane;
	}
	
	public HeadPanel getHeadPanel(){
		return headPanel;
	}
}
