package ui.home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import startup.ControllerFactory;
import tools.Config;
import tools.Debugger;
import tools.DefaultFileName;
import ui.foundation.BasePanel;
import vo.LoginVO;
import vo.UserInforVO;
import controller.UserManageController;



public class LoginFrame extends JFrame {
	private int clickTextFieldCount = 0;
	private int clickPasswordFieldCount = 0;
	private JPanel rootPanel;
	//private JPanel innerPanel;
	private JTextField nameTextField;
	private JButton registerButton;
	private JButton confirmButton;
	private JLabel reminderLabel;
	private JPasswordField passwordTextField;
	private JLabel logoLabel;
	//private JLabel iconLabel;
	private JLabel titleLabel;

	private ImageIcon logoIcon = new ImageIcon("img//logo.png");
	//private ImageIcon iconIcon = new ImageIcon("img//icon.png");


	private UserManageController loginRegisterController;
	
	private int baseX = Config.BASEX;
	private int baseY = Config.BASEY;

	public LoginFrame(UserManageController loginRegisterController) {
		
		
		this.loginRegisterController = loginRegisterController;

		this.setUndecorated(true);
		this.setSize(new Dimension(Config.FRAME_WIDTH,Config.FRAME_HEIGHT));
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		{	rootPanel = new RootPanel();
			rootPanel.setLayout(null);
			this.add(rootPanel);
		}
		
			{
				titleLabel = new JLabel();
				titleLabel.setForeground(UIHelper.getUnSelectedColor());
				titleLabel.setFont(new java.awt.Font("幼圆", 1, 18));
				titleLabel.setText("·用户登录");
				titleLabel.setBounds(300+baseX, 107+baseY, 127, 45);
			//	innerPanel.add(titleLabel);
				rootPanel.add(titleLabel);
			}
			{	logoLabel = new JLabel(logoIcon);
				logoLabel.setBounds(25+baseX, 57+baseY, 300, 45);
			//	innerPanel.add(logoLabel);
				rootPanel.add(logoLabel);
			//	iconLabel.setBounds(360, 15, 50, 50);
				
			}
			{
				nameTextField = new JTextField();
				nameTextField.setText("用户名");
				nameTextField.setBounds(63+baseX, 164+baseY, 266, 43);
				//innerPanel.add(nameTextField);

				nameTextField.addMouseListener(new TextFieldListener());
				rootPanel.add(nameTextField);
			}
			{
				passwordTextField = new JPasswordField();
				passwordTextField.setText("密码");
				passwordTextField.setBounds(63+baseX, 266+baseY, 266, 39);

				passwordTextField.addMouseListener(new PasswordFieldListener());
				
				rootPanel.add(passwordTextField);
			}
			{
				reminderLabel = new JLabel();
				reminderLabel.setText("用户名不存在或密码错误");
				reminderLabel.setBounds(66+baseX, 138+baseY, 165, 20);
				//innerPanel.add(reminderLabel);
				rootPanel.add(reminderLabel);
				reminderLabel.setVisible(false);

			}
			{
				confirmButton = new JButton();
				confirmButton.setText("登陆");
				confirmButton.setBounds(63+baseX, 340+baseY, 110, 40);
				//innerPanel.add(confirmButton);
				rootPanel.add(confirmButton);
				confirmButton
						.addActionListener(new ConfirmButtonActionListener());
				confirmButton.setBorder(BorderFactory
						.createEtchedBorder(BevelBorder.LOWERED));

			}// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			{
				registerButton = new JButton();
				registerButton.setText("注册");
				registerButton
						.addActionListener(new RegisterButtonActionListener());
				registerButton.setBounds(219+baseX, 340+baseY, 110, 40);
				
				//innerPanel.add(cancelButton);
				rootPanel.add(registerButton);
			}
			loadUser();
		}
	

	private LoginVO getLoginVO() {
		LoginVO lvo = new LoginVO(nameTextField.getText(),
				passwordTextField.getText());
		return lvo;
	}
	
	public boolean loadUser() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("user.txt"));
			String r = br.readLine();
			if(r.equals(""))
				r="用户名";
			nameTextField.setText(r);
			 r = br.readLine();
			 if(r.equals(""))
				 r="123456";
			passwordTextField.setText(r);
			br.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean storeUser(String n, String p) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					"user.txt")));
			bw.write(n+"\n");
			bw.write(p);
			bw.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	class ConfirmButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			reminderLabel.setVisible(false);
			storeUser(nameTextField.getText(),passwordTextField.getText());
			if ((nameTextField.getText() == null)
					|| (nameTextField.getText() == "")
					|| (passwordTextField.getPassword().length == 0)){
				reminderLabel.setText("用户名或密码不能为空");
				reminderLabel.setVisible(true);
			}
			else {
				
				UserInforVO uiv = loginRegisterController.login(getLoginVO());
				if(uiv!=null){
					
					LoginFrame.this.setVisible(false);
					LoginFrame.this.dispose();
				}
					
				else{
					reminderLabel.setForeground(Color.red);
					reminderLabel.setText("用户名不存在或密码错误");
					reminderLabel.setVisible(true);
				}
					
			}
		}

	}

	class RegisterButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
//			LoginFrame.this.setVisible(false);
//			LoginFrame.this.dispose();
			 ControllerFactory.getInstance().homepageController.registerButtonClicked();
			
			
		}

	}
class TextFieldListener implements MouseListener{

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		if(clickTextFieldCount == 0){
			nameTextField.setText("");
		}
		clickTextFieldCount++;
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	}
class PasswordFieldListener implements MouseListener{

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		if(clickPasswordFieldCount == 0){
			passwordTextField.setText("");
		}
		clickPasswordFieldCount++;
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
		
	
	}

	public static void main(String args[]) {
		 try {
			  org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			  BeautyEyeLNFHelper.frameBorderStyle =
			  BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow; } catch
			  (Exception e) {
			  
			  }
		UserManageController lc = null;
		LoginFrame lf = new LoginFrame(lc);
	}


}
	

	class RootPanel extends JPanel{
		BufferedImage bcp ;
		public RootPanel(){
			this.setPreferredSize(new Dimension(Config.FRAME_WIDTH,Config.FRAME_HEIGHT));
			try {
				bcp = ImageIO.read(new File(DefaultFileName.LOGIN_BACKROUGND_PIC));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		
		
		public void paintComponent(Graphics g){
			g.drawImage(bcp, 0, 0, this.getWidth(), this.getHeight(), 0, 0, bcp.getWidth(), bcp.getHeight(), null);
		}
	}
