package startup;

import java.util.ArrayList;

import dataService.PlanDataService;

import vo.UserInforVO;
import bean.PlanPO;
import bean.UserPO;
import businessLogicService.UserManageBLService;

/**
 * 
 * @author SongShuo 提供当前登陆了的用户的各种状态，用以显示在各panel上，为各panel服务
 *         当UserManageBLService_imp的login调用以后，会调用该类的setCurrentUserStatus
 *         当用到任何与当前用户有关的信息时，调用getCurrentUser
 * 
 *         单键模式！！
 */
public class CurrentUserStatus {
	// 与用户是否登陆有关：
	private boolean isLogin = false;
	private UserInforVO currentUser = null;
	private UserPO currentUserPO = null;
	private UserManageBLService userManageBLService;

	private static CurrentUserStatus currentUserStatus = null;

	/** 该方法转为UserManageBLService_imp的login制作
	 * 
	 * @return 返回是否成功
	*/
	public boolean setCurrentUser(UserInforVO userInforVO, UserPO userPO) {
		if (isLogin == false || currentUser == null) {

			currentUser = userInforVO;
			currentUserPO = userPO;
			isLogin = true;
			return true;
		} else {

			return false;
		}
	}

	public static CurrentUserStatus getInstance() {
		if (currentUserStatus == null) {
			currentUserStatus = new CurrentUserStatus();
		}
		return currentUserStatus;
	}

	private CurrentUserStatus() {

	}

	public UserInforVO getCurrentUserVO() {
		return currentUser;
	}

	public UserPO getCurrentUserPO() {
		return currentUserPO;
	}
	public int getCurrentUserID(){
		if(currentUserPO!=null)
			return currentUserPO.getUserID();
		else
			return 0;
	}
	public boolean getIsLogin() {
		return isLogin;
	}
	
}
