package ui.plan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import startup.ControllerFactory;
import startup.CurrentUserStatus;
import tools.Config;
import tools.Debugger;
import ui.blog.BlogLabel;
import ui.foundation.BasePanel;
import ui.foundation.Timeline;
import ui.foundation.UserIntroductionPanel;
import vo.PlanIntroductionVO;
import vo.TimelineVO;
import businessLogicService_imp.PlanHomeBLService_imp;
import controller.PlanHomeController;

public class MyPlanPanel extends BasePanel {


	JPanel introductionPanel;
	JPanel allPlanPanel;
	ArrayList<JComponent> myPlanResults = new ArrayList<JComponent>();

	Timeline timeline;

	JButton addNewPlan;

	PlanHomeController planHomeController;
	
	JFrame newPlanFrame;
	
	JPopupMenu deletePop;

	//sharedValue
	int deletePlanID;

	public MyPlanPanel(final PlanHomeController planHomeController) {

		this.planHomeController = planHomeController;

		// init begin
		introductionPanel = createHorizontalPanel(true);

		timeline = new Timeline(new TimelineVO(initDataVOs()));

		allPlanPanel = createVerticalPanel(false);

		// init and add
		addNewPlan = createImageButton("img/newPlan.png");
		initAllPlanPanel();

		NewPlan newPlan = new NewPlan(planHomeController);
		newPlanFrame = createSurroundFrame(newPlan);
		newPlan.setFrame(newPlanFrame);
		newPlanFrame.setVisible(false);
		
		Debugger.log("blogHomePanel init end");
		deletePop = new JPopupMenu();
		JMenuItem i1 = new JMenuItem("É¾³ý");
		deletePop.add(i1);
		
		// init end
		// listener begin

		i1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				planHomeController.deletePlan(deletePlanID);
			}
		});
		
		timeline.getSlider().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				if (!slider.getValueIsAdjusting()) {
					int value = slider.getValue();
					Date date = timeline.getDate(value);
					planHomeController.searchByDate(date);
					ArrayList<PlanIntroductionVO> myPlanVOs = planHomeController
							.getAllPlanIntroductions();

					refreshMyPlanResults(myPlanVOs);

				}
			}
		});
		
		addNewPlan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newPlanFrame.setVisible(true);
			}
		});

		// listener end
		// add begin

		JPanel center = new JPanel();
		center.setLayout(null);
		JButton returnHome = createColorButton(BEButtonUI.NormalColor.lightBlue, Color.white, "·µ»ØËÑË÷");
		returnHome.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ControllerFactory.getInstance().checkPlanController.toPlanHomePanel();
				
			}
		});
		returnHome.setBounds(20, 20, 70, 30);
		timeline.setBounds(0, 50, 100, 560);
		center.add(returnHome);
		center.add(timeline);
		JScrollPane sp = new JScrollPane(allPlanPanel);
		sp.setBounds(110, 0, 890, 610);
		center.add(sp);
		
//		leftPanel.add(east);
//		leftPanel.add(Box.createRigidArea(HGAP25));
//		leftPanel.add(createScrollPane(allPlanPanel));

		introductionPanel.add(Box.createRigidArea(HGAP30));
		introductionPanel.add(new UserIntroductionPanel());
		introductionPanel.add(Box.createRigidArea(HGAP30));

		this.add(center, BorderLayout.CENTER);
//		this.add(createScrollPane(allPlanPanel), BorderLayout.CENTER);
		this.add(introductionPanel, BorderLayout.EAST);
		// add end
	}

	private ArrayList<String> initDataVOs() {
		ArrayList<String> datalist = new ArrayList<String>();
		datalist.add("2013-1-1");
		datalist.add("2013-2-1");
		datalist.add("2013-3-1");
		datalist.add("2013-4-1");
		datalist.add("2013-5-1");
		datalist.add("2013-6-1");
		datalist.add("2013-7-1");
		datalist.add("2013-8-1");
		datalist.add("2013-9-1");
		datalist.add("2013-10-1");
		return datalist;
	}

	public void initAllPlanPanel() {
		ArrayList<PlanIntroductionVO> myPlanVOs = planHomeController
				.searchByUser(CurrentUserStatus.getInstance().getCurrentUserID());

		refreshMyPlanResults(myPlanVOs);
	}

	private void addMyPlanResults() {
		for (int i = 0; i < myPlanResults.size() / 3 + 1; i++) {
			JPanel temp = createHorizontalPanel(false);
			if (i * 3 >= myPlanResults.size())
				temp.add(Box.createRigidArea(new Dimension(1, 300)));
			else
				for (int j = 0; j < 3; j++) {
					if (i * 3 + j >= myPlanResults.size()) {
						temp.add(Box.createRigidArea(new Dimension(285, 1)));
					} else {
						JPanel p = createHorizontalPanel(true);
						p.add(myPlanResults.get(i * 3 + j));
						temp.add(p);
					}
				}
			allPlanPanel.add(temp);
		}
		// allPlanPanel.add(Box.createRigidArea(new Dimension(1, 600)));
	}

	public void refreshMyPlanResults(
			ArrayList<PlanIntroductionVO> searchPlanVOs) {
		while (allPlanPanel.getComponentCount() != 0)
			allPlanPanel.remove(allPlanPanel.getComponentCount() - 1);
		myPlanResults.clear();
		// Debugger.log("" + myPlanResults.size());
		myPlanResults.add(addNewPlan);
		for (PlanIntroductionVO introduction : searchPlanVOs) {
			myPlanResults.add(new PlanLabel(introduction));
		}
		
		
//		if (myPlanResults.size() == searchPlanVOs.size() + 1)
//			return;
//		else if (myPlanResults.size() > searchPlanVOs.size() + 1){
//			for (int i = searchPlanVOs.size(); i < myPlanResults.size(); i++)
//				myPlanResults.remove(i);
//		}else {
//			for (int i = myPlanResults.size(); i < searchPlanVOs.size() + 1; i++){
//				if (i == 0){
//					myPlanResults.add(addNewPlan);
//					continue;
//				}
//				myPlanResults.add(new PlanLabel(searchPlanVOs.get(i - 1)));
//			}
//		}
			
		addMyPlanResults();
		allPlanPanel.validate();
		allPlanPanel.repaint();
		for (final JComponent planLabel: myPlanResults){
			if (myPlanResults.indexOf(planLabel) > 0)
			((PlanLabel) planLabel).getTitle().addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					planHomeController.toOldPlanPanel(((PlanLabel) planLabel).getPlanVO());		
				}
			});
		}
		
		for (int i = 1; i < myPlanResults.size(); i++){
			PlanLabel l = (PlanLabel) myPlanResults.get(i);
			l.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					int mods = e.getModifiers();
					// Êó±êÓÒ¼ü
					if ((mods & InputEvent.BUTTON3_MASK) != 0) {
						// µ¯³ö²Ëµ¥
						deletePop.show(e.getComponent(), e.getX(), e.getY());
						deletePlanID = ((PlanLabel) e.getComponent()).getPlanVO().planID;
					}
				}
			});
		}
	}

	public static void main(String args[]) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
		} catch (Exception e) {
			// TODO exception
		}
		JFrame frame = new JFrame("test");
		frame.add(new MyPlanPanel(new PlanHomeController(
				new PlanHomeBLService_imp(null))));
		frame.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

	}
}
