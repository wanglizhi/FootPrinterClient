package ui.home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import startup.ControllerFactory;
import tools.Config;
import tools.Debugger;
import vo.UserInforVO;
import controller.UserManageController;

public class RegisterPanel extends JPanel {

	private ImageIcon logoIcon = new ImageIcon("img//logo.png");
	private JLabel logoLabel = new JLabel();
	private JLabel titleLabel;
	private JPasswordField passwordField1;
	private JLabel passwordLabel2;
	private JFormattedTextField QQTextField;
	private JButton redoButton;
	private JButton confirmButton;
	private JComboBox tagComboBox2;
	private JLabel tagLabel2;
	private JComboBox tagComboBox1;
	private JLabel tagLabel;
	private JTextField addressTextField;
	private JLabel addressLabel;
	private JLabel telLabel;
	private JFormattedTextField telTextField;
	// private JLabel QQReminderLabel;
	private JTextField realNameTextField;
	private JLabel realNameLabel;
	private JLabel qqLabel;
	private JComboBox sexComboBox;
	private JLabel nameReminderLabel;
	private JLabel sexLabel;
	private JLabel passwordErrorLabel;
	private JLabel passwordCorrectLabel;
	private JPasswordField passwordField2;
	private JLabel passwordLabel;
	private JTextField usernameTextField;
	private JLabel nameLabel;
	private JLabel reminderLabel;
//	private MaskFormatter formatter;
	private DecimalFormat numberFormat ;

	private ArrayList<JTextField> allTextFields = new ArrayList<JTextField>();// 方便统一管理

	private UserManageController userManageController;
	private RegisterFrame registerFrame;

