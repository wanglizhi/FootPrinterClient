package businessLogicService_imp;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import dataService.PlaceDataService;
import startup.CurrentUserStatus;
import tools.LandmarkType;
import ui.foundation.BasePanel;
import vo.PlaceVO;
import bean.HotelPO;
import bean.LandmarkPO;
import bean.PlacePO;
import bean.RestaurantPO;
import businessLogicService.PlaceBLService;

public class PlaceBLService_imp implements PlaceBLService{
	private PlaceDataService placeDataService;
	public PlaceBLService_imp(PlaceDataService placeDataService){
		this.placeDataService=placeDataService;
	}

	@Override
	public PlaceVO showPlace(int placeID) {
		// TODO Auto-generated method stub
		try {
			return POToVO(placeDataService.searchByID(placeID));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addApplause(int userID, int placeID) {
		// TODO Auto-generated method stub
		try {
			return placeDataService.addApplause(userID, placeID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteApplause(int userID, int placeID) {
		// TODO Auto-generated method stub
		try {
			return placeDataService.deleteApplause(userID, placeID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkIsApplaused(int userID, int placeID) {
		// TODO Auto-generated method stub
		try {
			return placeDataService.checkApplause(userID, placeID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ArrayList<PlaceVO> searchByCityID(int cityID) {
		// TODO Auto-generated method stub
		return searchByCityID(cityID, 24);
	}
	
	public PlaceVO POToVO(PlacePO po){
		boolean isApplaused=false;
		if(CurrentUserStatus.getInstance().getIsLogin()){
			isApplaused=checkIsApplaused(CurrentUserStatus.getInstance().getCurrentUserID(), po.getCityID());
		}	
		PlaceVO vo=new PlaceVO(po.getID(),
				po.getName(),
				po.getCityID(),
				LandmarkType.LANDMARK,
				po.getIntroduction(),
				po.getType(),
				po.getApplauseNum(),
				//po.getImage(), Imag=================×¢Òâ¸Ä±äÍ¼Æ¬
				BasePanel.createFixedSizeImage(po.getImage(), 300, 240),
				po.getPrice(),//price
				isApplaused);//isApplaused
	return vo;
	}

	@Override
	public ArrayList<PlaceVO> searchByTag(ArrayList<String> tags) {
		// TODO Auto-generated method stub
		ArrayList<PlacePO> poList=null;
		ArrayList<PlaceVO> voList=new ArrayList<PlaceVO>();
		try {
			poList=placeDataService.searchByTag(tags);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(PlacePO place:poList){
			voList.add(POToVO(place));
		}
		return voList;
	}

	@Override
	public ArrayList<PlaceVO> search(String key) {
		// TODO Auto-generated method stub
		ArrayList<PlacePO> poList=null;
		ArrayList<PlaceVO> voList=new ArrayList<PlaceVO>();
		try {
			poList=placeDataService.search(key);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(PlacePO place:poList){
			voList.add(POToVO(place));
		}
		return voList;
	}

	@Override
	public ArrayList<PlaceVO> searchByCityID(int cityID, int size) {
		// TODO Auto-generated method stub
		ArrayList<PlacePO> poList=new ArrayList<PlacePO>();
		ArrayList<PlaceVO> voList=new ArrayList<PlaceVO>();
		try {
			poList=placeDataService.searchByCityID(cityID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(PlacePO place:poList){
			if(voList.size()>=size) return voList;
			voList.add(POToVO(place));
		}
		return voList;
	}

}
