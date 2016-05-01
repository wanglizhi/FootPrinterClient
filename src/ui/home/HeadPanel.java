package ui.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import startup.BLServiceFactory;
import startup.CurrentUserStatus;
import tools.Config;
import tools.Debugger;
import tools.DefaultFileName;
import ui.foundation.BasePanel;
import controller.HomepageController;


public class HeadPanel extends BasePanel {
	private HomepageController homepageController;
	private CurrentUserStatus userStatus = CurrentUserStatus.getInstance();
	private JLabel usernameLabel = new JLabel();
	private JLabel userHeadLabel = new JLabel();

	private ImageIcon logoIcon = new ImageIcon("img//logo.png");
	private ImageIcon iconIcon = new ImageIcon("img//icon.png");

	private JLabel logoLabel = new JLabel();
	private JLabel iconLabel = new JLabel();

	//private JLabel loginLabel = new JLabel();
	//private JLabel slashLabel = new JLabel("|");
	//private JLabel registerLabel = new JLabel();
	

	private JLabel myPlanLabel = new JLabel();
	private JLabel myBlogLabel = new JLabel();
	private JLabel exitLabel = new JLabel();
	
	private Font headFont = UIHelper.getUnderlinedFont(18, "宋体");// new
																	// Font("宋体",
																	// Font.CENTER_BASELINE,
																	// 18);
	private Font enterFont = new Font("宋体", Font.ITALIC, 20);
	private Font userNameFont = new Font("黑体", Font.BOLD, 18);
	private Font userNameEnterFont = new Font("黑体", Font.ITALIC, 20);

	public HeadPanel(HomepageController homepageController) {
		this.homepageController = homepageController;
		this.setLayout(null);
		this.setBounds(0, 0, Config.FRAME_WIDTH,Config.HEAD_PANEL_HEIGHT);
		this.setPreferredSize(new java.awt.Dimension(Config.FRAME_WIDTH,Config.HEAD_PANEL_HEIGHT));
		
		this.setBackground(new Color(189,235,246));
		
		logoLabel.setIcon(logoIcon);
		logoLabel.setBounds(40, 20, 300, 45);

		iconLabel.setIcon(iconIcon);
		iconLabel.setBounds(340, 10, 50, 50);

		//loginLabel.setText("登陆");
		//loginLabel.setFont(headFont);
		//loginLabel.addMouseListener(new LoginLabelMouseListener());

		
		//registerLabel.setText("注册");
		//registerLabel.setFont(headFont);
		//registerLabel.addMouseListener(new RegisterLabelMouseListener());

		//loginLabel.setBounds(1200, 50, 50, 20);
	//	slashLabel.setBounds(1260, 50, 10, 20);
		//registerLabel.setBounds(1270, 50, 50, 20);
		
		myPlanLabel.setText("我的计划");
		myPlanLabel.setFont(headFont);
		myPlanLabel.addMouseListener(new MyPlanListener());
		//myPlanLabel.setVisible(true);
		
		myBlogLabel.setText("我的记录");
		myBlogLabel.setFont(headFont);
		myBlogLabel.addMouseListener(new MyBlogListener());
		//myBlogLabel.setVisible(true);
		
		exitLabel.setText("退出");
		exitLabel.setFont(headFont);
		exitLabel.addMouseListener(new ExitListener());
		
		myPlanLabel.setBounds(1110, 50, 90, 20);
		myBlogLabel.setBounds(1200, 50, 90, 20);
		exitLabel.setBounds(1200, 10, 70, 20);
		
		usernameLabel.setFont(new Font("黑体", Font.PLAIN, 18));
		usernameLabel.setBounds(1020, 50, 100, 20);
		usernameLabel.setVisible(false);
		usernameLabel.addMouseListener(new UsernameMouseListener());
		this.add(usernameLabel);

		userHeadLabel.setBounds(950, 30, 50, 50);
		userHeadLabel.setVisible(false);
		this.add(userHeadLabel);
		// underline1.setBounds(1250, 55, 45, 20);// 1256, 55, 28, 20
		// underline2.setBounds(1310, 55, 45, 20);// 1256, 55, 28, 20

		this.add(logoLabel);
		this.add(iconLabel);

		//this.add(loginLabel);
//		this.add(slashLabel);
		//this.add(registerLabel);
		
		this.add(myPlanLabel);
		this.add(myBlogLabel);
		this.add(exitLabel);
		
		changeRightTopCorner();
	}

	/**
	 * 登陆了以后，设置该方法，使右上角显示用户名和头像
	 * 
	 * @return
	 */
	
