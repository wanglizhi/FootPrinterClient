package vo;

import javax.swing.ImageIcon;

public class LandmarkVO {

	/**
	 * @author wanglizhi
	 * @author SongShuo
	 */
	public String name;
	public String pictureFile; // �����ͼƬ��·��
	public ImageIcon picture;

	public String introduction; // ��ֱ��д�����棬�������ļ���ȡ����ģ�@songshuo��
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
		this.pictureFile = "img//����//" + name + ".jpg";
		this.picture = new ImageIcon(pictureFile);

	}

}
