package startup;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import tools.Config;

/**
 * 客户端连接服务器
 * 
 * @author wanglizhi
 * 
 */
public class ClientStartUp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField ipField;
	JButton jb;

	public static void main(String args[]) {
		new ClientStartUp();
	}

	public ClientStartUp() {
		super("ip");
		this.setBounds(300, 200, 200, 100);
		ipField = new JTextField();
		readIP();
		this.add(ipField);
		jb = new JButton("确定");
		this.add(jb, BorderLayout.SOUTH);
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				writeIP();
				try {
					String ip = ipField.getText();
//					boolean status = InetAddress.getByName(ip).isReachable(
//							100000);
//					if (status) {
						Config.IP=ip;
						System.out.println(ip);
						new ClientNetworkInit(ip);
						ClientStartUp.this.setVisible(false);
						ClientStartUp.this.dispose();
						start();
//					} else {
//						popWindows("未能连接服务器，请确定IP重试", "Connection Fail");
//					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private boolean readIP() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("ip.txt"));
			String r = br.readLine();
			ipField.setText(r);
			br.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private boolean writeIP() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					"ip.txt")));
			bw.write(ipField.getText());
			bw.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// 弹出消息窗口
	private void popWindows(String strWarning, String strTitle) {
		JOptionPane.showMessageDialog(this, strWarning, strTitle,
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void start(){
		  try {
		  org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		  BeautyEyeLNFHelper.frameBorderStyle =
		  BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow; } catch
		  (Exception e) {
		  
		  }

		  ControllerFactory.getInstance().userManageController.createLoginFrame();

	}

}
