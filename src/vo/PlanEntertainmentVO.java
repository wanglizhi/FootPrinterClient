package vo;

import java.awt.Image;
import java.net.URL;

import bean.EntertainmentPO;
import bean.PlacePO;

public class PlanEntertainmentVO extends PlanSelectorVO {

	public PlanEntertainmentVO(Image pic, String name, int landmarkID, int landmarkType) {
		super(pic, name, landmarkID, landmarkType);
		// TODO Auto-generated constructor stub
	}

	public PlanEntertainmentVO(URL url, String name, int landmarkID, int landmarkType) {
		super(url, name, landmarkID, landmarkType);
		// TODO Auto-generated constructor stub
	}

	public PlanEntertainmentVO(PlacePO ep, int type) {
		super(ep.getImage(), ep.getName(), ep.getID(), type);
	}

}
