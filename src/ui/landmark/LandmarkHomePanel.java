package ui.landmark;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import controller.LandmarkController;


import tools.CityConfig;
import tools.Config;
import tools.Debugger;
import tools.MapUtils;
import ui.foundation.BasePanel;
import ui.foundation.CityChooser;
import ui.foundation.Tag;
import vo.PlaceVO;

public class LandmarkHomePanel extends BasePanel {
	
	JPanel leftPanel;
	LandmarkSearchPanel resultPanel;
	JPanel searchPanel;
	
	JPanel tagPanel;
	JPanel cityPanel;
	JLabel map;
	
	ArrayList<Tag> tags = new ArrayList<Tag>(20);
	
	
	JButton searchByTag;
	JButton search;
	JTextField searchField;
	
	CityChooser cityChooser;
	JButton enterCity;
	
	ArrayList<LandmarkItemPanel> items = new ArrayList<LandmarkItemPanel>(5);
	
	LandmarkController landmarkController;

	boolean isSearch = false;
	boolean isSearchByTag = false;
	
	public LandmarkHomePanel(final LandmarkController landmarkController) {
//init begin
		Debugger.log("start loading landmarkhomepanel");
		this.landmarkController = landmarkController;
		
		this.setLayout(new BorderLayout(20, 5));
		
		leftPanel = new JPanel(null);
		searchPanel = createVerticalPanel(false);
		resultPanel = new LandmarkSearchPanel();
		
		tagPanel = createHorizontalPanel(true);
		tagPanel.setLayout(new GridLayout(3, 8));
		cityPanel = new JPanel(new GridLayout(1, 3));
		cityPanel.setBackground(Color.white);
				
		for (int i = 0; i < 19; i++)
			tags.add(createTag(Config.tagNames[i], "img/tags/btn_filter_tag_" + (i * 2 + 2) + ".gif", "img/tags/btn_filter_tag_" + (i * 2 + 1) + ".gif"));
		
		cityChooser = new CityChooser();
		enterCity = createColorButton(BEButtonUI.NormalColor.lightBlue, Color.white, " Go ");
		
		searchByTag = createColorButton(BEButtonUI.NormalColor.lightBlue, Color.white, " 搜 索 ");
		search = createColorButton(BEButtonUI.NormalColor.lightBlue, Color.white, " 搜 索 ");
		searchField = new JTextField("");
		searchField.setPreferredSize(new Dimension(150, 20));
		
		map = new JLabel(MapUtils.getMap(cityChooser.getRawCity(), 300, 450));
//		map = new JLabel();
		
//		for (int i = 0; i < 3; i++){
//			JPanel p = new LandmarkItemPanel();
//			p.setPreferredSize(new Dimension(300, 300));
//			cityPanel.add(p);
//		}
		landmarkController.setTop3Places(map, cityChooser.getRawCity(), cityPanel);
//init end
//listener begin
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (searchField.getText().length() == 0){
					JOptionPane.showMessageDialog(null, "搜索字符串不能为空");
					return;
				}
				if (isSearch)
					return;
				isSearch = true;
				landmarkController.search(searchField.getText());
				isSearch = false;
			}
		});
		
		searchByTag.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getTags().size() == 0){
					JOptionPane.showMessageDialog(null, "不能不选择tag");
					return;
				}
				if (isSearchByTag)
					return;
				isSearchByTag = true;
				landmarkController.searchByTag(getTags());
				isSearchByTag = false;
			}
		});
		
		enterCity.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				landmarkController.toCityPanel(CityConfig.CityToID.get(cityChooser.getRawCity()));
			}
		});
		
		cityChooser.addPropertyChangeListener("text", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				
				landmarkController.setTop3Places(map, cityChooser.getRawCity(), cityPanel);
			}
		});
		
//listener end
//add begin
		for (int i = 0; i < 19; i++)
			tagPanel.add(tags.get(i));
		
		
		JLabel l1 = createHeadLabel("选择你喜欢的标签", 1);
		l1.setBounds(0, 0, 140, 30);
		tagPanel.setBounds(0, 30, 950, 200);
		searchByTag.setBounds(800, 0, 100, 30);
		JLabel l2 = createHeadLabel("快速导航", 1);
		l2.setBounds(0, 250, 80, 30);
		cityChooser.setBounds(100, 250, 150, 30);
		enterCity.setBounds(300, 250, 80, 30);
		cityPanel.setBounds(0, 290, 950, 400);
		
		leftPanel.add(l1);
		leftPanel.add(tagPanel);
		leftPanel.add(searchByTag);
		leftPanel.add(l2);
		leftPanel.add(cityChooser);
		leftPanel.add(enterCity);
		leftPanel.add(cityPanel);
		
		JPanel p1 = createHorizontalPanel(false);
		p1.add(searchField);
		p1.add(Box.createRigidArea(HGAP15));
		p1.add(search);
		p1.add(Box.createRigidArea(HGAP20));
		searchPanel.add(p1);
		searchPanel.add(Box.createRigidArea(VGAP30));
		searchPanel.add(createHeadLabel("景点所在的城市", 1));
		searchPanel.add(create3DBox(map));
		searchPanel.add(Box.createRigidArea(new Dimension(1, 600)));
		
		this.add(new JPanel(), BorderLayout.WEST);
		this.add(leftPanel, BorderLayout.CENTER);
		this.add(searchPanel, BorderLayout.EAST);
		this.add(new JPanel(), BorderLayout.NORTH);
		this.add(new JPanel(), BorderLayout.SOUTH);
//add end
	}
	
	public void refreshResults(Map<String, ArrayList<PlaceVO>> landmarks){
		LandmarkHomePanel.this.remove(leftPanel);
		LandmarkHomePanel.this.remove(resultPanel);
		LandmarkHomePanel.this.revalidate();
		LandmarkHomePanel.this.repaint();
		resultPanel.updateSearchResults(landmarks);
		LandmarkHomePanel.this.add(resultPanel, BorderLayout.CENTER);
		LandmarkHomePanel.this.revalidate();
		LandmarkHomePanel.this.repaint();
		map.setIcon(MapUtils.getMap(searchField.getText(), Config.MAP_WIDTH, Config.MAP_HEIGHT));
	}
	
	private ArrayList<String> getTags(){
		ArrayList<String> strs = new ArrayList<String>();
		for (Tag t : tags)
			if (t.isPressed())
			strs.add(t.getTag());
		return strs;
	}
	
	public void refreshHome(){
		LandmarkHomePanel.this.remove(resultPanel);
		LandmarkHomePanel.this.revalidate();
		LandmarkHomePanel.this.add(leftPanel, BorderLayout.CENTER);
		LandmarkHomePanel.this.revalidate();
	}
	
	public static void main(String args[]) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
		} catch (Exception e) {
			// TODO exception
		}
		JFrame frame = new JFrame("test");
		frame.add(new LandmarkHomePanel(null));
		frame.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

}
