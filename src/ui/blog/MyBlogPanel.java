package ui.blog;

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
import ui.foundation.BasePanel;
import ui.foundation.Timeline;
import ui.foundation.UserIntroductionPanel;
import vo.BlogIntroductionVO;
import vo.TimelineVO;
import businessLogicService_imp.BlogHomeBLService_imp;
import controller.BlogController;
import controller.BlogHomeController;

public class MyBlogPanel extends BasePanel {

	JPanel leftPanel;
	JPanel introductionPanel;
	JPanel allBlogPanel;
	ArrayList<JComponent> myBlogResults = new ArrayList<JComponent>();

	Timeline timeline;

	JButton addNewBlog;

	BlogHomeController BlogHomeController;
	
	JFrame newBlogFrame;
	
	JPopupMenu deletePop;

	//sharedValue
	int deleteBlogID;
	
	public MyBlogPanel(final BlogHomeController BlogHomeController) {
		Debugger.log("start loading blogHomePanel");
		
		this.BlogHomeController = BlogHomeController;

		// init begin
		leftPanel = createHorizontalPanel(false);
		introductionPanel = createHorizontalPanel(true);

		timeline = new Timeline(new TimelineVO(initDataVOs()));

		allBlogPanel = createVerticalPanel(false);

		// init and add
		addNewBlog = createImageButton("img/newBlog.png");
		initAllBlogPanel();

		NewBlog newBlog = new NewBlog(BlogHomeController);
		newBlogFrame = createSurroundFrame(newBlog);
		newBlog.setFrame(newBlogFrame);
		newBlogFrame.setVisible(false);
		
		Debugger.log("blogHomePanel init end");
		deletePop = new JPopupMenu();
		JMenuItem i1 = new JMenuItem("É¾³ý");
		deletePop.add(i1);
		
		// init end
		// listener begin

		i1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BlogHomeController.deleteBlog(deleteBlogID);
			}
		});
		
		timeline.getSlider().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				if (!slider.getValueIsAdjusting()) {
					int value = slider.getValue();
					Date date = timeline.getDate(value);
					BlogHomeController.searchByDate(date);
					ArrayList<BlogIntroductionVO> myBlogVOs = BlogHomeController
							.getAllBlogIntroductions();

					refreshMyBlogResults(myBlogVOs);

				}
			}
		});
		
		addNewBlog.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newBlogFrame.setVisible(true);
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
				ControllerFactory.getInstance().blogShowController.toBlogHomePanel();
				
			}
		});
		returnHome.setBounds(20, 20, 70, 30);
		timeline.setBounds(0, 50, 100, 560);
		center.add(returnHome);
		center.add(timeline);
		JScrollPane sp = new JScrollPane(allBlogPanel);
		sp.setBounds(110, 0, 890, 610);
		center.add(sp);

//		leftPanel.add(timeline, " Ê±¼äÖá ");
//		leftPanel.add(Box.createRigidArea(HGAP25));
//		leftPanel.add(createScrollPane(allBlogPanel));

		introductionPanel.add(Box.createRigidArea(HGAP30));
		introductionPanel.add(new UserIntroductionPanel());
		introductionPanel.add(Box.createRigidArea(HGAP30));

//		this.add(timeline, BorderLayout.WEST);
//		this.add(allBlogPanel, BorderLayout.CENTER);
		this.add(center, BorderLayout.CENTER);
		this.add(introductionPanel, BorderLayout.EAST);
		Debugger.log("blogHomePanel init end");
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

	public void initAllBlogPanel() {
		ArrayList<BlogIntroductionVO> myBlogVOs = BlogHomeController
				.searchByUser(CurrentUserStatus.getInstance().getCurrentUserID());

		refreshMyBlogResults(myBlogVOs);
	}

	private void addMyBlogResults() {
		JPanel gridBox = new JPanel(new GridLayout((myBlogResults.size() - 1)  / 3 + 1, 3));
		for (int i = 0; i < myBlogResults.size(); i++){
			JComponent p = myBlogResults.get(i);
			gridBox.add(p);
		}
		JScrollPane sp = createScrollPane(gridBox);
		allBlogPanel.add(sp);
	}

	public void refreshMyBlogResults(
			ArrayList<BlogIntroductionVO> searchBlogVOs) {
		while (allBlogPanel.getComponentCount() != 0)
			allBlogPanel.remove(allBlogPanel.getComponentCount() - 1);
		leftPanel.revalidate();
		leftPanel.repaint();
		myBlogResults.clear();
		// Debugger.log("" + myBlogResults.size());
		JPanel p = new JPanel();
		p.setLayout(null);
		addNewBlog.setBounds(0, 0, 280, 200);
		p.add(addNewBlog);
		p.setBackground(Color.white);
		myBlogResults.add(p);
		for (BlogIntroductionVO introduction : searchBlogVOs) {
			myBlogResults.add(new BlogLabel(introduction));
		}
		addMyBlogResults();
		leftPanel.validate();
		leftPanel.repaint();
		for (final JComponent blogLabel: myBlogResults){
			if (myBlogResults.indexOf(blogLabel) > 0)
			((BlogLabel) blogLabel).getTitle().addMouseListener(new MouseListener() {
				
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
						BlogHomeController.toNewBlogPanel(((BlogLabel) blogLabel).getBlogVO());
				}
			});
		}
		
		for (int i = 1; i < myBlogResults.size(); i++){
			BlogLabel l = (BlogLabel) myBlogResults.get(i);
			l.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					int mods = e.getModifiers();
					// Êó±êÓÒ¼ü
					if ((mods & InputEvent.BUTTON3_MASK) != 0) {
						// µ¯³ö²Ëµ¥
						deletePop.show(e.getComponent(), e.getX(), e.getY());
						deleteBlogID = ((BlogLabel) e.getComponent()).getBlogVO().blogID;
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
		frame.add(new MyBlogPanel(new BlogHomeController(
				new BlogHomeBLService_imp(null))));
		frame.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

	}
}
