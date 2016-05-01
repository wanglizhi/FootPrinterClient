package vo;

import java.awt.Image;
import java.net.URL;

import bean.HotelPO;
import bean.PlacePO;

public class PlanHotelVO extends PlanSelectorVO{

	public PlanHotelVO(Image pic, String name, int landmarkID, int landmarkType) {
		super(pic, name, landmarkID, landmarkType);
		// TODO Auto-generated constructor stub
	}

	public PlanHotelVO(URL url, String name, int landmarkID, int landmarkType) {
		super(url, name, landmarkID, landmarkType);
		// TODO Auto-generated constructor stub
	}
	
	public PlanHotelVO(PlacePO hp, int type){
		super(hp.getImage(),hp.getName(), hp.getID(), type);
	}

}
