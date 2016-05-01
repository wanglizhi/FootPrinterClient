package ui.blog;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import controller.BlogController;

public class RecordPanel extends JPanel implements MouseMotionListener, MouseListener, KeyListener, MouseWheelListener {
	public enum mouseState {
		nothing, recordDrag, recordPress
	};

	private BlogController blogController;
	private BufferedImage dragbf;// 被拖动的image
	private Item dragItem;// 被拖动的image
	private int dragL;// 拖动的在Arraylist中的位置
	private MouseEvent dragMouse;
	private mouseState mouseState;
	private ArrayList<Item> items;
	private ArrayList<Label> labels;
	private int offset;// 横屏滚动屏幕时有用，距左边的距离
	private int cursor;
	int itemCursor;// 滑动时用

	public RecordPanel(int cursor, ArrayList<Item> items, ArrayList<Label> labels, BlogController bc) throws IOException {
		this.cursor = cursor;
		mouseState = mouseState.nothing;
		dragbf = new BufferedImage(100, 100, BufferedImage.TYPE_INT_BGR);
		offset = 0;

		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.blogController = bc;// 对这个界面的操作全转到controller
		this.items = items;
		for (Item i : items) {
			if (i instanceof ItemAudio) {
				((ItemAudio) i).runTimer();
			}
		}
		this.labels = labels;
		setSize(new Dimension(Constant.SHOWWIDTH,Constant.SHOWHEIGHT0));

		setBackground(Color.WHITE);
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// 不让记录滚太远
		if (offset > 50) {
			offset = 50;
		}

		ImageIcon icon = new ImageIcon("chan/beijing0.jpg");
		g.drawImage(icon.getImage(), 0, 0, null);
		// g.setColor(new Color(199, 225, 198));
		g.setColor(Color.white);
		g.fillRect(0, Constant.SHOWLEFTUP - 5, Constant.SHOWWIDTH, Constant.SHOWHEIGHT + 10);
		for (Item i : items) {
			i.draw(offset, g);
		}
		for (Label l : labels) {
			l.draw(g);
		}
		ImageIcon i1 = new ImageIcon("chan/semitransparent.png");
		g.drawImage(i1.getImage(), 0, Constant.SHOWLEFTUP, 300, Constant.SHOWHEIGHT, null);
		ImageIcon i2 = new ImageIcon("chan/semitransparent2.png");
		g.drawImage(i2.getImage(), 1366 - 300, Constant.SHOWLEFTUP, 300, Constant.SHOWHEIGHT, null);
		if (mouseState == mouseState.recordDrag) {
			int width, height;
			if (dragbf.getHeight() > 100) {
				height = 100;
				width = dragbf.getWidth() * 100 / dragbf.getHeight();
			} else {
				height = dragbf.getHeight();
				width = dragbf.getWidth();
			}
			Graphics2D g2 = (Graphics2D) g;
			AlphaComposite yuanac = (AlphaComposite) g2.getComposite();
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
			g2.setComposite(ac);
			g2.drawImage(dragbf, dragMouse.getX() - width / 2, dragMouse.getY() - height / 2, width, height, null);
			g2.setComposite(yuanac);
		}
	}

