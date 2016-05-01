package vo;

import java.awt.Image;
import java.net.URL;

import bean.MarketPO;
import bean.PlacePO;

public class PlanShoppingMallVO extends PlanSelectorVO{

	public PlanShoppingMallVO(Image pic, String name, int landmarkID, int landmarkType) {
		super(pic, name, landmarkID, landmarkType);
		// TODO Auto-generated constructor stub
	}

	public PlanShoppingMallVO(URL url, String name, int landmarkID, int landmarkType) {
		super(url, name, landmarkID, landmarkType);
		// TODO Auto-generated constructor stub
	}
	
	public PlanShoppingMallVO(PlacePO mp, int type){
		super(mp.getImage(), mp.getName(), mp.getID(),type);
	}

}
