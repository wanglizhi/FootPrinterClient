package ui.foundation;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import tools.CityConfig;
import tools.Debugger;

public class CityChooser extends BasePanel {

	private static final long serialVersionUID = 1L;

	private JPanel cityPanel;
	private Up up;
	private Center center;
	// A-Z...
	ArrayList<JLabel> labelList = new ArrayList<JLabel>();

	private final LabelManager lm = new LabelManager();
	private JLabel showCity;
	private boolean isShow = false;
	private Popup pop;

	private String selectCity;
	private String selectRegion;
	private Map<String, List<String>> cityList;

	public CityChooser(Map<String, List<String>> cityList) {
		selectRegion = "国内";
		selectCity = "南京";
		this.cityList = cityList;
		initPanel();
		initLabel();
	}
	
	public CityChooser(){
		String[][] cs = CityConfig.citys;
		Map<String, List<String>> cityList = new HashMap<String, List<String>>();
		cityList.put("国内", Arrays.asList(cs[0]));
		cityList.put("东亚", Arrays.asList(cs[1]));
		cityList.put("东南亚", Arrays.asList(cs[2]));
		cityList.put("A-G", Arrays.asList(cs[3]));
		cityList.put("H-L", Arrays.asList(cs[4]));
		cityList.put("M-T", Arrays.asList(cs[5]));
		cityList.put("W-Z", Arrays.asList(cs[6]));
		selectRegion = "国内";
		selectCity = "南京";
		this.cityList = cityList;
		initPanel();
		initLabel();
	}

	@Deprecated
	public String getCity() {
		return null;
	}
	
	public String getRawCity(){
		return showCity.getText();
	}
	
	public Integer getCityID(){
		return CityConfig.CityToID.get(showCity.getText());
	}
	
	@Override
	public void addPropertyChangeListener(String property, PropertyChangeListener listener){
		showCity.addPropertyChangeListener(property, listener);
	}

