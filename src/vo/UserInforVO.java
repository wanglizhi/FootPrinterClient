package vo;

import java.util.ArrayList;

/**
 * 
 * @author SongShuo 注册的时候，从客户端UI得到的数据结构
 * @param name
 * @param password
 * @param sex
 * @param qq
 * @param tel
 * @param realName
 * @param address
 * @param tag
 */
public class UserInforVO {

	public String name;
	public 	String password;
	public boolean sex; // man: false woman:true
	public String qq;
	public String tel;
	public String realName;
	public String address;
	public String headServerPath;
	public String tags;

	/**
	 * 
	 * @param name
	 * @param password
	 * @param sex
	 * @param qq
	 * @param tel
	 * @param realName
	 * @param address
	 * @param tag
	 * @param headServerPath
	 */
	public UserInforVO(String name, String password, boolean sex, String qq,
			String tel, String realName, String address, String tag) {
		super();
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.qq = qq;
		this.tel = tel;
		this.realName = realName;
		this.address = address;
		this.tags = tag; // ////***delete
	}
	
	public UserInforVO(String name, String password, boolean sex, String qq,
			String tel, String realName, String address, String tag,String url) {
		super();
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.qq = qq;
		this.tel = tel;
		this.realName = realName;
		this.address = address;
		this.tags = tag; // ////***delete
		this.headServerPath = url;
	}

	public String getName() {
		return name;
	}

}
