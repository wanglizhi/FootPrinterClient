package ui.plan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import tools.Config;
import tools.Debugger;
import ui.foundation.BasePanel;
import ui.foundation.Timeline;
import vo.PlanIntroductionVO;
import vo.TimelineVO;
import businessLogicService_imp.PlanHomeBLService_imp;
import controller.PlanHomeController;

public class PlanHomePanel extends BasePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel leftPanel;
	JPanel rightPanel;
	JPanel searchPlanPanel;
	ArrayList<PlanLabel> searchResults = new ArrayList<PlanLabel>(5);
	JLabel prevPage;
	JLabel nextPage;
	JLabel currentPageLabel;

	Timeline timeline;

	JTextField searchField;
	JButton searchButton;
	JLabel map;

	JPanel recommendBlogPanel;
	JLabel recommendBlogHead;

	// control
	boolean isSearch = false;

	PlanHomeController planHomeController;

	public PlanHomePanel(final PlanHomeController planHomeController) {
		Debugger.log("start loading planhomepanel");

		this.planHomeController = planHomeController;

		// init begin
		leftPanel = createHorizontalPanel(false);
		rightPanel = createVerticalPanel(false);

		timeline = new Timeline(new TimelineVO(initDataVOs()));

		searchPlanPanel = createVerticalPanel(false);
		// for (int i = 0; i < 9; i++)
		// searchResults.add(new PlanLabel(null));

		prevPage = createUnderLineLabel("前一页", 15);
		nextPage = createUnderLineLabel("后一页", 15);
		currentPageLabel = createHeadLabel("", 5);

		searchField = new JTextField("");
		searchField.setPreferredSize(new Dimension(150, 20));
		searchButton = createColorButton(BEButtonUI.NormalColor.lightBlue,
				Color.white, "  搜 索  ");
		map = new JLabel(planHomeController.getMap());

		// init end
		// listner begin
			
		
		// listner end
		// add begin
		JPanel p1 = createHorizontalPanel(false);
		p1.add(prevPage);
		p1.add(Box.createRigidArea(new Dimension(320, 1)));
		p1.add(currentPageLabel);
		p1.add(Box.createRigidArea(new Dimension(320, 1)));
		p1.add(nextPage);
		searchPlanPanel.add(p1);
		prevPage.setVisible(false);
		nextPage.setVisible(false);

		searchPlanPanel.add(Box.createRigidArea(VGAP10));
		ArrayList<PlanIntroductionVO> searchPlanVOs = planHomeController
				.search(searchField.getText());
		refreshSearchResults(searchPlanVOs);
		// 刚开始时不能让它显示出来
		currentPageLabel.setVisible(false);

		leftPanel.add(timeline);
		leftPanel.add(Box.createRigidArea(HGAP25));
		leftPanel.add(createScrollPane(searchPlanPanel));
		leftPanel.add(Box.createRigidArea(HGAP25));

		JPanel p = createHorizontalPanel(false);
		p.add(Box.createRigidArea(HGAP10));
		p.add(searchField);
		p.add(Box.createRigidArea(HGAP10));
		p.add(searchButton);
		p.add(Box.createRigidArea(HGAP10));
		rightPanel.add(Box.createRigidArea(VGAP20));
		rightPanel.add(p);
		rightPanel.add(Box.createRigidArea(VGAP30));
		rightPanel.add(createHeadLabel(" 计划所在的城市 ", 1));
		rightPanel.add(create3DBox(map));
		rightPanel.add(Box.createRigidArea(new Dimension(1, 600)));
		
		this.add(leftPanel, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.EAST);
		this.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
		// add end
		initListeners();
	}

	private void addSearchResults() {
		for (int i = 0; i < 3; i++) {
			JPanel temp = createHorizontalPanel(false);
			if (i * 3 >= searchResults.size())
				temp.add(Box.createRigidArea(new Dimension(1, 300)));
			else
				for (int j = 0; j < 3; j++) {
					if (i * 3 + j >= searchResults.size()) {
						temp.add(Box.createRigidArea(new Dimension(265, 1)));
					} else {
						JPanel p = createHorizontalPanel(true);
						p.add(searchResults.get(i * 3 + j));
						temp.add(p);
					}
				}
			searchPlanPanel.add(temp);
		}
		// searchPlanPanel.add(Box.createRigidArea(new Dimension(1, 600)));
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

	private void refreshSearchResults(
			ArrayList<PlanIntroductionVO> searchPlanVOs) {
		while (searchPlanPanel.getComponentCount() != 1) {
			searchPlanPanel.remove(searchPlanPanel.getComponentCount() - 1);
		}
		leftPanel.revalidate();
		leftPanel.repaint();
		searchResults.clear();
		// Debugger.log("" + searchResults.size());
		for (PlanIntroductionVO introduction : searchPlanVOs) {
			searchResults.add(new PlanLabel(introduction));
		}
		addSearchResults();
		leftPanel.validate();
		leftPanel.repaint();
		currentPageLabel.setText("当前页号 ：" + planHomeController.getCurrentPage()
				+ " / " + planHomeController.getPagesNum());
		currentPageLabel.setVisible(true);
		map.setIcon(planHomeController.getMap());
		
		for (final PlanLabel planLabel: searchResults){
			planLabel.getTitle().addMouseListener(new MouseListener() {
				
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
					Debugger.log("to check");
					planHomeController.toCheckPlanPanel(planLabel.getPlanVO());		
				}
			});
		}
	}
	
	private void initListeners(){
		timeline.getSlider().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				if (!slider.getValueIsAdjusting()) {
					int value = slider.getValue();
					Date date = timeline.getDate(value);
					ArrayList<PlanIntroductionVO> searchPlanVOs = planHomeController
							.searchByDate(date);

					if (planHomeController.getCurrentPage() != planHomeController
							.getPagesNum())
						nextPage.setVisible(true);
					if (planHomeController.getCurrentPage() != 1)
						prevPage.setVisible(true);

					refreshSearchResults(searchPlanVOs);

					Debugger.log("currentPage : "
							+ planHomeController.getCurrentPage());
					Debugger.log("currentPage : "
							+ planHomeController.getPagesNum());

					if (planHomeController.getCurrentPage() == 1)
						prevPage.setVisible(false);
					if (planHomeController.getCurrentPage() != planHomeController
							.getPagesNum())
						nextPage.setVisible(true);
					if (planHomeController.getCurrentPage() == planHomeController
							.getPagesNum())
						nextPage.setVisible(false);
					if (planHomeController.getCurrentPage() != 1)
						prevPage.setVisible(true);
				}
			}
		});

		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (searchField.getText().length() == 0){
					JOptionPane.showMessageDialog(null, "搜索字符串不能为空");
					return;
				}
				
				if (isSearch)
					return;
				
				isSearch = true;
				ArrayList<PlanIntroductionVO> searchPlanVOs = planHomeController
						.search(searchField.getText());

				if (planHomeController.getCurrentPage() != planHomeController
						.getPagesNum())
					nextPage.setVisible(true);
				if (planHomeController.getCurrentPage() != 1)
					prevPage.setVisible(true);

				refreshSearchResults(searchPlanVOs);
				isSearch = false;
				
			}
		});

		nextPage.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<PlanIntroductionVO> searchPlanVOs = planHomeController
						.nextPage();
				refreshSearchResults(searchPlanVOs);
				if (planHomeController.getCurrentPage() == planHomeController
						.getPagesNum())
					nextPage.setVisible(false);
				if (planHomeController.getCurrentPage() != 1)
					prevPage.setVisible(true);

			}
		});

		prevPage.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				ArrayList<PlanIntroductionVO> searchPlanVOs = planHomeController
						.prevPage();
				refreshSearchResults(searchPlanVOs);
				Debugger.log("" + planHomeController.getPagesNum());
				if (planHomeController.getCurrentPage() == 1)
					prevPage.setVisible(false);
				if (planHomeController.getCurrentPage() != planHomeController
						.getPagesNum())
					nextPage.setVisible(true);
			}
		});


	}

	public static void main(String args[]) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
		} catch (Exception e) {
			// TODO exception
		}
		JFrame frame = new JFrame("test");
		frame.add(new PlanHomePanel(new PlanHomeController(
				new PlanHomeBLService_imp(null))));
		frame.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}
