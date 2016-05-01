package vo;

import java.awt.Image;
import java.net.URL;

import bean.PlacePO;
import bean.RestaurantPO;

public class PlanRestaurantVO extends PlanSelectorVO {

	public PlanRestaurantVO(Image pic, String name, int landmarkID, int landmarkType) {
		super(pic, name, landmarkID, landmarkType);
		// TODO Auto-generated constructor stub
	}

	public PlanRestaurantVO(URL url, String name, int landmarkID, int landmarkType) {
		super(url, name, landmarkID, landmarkType);
		// TODO Auto-generated constructor stub
	}

	public PlanRestaurantVO(PlacePO rp, int type) {
		super(rp.getImage(), rp.getName(), rp.getID(), type);
	}

}
