package vo;

import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class BlogVO {
	public int blogID;
	public String userName;
	public String cityName;
	public String title;
	public int likeNum;
	public ArrayList<String> items;

	public String text;
	public int cityID;
	public int userID;
	public URL pictureFile; // 存放其图片的路径
	public ImageIcon picture;

	/**
	 * 
	 * @param userName
	 * @param cityName
	 * @param title
	 * @param likeNum
	 * @param url
	 */
	public BlogVO(String userName,String cityName,String title,int likeNum,URL url){
		this.userName=userName;
		this.cityName=cityName;
		this.title=title;
		this.likeNum=likeNum;
		this.pictureFile = url;
	}
	///*
	public BlogVO(String title, int likeNum) {
		super();
		this.title = title;
		this.cityID = cityID;
		this.userID = userID;
		this.likeNum = likeNum;
	//	this.pictureFile = "img//记录//" + title + ".jpg";
		this.picture = new ImageIcon(pictureFile);
	}
	//*/


	public BlogVO(int blogID,String title,String userName, int likeNum) {
		super();
		this.blogID=blogID;
		this.title = title;
		this.userName=userName;
		this.likeNum = likeNum;
	}
	
	public BlogVO() {
	}
//>>>>>>> .r581
	
	/**
	 * 
	 * @param blogID
	 * @param userName
	 * @param cityName
	 * @param title
	 * @param cityID
	 * @param userID
	 * @param url
	 */
	public BlogVO(int blogID, String userName, String cityName, String title,
			int cityID, int userID,URL url) {
		super();
		this.blogID = blogID;
		this.userName = userName;
		this.cityName = cityName;
		this.title = title;
		this.cityID = cityID;
		this.userID = userID;
		this.pictureFile =url;
	}

}
