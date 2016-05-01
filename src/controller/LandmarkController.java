package controller;

import java.awt.Dimension;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import dataService.BlogDataService;
import dataService.PlanDataService;

import startup.BLServiceFactory;
import startup.ClientNetworkInit;
import startup.ControllerFactory;
import startup.CurrentUserStatus;
import tools.CityConfig;
import tools.Config;
import tools.Debugger;
import tools.LandmarkType;
import tools.MapUtils;
import ui.foundation.BasePanel;
import ui.landmark.CityPanel;
import ui.landmark.LandmarkHomePanel;
import ui.landmark.LandmarkItemPanel;
import ui.landmark.LandmarkSearchPanel;
import vo.CityVO;
import vo.PlaceVO;
import bean.BlogPO;
import bean.PlacePO;
import bean.PlanPO;
import businessLogicService.CityBLService;
import businessLogicService.PlaceBLService;
import businessLogicService_imp.BlogHomeBLService_imp;
import businessLogicService_imp.PlanBLService_imp;
import businessLogicService_imp.PlanHomeBLService_imp;

public class LandmarkController {
	
	private CityPanel cityPanel;
	
	private LandmarkHomePanel landmarkHomePanel;
	
	private LandmarkSearchPanel searchPanel;
	
	private boolean isSearch=false;
	
	Map<String, PlaceBLService> placeBLServices = new HashMap<String, PlaceBLService>();
	private CityBLService cityBLService;
	
	public LandmarkController(BLServiceFactory blServiceFactory) {
		super();
		placeBLServices.put("”È¿÷", blServiceFactory.entertainmentBLService);
		placeBLServices.put("±ˆπ›", blServiceFactory.hotelBLService);
		placeBLServices.put(" –≥°", blServiceFactory.marketBLSetvice);
		placeBLServices.put("≤Õπ›", blServiceFactory.restaurantBLService);;
		placeBLServices.put("æ∞µ„", blServiceFactory.landmarkBLService);
		this.cityBLService = blServiceFactory.cityBLService;
	}



	public JScrollPane initCityPanel(){
		cityPanel=new CityPanel();
		JScrollPane jsp = new JScrollPane(cityPanel);
		jsp.getVerticalScrollBar().setUnitIncrement(20);
		return jsp;
	}
	
	public void search(String key){
		Debugger.log("is searching");
		Map<String, ArrayList<PlaceVO>> landmarks = new HashMap<String, ArrayList<PlaceVO>>();
		for (String k : placeBLServices.keySet())
			landmarks.put(k, placeBLServices.get(k).search(key));
		Debugger.log("" + landmarks.keySet().size());
		landmarkHomePanel.refreshResults(landmarks);
	}
	
	public void searchByTag(ArrayList<String> tags){
		Map<String, ArrayList<PlaceVO>> landmarks = new HashMap<String, ArrayList<PlaceVO>>();
		for(String key:placeBLServices.keySet()){
			if(key.equals("æ∞µ„"))
				landmarks.put("æ∞µ„", placeBLServices.get("æ∞µ„").searchByTag(tags));
		}
		
		Debugger.log("" + landmarks.get("æ∞µ„").size());
		
		landmarkHomePanel.refreshResults(landmarks);
		return ;
	}
	
	public LandmarkHomePanel getLandmarkHomePanel(){
		if (landmarkHomePanel == null)
			landmarkHomePanel = new LandmarkHomePanel(this);
		return landmarkHomePanel;	
	}
	
	public JScrollPane getCitypanel(CityVO cityVO){
		cityPanel = new CityPanel(cityVO,this);
		JScrollPane jsp=BasePanel.createScrollPane(cityPanel);
		JScrollBar jsb=jsp.getVerticalScrollBar();
		if(jsb!=null){
			jsb.setValue(jsb.getMaximum());
		}
		return jsp;
	}
	
