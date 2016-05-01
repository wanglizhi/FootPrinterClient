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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import ui.blog.RecordPanel.addTextFrame;
import ui.home.ShowBlogPanel;
import vo.BlogVO;

import controller.BlogController;
import controller.BlogShowController;

public class RecordShowPanel extends JPanel implements MouseMotionListener, MouseListener, KeyListener, MouseWheelListener {
	public enum mouseState {
		nothing, recordDrag, recordPress
	};

	private BlogShowController blogController;
	private BufferedImage dragbf;// 被拖动的image
	private Item dragItem;// 被拖动的image
	private int dragL;// 拖动的在Arraylist中的位置
	private ImageIcon diseIcon;
	private MouseEvent dragMouse;
	private mouseState mouseState;
	private ArrayList<Item> items;
	private ArrayList<Comments> comments;
	private Comments showComment;
	private int numComments;
	private int num;//当前显示的评论条数
	private Label likeLabel;
	private Label quitLabel;
	private Label likeLabel1;
	private Label likeLabel2;
	private Label addLabel;
	private Label previousLabel;
	private Label nextLabel;
	private ArrayList<Label> labels;
	private int offset;// 横屏滚动屏幕时有用，距左边的距离

	private String userName;
	private String cityName;
	private String title;
	private int likeNum;
	
	private boolean isLoad;
	public Timer stopTimmer;
	private String showString;
	

	public RecordShowPanel(){
		isLoad=true;
		final String[] String1={"正在加载中，请耐心等待。","正在加载中，请耐心等待。。","正在加载中，请耐心等待。。。"};

		showString=String1[0];
		setSize(new Dimension(Constant.SHOWPADWIDTH, Constant.SHOWHEIGHT0));

		stopTimmer = new Timer(1000, new ActionListener() {
			int i=0;
			public void actionPerformed(ActionEvent e) {
				showString=String1[(i++)%3];
			}
		});
		stopTimmer.start();
	}
	
