package ui.landmark;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import controller.LandmarkController;
import tools.MapUtils;
import ui.foundation.BasePanel;
import ui.home.UIHelper;
import vo.BlogVO;
import vo.CityVO;
import vo.PlaceVO;
import vo.PlanVO;

public class CityPanel extends BasePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CityVO cityVO;
	JLabel cityName;
	JLabel applause;
	JLabel applauseNum;
	JEditorPane introduction;
	JLabel information;
	LandmarkItemPanel landmarkPanel;

	JPanel northPanel;// ���б��⣬���ܣ�ͼƬ
	JTabbedPane centerPane;// ���о��㡢�Ƶꡣ����
	JPanel eastPanel;// �ƻ�����¼�Ƽ�

	ArrayList<PlanVO> recommandPlanList;
	ArrayList<BlogVO> recommandBlogList;
	LandmarkController controller;
	public static CityVO stub(){
		CityVO city=new CityVO(1,
				"����",
				"�������ҹ����׶���ȫ�������Ρ��Ļ����ĺ͹��ʽ�������Ŧ��Ҳ��һ�������ġ���ʷ�Ļ����ǡ�����������ʷ�������Ǽ��������Ķ��ǣ��Ӹ߿ո����ģ���ĵ������԰�֡���̳����...",
				"<div class=\"e_cover_des_r\">�������ʱ�ڣ��ﶬ��<br />\\��������ʱ�䣺<i>4</i>��</div>",
				0,
				false, null);
		return city;
	}
	public CityPanel(){
		this(stub(),null);
	}
	public CityPanel(CityVO cityVO,LandmarkController controller) {
		this.cityVO=cityVO;
		this.controller=controller;
		northPanel = createVerticalPanel(true);
		initNorthPanel();
		centerPane = new JTabbedPane();
		initCenterPanel();
		eastPanel=createVerticalPanel(true);
		eastPanel.setPreferredSize(new Dimension(280, 200));
		initEastPanel();
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPane, BorderLayout.CENTER);
		this.add(eastPanel, BorderLayout.EAST);
	}

	public void initNorthPanel() {
		JPanel headPanel = createHorizontalPanel(false);
		cityName = createHeadLabel(cityVO.name, 5,30);
		final ImageIcon icon1=BasePanel.createFixedSizeImage("chan/like2.png", 30, 30);
		final ImageIcon icon2=BasePanel.createFixedSizeImage("chan/like.png", 30, 30);
		if(cityVO.isApplaused){
			applause = new JLabel(icon1);
		}else{
			applause = new JLabel(icon2);
		}
		applause.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(cityVO.isApplaused){
					if(controller.deleteCityApplause(cityVO.id)){
						applause.setIcon(icon2);
						cityVO.isApplaused=false;
						cityVO.applauseNum--;
						applauseNum.setText(" �ȶ�: "+cityVO.applauseNum);
					}
				}else{
					if(controller.addCityApplause(cityVO.id)){
						applause.setIcon(icon1);
						cityVO.isApplaused=true;
						cityVO.applauseNum++;
						applauseNum.setText(" �ȶ�: "+cityVO.applauseNum);
					}
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				CityPanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				CityPanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
		final JButton exit=this.createImageButton("img/�ƻ�/�˳�1.png");
		final ImageIcon exit1=new ImageIcon("img/�ƻ�/�˳�1.png");
		final ImageIcon exit2=new ImageIcon("img/�ƻ�/�˳�2.png");
		exit.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				exit.setIcon(exit2);
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				exit.setIcon(exit1);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				CityPanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				CityPanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
//				System.out.println("city exit");
				controller.toLandmarkHomePanel();
			}
		});
		
		applauseNum=new JLabel(" �ȶȣ� "+cityVO.applauseNum);
		headPanel.add(cityName);
		headPanel.add(applause);
		headPanel.add(applauseNum);
		headPanel.add(Box.createRigidArea(new Dimension(900,30)));
		headPanel.add(exit);
		//==============================
		northPanel.add(headPanel);
		JPanel introPanel = createHorizontalPanel(false);
		CityImagePanel imagePanel = new CityImagePanel();
		introPanel.add(imagePanel);
		imagePanel.setPreferredSize(new Dimension(800, 300));
		JEditorPane intro = new JEditorPane(
				"text/html", "<div>"+cityVO.introduction+"</div>");
		intro.setPreferredSize(new Dimension(400, 300));
		intro.setEditable(false);
		intro.setEnabled(false);
		intro.setBackground(Color.WHITE);
		introPanel.add(intro);
		introPanel.setPreferredSize(new Dimension(400, 300));
		northPanel.add(introPanel);
	}

	public void initCenterPanel() {
		JPanel landmark = createVerticalPanel(false);
		centerPane.add("����", landmark);
		landmark.setPreferredSize(new Dimension(1000, 2500));
		initLandmark(landmark,cityVO.landmarkList);

	}
	public void initBuffer(){
		JPanel hotel = createVerticalPanel(false);
		JPanel restaurant = createVerticalPanel(false);
		JPanel entertainment = createVerticalPanel(false);
		JPanel market = createVerticalPanel(false);
		centerPane.add("�Ƶ�", hotel);
		centerPane.add("��ʳ", restaurant);
		centerPane.add("����", entertainment);
		centerPane.add("�̳�", market);
		initLandmark(hotel,cityVO.hotelList);
		initLandmark(restaurant,cityVO.restaurantList);
		initLandmark(entertainment,cityVO.entertainmentList);
		initLandmark(market,cityVO.marketList);
		this.validate();
	}

	/**
	 * �������ĸ�ֵ
	 * 
	 * @param panel
	 */
	public void initLandmark(JPanel panel,ArrayList<PlaceVO> landmarkList) {
		int size=landmarkList.size();
		int count1=size/3;
		int count2=size%3;
		for(int i=0;i<count1;i++){
			JPanel jp = createHorizontalPanel(true);
			LandmarkItemPanel lip1 = new LandmarkItemPanel(landmarkList.get(i*3+0),controller);
			LandmarkItemPanel lip2 = new LandmarkItemPanel(landmarkList.get(i*3+1),controller);
			LandmarkItemPanel lip3 = new LandmarkItemPanel(landmarkList.get(i*3+2),controller);
			jp.add(lip1);
			jp.add(lip2);
			jp.add(lip3);
			jp.setSize(jp.getWidth(), 400);
			panel.add(jp);
		}
		JPanel jp2 = createHorizontalPanel(true);
		for(int i=0;i<count2;i++){
			LandmarkItemPanel lip = new LandmarkItemPanel(cityVO.landmarkList.get(count1*3+i),controller);
			jp2.add(lip);
		}
		panel.add(jp2);	
	}

	public void initEastPanel() {
		
		
		final Font planFont = UIHelper.getUnderlinedFont(18,"΢���ź�");
		final Font planSelectedFont = UIHelper.getUnderlinedFont(22, "��Բ");
		final Font RecommandFont =new  Font("΢���ź�",Font.PLAIN,14);
		//��ͼ
		JLabel map=new JLabel(MapUtils.getMap(cityVO.name, 300, 200));
		JLabel info=new JLabel("<html><p>"+cityVO.information+"</p></html");
		info.setFont(RecommandFont);
		eastPanel.add(map);
		eastPanel.add(info);
		//�Ƽ��ƻ�
		JLabel leftmiddleLabel = new JLabel();
		this.add(leftmiddleLabel);
		leftmiddleLabel.setText("���żƻ�...");
		leftmiddleLabel.setFont(new java.awt.Font("΢���ź�",1,20));
		leftmiddleLabel.setForeground(UIHelper.getUnSelectedColor());
		eastPanel.add(leftmiddleLabel);
		for(final PlanVO plan:cityVO.planList){
			final JLabel titleLabel = new JLabel(plan.title);
			final JLabel likeLabel = new JLabel(" �ȶȣ� "+plan.applauseNum);
			titleLabel.setFont(planFont);
			titleLabel.setForeground(UIHelper.getGray());
			likeLabel.setFont(RecommandFont);
			likeLabel.setForeground(UIHelper.getGray());
			
			titleLabel.addMouseListener(new MouseListener(){
	
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					titleLabel.setFont(planSelectedFont);
					CityPanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
					//homepageController.recommandPlanItemClicked(mark);
					//����controller�ķ���
					controller.toCheckPlanPanel(plan.planID);
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
					titleLabel.setFont(planSelectedFont);	
					CityPanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseReleased(MouseEvent e) {
					titleLabel.setFont(planFont);
					CityPanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
	
				@Override
				public void mouseEntered(MouseEvent e) {
					titleLabel.setFont(planSelectedFont);
					CityPanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					titleLabel.setFont(planFont);
					CityPanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				
			});
			eastPanel.add(titleLabel);
			eastPanel.add(likeLabel);
		}
		
		JLabel leftupLabel = new JLabel();
		this.add(leftupLabel);
		leftupLabel.setText("���ż�¼...");
		leftupLabel.setFont(new java.awt.Font("΢���ź�",1,20));
		leftupLabel.setForeground(UIHelper.getUnSelectedColor());
		eastPanel.add(leftupLabel);
		for(final BlogVO blog:cityVO.blogList){
			final JLabel titleLabel = new JLabel(blog.title);
			final JLabel likeLabel = new JLabel(" �ȶȣ� "+blog.likeNum);
			titleLabel.setFont(planFont);
			titleLabel.setForeground(UIHelper.getGray());
			likeLabel.setFont(RecommandFont);
			likeLabel.setForeground(UIHelper.getGray());
			
			titleLabel.addMouseListener(new MouseListener(){
	
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					titleLabel.setFont(planSelectedFont);
					CityPanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
					//homepageController.recommandPlanItemClicked(mark);
					//����controller�ķ���
					controller.toBlogShowPanel(blog.blogID);
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
					titleLabel.setFont(planSelectedFont);	
					CityPanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseReleased(MouseEvent e) {
					titleLabel.setFont(planFont);
					CityPanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
	
				@Override
				public void mouseEntered(MouseEvent e) {
					titleLabel.setFont(planSelectedFont);
					CityPanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					titleLabel.setFont(planFont);
					CityPanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				
			});
			eastPanel.add(titleLabel);
			eastPanel.add(likeLabel);
		}
	}

	class CityImagePanel extends JPanel implements ActionListener {
		/**
		 * size*400=
		 */
		private static final long serialVersionUID = 1L;
		private int x=400;
		private Image image1;
		private Image image2;
		private Image image3;
		private Timer timer1;
		private Timer timer2;
		private int index=2;

		public CityImagePanel() {
			image1=cityVO.images.get(0).getImage();
			image2=cityVO.images.get(1).getImage();
			image3=cityVO.images.get(2).getImage();
			this.setSize(400, 300);
			this.setVisible(true);
			timer1 = new Timer(4000, this);
			timer2 = new Timer(100, this);
			timer1.start();
		}

		@Override
		public void paint(Graphics g) {
			g.clearRect(0, 0, 800, 300);
			g.drawImage(image1, 0, 0,x,300,400-x,0,400,300, this);
			g.drawImage(image2,x+2,0,400+x+2,300,0,0,400,300,this);
			g.drawImage(image3, 400+x+4,0,804,300,0,0,400-x,300,this);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (timer2.isRunning()) {
					if (x<=3) {
						timer2.stop();
						x=400;
						//����ͼƬ
						if(index==(cityVO.images.size()-1)){
							index=2;
						}else{
							index++;
						}
						Image temp=image1;
							image1=image2;
							image2=image3;
							image3=temp;
							image3=cityVO.images.get(index).getImage();
						this.paintAll(getGraphics());
					} else  x -= 40;
				this.repaint();
				
			} else {
				timer2.start();
			}
		}
	}
}
