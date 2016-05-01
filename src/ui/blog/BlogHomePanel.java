package ui.blog;


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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
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
import ui.plan.PlanLabel;
import vo.BlogIntroductionVO;
import vo.TimelineVO;
import businessLogicService_imp.BlogHomeBLService_imp;
import controller.BlogHomeController;

public class BlogHomePanel extends BasePanel {

	JPanel leftPanel;
	JPanel rightPanel;
	JPanel searchBlogPanel;
	ArrayList<BlogLabel> searchResults = new ArrayList<BlogLabel>(5);
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

	BlogHomeController BlogHomeController;
	
	boolean isSearch = false;

	public BlogHomePanel(final BlogHomeController BlogHomeController) {
		
		Debugger.log("start loading bloghomepanel");

		this.BlogHomeController = BlogHomeController;

		// init begin
		this.setLayout(new BorderLayout(15, 15));
		leftPanel = createHorizontalPanel(false);
		rightPanel = createVerticalPanel(false);

		timeline = new Timeline(new TimelineVO(initDataVOs()));

		searchBlogPanel = new JPanel();
		searchBlogPanel.setLayout(null);
		// for (int i = 0; i < 9; i++)
		// searchResults.add(new BlogLabel(null));

		prevPage = createUnderLineLabel("前一页", 15);
		nextPage = createUnderLineLabel("后一页", 15);
		currentPageLabel = createHeadLabel("", 5);

		searchField = new JTextField("");
		searchField.setPreferredSize(new Dimension(150, 20));
		searchButton = createColorButton(BEButtonUI.NormalColor.lightBlue,
				Color.white, "  搜 索  ");
		map = new JLabel(BlogHomeController.getMap());

		// init end
		// listner begin

		timeline.getSlider().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				if (!slider.getValueIsAdjusting()) {
					int value = slider.getValue();
					Date date = timeline.getDate(value);
					ArrayList<BlogIntroductionVO> searchBlogVOs = BlogHomeController
							.searchByDate(date);

					if (BlogHomeController.getCurrentPage() != BlogHomeController
							.getPagesNum())
						nextPage.setVisible(true);
					if (BlogHomeController.getCurrentPage() != 1)
						prevPage.setVisible(true);

					refreshSearchResults(searchBlogVOs);

					Debugger.log("currentPage : "
							+ BlogHomeController.getCurrentPage());
					Debugger.log("currentPage : "
							+ BlogHomeController.getPagesNum());

					if (BlogHomeController.getCurrentPage() == 1)
						prevPage.setVisible(false);
					if (BlogHomeController.getCurrentPage() != BlogHomeController
							.getPagesNum())
						nextPage.setVisible(true);
					if (BlogHomeController.getCurrentPage() == BlogHomeController
							.getPagesNum())
						nextPage.setVisible(false);
					if (BlogHomeController.getCurrentPage() != 1)
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
				ArrayList<BlogIntroductionVO> searchBlogVOs = BlogHomeController
						.search(searchField.getText());

				if (BlogHomeController.getCurrentPage() != BlogHomeController
						.getPagesNum())
					nextPage.setVisible(true);
				if (BlogHomeController.getCurrentPage() != 1)
					prevPage.setVisible(true);

				refreshSearchResults(searchBlogVOs);
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
				ArrayList<BlogIntroductionVO> searchBlogVOs = BlogHomeController
						.nextPage();
				refreshSearchResults(searchBlogVOs);
				if (BlogHomeController.getCurrentPage() == BlogHomeController
						.getPagesNum())
					nextPage.setVisible(false);
				if (BlogHomeController.getCurrentPage() != 1)
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
				ArrayList<BlogIntroductionVO> searchBlogVOs = BlogHomeController
						.prevPage();
				refreshSearchResults(searchBlogVOs);
				Debugger.log("" + BlogHomeController.getPagesNum());
				if (BlogHomeController.getCurrentPage() == 1)
					prevPage.setVisible(false);
				if (BlogHomeController.getCurrentPage() != BlogHomeController
						.getPagesNum())
					nextPage.setVisible(true);
			}
		});

		
		// listner end
		// add begin
		JPanel p1 = createHorizontalPanel(false);
		p1.add(prevPage);
		p1.add(Box.createRigidArea(new Dimension(320, 1)));
		p1.add(currentPageLabel);
		p1.add(Box.createRigidArea(new Dimension(320, 1)));
		p1.add(nextPage);
		p1.setBounds(0, 0, 800, 50);
		searchBlogPanel.add(p1);
		prevPage.setVisible(false);
		nextPage.setVisible(false);

		searchBlogPanel.add(Box.createRigidArea(VGAP10));
		ArrayList<BlogIntroductionVO> searchBlogVOs = BlogHomeController
				.search(searchField.getText());
		refreshSearchResults(searchBlogVOs);
		// 刚开始时不能让它显示出来
		currentPageLabel.setVisible(false);

		leftPanel.add(timeline);
		leftPanel.add(Box.createRigidArea(HGAP25));
		leftPanel.add(searchBlogPanel);
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
		rightPanel.add(createHeadLabel(" 博客所在的城市 ", 1));
		rightPanel.add(create3DBox(map));
		rightPanel.add(Box.createRigidArea(new Dimension(1, 600)));
		
		this.add(timeline, BorderLayout.WEST);
		this.add(searchBlogPanel, BorderLayout.CENTER);
//		this.add(leftPanel, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.EAST);
		this.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
		// add end
	}

	private void addSearchResults() {
		JPanel gridBox = new JPanel(new GridLayout((searchResults.size() - 1)  / 3 + 1, 3));
		for (int i = 0; i < searchResults.size(); i++){
			gridBox.add(searchResults.get(i));
		}
//		gridBox.setPreferredSize(new Dimension(900, 700));
		JScrollPane sp = createScrollPane(gridBox);
		sp.setBounds(0, 50, 840, 560);
		searchBlogPanel.add(sp);
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
			ArrayList<BlogIntroductionVO> searchBlogVOs) {
		if (searchBlogPanel.getComponentCount() > 1) 
				searchBlogPanel.remove(searchBlogPanel.getComponentCount() - 1);
		searchResults.clear();
		// Debugger.log("" + searchResults.size());
		for (BlogIntroductionVO introduction : searchBlogVOs) {
			searchResults.add(new BlogLabel(introduction));
		}
		addSearchResults();
		searchBlogPanel.validate();
		searchBlogPanel.repaint();
		currentPageLabel.setText("当前页号 ：" + BlogHomeController.getCurrentPage()
				+ " / " + BlogHomeController.getPagesNum());
		currentPageLabel.setVisible(true);
		map.setIcon(BlogHomeController.getMap());
		for (final BlogLabel blogLabel: searchResults){
			blogLabel.getTitle().addMouseListener(new MouseListener() {
				
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
					BlogHomeController.toCheckBlogPanel(blogLabel.getBlogVO());		
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
		frame.add(new BlogHomePanel(new BlogHomeController(
				new BlogHomeBLService_imp(null))));
		frame.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}

