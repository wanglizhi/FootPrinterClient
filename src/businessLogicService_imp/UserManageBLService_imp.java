package businessLogicService_imp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import dataService.UserManageDataService;

import startup.CurrentUserStatus;
import tools.Debugger;
import vo.LoginVO;
import vo.UserInforVO;
import bean.UserPO;
import businessLogicService.UserManageBLService;

public class UserManageBLService_imp implements UserManageBLService {

	CurrentUserStatus currentUserStatus = CurrentUserStatus.getInstance();
	UserManageDataService userManageDataService;
	
	/**
	 * 
	 * @param userManageDataService
	 */
	public UserManageBLService_imp(UserManageDataService userManageDataService) {
		this.userManageDataService = userManageDataService;

	}

	// 用于测试
	public UserManageBLService_imp() {

	}

	@Override
	public boolean register(UserInforVO userInfor) {
		boolean isSuccess = false;
		try {
			UserPO po = VOToPO(userInfor);
			po.setRegisteredDate(new Date());
			isSuccess = userManageDataService.register(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return isSuccess;
		return true;
	}

	/**
	 * @Override
	 * @author SongShuo
	 * @param LoginVO
	 *            将从网络得来的用户PO存入currentuserstatus类中，同时 从网络得到PO再转化成VO传出
	 */
	@Override
	public UserInforVO login(LoginVO loginInput) {
		UserInforVO userInforVO = null;
		UserPO userPO = null;
		
		try {
			userPO = userManageDataService.login(VOToPO(loginInput));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		// stub:
		//userPO = new UserPO(1, "ss", "123456", null,true,"3321234567","13900000000","song shuo","nju",null,null);
		//
		 if(userPO!=null){
		userInforVO = POToVO(userPO);
		 }
		currentUserStatus.setCurrentUser(userInforVO, userPO);
		return userInforVO;
	}

	@Override
	public boolean modifyUser(UserInforVO modifiedInput) {
		boolean isSuccess = false;
		try {
			UserPO po = VOToPO(modifiedInput);
			
			isSuccess = userManageDataService.modifyUser(po);
			if(isSuccess){
				CurrentUserStatus.getInstance().setCurrentUser(modifiedInput, po);
				Debugger.log("vo tag"+modifiedInput.tags);
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return isSuccess;
		return true;
	}

	@Override
	public ArrayList<UserInforVO> searchUser(String name) {
		ArrayList<UserInforVO> userVOList = new ArrayList<UserInforVO>();
		if(userManageDataService==null)
			Debugger.log("!!!!!!!!!!!!!!dataservice null!!!!!!!!!");
				try {
					ArrayList<UserPO> userPOList = userManageDataService.searchUser(name);
					for(int i = 0; i < userPOList.size();i++){
						userVOList.add(POToVO(userPOList.get(i)));
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return userVOList;
	}
	
	private UserInforVO POToVO(UserPO po){
		UserInforVO vo = new UserInforVO(po.getUserName(),po.getPassword(),po.isSex(),po.getQq(),po.getTel(),
				po.getRealName(),po.getAddress(),po.getTags());
		
		return vo;
	}
	
	/**
	 * 将客户端传给服务器的vo转换成po
	 * @param vo
	 * @return
	 */
	private UserPO VOToPO(UserInforVO vo){
		int id = -1;
		
		if(CurrentUserStatus.getInstance().getIsLogin()){
			
			id = CurrentUserStatus.getInstance().getCurrentUserID();
		}
		UserPO po = new UserPO(id,vo.name,vo.password,null,vo.sex,vo.qq,vo.tel,vo.realName,vo.address,vo.tags,null);
		return po;
	}
	
	private UserPO VOToPO(LoginVO vo){
		UserPO po = new UserPO(-1,vo.name,vo.password,null,false,null,null,null,null,null,null);
		
		return po;
	}

	@Override
	public boolean exit(int userID) {
		try {
			return userManageDataService.exit(userID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
