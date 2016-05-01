package vo;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author wanglizhi
 */
public class CityVO {
	
	public int id;
	public String name;
	public String introduction;
	public String information;
	public CityVO(int id, String name, String introduction, String information,
			int applauseNum, boolean isApplaused, ArrayList<ImageIcon> images) {
		super();
		this.id = id;
		this.name = name;
		this.introduction = introduction;
		this.information = information;
		this.applauseNum = applauseNum;
		this.isApplaused = isApplaused;
		this.images = images;
	}
	public int applauseNum;
	public boolean isApplaused;
	public ArrayList<ImageIcon> images;
	public ArrayList<PlaceVO> landmarkList;
	public ArrayList<PlaceVO> hotelList;
	public ArrayList<PlaceVO> restaurantList;
	public ArrayList<PlaceVO> entertainmentList;
	public ArrayList<PlaceVO> marketList;
	
	public ArrayList<PlanVO> planList;
	public ArrayList<BlogVO> blogList;
	
}