	// 在横屏结尾处添加一张图片
	public void addPicture() {
		String imgPath = "";
		JFileChooser dlg = new JFileChooser();
		dlg.setDialogTitle("Open JPEG file");
		int result = dlg.showOpenDialog(this);
		File infile = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			infile = dlg.getSelectedFile();
			imgPath = infile.getPath();
			if (!imgPath.contains(".jpg")&&!imgPath.contains(".png")) {
				JOptionPane.showMessageDialog(RecordPanel.this, "请选择一张图片！");
				return;
			}
			File outFile = new File("blog/" + infile.getName());
			if (!outFile.exists()) {
				try {
					BlogController.copyFile(infile, outFile);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			ItemPicture ip = new ItemPicture(infile.getName(), cursor);
			items.add(ip);
			cursor += (ip.getWidth() + Constant.SHOWPADWIDTH);
		}
	}

	// 在横屏结尾处添加一张图片
	public void addAudio() {
		String audPath = "";
		JFileChooser dlg = new JFileChooser();
		dlg.setDialogTitle("Open MP3 file");
		int result = dlg.showOpenDialog(this);
		File infile = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			infile = dlg.getSelectedFile();
			audPath = infile.getPath();
			if (!audPath.contains(".mp3")&&!audPath.contains(".wma")&&!audPath.contains(".wav")&&!audPath.contains(".ogg")) {
				JOptionPane.showMessageDialog(RecordPanel.this, "请选择一段音频！");
				return;
			}
			File outFile = new File("blog/" + infile.getName());
			if (!outFile.exists()) {
				try {
					BlogController.copyFile(infile, outFile);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			ItemAudio ia = new ItemAudio(infile.getName(), cursor, blogController);
			items.add(ia);
			cursor += (ia.getWidth() + Constant.SHOWPADWIDTH);
		}
	}
	public void addRadio() {
		String audPath = "";
		JFileChooser dlg = new JFileChooser();
		dlg.setDialogTitle("Open SWF file");
		int result = dlg.showOpenDialog(this);
		File infile = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			infile = dlg.getSelectedFile();
			audPath = infile.getPath();
			if (!audPath.contains(".flv")&&!audPath.contains(".swf")&&!audPath.contains(".rmvb")&&!audPath.contains(".avi")&&!audPath.contains(".mp4")) {
				JOptionPane.showMessageDialog(RecordPanel.this, "请上传一段视频");
				return;
			}
			File outFile = new File("blog/" + infile.getName());
			if (!outFile.exists()) {
				try {
					BlogController.copyFile(infile, outFile);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			ItemRadio ia = new ItemRadio(infile.getName(), cursor);
			items.add(ia);
			cursor += (ia.getWidth() + Constant.SHOWPADWIDTH);
		}
	}
	public void addItemText(String code) {
		ItemText ip = new ItemText(code, cursor);
		items.add(ip);
		cursor += (ip.getWidth() + Constant.SHOWPADWIDTH);
	}

	public void addItemTitle(String title, String city, String date) {
		ItemTitle it = new ItemTitle(title + "/" + city + "/" + date, cursor);
		items.add(it);
		cursor += (it.getWidth() + Constant.SHOWPADWIDTH);
	}

	private static boolean isInRect(MouseEvent e, int leftupX, int LeftupY, int width, int height) {
		if (e.getX() - leftupX > 0 && e.getX() - leftupX < width && e.getY() - LeftupY > 0 && e.getY() - LeftupY < height) {
			return true;
		} else {
			return false;
		}
	}

	// clicked (pressed and released发生在同一点，否在会有drag）
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX() + "," + e.getY() + "," + e.getXOnScreen() + "," + e.getYOnScreen() + "click");
		if (isInRect(e, labels.get(0).leftupX, labels.get(0).leftupY, labels.get(0).width, labels.get(0).height)) {
			// 添加图片
			addPicture();
			offset = -cursor + Constant.SHOWWIDTH;
		} else if (isInRect(e, labels.get(1).leftupX, labels.get(1).leftupY, labels.get(1).width, labels.get(1).height)) {
			// 添加文字
			new addTextFrame("");
			offset = -cursor + Constant.SHOWWIDTH - 400;
		} else if (isInRect(e, labels.get(2).leftupX, labels.get(2).leftupY, labels.get(2).width, labels.get(2).height)) {
			// 添加视频
			addRadio();
			offset = -cursor + Constant.SHOWWIDTH;
		} else if (isInRect(e, labels.get(3).leftupX, labels.get(3).leftupY, labels.get(3).width, labels.get(3).height)) {
			// 添加音频
			addAudio();
			offset = -cursor + Constant.SHOWWIDTH;
		} else if (isInRect(e, labels.get(5).leftupX, labels.get(5).leftupY, labels.get(5).width, labels.get(5).height)) {
			LoginING l=new LoginING();
			l.requestFocus();
			if(blogController.saveToServer(items)){
				l.setVisible(false);
				JOptionPane.showMessageDialog(RecordPanel.this, "上传完成");
				l.dispose();
				blogController.toMyBlogPanel();
			}
			// 保存并退出
		} else if (isInRect(e, labels.get(6).leftupX, labels.get(6).leftupY, labels.get(6).width, labels.get(6).height)) {
			new addTitleFrame();
			offset = -cursor + Constant.SHOWWIDTH - 400;
		}else if (isInRect(e, labels.get(7).leftupX, labels.get(7).leftupY, labels.get(7).width, labels.get(7).height)) {
			//退出
			int result=JOptionPane.showConfirmDialog(RecordPanel.this, "你确定不保存退出吗？");
			if (result==JOptionPane.YES_OPTION) {
				blogController.toMyBlogPanel();
			}
		}
		for (int i = 0; i < items.size(); i++) {
			if (isInRect(e, items.get(i).toLeft + offset, Constant.SHOWLEFTUP, items.get(i).width, items.get(i).height)) {
				items.get(i).click();
				return;
			}
		}

	}

	public void mousePressed(MouseEvent e) {
		System.out.println(e.getX() + "," + e.getY() + "," + e.getXOnScreen() + "," + e.getYOnScreen() + "press");
	}

	public void mouseReleased(MouseEvent e) {
		System.out.println(e.getX() + "," + e.getY() + "," + e.getXOnScreen() + "," + e.getYOnScreen() + "release");
		RecordPanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		// 写一个方法，最后释放的时候，可以根据当前arraylist来修改文件
		if (mouseState == mouseState.recordDrag) {
			int i, addWidth = 0;
			boolean backInRect = false;
			for (i = 0; i < items.size(); i++) {
				if (isInRect(e, items.get(i).toLeft + offset, Constant.SHOWLEFTUP, items.get(i).width, items.get(i).height)) {
					addWidth = dragItem.width;
					dragItem.toLeft = items.get(i).toLeft;
					items.add(i, dragItem);
					backInRect = true;
					break;
				}
			}
			if (!backInRect) {
				Label l = labels.get(4);// 获得删除的label
				if (!isInRect(e, l.leftupX, l.leftupY, l.width, l.height)) {
					// 如果没有进删除按钮，就回到原来位置
					addWidth = dragItem.width;
					i = dragL;
					if (i == items.size()) {
						items.add(dragItem);
					} else {
						dragItem.toLeft = items.get(i).toLeft;
						items.add(i, dragItem);
					}
				} else {
					int result = JOptionPane.showConfirmDialog(this, "你确认要删除吗？", "删除确认", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.NO_OPTION) {
						addWidth = dragItem.width;
						i = dragL;
						if (i == items.size()) {
							items.add(dragItem);
						} else {
							dragItem.toLeft = items.get(i).toLeft;
							items.add(i, dragItem);
						}
					}
				}
			}
			for (i++; i < items.size(); i++) {
				items.get(i).toLeft += (addWidth + Constant.SHOWPADWIDTH);
				if (i == items.size() - 1) {
					break;
				}
			}
			cursor += addWidth;
		}
		mouseState = mouseState.nothing;
		repaint(1000);
	}

	// 没用：移入组件Invoked when the mouse enters a component.
	public void mouseEntered(MouseEvent e) {
	}

	// 没用：移出组建Invoked when the mouse exits a component.
	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		System.out.println(e.getX() + "," + e.getY() + "," + e.getXOnScreen() + "," + e.getYOnScreen() + "drag");
		RecordPanel.this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		if (mouseState == mouseState.nothing) {
			itemCursor = 0;
			int minusWidth = 0;
			for (itemCursor = 0; itemCursor < items.size(); itemCursor++) {
				if (isInRect(e, items.get(itemCursor).toLeft + offset, Constant.SHOWLEFTUP, items.get(itemCursor).width, items.get(itemCursor).height)) {
					mouseState = mouseState.recordDrag;
					minusWidth = items.get(itemCursor).width;
					dragItem = items.get(itemCursor);
					dragL = itemCursor;
					// 获得当前拖动界面的截屏
					Robot robot;
					try {
						robot = new Robot();
						int leftupX = items.get(itemCursor).toLeft + offset + e.getXOnScreen() - e.getX();
						int rightupX = leftupX + items.get(itemCursor).width;
						// 防止截屏截到屏幕以外出现黑色
						if (leftupX < 0) {
							leftupX = 0;
						}
						if (rightupX > Constant.SHOWWIDTH) {
							rightupX = Constant.SHOWWIDTH;
						}
						dragbf = robot.createScreenCapture(new Rectangle(leftupX, Constant.SHOWLEFTUP + e.getYOnScreen() - e.getY(), rightupX - leftupX, items.get(itemCursor).height));
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					items.remove(itemCursor);
					break;
				}
			}
			cursor -= minusWidth;
			final int minus = minusWidth + Constant.SHOWPADWIDTH;
			// 后面的往前平移
			for (; itemCursor < items.size(); itemCursor++) {
				items.get(itemCursor).toLeft -= (minusWidth + Constant.SHOWPADWIDTH);
				if (itemCursor == items.size() - 1) {
					break;
				}
			}
		}
		dragMouse = e;
		repaint();
	}

	// Invoked when the mouse cursor has been moved onto a component but no
	// buttons have been pushed.
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		for (Label l : labels) {
			l.returnNormal();
			if (isInRect(e, l.oleftupX, l.oleftupY, l.owidth, l.oheight)) {
				l.getBigger();
				RecordPanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				repaint();
				return;
			}
			repaint();
		}
		RecordPanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_KP_LEFT || e.getKeyCode() == KeyEvent.VK_LEFT) {
			offset -= 10;
		} else if (e.getKeyCode() == KeyEvent.VK_KP_RIGHT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			offset += 10;
		}
		repaint();
	}

	public void keyReleased(KeyEvent e) {
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			offset -= e.getUnitsToScroll() * 20;
			repaint();
		}
	}

	class LoginING extends JFrame{
		public LoginING() {
			
			this.setSize(300, 150);
			JLabel l = new JLabel("上传中，请耐心等待.....");
			LoginINGPanel lp = new LoginINGPanel();
			this.getContentPane().add(l,BorderLayout.NORTH);
			this.getContentPane().add(lp,BorderLayout.CENTER);
//			this.setResizable(false);
			//this.setMiddle();
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			
		}

		public void setMiddle() {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int screenWidth = (int) screenSize.getWidth();
			int screenHeight = (int) screenSize.getHeight();

			int x = (screenWidth - this.getWidth()) / 2;
			int y = (screenHeight - this.getHeight()) / 2;

			this.setLocation(x, y);
		}
	}

	class LoginINGPanel extends JPanel {
		int p = 0;

		public LoginINGPanel() {
//			setSize(100, 60);
			new Thread() {
				public void run() {
					while (true) {
						try {
							Thread.sleep(1000);
							if (p < 11) {
								p++;
							} else {
								p = 0;
							}
							repaint();
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
			}.start();
		}

		public void paint(Graphics g) {

			g.setColor(Color.green);
			for (int i = 0; i < 12; i++) {
				if (i == p) {
					g.fillRect(5 + i * 10, 10, 8, 25);
				} else {
					g.fillRect(5 + i * 10, 10, 5, 20);
				}
			}
		}

	}

	class addTextFrame extends JFrame implements FocusListener {
		int width = 500;
		int height = 600;
		JTextArea jta;
		JScrollPane jsp;//
		JButton sureButton;
		JButton quitButton;

		public addTextFrame(String temp) {
			super("添加文字");
			jta = new JTextArea(temp);
			jta.setFont(new Font("黑体", Font.PLAIN, 28));
			jta.setLineWrap(true);
			jsp = new JScrollPane(jta);
			jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			sureButton = new JButton("添加");
			quitButton = new JButton("取消");
			initial();
			JPanel southPanel = new JPanel();
			southPanel.setLayout(new FlowLayout());
			southPanel.add(sureButton);
			southPanel.add(quitButton);
			add(BorderLayout.CENTER, jsp);
			add(BorderLayout.SOUTH, southPanel);
			this.setLocation(Constant.SHOWWIDTH / 2 - width / 2, 668 / 2 - height / 2);
			this.setSize(new Dimension(width, height));
			setUndecorated(true);
			setVisible(true);
			addFocusListener(this);
		}

		private void initial() {
			sureButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String code = jta.getText();
					addItemText(code);
					addTextFrame.this.setVisible(false);
					addTextFrame.this.dispose();
				}
			});
			quitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addTextFrame.this.setVisible(false);
					addTextFrame.this.dispose();
				}
			});
		}

		public void focusGained(FocusEvent e) {
		}

		public void focusLost(FocusEvent e) {
			// 为什么没用？？？在frame失去焦点时销毁
			addTextFrame.this.setVisible(false);
			addTextFrame.this.dispose();
		}
	}

	class addTitleFrame extends JFrame implements FocusListener {
		int width = 400;
		int height = 400;
		JLabel titltLabel;
		JTextField jt1;
		JLabel cityLabel;
		JTextField jt2;
		JLabel dateLabel;
		JTextField jt3;
		JButton sureButton;
		JButton quitButton;

		public addTitleFrame() {
			super("添加文字");
			setLayout(null);
			JLabel lblNewLabel_1 = new JLabel("添加一个主题");
			lblNewLabel_1.setForeground(new Color(222, 222, 222));
			lblNewLabel_1.setFont(new Font("华文琥珀", Font.PLAIN, 20));
			lblNewLabel_1.setBounds(126, 10, 120, 33);
			sureButton = new JButton("添加");
			sureButton.setBounds(39, 228, 132, 34);
			quitButton = new JButton("取消");
			quitButton.setBounds(198, 228, 132, 34);
			initial();
			titltLabel = new JLabel("标题");
			titltLabel.setBounds(53, 53, 103, 28);
			jt1 = new JTextField(10);
			jt1.setBounds(198, 53, 132, 28);
			cityLabel = new JLabel("城市");
			cityLabel.setBounds(53, 103, 103, 28);
			jt2 = new JTextField(10);
			jt2.setBounds(198, 103, 132, 28);
			dateLabel = new JLabel("日期");
			dateLabel.setBounds(53, 153, 103, 28);
			jt3 = new JTextField(10);
			jt3.setBounds(198, 153, 132, 28);
			add(cityLabel);
			add(dateLabel);
			add(titltLabel);
			add(jt1);
			add(jt2);
			add(jt3);
			add(sureButton);
			add(quitButton);
			this.setLocation(Constant.SHOWWIDTH / 2 - width / 2, 668 / 2 - height / 2);
			this.setSize(new Dimension(width, height));
			setUndecorated(true);
			setVisible(true);
			addFocusListener(this);
			// setAlwaysOnTop(true);
		}

		private void initial() {
			sureButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addItemTitle(jt1.getText(), jt2.getText(), jt3.getText());
					addTitleFrame.this.setVisible(false);
					addTitleFrame.this.dispose();
				}
			});
			quitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addTitleFrame.this.setVisible(false);
					addTitleFrame.this.dispose();
				}
			});
		}

		public void focusGained(FocusEvent e) {
		}

		public void focusLost(FocusEvent e) {
			// 为什么没用？？？在frame失去焦点时销毁
			addTitleFrame.this.setVisible(false);
			addTitleFrame.this.dispose();
		}
	}

}
