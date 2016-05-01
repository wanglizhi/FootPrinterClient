package controller;

import java.util.ArrayList;

import startup.ControllerFactory;
import ui.home.LoginFrame;
import ui.home.ModifyUserFrame;
import ui.home.RegisterFrame;
import vo.LoginVO;
import vo.UserInforVO;
import businessLogicService.UserManageBLService;

public class UserManageController {

	private UserManageBLService userManageBLService;
	private LoginFrame loginFrame;
	private RegisterFrame registerFrame;
	private ModifyUserFrame modifyUserFrame;
	/*
	public UserManageController(){
		loginFrame = new LoginFrame(this);
		registerFrame = new RegisterFrame(this);
		modifyUserFrame = new ModifyUserFrame(this);
		
	}*/
	public void createLoginFrame() {
		if(loginFrame==null){
			
			loginFrame = new LoginFrame(this);
		}
		
		loginFrame.setVisible(true);
	}
	public LoginFrame getLoginFrame(){
		
		return loginFrame;
	}

	public void createRegisterFrame() {
		if(registerFrame==null)
		registerFrame = new RegisterFrame(this);
		
		registerFrame.setVisible(true);
	}
	public RegisterFrame getRegisterFrame(){
		return registerFrame;
	}

	public void createModifyUserFrame() {
		if(modifyUserFrame==null)
		modifyUserFrame = new ModifyUserFrame(this);
		
		modifyUserFrame.setVisible(true);
		modifyUserFrame.getModifyUserPanel().refresh();
	}

	public UserManageController(UserManageBLService userManageBLService) {
		this.userManageBLService = userManageBLService;
	}

	/**
	 * !!!!!!!!!!!!!!!!!!!注意，在此改变右上角的显示啊
	 * 
	 * @param loginVO
	 * @return
	 */
	public UserInforVO login(LoginVO loginVO) {
		UserInforVO userInforVO = userManageBLService.login(loginVO);
		if (userInforVO != null){
			//CurrentUserStatus
			System.out.println("登陆成功！噢耶！！");
			  ControllerFactory.getInstance().homepageController.getHomepageFrame();
			ControllerFactory.getInstance().homepageController.getHomepageFrame().headPanel.changeRightTopCorner();
		}
		return userInforVO;
	}

	public ArrayList<UserInforVO> search(String name) {
		return userManageBLService.searchUser(name);
	}

	public boolean register(UserInforVO userInforVO) {
		boolean isSuccess = userManageBLService.register(userInforVO);
		return isSuccess;
	}

	public boolean modify(UserInforVO userInforVO) {
		boolean isSuccess = userManageBLService.modifyUser(userInforVO);
		return isSuccess;
	}

}
