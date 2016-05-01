package businessLogicService;

import java.util.ArrayList;

import vo.LoginVO;
import vo.UserInforVO;

/**
 * 
 * @author SongShuo 主管登陆、注册、用户修改个人信息的接口
 */
public interface UserManageBLService {

	public boolean register(UserInforVO userInfor);

	public UserInforVO login(LoginVO loginInput);

	public boolean modifyUser(UserInforVO modifiedInput);// 从界面获取修改了的用户信息

	public ArrayList<UserInforVO> searchUser(String name); // 搜索用户
	
	public boolean exit(int userID);

}
