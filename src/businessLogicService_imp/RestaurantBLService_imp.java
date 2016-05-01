package businessLogicService_imp;

import javax.swing.ImageIcon;

import startup.CurrentUserStatus;
import tools.FileUtils;
import tools.LandmarkType;
import ui.foundation.BasePanel;
import vo.PlaceVO;
import bean.PlacePO;
import dataService.PlaceDataService;

public class RestaurantBLService_imp extends PlaceBLService_imp{

	public RestaurantBLService_imp(PlaceDataService placeDataService) {
		super(placeDataService);
		// TODO Auto-generated constructor stub
	}
	public PlaceVO POToVO(PlacePO po){
		boolean isApplaused=false;
		isApplaused=checkIsApplaused(CurrentUserStatus.getInstance().getCurrentUserID(), po.getCityID());
		ImageIcon image=null;
			image=BasePanel.createFixedSizeImage(po.getImage(), 300, 240);
		PlaceVO vo=new PlaceVO(po.getID(),
				po.getName(),
				po.getCityID(),
				LandmarkType.RESTAURANT,
				po.getIntroduction(),
				po.getType(),
				po.getApplauseNum(),
				//po.getImage(), Imag=================ע��ı�ͼƬ
				image,
				po.getPrice(),//price
				isApplaused);//isApplaused
	return vo;
	}

}
