package businessLogicService_imp;

import javax.swing.ImageIcon;

import startup.CurrentUserStatus;
import tools.FileUtils;
import tools.LandmarkType;
import ui.foundation.BasePanel;
import vo.PlaceVO;
import bean.PlacePO;
import dataService.PlaceDataService;

public class MarketBLService extends PlaceBLService_imp{

	public MarketBLService(PlaceDataService placeDataService) {
		super(placeDataService);
		// TODO Auto-generated constructor stub
	}

	public PlaceVO POToVO(PlacePO po){
		boolean isApplaused=false;
		isApplaused=checkIsApplaused(CurrentUserStatus.getInstance().getCurrentUserID(), po.getCityID());
		ImageIcon image=null;
//		if(FileUtils.isConnect(po.getImage())){
			image=BasePanel.createFixedSizeImage(po.getImage(), 300, 240);
//		}else{
//			image=BasePanel.createFixedSizeImage("img/景点/market.png", 300, 240);
//		}
		PlaceVO vo=new PlaceVO(po.getID(),
				po.getName(),
				po.getCityID(),
				LandmarkType.MARKET,
				po.getIntroduction(),
				po.getType(),
				po.getApplauseNum(),
				//po.getImage(), Imag=================注意改变图片
				image,
				po.getPrice(),//price
				isApplaused);//isApplaused
	return vo;
	}
}