	public RecordShowPanel(ArrayList<Comments> comments, ArrayList<Item> items, BlogVO vo, Boolean isLike, BlogShowController bc) throws IOException {
		isLoad=false;
		this.comments = comments;
		numComments = comments.size();
		if (numComments>0) {
			showComment=comments.get(0);
		}
		num = 1;
		labels = new ArrayList<Label>();
		userName = vo.userName;
		cityName = vo.cityName;
		title = vo.title;
		likeNum = vo.likeNum;
		mouseState = mouseState.nothing;
		dragbf = new BufferedImage(100, 100, BufferedImage.TYPE_INT_BGR);
		offset = 0;

		quitLabel = new Label(1200, 30, 40, 40, new ImageIcon("chan/tuichu.png"));
		likeLabel1 = new Label(1100, 550, 50, 50, new ImageIcon("chan/like.png"));
		likeLabel2 = new Label(1100, 550, 50, 50, new ImageIcon("chan/like2.png"));
		addLabel = new Label(Constant.COMMENTLEFT - 40, Constant.COMMENTUP + Constant.COMMENTHEIGHT - 140, 40, 40, new ImageIcon("chan/addComment.png"));
//		previousLabel = new Label(Constant.COMMENTLEFT - 40, Constant.COMMENTUP + Constant.COMMENTHEIGHT - 40, 40, 40, new ImageIcon("chan/commentPrevious.png"));
//		nextLabel = new Label(Constant.COMMENTLEFT + Constant.COMMENTWIDTH, Constant.COMMENTUP + Constant.COMMENTHEIGHT - 40, 40, 40, new ImageIcon("chan/commentNext.png"));
		previousLabel = new Label(Constant.COMMENTLEFT - 40, Constant.COMMENTUP +  40, 40, 40, new ImageIcon("chan/commentPrevious.png"));
		nextLabel = new Label(Constant.COMMENTLEFT + Constant.COMMENTWIDTH, Constant.COMMENTUP +  40, 40, 40, new ImageIcon("chan/commentNext.png"));
		
		labels.add(addLabel);
		labels.add(previousLabel);
		labels.add(nextLabel);
		labels.add(quitLabel);
		if (isLike) {
			likeLabel = likeLabel2;
		} else {
			likeLabel = likeLabel1;
		}
		labels.add(likeLabel);
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
		setSize(new Dimension(Constant.SHOWPADWIDTH, Constant.SHOWHEIGHT0));

		setBackground(Color.WHITE);
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// 不让记录滚太远
		if(isLoad){
			g.setFont(new Font("隶书", Font.BOLD, 40));
			g.drawString("正在加载中，请耐心等待", 50, 50);
		}else{
		if (offset > 50) {
			offset = 50;
		}

		ImageIcon icon = new ImageIcon("chan/beijing0.jpg");
		g.drawImage(icon.getImage(), 0, 0, null);
		g.setColor(new Color(228, 241, 244));
		g.fillRect(0, Constant.SHOWLEFTUP - 5, Constant.SHOWWIDTH, Constant.SHOWHEIGHT + 10);
		for (Item i : items) {
			i.draw(offset, g);
		}
		for (Label label : labels) {
			label.draw(g);
		}
		likeLabel.draw(g);
		ImageIcon i1 = new ImageIcon("chan/semitransparent.png");
		g.drawImage(i1.getImage(), 0, Constant.SHOWLEFTUP, 300, Constant.SHOWHEIGHT, null);
		ImageIcon i2 = new ImageIcon("chan/semitransparent2.png");
		g.drawImage(i2.getImage(), 1366 - 300, Constant.SHOWLEFTUP, 300, Constant.SHOWHEIGHT, null);
		Graphics2D g2 = (Graphics2D) g;
		AlphaComposite yuanac = (AlphaComposite) g2.getComposite();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		g2.setComposite(ac);
		diseIcon = new ImageIcon("chan/dise.jpg");
		g2.drawImage(diseIcon.getImage(), Constant.INFOLEFT, Constant.INFOUP, Constant.INFOWIDTH, Constant.INFOHEIGHT, null);
		g2.drawImage(diseIcon.getImage(), Constant.TITLELEFT, Constant.TITLEUP, Constant.TITLEWIDTH, Constant.TITLEHEIGHT, null);
		g2.drawImage(diseIcon.getImage(), Constant.COMMENTLEFT, Constant.COMMENTUP, Constant.COMMENTWIDTH, Constant.COMMENTHEIGHT, null);
		if (mouseState == mouseState.recordDrag) {
			int width, height;
			if (dragbf.getHeight() > 100) {
				height = 100;
				width = dragbf.getWidth() * 100 / dragbf.getHeight();
			} else {
				height = dragbf.getHeight();
				width = dragbf.getWidth();
			}
			g2.drawImage(dragbf, dragMouse.getX() - width / 2, dragMouse.getY() - height / 2, width, height, null);
			g2.setComposite(yuanac);
		}
		g.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		g.setColor(Color.black);
		g.drawString(title, Constant.TITLELEFT, Constant.TITLEUP + 35);
		g.drawString("用户名：" + userName, Constant.INFOLEFT, Constant.INFOUP + 35);
		g.drawString("城市：" + cityName, Constant.INFOLEFT, Constant.INFOUP + 70);
		g.drawString(String.valueOf(likeNum), 1150, 590);
		g.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		if (comments.size() == 0) {
			g.drawString("抱歉，本条记录暂时没有评论，快来做第一个评论者吧！", Constant.COMMENTLEFT, Constant.COMMENTUP + 30);
		} else {
			g.drawString(num + "/" + numComments, Constant.COMMENTLEFT + Constant.COMMENTWIDTH, Constant.COMMENTUP+25);
			showComment.draw(g);
		}
		}
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
		for (int i = 0; i < items.size(); i++) {
			// 点图像显示大图，点文字不做任何操作但在编辑界面点击文字进入修改文字
			if (isInRect(e, items.get(i).toLeft + offset, Constant.SHOWLEFTUP, items.get(i).width, items.get(i).height)) {
				if (items.get(i) instanceof ItemText) {
				} else {
					items.get(i).click();
					return;
				}
			}
		}
		if (isInRect(e, likeLabel.oleftupX, likeLabel.oleftupY, likeLabel.owidth, likeLabel.oheight)) {
			changeLikeLabel();
			repaint();
			return;
		}
		if (isInRect(e, previousLabel.oleftupX, previousLabel.oleftupY, previousLabel.owidth, previousLabel.oheight)) {
			if (num>1) {
				num--;
				showComment=comments.get(num-1);
			}
			return;
		}
		if (isInRect(e, nextLabel.oleftupX, nextLabel.oleftupY, nextLabel.owidth, nextLabel.oheight)) {
			if (num<numComments) {
				num++;
				showComment=comments.get(num-1);
			}
			return;
		}
		if (isInRect(e, addLabel.oleftupX, addLabel.oleftupY, addLabel.owidth, addLabel.oheight)) {
			new addTextFrame("");
			return;
		}
		if (isInRect(e, quitLabel.oleftupX, quitLabel.oleftupY, quitLabel.owidth, quitLabel.oheight)) {
			blogController.toBlogHomePanel();
			return;
		}
	}

