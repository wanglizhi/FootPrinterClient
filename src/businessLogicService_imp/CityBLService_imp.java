package businessLogicService_imp;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import startup.ClientNetworkInit;
import startup.CurrentUserStatus;
import tools.FileUtils;
import ui.foundation.BasePanel;
import vo.BlogVO;
import vo.CityVO;
import vo.PlanVO;
import dataService.BlogDataService;
import dataService.CityDataService;
import dataService.PlanDataService;
import bean.BlogPO;
import bean.CityPO;
import bean.PlanPO;
import businessLogicService.CityBLService;

public class CityBLService_imp implements CityBLService {
	private CityDataService cityDataService;
	private PlanDataService planDataService;
	private BlogDataService blogDataService;

	public CityBLService_imp(CityDataService cityDataService) {
		super();
		this.cityDataService = cityDataService;
		planDataService=ClientNetworkInit.planDataService;
		blogDataService=ClientNetworkInit.blogDataService;
	}


	@Override
	public boolean addApplause(int userID, int cityID) {
		// TODO Auto-generated method stub
		try {
			return cityDataService.addApplause(userID, cityID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteApplause(int userID, int cityID) {
		// TODO Auto-generated method stub
		try {
			return cityDataService.deleteApplause(userID, cityID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkIsApplaused(int userID, int cityID) {
		// TODO Auto-generated method stub
		try {
			return cityDataService.checkApplause(userID, cityID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public CityVO showCity(int cityID) {
		// TODO Auto-generated method stub
		
		try {
			return POToVO(cityDataService.searchByID(cityID));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public ArrayList<CityVO> searchByName(String cityName) {
		// TODO Auto-generated method stub
		ArrayList<CityVO> voList=new ArrayList<CityVO>();
		try {
			for(CityPO po:cityDataService.searchByName(cityName)){
				voList.add(POToVO(po));
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return voList;
	}
	
	private CityVO POToVO(final CityPO po){
		boolean isApplaused=false;
		if(CurrentUserStatus.getInstance().getIsLogin()){
			isApplaused=checkIsApplaused(CurrentUserStatus.getInstance().getCurrentUserID(), po.getCityID());
		}
		ArrayList<ImageIcon> images=new ArrayList<ImageIcon>();
		for(URL url:po.getImages()){
				images.add(BasePanel.createFixedSizeImage(url, 400, 300));
		}
		if(images.size()<3){
			images.add(BasePanel.createFixedSizeImage("img/景点/周庄.jpg", 400, 300));
			images.add(BasePanel.createFixedSizeImage("img/景点/滨湖公园.jpg", 400, 300));
			images.add(BasePanel.createFixedSizeImage("img/景点/东小天池.jpg", 400, 300));
		}
		CityVO vo=new CityVO(po.getCityID(),
				po.getName(),
				po.getIntroduction(),
				po.getInformation(),
				po.getApplauseNum(),
				isApplaused,
				images);//images
		return vo;
	}


	@Override
	public ArrayList<PlanVO> recommendPlan(int cityID) {
		// TODO Auto-generated method stub
		ArrayList<PlanVO> voList=new ArrayList<PlanVO>();
		try {
			ArrayList<PlanPO> poList=planDataService.searchByCity(cityID);
			for(PlanPO po:poList){
				PlanVO vo=new PlanVO(po.getPlanID(), po.getUserName(), po.getTitle(), po.getApplauseNum());
				voList.add(vo);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return voList;
	}


	@Override
	public ArrayList<BlogVO> recommendBlog(int cityID) {
		// TODO Auto-generated method stub
		ArrayList<BlogVO> voList=new ArrayList<BlogVO>();
		try {
			ArrayList<BlogPO> poList=blogDataService.searchByCityID(cityID);
			for(BlogPO po:poList){
				BlogVO vo=new BlogVO(po.getBlogID(), po.getTitle(), po.getUserName(), po.getApplauseNum());
				voList.add(vo);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return voList;
	}
	

}
