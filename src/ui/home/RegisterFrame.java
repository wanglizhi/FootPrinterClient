package ui.home;

import java.awt.Dimension;

import javax.swing.JFrame;

import controller.UserManageController;

public class RegisterFrame extends JFrame {

	public RegisterFrame(UserManageController loginRegisterController) {
		this.setSize(new Dimension(1000, 650));
		this.setVisible(true);
		this.add(new RegisterPanel(loginRegisterController,this));
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public static void main (String args[]){
		new RegisterFrame(null);
	}
}
