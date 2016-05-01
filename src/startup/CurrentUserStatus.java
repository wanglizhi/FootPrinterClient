package startup;

import java.util.ArrayList;

import dataService.PlanDataService;

import vo.UserInforVO;
import bean.PlanPO;
import bean.UserPO;
import businessLogicService.UserManageBLService;

/**
 * 
 * @author SongShuo �ṩ��ǰ��½�˵��û��ĸ���״̬��������ʾ�ڸ�panel�ϣ�Ϊ��panel����
 *         ��UserManageBLService_imp��login�����Ժ󣬻���ø����setCurrentUserStatus
 *         ���õ��κ��뵱ǰ�û��йص���Ϣʱ������getCurrentUser
 * 
 *         ����ģʽ����
 */
public class CurrentUserStatus {
	// ���û��Ƿ��½�йأ�
	private boolean isLogin = false;
	private UserInforVO currentUser = null;
	private UserPO currentUserPO = null;
	private UserManageBLService userManageBLService;

	private static CurrentUserStatus currentUserStatus = null;

	/** �÷���תΪUserManageBLService_imp��login����
	 * 
	 * @return �����Ƿ�ɹ�
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