	public void changeRightTopCorner() {

		if (userStatus.getIsLogin()) {
			//loginLabel.setVisible(false);
			//slashLabel.setVisible(true);
			//registerLabel.setVisible(false);
			myPlanLabel.setVisible(true);
			myBlogLabel.setVisible(true);

			if (userStatus.getCurrentUserVO() != null) {
				String n = userStatus.getCurrentUserVO().getName();
				usernameLabel.setText(n);
				ImageIcon headIcon = BasePanel.createFixedSizeImageWithNull(
						userStatus.getCurrentUserPO().getHead(), 50, 50);
Debugger.log(""+userStatus.getCurrentUserPO().getHead());
				if(headIcon==null){
					headIcon = BasePanel.createFixedSizeImage(
							DefaultFileName.HEAD_FILE+"defaultProfile.png", 50, 50);
				}
				userHeadLabel.setIcon(headIcon);
				userHeadLabel.setVisible(true);
				usernameLabel.setVisible(true);
			}
		}
		else{
			//loginLabel.setVisible(true);
			//registerLabel.setVisible(true);
		//	slashLabel.setVisible(true);
			myPlanLabel.setVisible(false);
			myBlogLabel.setVisible(false);
		}
	}

	private void testChangeRightTopCorner() {
		//loginLabel.setVisible(false);
//		slashLabel.setVisible(false);
		//registerLabel.setVisible(false);
		usernameLabel.setText("Bunny sure");
		ImageIcon headIcon = BasePanel.createFixedSizeImage("img//头像//头像.png",
				50, 50);
		userHeadLabel.setIcon(headIcon);
		userHeadLabel.setVisible(true);
		usernameLabel.setVisible(true);
	}

	class UsernameMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			usernameLabel.setFont(userNameEnterFont);
			homepageController.userNameLabelClicked();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			usernameLabel.setFont(userNameEnterFont);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			usernameLabel.setFont(userNameFont);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			usernameLabel.setFont(userNameEnterFont);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			usernameLabel.setFont(userNameFont);
		}
	}

	class LoginLabelMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			homepageController.loginButtonClicked();

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			//loginLabel.setFont(enterFont);
			//loginLabel.setForeground(Color.blue);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			//loginLabel.setFont(headFont);
			//loginLabel.setForeground(Color.black);
		}
	}
/*
	class RegisterLabelMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			homepageController.registerButtonClicked();
			//registerLabel.setFont(enterFont);
			//registerLabel.setForeground(Color.blue);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			//registerLabel.setFont(enterFont);
			//registerLabel.setForeground(Color.blue);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//registerLabel.setFont(headFont);
			//registerLabel.setForeground(Color.black);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			//registerLabel.setFont(enterFont);
			//registerLabel.setForeground(Color.blue);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			registerLabel.setFont(headFont);
			registerLabel.setForeground(Color.black);
		}
	}
*/	
	class MyPlanListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			homepageController.myPlanButtonClicked();
			myPlanLabel.setFont(enterFont);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			myPlanLabel.setFont(enterFont);			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			myPlanLabel.setFont(headFont);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			myPlanLabel.setFont(enterFont);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			myPlanLabel.setFont(headFont);
		}
		
	}
	
	class MyBlogListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			myBlogLabel.setFont(enterFont);
			homepageController.myBlogButtonClicked();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			myBlogLabel.setFont(enterFont);			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			myBlogLabel.setFont(headFont);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			myBlogLabel.setFont(enterFont);			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			myBlogLabel.setFont(headFont);			
		}
		
	}
	class ExitListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			exitLabel.setFont(enterFont);			
			int choice = JOptionPane.showConfirmDialog(null, "确认退出？");
			if(choice==JOptionPane.OK_OPTION){
				int id = CurrentUserStatus.getInstance().getCurrentUserID();
				BLServiceFactory.getInstance().userManageBLService.exit(id);
				//退出系统了！！！
				System.exit(0);
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			exitLabel.setFont(enterFont);
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			exitLabel.setFont(headFont);
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			exitLabel.setFont(enterFont);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			exitLabel.setFont(headFont);
			
		}
		
	}
	public static void main(String args[]) {
		new TestFrame();
	}
}

class TestFrame extends JFrame {
	public TestFrame() {

		this.setSize(Config.FRAME_WIDTH, Config.HEAD_PANEL_HEIGHT);
		this.setVisible(true);
		this.setLayout(null);
		HomepageController hc = new HomepageController(null);
		HeadPanel hp = new HeadPanel(hc);
		hp.setBounds(0, 0, Config.FRAME_WIDTH, Config.HEAD_PANEL_HEIGHT);
		this.add(hp);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