	public JPanel getSearchPanel(Map<String, ArrayList<PlaceVO>> landmarks){
		searchPanel=new LandmarkSearchPanel(landmarks);
		return searchPanel;
	}
	public boolean addCityApplause(int cityID){
		return cityBLService.addApplause(CurrentUserStatus.getInstance().getCurrentUserID(), cityID);
	}
	public boolean deleteCityApplause(int cityID){
		return cityBLService.deleteApplause(CurrentUserStatus.getInstance().getCurrentUserID(), cityID);
	}
	public boolean addPlaceApplause(int placeID,LandmarkType type){
		switch(type){
		case LANDMARK:
			return placeBLServices.get("æ∞µ„").addApplause(CurrentUserStatus.getInstance().getCurrentUserID(), placeID);
		case HOTEL:
			return placeBLServices.get("±ˆπ›").addApplause(CurrentUserStatus.getInstance().getCurrentUserID(), placeID);
		case ENTERTAINMENT:
			return placeBLServices.get("”È¿÷").addApplause(CurrentUserStatus.getInstance().getCurrentUserID(), placeID);
		case MARKET:
			return placeBLServices.get(" –≥°").addApplause(CurrentUserStatus.getInstance().getCurrentUserID(), placeID);
		case RESTAURANT:
			return placeBLServices.get("≤Õπ›").addApplause(CurrentUserStatus.getInstance().getCurrentUserID(), placeID);
			default :
				break;
		}
		return false;
	}
	public boolean deletePlaceApplause(int placeID,LandmarkType type){
		switch(type){
		case LANDMARK:
			return placeBLServices.get("æ∞µ„").deleteApplause(CurrentUserStatus.getInstance().getCurrentUserID(), placeID);
		case HOTEL:
			return placeBLServices.get("±ˆπ›").deleteApplause(CurrentUserStatus.getInstance().getCurrentUserID(), placeID);
		case ENTERTAINMENT:
			return placeBLServices.get("”È¿÷").deleteApplause(CurrentUserStatus.getInstance().getCurrentUserID(), placeID);
		case MARKET:
			return placeBLServices.get(" –≥°").deleteApplause(CurrentUserStatus.getInstance().getCurrentUserID(), placeID);
		case RESTAURANT:
			return placeBLServices.get("≤Õπ›").deleteApplause(CurrentUserStatus.getInstance().getCurrentUserID(), placeID);
		default :
			break;
		}
		return false;
	}
	public void toCityPanel(final int cityID){
		if(isSearch){
			return;
		}
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				isSearch=true;
				CityVO c = cityBLService.showCity(cityID);
				c.landmarkList=placeBLServices.get("æ∞µ„").searchByCityID(cityID);
				c.planList=cityBLService.recommendPlan(cityID);
				c.blogList=cityBLService.recommendBlog(cityID);
				ControllerFactory.getInstance().homepageController.setTab(2, getCitypanel(c));
				c.hotelList=placeBLServices.get("±ˆπ›").searchByCityID(cityID);
				c.restaurantList=placeBLServices.get("≤Õπ›").searchByCityID(cityID);
				c.entertainmentList=placeBLServices.get("”È¿÷").searchByCityID(cityID);
				c.marketList=placeBLServices.get(" –≥°").searchByCityID(cityID);
				cityPanel.initBuffer();
				isSearch=false;
			}
			
		}).start();
		
	}
	
	public void setTop3Places(JLabel map, String name, JPanel cityPanel){
		ArrayList<PlaceVO> places = placeBLServices.get("æ∞µ„").searchByCityID(CityConfig.CityToID.get(name), 3);
		ArrayList<String> placeNames = new ArrayList<String>();
		cityPanel.removeAll();
		for (int i = 0; i < 3; i++){
			JPanel p = new LandmarkItemPanel(places.get(i));
			p.setPreferredSize(new Dimension(300, 300));
			cityPanel.add(p);
			placeNames.add(places.get(i).name + "," + name);
		}
		cityPanel.revalidate();
		map.setIcon(MapUtils.getMap(placeNames, Config.MAP_WIDTH, Config.MAP_HEIGHT));
	}
	
	public void toLandmarkHomePanel(){
		getLandmarkHomePanel().refreshHome();
		ControllerFactory.getInstance().homepageController.setTab(2,
				getLandmarkHomePanel());
	}

	public void toCheckPlanPanel(int planID){
		PlanDataService planDataService = ClientNetworkInit.planDataService;
		PlanPO planPO = null;
		try {
			planPO = planDataService.searchByID(planID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ControllerFactory.getInstance().homepageController.setTab(3,
				ControllerFactory.getInstance().checkPlanController.getCheckPlanPanel(PlanHomeBLService_imp.toIntroduction(planPO).planVO));
	}
	
	public void toBlogShowPanel(int blogID){
		BlogDataService blogDataService = ClientNetworkInit.blogDataService;
		BlogPO blogPO = null;
		try {
			blogPO = blogDataService.searchByID(blogID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ControllerFactory.getInstance().homepageController.setTab(4,
				ControllerFactory.getInstance().blogShowController.getPanel(blogPO.getBlogID(), CurrentUserStatus.getInstance().getCurrentUserID()));
	}
}
