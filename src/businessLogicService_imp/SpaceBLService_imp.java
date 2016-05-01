package businessLogicService_imp;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import startup.ClientNetworkInit;
import startup.CurrentUserStatus;
import tools.Debugger;
import tools.FileUtils;
import tools.MapUtils;
import ui.foundation.BasePanel;
import vo.BlogIntroductionVO;
import vo.PlanIntroductionVO;
import bean.BlogPO;
import bean.PlanPO;
import businessLogicService.SpaceBLService;
import dataService.BlogDataService;
import dataService.PlanDataService;

public class SpaceBLService_imp implements SpaceBLService {

	PlanDataService planDataService;
	BlogDataService blogDataService;

	Map<Integer, ImageIcon> mapList = new HashMap<Integer, ImageIcon>();
	String center = "Nanjing";
	String type = "roadmap";
	LoadMapThread loadMapThread;
	ArrayList<String> markers = new ArrayList<String>();
	
	public int currentZoom;

	// UserDataService userDataService;

	/** @author SongShuo */
	public SpaceBLService_imp(PlanDataService planDataService,
			BlogDataService blogDataService) {

		this.planDataService = planDataService;
		this.blogDataService = blogDataService;
		currentZoom = 10;
//		loadAllMap();
	}

	@Override
	public ArrayList<BlogIntroductionVO> getBlogs() {
		ArrayList<BlogPO> blogs = new ArrayList<BlogPO>();
Debugger.log(CurrentUserStatus.getInstance().getCurrentUserID()+"  " + (blogDataService == null));
		try {
			blogs = blogDataService.searchByUserID(CurrentUserStatus.getInstance().getCurrentUserID());
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList<BlogIntroductionVO> currentBlogs = new ArrayList<BlogIntroductionVO>();
		
		Debugger.log("blogs' size" + blogs.size());
		for (BlogPO b : blogs)
			if (blogs.indexOf(b) < 3)
				currentBlogs.add(BlogHomeBLService_imp.toIntroduction(b));

		return currentBlogs;
	}

	@Override
	public ArrayList<PlanIntroductionVO> getPlans() {
		
		ArrayList<PlanPO> plans = new ArrayList<PlanPO>();
		
		try {
			plans = planDataService.searchByUser(CurrentUserStatus.getInstance().getCurrentUserID());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<PlanIntroductionVO> currentPlans = new ArrayList<PlanIntroductionVO>();
		
		for (PlanPO p : plans)
			if (plans.indexOf(p) < 3)
				currentPlans.add(PlanHomeBLService_imp.toIntroduction(p));

		return currentPlans;
	}

	@Override
	public ImageIcon getMap(int zoom) {
		// 需要得到用户的所有去过的城市
		if (mapList.get(zoom) == null){
			Debugger.log("CITY NULL");
			return MapUtils.getMap(center, zoom, markers, type, 480, 180, 2);
//			return new ImageIcon();
		}
		return mapList.get(zoom);
	}
	
	@Override
	public void changeMapType(String type, int zoom) {
		this.type = type;
		changeCenter(center, zoom);
	}
	
	@Override
	public void changeCenter(String center, int zoom) {
		getAllCitys();
		currentZoom = zoom;
		this.center = center;
		mapList.clear();
		loadAllMap();
		return;
	}

	// private
	
	private void loadAllMap(){
		for (int i = 1; i <= 20; i++){
			LoadMapThread t = new LoadMapThread(i);
			t.start();
		}
	}
	
	private class LoadMapThread extends Thread{
		 volatile boolean exit = false;
		 int zoom = 1;
		 
		 public LoadMapThread(int zoom){
			 this.zoom = zoom;
		 }
		 
		 public void run() {

			mapList.put(zoom, MapUtils.getMap(center, zoom, markers, type, 480, 180, 2));
			if (exit == true)
				mapList.put(zoom, null);
		 }
	}
	
	private void getAllCitys(){
		ArrayList<PlanPO> plans = new ArrayList<PlanPO>();
		try {
			plans = planDataService.searchByUser(CurrentUserStatus.getInstance().getCurrentUserID());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (PlanPO p : plans)
			if (!markers.contains(p.getCityName()))
				markers.add(p.getCityName());
	}
}
