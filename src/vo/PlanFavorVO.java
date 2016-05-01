package vo;

import java.awt.Image;
import java.net.URL;


public class PlanFavorVO extends PlanSelectorVO {

	public PlanFavorVO(Image pic, String name, int landmarkID, int landmarkType) {
		super(pic, name, landmarkID, landmarkType);
		// TODO Auto-generated constructor stub
	}

	public PlanFavorVO(URL url, String name,int landmarkID, int landmarkType) {
		super(url, name, landmarkID, landmarkType);
	}

}
