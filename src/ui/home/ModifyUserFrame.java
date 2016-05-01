package ui.home;

import java.awt.Dimension;

import javax.swing.JFrame;

import controller.UserManageController;

public class ModifyUserFrame extends JFrame {
	
	private ModifyUserPanel modifyUserPanel;
	
	public ModifyUserFrame (UserManageController loginRegisterController) {
		
		modifyUserPanel = new ModifyUserPanel(loginRegisterController,this);
		this.setSize(new Dimension(1000, 650));
		this.setVisible(true);
		this.add(modifyUserPanel);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public static void main(String[] args){
		new ModifyUserFrame(null);
	}
	
	public ModifyUserPanel getModifyUserPanel(){
			
		return modifyUserPanel;
	}
	
}