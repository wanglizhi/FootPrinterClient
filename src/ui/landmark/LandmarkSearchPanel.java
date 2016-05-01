package ui.landmark;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;

import tools.Debugger;
import ui.foundation.BasePanel;
import vo.PlaceVO;

public class LandmarkSearchPanel extends BasePanel{

	ArrayList<LandmarkItemPanel> landmarks = new ArrayList<LandmarkItemPanel>();
	JTabbedPane tabs;
	ArrayList<String> placeNames = new ArrayList<String>();
	
	public LandmarkSearchPanel(){
		tabs = new JTabbedPane(JTabbedPane.LEFT);
		this.add(tabs);
	}
	
	public LandmarkSearchPanel(Map<String, ArrayList<PlaceVO>> landmarkVOs) {
		tabs = new JTabbedPane(JTabbedPane.LEFT);
		updateSearchResults(landmarkVOs);
		this.add(tabs);
	}
	
	public void updateSearchResults(Map<String, ArrayList<PlaceVO>> landmarkVOs){
		Debugger.log("" + landmarkVOs.keySet().size());
	tabs.removeAll();
	placeNames.clear();
		for (String k : landmarkVOs.keySet()){
			ArrayList<PlaceVO> places = new ArrayList<PlaceVO>();
			if (landmarkVOs.get(k) != null)
				places = landmarkVOs.get(k);
			
			JPanel p = new JPanel(new GridLayout((places.size() - 1)  / 3 + 1, 3));
//			p.setPreferredSize(new Dimension(900, 900));
			for (PlaceVO place : places){
				JPanel p1 = new LandmarkItemPanel(place);
				p1.setPreferredSize(new Dimension(300, 300));
				p.add(p1);
				placeNames.add(place.name);
			}
//			tabs.add(k, createScrollPane(p));
			if (places.size() != 0)
				tabs.addTab(k, createScrollPane(p));
		}
		this.revalidate();
		this.repaint();
//		this.add(tabs);
	}
	
	public ArrayList<String> getPlaceNames(){
		Debugger.log("" + placeNames.size());
		return placeNames;
	}

}