	public void initPanel() {
		cityPanel = new JPanel(new BorderLayout());
		cityPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		JPanel up = new Up();
		cityPanel.add(up, BorderLayout.NORTH);
		cityPanel.add(center = new Center(), BorderLayout.CENTER);
		this.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent event) {

			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {

			}

			// 只要祖先组件一移动,马上就让popup消失
			@Override
			public void ancestorMoved(AncestorEvent event) {
				hidePanel();
			}
		});
	}

	public void initLabel() {
		showCity = new JLabel(selectCity);
		showCity.setRequestFocusEnabled(true);
		showCity.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				showCity.requestFocusInWindow();
			}
		});
		this.setBackground(Color.white);
		this.add(showCity, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(120, 25));
		this.setBorder(BorderFactory.createLineBorder(Color.gray));
		showCity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if (showCity.isEnabled()) {
					showCity.setCursor(new Cursor(Cursor.HAND_CURSOR));
					showCity.setForeground(Color.RED);
				}
			}

			@Override
			public void mouseExited(MouseEvent me) {
				if (showCity.isEnabled()) {
					showCity.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					showCity.setForeground(Color.BLACK);
				}
			}

			@Override
			public void mousePressed(MouseEvent me) {
				if (showCity.isEnabled()) {
					showCity.setForeground(Color.CYAN);
					if (isShow) {
						hidePanel();
					} else {
						showPanel(showCity);
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				if (showCity.isEnabled()) {
					showCity.setForeground(Color.BLACK);
				}
			}
		});
		showCity.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				hidePanel();
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});
	}

	private void refresh() {
		center.updateCity();
		SwingUtilities.updateComponentTreeUI(this);
	}

	private void commit() {
		showCity.setText(selectCity);
		hidePanel();
	}

	private void hidePanel() {
		if (pop != null) {
			isShow = false;
			pop.hide();
			pop = null;
		}
	}

	private void showPanel(Component owner) {
		if (pop != null) {
			pop.hide();
		}
		Point show = new Point(0, showCity.getHeight());
		SwingUtilities.convertPointToScreen(show, showCity);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int x = show.x;
		int y = show.y;
		if (x < 0) {
			x = 0;
		}
		if (x > size.width - 295) {
			x = size.width - 295;
		}
		if (y < size.height - 170) {
		} else {
			y -= 188;
		}
		pop = PopupFactory.getSharedInstance().getPopup(owner, cityPanel, x, y);
		pop.show();
		isShow = true;
	}

	private class Up extends JPanel {

		public Up() {
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.setBackground(new Color(160, 185, 215));
			initUp();
		}

		private void initUp() {
			
			for (String k : CityConfig.regions) {
				final JLabel l = new JLabel(k);
				labelList.add(l);
				this.add(Box.createRigidArea(HGAP10));
				this.add(l);
				if (k == selectRegion)
					l.setBorder(BorderFactory.createLineBorder(Color.black));
				l.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent me) {
						l.setCursor(new Cursor(Cursor.HAND_CURSOR));
						l.setForeground(Color.RED);
					}

					@Override
					public void mouseExited(MouseEvent me) {
						l.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						l.setForeground(Color.BLACK);
					}

					@Override
					public void mousePressed(MouseEvent me) {
						selectRegion = l.getText();
						Debugger.log(selectRegion);
						l.setForeground(Color.WHITE);
						// 将其他label设置为无border
						for (JLabel label : labelList)
							if (l != label)
								label.setBorder(BorderFactory
										.createEmptyBorder());
						l.setBorder(BorderFactory.createLineBorder(Color.black));
						refresh();
					}

					@Override
					public void mouseReleased(MouseEvent me) {
						l.setForeground(Color.BLACK);
					}
				});
			}
		}
	}

	private class Center extends JPanel {
		public Center() {
			super(new GridLayout(9, 5));
			this.setPreferredSize(new Dimension(400, 200));
			initCenter();
		}

		private void initCenter() {
			updateCity();
		}

		public void updateCity() {
			this.removeAll();
			lm.clear();
			this.revalidate();
			this.repaint();
			for(String city : cityList.get(selectRegion)){
				lm.addLabel(new MyLabel(selectRegion, city));
			}
	        for(MyLabel my:lm.getLabels()){
	            this.add(my);
	        }
		}
	}

	private class MyLabel extends JLabel implements MouseListener,
			MouseMotionListener {
		private String city;
		private boolean isSelected;

		public MyLabel(String region, String city) {
			super("" + city);
			this.city = city;
			this.addMouseListener(this);
			this.addMouseMotionListener(this);

				this.setForeground(Color.BLACK);
		}

		public boolean getIsSelected() {
			return isSelected;
		}

		public void setSelected(boolean b, boolean isDrag) {
			isSelected = b;
			if (b && !isDrag)
				selectCity = city;
			this.repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			if (city == selectCity) {
				// 如果日期和当前日期一样,则用红框
				Graphics2D gd = (Graphics2D) g;
				gd.setColor(Color.RED);
				Polygon p = new Polygon();
				p.addPoint(0, 0);
				p.addPoint(getWidth() - 1, 0);
				p.addPoint(getWidth() - 1, getHeight() - 1);
				p.addPoint(0, getHeight() - 1);
				gd.drawPolygon(p);
			}
			if (isSelected) {// 如果被选中了就画出一个虚线框出来
				Stroke s = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
						BasicStroke.JOIN_BEVEL, 1.0f,
						new float[] { 2.0f, 2.0f }, 1.0f);
				Graphics2D gd = (Graphics2D) g;
				gd.setStroke(s);
				gd.setColor(Color.RED);
				Polygon p = new Polygon();
				p.addPoint(0, 0);
				p.addPoint(getWidth() - 1, 0);
				p.addPoint(getWidth() - 1, getHeight() - 1);
				p.addPoint(0, getHeight() - 1);
				gd.drawPolygon(p);
			}
			super.paintComponent(g);
		}

		@Override
		public boolean contains(Point p) {
			return this.getBounds().contains(p);
		}

		private void update() {
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			isSelected = true;
			update();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			Point p = SwingUtilities.convertPoint(this, e.getPoint(), center);
			lm.setSelect(p, false);
			commit();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Point p = SwingUtilities.convertPoint(this, e.getPoint(), center);
			lm.setSelect(p, true);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}

	private class LabelManager {
		private List<MyLabel> list;

		public LabelManager() {
			list = new ArrayList<MyLabel>();
		}

		public List<MyLabel> getLabels() {
			return list;
		}

		public void addLabel(MyLabel my) {
			list.add(my);
		}

		public void clear() {
			list.clear();
		}

		public void setSelect(MyLabel my, boolean b) {
			for (MyLabel m : list) {
				if (m.equals(my)) {
					m.setSelected(true, b);
				} else {
					m.setSelected(false, b);
				}
			}
		}

		public void setSelect(Point p, boolean b) {
			// 如果是拖动,则要优化一下,以提高效率
			if (b) {
				// 表示是否能返回,不用比较完所有的标签,能返回的标志就是把上一个标签和
				// 将要显示的标签找到了就可以了
				boolean findPrevious = false, findNext = false;
				for (MyLabel m : list) {
					if (m.contains(p)) {
						findNext = true;
						if (m.getIsSelected()) {
							findPrevious = true;
						} else {
							m.setSelected(true, b);
						}
					} else if (m.getIsSelected()) {
						findPrevious = true;
						m.setSelected(false, b);
					}
					if (findPrevious && findNext) {
						return;
					}
				}
			} else {
				MyLabel temp = null;
				for (MyLabel m : list) {
					if (m.contains(p)) {
						temp = m;
					} else if (m.getIsSelected()) {
						m.setSelected(false, b);
					}
				}
				if (temp != null) {
					temp.setSelected(true, b);
				}
			}
		}
	}
    
	public static void main(String[] args) {
		
		try {
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
			System.out.println("finished loading BeautyEye");
			BeautyEyeLNFHelper.frameBorderStyle =
					BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow; 
			System.out.println("finished loading BeautyEye Border");
			}catch(Exception e) {
			//TODO exception 
		}
		
//		Map<String, List<String>> cityList = new HashMap<String, List<String>>();
//		ArrayList<String> ss = new ArrayList<String>();
//		ss.add("盘锦");
//		ss.add("北京");
//		ss.add("南京");
//		ss.add("上海");
//		ss.add("苏州");
//		ss.add("济南");
//		ss.add("吉隆坡");
//		ss.add("洛杉矶");
//		ArrayList<String> ss2 = new ArrayList<String>();
//		ss2.add("盘锦");
//		ss2.add("北京");
//		ss2.add("南京");
//		ss2.add("上海");
//		ss2.add("苏州");
//		ss2.add("济南");
//		ss2.add("吉隆坡");
//		ss2.add("hehe");
//		cityList.put("a-g", ss);
//		cityList.put("h-n", ss2);
		final CityChooser cc = new CityChooser();
		JFrame jf = new JFrame("测试城市选择器");
		jf.add(cc);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
}
