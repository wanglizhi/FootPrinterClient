package ui.home;
/*
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import tools.Config;
import ui.foundation.BasePanel;
import vo.LoginVO;
import controller.HomepageController;

public class LoginPanel extends BasePanel {
	private JPanel innerPanel;
	private JTextField nameTextField;
	private JButton registerButton;
	private JButton confirmButton;
	private JLabel reminderLabel;
	private JPasswordField passwordTextField;
	
	int clickTextFieldCount = 0;

	public LoginPanel(HomepageController homepageController) {
		this.setPreferredSize(new Dimension(Config.PANEL_WIDTH, Config.PANEL_HEIGHT));
		this.setLayout(null);
		this.setBackground(Color.white);
		{
			innerPanel = new JPanel();
			this.add(innerPanel);
			innerPanel.setLayout(null);
			innerPanel.setBounds(715, 86, 472, 544);
			innerPanel.setBackground(new java.awt.Color(217, 236, 255));
			{
				nameTextField = new JTextField();
				innerPanel.add(nameTextField);
				nameTextField.setText("用户名");
				
				nameTextField.addMouseListener(new MouseAdapter(){
						public void mousePressed(MouseEvent e){
							if(clickTextFieldCount==0){
								nameTextField.setText("");
							}
							clickTextFieldCount++;
						}
					}
					
				);
				nameTextField.addMouseMotionListener(new MouseMotionListener(){

					@Override
					public void mouseDragged(java.awt.event.MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseMoved(java.awt.event.MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
				nameTextField.setBounds(61, 132, 266, 43);
			}
			{
				passwordTextField = new JPasswordField();
				innerPanel.add(passwordTextField);
				passwordTextField.setText("12345");
				passwordTextField.setBounds(61, 252, 266, 39);
				
				passwordTextField.addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent e){
						if(clickTextFieldCount==0){
							passwordTextField.setText("");
						}
						clickTextFieldCount++;
					}
				}
			);
			}
			{
				reminderLabel = new JLabel();
				innerPanel.add(reminderLabel);
				reminderLabel.setText("用户名不存在或密码错误");
				reminderLabel.setBounds(66, 88, 165, 20);
				reminderLabel.setVisible(false);

			}
			{
				confirmButton = new JButton();
				innerPanel.add(confirmButton);
				confirmButton.setText("登陆");
				confirmButton.setBounds(61, 360, 110, 40);
				confirmButton
						.addActionListener(new ConfirmButtonActionListener());
				confirmButton.setBorder(BorderFactory
						.createEtchedBorder(BevelBorder.LOWERED));

			}
			{
				registerButton = new JButton();
				innerPanel.add(registerButton);
				registerButton.setText("注册");
				registerButton.setBounds(217, 361, 110, 40);
				registerButton
						.addActionListener(new RegisterButtonActionListener());
			}
		}

	}

	private LoginVO getLoginVO() {
		LoginVO lvo = new LoginVO(nameTextField.getText(),
				passwordTextField.getText());
		return lvo;
	}

	class ConfirmButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class RegisterButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
*/