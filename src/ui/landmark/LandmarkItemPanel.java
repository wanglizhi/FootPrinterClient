package ui.landmark;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.LandmarkController;

import tools.LandmarkType;
import ui.foundation.BasePanel;
import vo.PlaceVO;

/**
 * 景点、酒店、餐馆、娱乐、商场等统一的面板
 * 
 * @author wanglizhi
 * 
 */
public class LandmarkItemPanel extends BasePanel {

	/**
	 * 
	 */
	public static int WIDTH = 300;
	public static int HEIGHT = 300;
	private static final long serialVersionUID = 1L;
	private JEditorPane intro;
	private int introX = 0;
	public int introY = 240;
	public int introW = WIDTH;
	private int introH = 30;
	private Timer timer;
	private IntroMove introMove;
	private JLabel price;
	public PlaceVO place;

	int location=180;
	private JLabel applause;
	private JLabel applauseNum;
	private  LandmarkController controller;
	public LandmarkItemPanel(){
		this(stub());
	}
	public LandmarkItemPanel(PlaceVO place,LandmarkController controller){
		this(place);
		this.controller=controller;
		initApplause();
	}
	public LandmarkItemPanel(PlaceVO place) {
		this.place=place;
		this.setLayout(null);
		this.setSize(WIDTH, HEIGHT);

		intro = new JEditorPane(
				"text/html",
				"<h4 style=\""+"font-family:verdana;\""+">"+place.name+"</h4>"
						+ "<div>"+place.introduction+"</div>");
		intro.setBounds(introX, introY, introW, introH);
		this.add(intro);
		intro.setEditable(false);
		intro.setEnabled(false);
		intro.setBackground(Color.WHITE);
		if(place.category==LandmarkType.LANDMARK) intro.setToolTipText("<html>"+place.price+"</html>");
		
		JLabel jl = new JLabel(place.image);
		this.add(jl);
		jl.setBounds(0, 30, WIDTH, 240);
		
		String[] ss=place.type.split("_");
		int x=0;
		int size=25;
		for(int i=1;i<ss.length;i++){
			JLabel tags = createHeadLabel("<html><b>"+ss[i]+"</b></html>", 3);
			this.add(tags);
			if(ss[i].length()>=4) size=20;
			else size=25;
			tags.setBounds(x, 0, ss[i].length()*size, 30);
			tags.setBackground(Color.white);
			tags.setEnabled(false);
			x+=ss[i].length()*size+3;
		}
		

		if(place.category!=LandmarkType.LANDMARK){
			price = createHeadLabel("<html>"+place.price+"</html>", 2);
			this.add(price);
			price.setBounds(220, 0, 90, 30);
			price.setEnabled(false);
			price.setBackground(Color.white);
		}
		

		introMove=new IntroMove(this);
		timer = new Timer(10, introMove);

		// 对简介的监听
		intro.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!timer.isRunning()){
					timer.start();
				}
				introMove.setState(1);				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!timer.isRunning())
				timer.start();
				introMove.setState(0);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
		this.setVisible(true);
	}
	public void initApplause(){
		final ImageIcon icon1=BasePanel.createFixedSizeImage("chan/like2.png", 20, 20);
		final ImageIcon icon2=BasePanel.createFixedSizeImage("chan/like.png", 20, 20);
		if(place.isApplaused){
			applause = new JLabel(icon1);
		}else{
			applause = new JLabel(icon2);
		}
		applauseNum=new JLabel(""+place.applauseNum);
		this.add(applause);
		applause.setBounds(180, 0, 20, 20);
		this.add(applauseNum);
		applauseNum.setBounds(200, 0, 20, 20);
		applause.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(place.isApplaused){
					if(controller.deletePlaceApplause(place.id, place.category)){
						applause.setIcon(icon2);
						place.isApplaused=false;
						place.applauseNum--;
						applauseNum.setText(""+place.applauseNum);
					}
				}else{
					if(controller.addPlaceApplause(place.id, place.category)){
						applause.setIcon(icon1);
						place.isApplaused=true;
						place.applauseNum++;
						applauseNum.setText(""+place.applauseNum);
					}
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				LandmarkItemPanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	public static PlaceVO stub(){
		ImageIcon imageIcon=BasePanel.createFixedSizeImage("img/景点/周庄.jpg", WIDTH, 240);
		PlaceVO place=new PlaceVO(1,
				"天安门广场",
				1,
				LandmarkType.LANDMARK,
				"饱经500余年风雨沧桑的天安门广场是当今世界上最大的城市广场。它不仅见证了中国人民一次次要民主、争自由，反抗外国侵略和反动统治的斗争，更是共和国举行重大庆典、盛大集会和外事...",
				"_升旗_国家象征",
				0,
				imageIcon,
				"<ul class=\"e_list\"><!-- 有门票信息 --><!-- 没有门票组的门票信息 --><li title=\"成人5元,老人及学生3元\"><span class=\"l_title\">门票：</span><span class=\"des_text\">成人5元,...",
				false);
		return place;
	}

	class IntroMove implements ActionListener {
		private JPanel panel;
		private int state = 0;

		public IntroMove(JPanel panel) {
			this.panel = panel;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (state == 0) {
				if (introY > 30) {
					introY -= 10;
					introH += 10;
					intro.setBounds(introX, introY, introW, introH);
					panel.paintComponents(getGraphics());
				} else {
					state = 1;
					timer.stop();
				}
			} else if (state == 1) {
				if (introY < 240) {
					introY += 10;
					introH -= 10;
					intro.setBounds(introX, introY, introW, introH);
					panel.paintComponents(getGraphics());
				} else {
					state = 0;
					timer.stop();
				}
			}
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

	}

}
