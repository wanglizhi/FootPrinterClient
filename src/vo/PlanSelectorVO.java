package vo;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class PlanSelectorVO {
	public Image pic;
	public String name;
	public int landmarkID;
	public int landmarkType;

	public PlanSelectorVO(Image pic, String name, int landmarkID,
			int landmarkType) {
		this.pic = pic;
		this.name = name;
		this.landmarkID = landmarkID;
		this.landmarkType = landmarkType;
	}

	// 测试用的方法
	public PlanSelectorVO(URL url, String name, int landmarkID, int landmarkType) {
		ImageIcon icon = new ImageIcon(url);
		pic = icon.getImage();
		this.name = name;
		this.landmarkID = landmarkID;
		this.landmarkType = landmarkType;
	}
}
