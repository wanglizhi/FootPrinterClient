package vo;

import java.awt.Image;
import java.net.URL;

import bean.LandmarkPO;
import bean.PlacePO;


public class PlanSpotVO extends PlanSelectorVO{

	public PlanSpotVO(Image pic, String name, int landmarkID, int landmarkType) {
		super(pic, name, landmarkID, landmarkType);
		// TODO Auto-generated constructor stub
	}

	public PlanSpotVO(URL url, String name, int landmarkID, int landmarkType) {
		super(url, name, landmarkID, landmarkType);
		// TODO Auto-generated constructor stub
	}
	
	public PlanSpotVO(PlacePO lp, int type){
		super(lp.getImage(),lp.getName(), lp.getID(), type);
	}

}