	private void changeLikeLabel() {
		if (likeLabel == likeLabel1) {
			likeLabel = likeLabel2;
			likeNum++;
			blogController.addLike();
		} else {
			if (likeNum >= 1) {
				likeLabel = likeLabel1;
				likeNum--;
				blogController.minusLike();
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		System.out.println(e.getX() + "," + e.getY() + "," + e.getXOnScreen() + "," + e.getYOnScreen() + "press");
	}

	public void mouseReleased(MouseEvent e) {
		System.out.println(e.getX() + "," + e.getY() + "," + e.getXOnScreen() + "," + e.getYOnScreen() + "release");
		RecordShowPanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
				addWidth = dragItem.width;
				i = dragL;
				if (i == items.size()) {
					items.add(dragItem);
				} else {
					dragItem.toLeft = items.get(i).toLeft;
					items.add(i, dragItem);
				}
			}
			for (i++; i < items.size(); i++) {
				items.get(i).toLeft += (addWidth + Constant.SHOWPADWIDTH);
				if (i == items.size() - 1) {
					break;
				}
			}
		}
		mouseState = mouseState.nothing;
		repaint();
	}

	// 没用：移入组件Invoked when the mouse enters a component.
	public void mouseEntered(MouseEvent e) {
	}

	// 没用：移出组建Invoked when the mouse exits a component.
	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		System.out.println(e.getX() + "," + e.getY() + "," + e.getXOnScreen() + "," + e.getYOnScreen() + "drag");
		RecordShowPanel.this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		if (mouseState == mouseState.nothing) {
			int i, minusWidth = 0;
			for (i = 0; i < items.size(); i++) {
				if (isInRect(e, items.get(i).toLeft + offset, Constant.SHOWLEFTUP, items.get(i).width, items.get(i).height)) {
					mouseState = mouseState.recordDrag;
					minusWidth = items.get(i).width;
					dragItem = items.get(i);
					dragL = i;
					// 获得当前拖动界面的截屏
					Robot robot;
					try {
						robot = new Robot();
						int leftupX = items.get(i).toLeft + offset + e.getXOnScreen() - e.getX();
						int rightupX = leftupX + items.get(i).width;
						// 防止截屏截到屏幕以外出现黑色
						if (leftupX < 0) {
							leftupX = 0;
						}
						if (rightupX > Constant.SHOWWIDTH) {
							rightupX = Constant.SHOWWIDTH;
						}
						dragbf = robot.createScreenCapture(new Rectangle(leftupX, Constant.SHOWLEFTUP + e.getYOnScreen() - e.getY(), rightupX - leftupX, items.get(i).height));
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					items.remove(i);
					break;
				}
			}
			// 后面的往前平移
			for (; i < items.size(); i++) {
				items.get(i).toLeft -= (minusWidth + Constant.SHOWPADWIDTH);
				if (i == items.size() - 1) {
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
		for (Label label : labels) {
			label.returnNormal();
			if (isInRect(e, label.oleftupX, label.oleftupY, label.owidth, label.oheight)) {
				label.getBigger();
				RecordShowPanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				repaint();
				return;
			}
		}
		repaint();
		RecordShowPanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
	class addTextFrame extends JFrame implements FocusListener {
		int width = 400;
		int height = 400;
		JTextArea jta;
		JScrollPane jsp;//
		JButton sureButton;
		JButton quitButton;

		public addTextFrame(String temp) {
			super("添加评论");
			jta = new JTextArea(temp);
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
					code=code.replaceAll("\n", "//");
					Comments c=new Comments(code);
					comments.add(c);
					numComments++;
					num=numComments;
					showComment=c;
					blogController.addComment(code);
					
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

}
