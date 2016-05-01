package vo;


import javax.swing.ImageIcon;
import tools.LandmarkType;

public class PlaceVO {
	public int id;
	public String name;
	public int cityID;
	public LandmarkType category;//1,2,3,4,5 æ∞µ„£¨æ∆µÍ£¨≤Õπ›£¨”È¿÷£¨…Ã≥°
	public String introduction;
	public String type;
	public int applauseNum;
	public ImageIcon image;
	public String price;
	public boolean isApplaused;
	public PlaceVO(int id, String name, int cityID, LandmarkType category,
			String introduction, String type,
			int applauseNum, ImageIcon image, String price,boolean isApplaused) {
		super();
		this.id = id;
		this.name = name;
		this.cityID = cityID;
		this.category = category;
		this.introduction = introduction;
		this.type = type;
		this.applauseNum = applauseNum;
		this.image = image;
		this.price = price;
		this.isApplaused=isApplaused;
	}

}
