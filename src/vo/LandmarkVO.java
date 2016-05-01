package vo;

import javax.swing.ImageIcon;

public class LandmarkVO {

	/**
	 * @author wanglizhi
	 * @author SongShuo
	 */
	public String name;
	public String pictureFile; // 存放其图片的路径
	public ImageIcon picture;

	public String introduction; // 是直接写在里面，还是用文件读取保存的？@songshuo问
	public boolean isApplaused;
	public int applauseNum = 0;
	public String tag1;
	public String tag2;

	/**
	 * 
	 * @param name
	 * @param isApplaused
	 * @param applauseNum
	 * @param tag1
	 * @param tag2
	 */
	public LandmarkVO(String name, boolean isApplaused, int applauseNum,
			String tag1, String tag2) {
		super();
		this.name = name;
		this.isApplaused = isApplaused;
		this.applauseNum = applauseNum;
		this.tag1 = tag1;
		this.tag2 = tag2;
		this.pictureFile = "img//景点//" + name + ".jpg";
		this.picture = new ImageIcon(pictureFile);

	}

}