	public RegisterPanel(UserManageController loginRegisterController,
			RegisterFrame registerFrame) {
		this.registerFrame = registerFrame;

		this.userManageController = loginRegisterController;

		this.setLayout(null);
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(1000, 700));
		numberFormat = new DecimalFormat("###########");
		{
			logoLabel.setIcon(logoIcon);
			logoLabel.setBounds(264, 20, 300, 45);
			this.add(logoLabel);

			titleLabel = new JLabel();
			this.add(titleLabel);
			titleLabel.setText("· 用户注册");
			titleLabel.setBounds(576, 20, 127, 46);
			titleLabel.setFont(new java.awt.Font("幼圆", 1, 18));
			titleLabel.setForeground(UIHelper.getSelectedColot());
		}
		{
			reminderLabel = new JLabel();
			this.add(reminderLabel);
			reminderLabel.setText("打*为必填项");
			reminderLabel.setForeground(Color.red);
			reminderLabel.setBounds(60, 71, 207, 22);
		}
		{
			nameLabel = new JLabel();
			this.add(nameLabel);
			nameLabel.setText("用户名:		*");
			nameLabel.setBounds(59, 115, 85, 20);
		}
		{
			usernameTextField = new JTextField();
			this.add(usernameTextField);
			usernameTextField.setBounds(149, 115, 214, 27);
			usernameTextField.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("checkname called in usernametextfield");
					checkNameValid();
				}
			});

		}
		{
			passwordLabel = new JLabel();
			this.add(passwordLabel);
			passwordLabel.setText("密码:		*");
			passwordLabel.setBounds(59, 175, 85, 20);
		}
		{
			passwordField1 = new JPasswordField();
			this.add(passwordField1);
			passwordField1.setBounds(149, 175, 214, 27);
			passwordField1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					doubleCheckPassword();
				}

			});
		}
		{
			passwordLabel2 = new JLabel();
			this.add(passwordLabel2);
			passwordLabel2.setText("重输密码:	*");
			passwordLabel2.setBounds(59, 235, 90, 20);
		}
		{
			passwordField2 = new JPasswordField();
			this.add(passwordField2);
			passwordField2.setBounds(149, 235, 209, 27);
			passwordField2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					doubleCheckPassword();
				}

			});
		}
		{
			nameReminderLabel = new JLabel();
			this.add(nameReminderLabel);
			nameReminderLabel.setText("·对不起，该用户名已被使用");
			nameReminderLabel.setForeground(Color.red);
			nameReminderLabel.setBounds(59, 143, 259, 20);
			nameReminderLabel.setVisible(false);
		}
		{
			passwordErrorLabel = new JLabel();
			this.add(passwordErrorLabel);
			passwordErrorLabel.setText("·两次输入密码不一致");
			passwordErrorLabel.setForeground(Color.red);
			passwordErrorLabel.setBounds(59, 267, 171, 20);
			passwordErrorLabel.setVisible(false);
		}
		{
			passwordCorrectLabel = new JLabel();
			this.add(passwordCorrectLabel);
			passwordCorrectLabel.setText("·两次输入密码一致:)");
			passwordCorrectLabel.setForeground(UIHelper.getUnSelectedColor());
			passwordCorrectLabel.setBounds(59, 267, 171, 20);
			passwordCorrectLabel.setVisible(false);
		}
		{
			sexLabel = new JLabel();
			this.add(sexLabel);
			sexLabel.setText("性别:");
			sexLabel.setBounds(536, 175, 70, 20);
		}
		{
			ComboBoxModel sexComboBoxModel = new DefaultComboBoxModel(
					new String[] { "男", "女" });
			sexComboBox = new JComboBox();
			this.add(sexComboBox);
			sexComboBox.setModel(sexComboBoxModel);
			sexComboBox.setBounds(626, 172, 109, 27);
		}
		{
			qqLabel = new JLabel();
			this.add(qqLabel);
			qqLabel.setText("QQ:");
			qqLabel.setBounds(536, 115, 28, 20);
		}
		{/*
			try {
				formatter = new MaskFormatter("##########");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			QQTextField = new JFormattedTextField(numberFormat);

			this.add(QQTextField);
			QQTextField.setBounds(626, 115, 209, 27);

		}
		{
			realNameLabel = new JLabel();
			this.add(realNameLabel);
			realNameLabel.setText("真实姓名:		");
			realNameLabel.setBounds(536, 235, 77, 20);
		}
		{
			realNameTextField = new JTextField();
			this.add(realNameTextField);
			realNameTextField.setBounds(626, 235, 209, 27);
		}
		{/*
		 * QQReminderLabel = new JLabel(); this.add(QQReminderLabel);
		 * QQReminderLabel.setText("·QQ号码必为数字");
		 * QQReminderLabel.setForeground(Color.red);
		 * QQReminderLabel.setBounds(536, 143, 189, 20);
		 * QQReminderLabel.setVisible(false);
		 */
		}
		{
			telLabel = new JLabel();
			this.add(telLabel);
			telLabel.setText("移动电话:	");
			telLabel.setBounds(59, 318, 95, 20);
		}
		{/*
			try {
				formatter = new MaskFormatter("###########");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
			telTextField = new JFormattedTextField(numberFormat);
			this.add(telTextField);
			telTextField.setBounds(149, 318, 209, 27);
		}
		{
			addressLabel = new JLabel();
			this.add(addressLabel);
			addressLabel.setText("城市:		");
			addressLabel.setBounds(536, 318, 95, 20);
		}
		{
			addressTextField = new JTextField();
			this.add(addressTextField);
			addressTextField.setBounds(626, 318, 209, 27);
		}
		{
			tagLabel = new JLabel();
			this.add(tagLabel);
			tagLabel.setText("标签:	");
			tagLabel.setBounds(59, 404, 85, 20);
		}
		{
			tagComboBox1 = new JComboBox(Config.tagNames);
			this.add(tagComboBox1);
			tagComboBox1.setBounds(149, 404, 86, 27);
		}
		{
			tagLabel2 = new JLabel();
			this.add(tagLabel2);
			tagLabel2.setBounds(263, 404, 95, 22);
		}
		{
			tagComboBox2 = new JComboBox(Config.tagNames);
			this.add(tagComboBox2);
			tagComboBox2.setBounds(275, 404, 86, 27);
		}
		{
			confirmButton = new JButton();
			this.add(confirmButton);
			confirmButton.setText("提交");
			confirmButton.addActionListener(new ConfirmButtonActionListener());
			confirmButton.setBounds(358, 503, 105, 30);
		}
		{
			redoButton = new JButton();
			this.add(redoButton);
			redoButton.setText("重填");
			redoButton.addActionListener(new RedoButtonActionListener());
			redoButton.setBounds(548, 503, 105, 30);
		}

		allTextFields.add(usernameTextField);
		allTextFields.add(passwordField1);
		allTextFields.add(passwordField2);
		allTextFields.add(realNameTextField);
		allTextFields.add(QQTextField);
		allTextFields.add(telTextField);
		allTextFields.add(addressTextField);

	}

	/**
	 * 将用户输入封装起来
	 * 
	 * @return
	 */
	private UserInforVO getUserInforVO() {
		/*
		 * @param name
		 * 
		 * @param password
		 * 
		 * @param sex
		 * 
		 * @param qq
		 * 
		 * @param tel
		 * 
		 * @param realName
		 * 
		 * @param address
		 * 
		 * @param tag
		 */
		String usernameS = usernameTextField.getText();
		String passwordS =passwordField1.getText();
		boolean sexB = (sexComboBox.getSelectedIndex() == 0) ? false : true;
		String qqS = QQTextField.getText();
		String telS = telTextField.getText();
		String realnameS = realNameTextField.getText();
		String addressS = addressTextField.getText();
		String tag1 = (String) tagComboBox1.getSelectedItem();
		String tag2 = (String) tagComboBox2.getSelectedItem();
		UserInforVO inputUserInfor = new UserInforVO(usernameS, passwordS,
				sexB, qqS, telS, realnameS, addressS, tag1 + "_" + tag2, null);
		return inputUserInfor;
	}

	/*
	 * private boolean checkQQValid() { boolean isValid = true; String input =
	 * QQTextField.getText(); for (int i = 0; i < input.length(); i++) { if
	 * (input.charAt(i) > '9' || input.charAt(i) < '0') {
	 * 
	 * isValid = false; break; } }
	 * 
	 * return isValid; }
	 */
	private boolean doubleCheckPassword() {
		boolean isValid = true;
		char[] c1 = passwordField1.getPassword();
		char[] c2 = passwordField2.getPassword();
		/*
		 * if(c1.length==0){ passwordErrorLabel.setText("密码不能为空"); isValid =
		 * false; }else
		 */
		if ((c1.length != c2.length))
			isValid = false;
		for (int i = 0; i < c1.length; i++) {
			if (c1[i] != c2[i]) {
				return false;
			}
		}
		passwordErrorLabel.setVisible(!isValid);
		passwordCorrectLabel.setVisible(isValid);
		return isValid;
	}

	private boolean checkNameValid() {
		boolean isValid = true;
		// 返回的列表不为空，说明已有人注册该名称

		if ((usernameTextField.getText().trim().equals(""))
				|| (usernameTextField.getText() == null)) {
			nameReminderLabel.setText("用户名不能为空");
			isValid = false;
		} else if (userManageController.search(usernameTextField.getText())
				.size() > 1) {
			nameReminderLabel.setText("该用户名已存在");
			isValid = false;
		}

		nameReminderLabel.setVisible(!isValid);
		return isValid;
	}

	class ConfirmButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// 注意！非短路操作符！
			getUserInforVO();
			if (passwordField1.getPassword().length == 0) {
				JOptionPane.showMessageDialog(null, "密码不能为空！");
			} else if (doubleCheckPassword() & checkNameValid()) {
				userManageController.register(getUserInforVO());
				JOptionPane.showMessageDialog(null, "注册成功");
				
				/**写入登陆界面的输入框*/
				ControllerFactory.getInstance().userManageController.getLoginFrame().storeUser(usernameTextField.getText(), passwordField1.getText());
				ControllerFactory.getInstance().userManageController.getLoginFrame().loadUser();
				
				registerFrame.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "输入有误，请更正后提交！");
			}
		}

	}

	class RedoButtonActionListener implements ActionListener {
		/**
		 * 清空上次填写的项
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			for (int i = 0; i < allTextFields.size(); i++) {
				allTextFields.get(i).setText("");
			}
			tagComboBox1.setSelectedIndex(0);
			tagComboBox2.setSelectedIndex(0);
		}

	}
}
