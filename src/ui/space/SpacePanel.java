package ui.space;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import businessLogicService.PlanHomeBLService;

import tools.Debugger;
import ui.blog.BlogLabel;
import ui.foundation.BasePanel;
import ui.foundation.CityChooser;
import ui.foundation.UserIntroductionPanel;
import ui.plan.PlanLabel;
import vo.BlogIntroductionVO;
import vo.PlanIntroductionVO;
import controller.SpaceController;

public class SpacePanel extends BasePanel {

	JPanel contentPanel;
	JPanel introductionPanel;

	JLabel name;
	JLabel map;
	JSlider mapZoom;
	ArrayList<PlanLabel> plans = new ArrayList<PlanLabel>(3);
	ArrayList<BlogLabel> blogs = new ArrayList<BlogLabel>(3);

	JLabel blogMore;
	JLabel planMore;
	JPanel littleBlogPanel;
	JPanel littlePlanPanel;
	
	CityChooser cityChooser;

	ButtonGroup mapTypes;
	JRadioButton roadmap;
	JRadioButton satellite;
	JRadioButton terrain;
	JRadioButton hybrid;
	
	SpaceController spaceController;

	public SpacePanel(final SpaceController spaceController) {
		
		Debugger.log("loading spacePanel");

		this.spaceController = spaceController;

		contentPanel = createVerticalPanel(false);
		introductionPanel = createHorizontalPanel(true);
		littleBlogPanel = createHorizontalPanel(false);
		littlePlanPanel = createHorizontalPanel(false);

		spaceController.changeCenter("南京", 1);
		map = new JLabel(spaceController.getMap(1));
		map.setPreferredSize(new Dimension(960, 360));
		mapZoom = new JSlider(SwingConstants.VERTICAL);
		mapZoom.setPreferredSize(new Dimension(20, 300));
		mapZoom.setValue(10 * 5);

		updateSpace();
		
		cityChooser = new CityChooser();
		
		mapTypes = new ButtonGroup();
		roadmap = new JRadioButton("roadmap");
		roadmap.setSelected(true);
		satellite = new JRadioButton("satellite");
		terrain = new JRadioButton("terrain");
		hybrid = new JRadioButton("hybird");
		
		mapTypes.add(roadmap);
		mapTypes.add(satellite);
		mapTypes.add(terrain);
		mapTypes.add(hybrid);
		

		// init end
		// listener begin

		planMore.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				spaceController.toMyPlanPanel();
			}
		});

		blogMore.addMouseListener(new MouseListener() {

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
				spaceController.toMyBlogPanel();
			}
		});

		
		mapZoom.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if((mapZoom.getValue() / 5) != 0)
					map.setIcon(spaceController.getMap(mapZoom.getValue() / 5));	
			}
		});
		
		cityChooser.addPropertyChangeListener("text", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				spaceController.changeCenter(cityChooser.getRawCity(), mapZoom.getValue() / 5);
				map.setIcon(spaceController.getMap(mapZoom.getValue() / 5));	
			}
		});
		
		roadmap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				spaceController.changeMapType("roadmap", mapZoom.getValue() / 5);
				map.setIcon(spaceController.getMap(mapZoom.getValue() / 5));	
			}
		});
		
		satellite.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				spaceController.changeMapType("satellite", mapZoom.getValue() / 5);
				map.setIcon(spaceController.getMap(mapZoom.getValue() / 5));	
			}
		});
		
		terrain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				spaceController.changeMapType("terrain", mapZoom.getValue() / 5);
				map.setIcon(spaceController.getMap(mapZoom.getValue() / 5));	
			}
		});
		
		hybrid.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				spaceController.changeMapType("hybird", mapZoom.getValue() / 5);
				map.setIcon(spaceController.getMap(mapZoom.getValue() / 5));	
			}
		});
		

		
		// listener end
		// add begin
		
		introductionPanel.add(Box.createRigidArea(HGAP30));
		introductionPanel.add(new UserIntroductionPanel());
		introductionPanel.add(Box.createRigidArea(HGAP30));

		contentPanel.add(createHeadLabel(" 欢迎，习近平 ", 5, 25));
		contentPanel.add(createSeparateLine(SwingConstants.HORIZONTAL,
				contentPanel.getWidth()));
		contentPanel.add(Box.createRigidArea(VGAP10));
		contentPanel.add(createHeadLabel(" 我的地图 ", 1, 25));
		contentPanel.add(Box.createRigidArea(VGAP10));
		JPanel p4 = createHorizontalPanel(false);
		p4.add(createHeadLabel("选择地图所在的中心", 15));
		p4.add(Box.createRigidArea(HGAP30));
		p4.add(cityChooser);
		p4.add(Box.createRigidArea(HGAP30));
		p4.add(roadmap);
		p4.add(Box.createRigidArea(HGAP30));
		p4.add(satellite);
		p4.add(Box.createRigidArea(HGAP30));
		p4.add(terrain);
		p4.add(Box.createRigidArea(HGAP30));
		p4.add(hybrid);
		p4.add(Box.createRigidArea(new Dimension(200, 1)));
		contentPanel.add(p4);
		contentPanel.add(Box.createRigidArea(VGAP10));
		JPanel p3 = createHorizontalPanel(true);
		p3.add(map);
		p3.add(mapZoom);
		contentPanel.add(p3);
		contentPanel.add(createSeparateLine(SwingConstants.HORIZONTAL,
				contentPanel.getWidth()));
		contentPanel.add(Box.createRigidArea(VGAP10));
		JPanel p1 = createHorizontalPanel(false);
		p1.add(createHeadLabel(" 我的日志 ", 1, 25));
		p1.add(Box.createRigidArea(HGAP15));
		p1.add(blogMore);
		contentPanel.add(p1);
		contentPanel.add(Box.createRigidArea(VGAP20));
		contentPanel.add(littleBlogPanel);
		contentPanel.add(createSeparateLine(SwingConstants.HORIZONTAL,
				contentPanel.getWidth()));
		contentPanel.add(Box.createRigidArea(VGAP10));
		JPanel p2 = createHorizontalPanel(false);
		p2.add(createHeadLabel(" 我的计划 ", 1, 25));
		p2.add(Box.createRigidArea(HGAP15));
		p2.add(planMore);
		contentPanel.add(p2);
		contentPanel.add(Box.createRigidArea(VGAP20));
		contentPanel.add(littlePlanPanel);
		contentPanel.add(Box.createRigidArea(new Dimension(1, 200)));
		this.add(createScrollPane(contentPanel), BorderLayout.CENTER);
		this.add(introductionPanel, BorderLayout.EAST);
	}

	public void updateSpace(){
		littleBlogPanel.removeAll();
		ArrayList<BlogIntroductionVO> blogVos = spaceController.getBlogs();
		for (BlogIntroductionVO b : blogVos){
			final BlogLabel l = new BlogLabel(b);
			l.getTitle().addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					spaceController.toCheckBlogPanel(l.getBlogVO());
				}
			});
			blogs.add(l);
		}
		for (int i = 0; i < 3; i++) {
			if (i < blogs.size()) {
				littleBlogPanel.add(create3DBox(blogs.get(i)));
			} else
				littleBlogPanel.add(Box.createRigidArea(new Dimension(350, 1)));
		}
		blogMore = createUnderLineLabel("查看全部", 15);

		littlePlanPanel.removeAll();
		ArrayList<PlanIntroductionVO> planVos = spaceController.getPlans();
		for (PlanIntroductionVO b : planVos){
			final PlanLabel l = new PlanLabel(b);
			l.getTitle().addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					spaceController.toCheckPlanPanel(l.getPlanVO());
				}
			});
			plans.add(l);
		}
		for (int i = 0; i < 3; i++) {
			if (i < plans.size())
				littlePlanPanel.add(create3DBox(plans.get(i)));
			else
				littlePlanPanel.add(Box.createRigidArea(new Dimension(350, 1)));
		}
		planMore = createUnderLineLabel("查看全部", 15);
		
		contentPanel.revalidate();
	}
}
