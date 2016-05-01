package ui.foundation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI.NormalColor;
import org.jb2011.lnf.beautyeye.widget.N9ComponentFactory;

import tools.Config;
import tools.Debugger;
import ui.home.UIHelper;

public class BasePanel extends JPanel {

	/** The lowered border. */
	Border loweredBorder = // new CompoundBorder(new
							// SoftBevelBorder(SoftBevelBorder.LOWERED),
	new EmptyBorder(15, 10, 5, 10);// );

	// Premade convenience dimensions, for use wherever you need 'em.
	/** The HGA p2. */
	public static Dimension HGAP2 = new Dimension(2, 1);

	/** The VGA p2. */
	public static Dimension VGAP2 = new Dimension(1, 2);

	/** The HGA p5. */
	public static Dimension HGAP5 = new Dimension(5, 1);

	/** The VGA p5. */
	public static Dimension VGAP5 = new Dimension(1, 5);

	/** The HGA p10. */
	public static Dimension HGAP10 = new Dimension(10, 1);

	/** The VGA p10. */
	public static Dimension VGAP10 = new Dimension(1, 10);

	/** The HGA p15. */
	public static Dimension HGAP15 = new Dimension(15, 1);

	/** The VGA p15. */
	public static Dimension VGAP15 = new Dimension(1, 15);

	/** The HGA p20. */
	public static Dimension HGAP20 = new Dimension(20, 1);

	/** The VGA p20. */
	public static Dimension VGAP20 = new Dimension(1, 20);

	/** The HGA p25. */
	public static Dimension HGAP25 = new Dimension(25, 1);

	/** The VGA p25. */
	public static Dimension VGAP25 = new Dimension(1, 25);

	/** The HGA p30. */
	public static Dimension HGAP30 = new Dimension(30, 1);

	/** The VGA p30. */
	public static Dimension VGAP30 = new Dimension(1, 30);

	public BasePanel() {
		this.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
		this.setLayout(new BorderLayout());
//		System.out.println("finished loading BasePanel");
	}

	public JPanel createHorizontalPanel(boolean threeD) {
		JPanel p = null;
		if (threeD)
			p = N9ComponentFactory.createPanel_style1(null).setDrawBg(threeD);// modified
																				// by
																				// jb2011
		else
			p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setAlignmentY(TOP_ALIGNMENT);
		p.setAlignmentX(LEFT_ALIGNMENT);
		if (threeD) {
			p.setBorder(loweredBorder);
		}
		// 因背景是白色N9图，这里设置它不填充默认背景好看一点，要不然灰色背景出来就不好看了
		p.setOpaque(false);// add by jb2011 2012-08-24
		return p;
	}

	public JPanel createHBackgroundPanel(String filename) {
		JPanel p = new ImagePanel(filename);
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setAlignmentY(TOP_ALIGNMENT);
		p.setAlignmentX(LEFT_ALIGNMENT);
		return p;
	}

	public JPanel createVBackgroundlPanel(String filename) {
		JPanel p = new ImagePanel(filename);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setAlignmentY(TOP_ALIGNMENT);
		p.setAlignmentX(LEFT_ALIGNMENT);
		return p;
	}

	public JPanel createVerticalPanel(boolean threeD) {
		JPanel p = null;
		if (threeD)
			p = N9ComponentFactory.createPanel_style1(null).setDrawBg(threeD);// modified
																				// by
																				// jb2011
		else
			p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		p.setAlignmentY(TOP_ALIGNMENT);
		p.setAlignmentX(CENTER_ALIGNMENT);
		if (threeD) {
			p.setBorder(loweredBorder);
		}
		return p;
	}

	public JButton createColorButton(NormalColor background, Color foreground,
			String text) {
		JButton btn = new JButton(text);
		btn.setUI(new BEButtonUI().setNormalColor(background));
		btn.setForeground(foreground);
		return btn;
	}

	public JPanel createVerticalHintBox(JComponent c, String txt) {
		JPanel p = new JPanel();
		p.setOpaque(false);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		JLabel l1 = createHeadLabel(txt, 3);
		l1.setAlignmentX(Component.CENTER_ALIGNMENT);
		p.add(l1);
		c.setAlignmentX(Component.CENTER_ALIGNMENT);
		p.add(c);

		p.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
		return p;
	}

	public JLabel createHeadLabel(String text, int style) {
		return createHeadLabel(text, style, 15);
	}

	public JLabel createHeadLabel(String text, int style, int fontSize) {
		JLabel label = null;
		switch (style) {
		case 1:
			label = N9ComponentFactory.createLabel_style1(text);
			break;
		case 2:
			label = N9ComponentFactory.createLabel_style2(text);
			break;
		case 3:
			label = N9ComponentFactory.createLabel_style3(text);
			break;
		case 4:
			label = N9ComponentFactory.createLabel_style4(text);
			break;
		default:
			label = new JLabel(text);
			label.setForeground(UIHelper.getSelectedColot());
			break;
		}
		label.setFont(new Font(Config.FONT_NAME, Config.FONT_STYLE, fontSize));
		return label;
	}

	public static JScrollPane createScrollPane(JPanel p) {
		JScrollPane jsp = new JScrollPane(p);
		jsp.getVerticalScrollBar().setUnitIncrement(20);
		return jsp;
	}

	public JSeparator createSeparateLine(int orientation, int length) {
		JSeparator js = new JSeparator(orientation);
		js.setPreferredSize(new Dimension(length, 10));
		return js;
	}

	public JLabel createUnderLineLabel(String text, final int fontSize) {
		final HashMap<TextAttribute, Object> hm = new HashMap<TextAttribute, Object>();
		hm.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON); // 定义是否有下划线
		hm.put(TextAttribute.SIZE, fontSize); // 定义字号
		hm.put(TextAttribute.FAMILY, "Simsun"); // 定义字体名
		final JLabel label = new JLabel(text);
//		label.setFont(new Font(hm));
		label.setForeground(Color.black);
		label.addMouseListener(new MouseListener() {

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
				label.setForeground(Color.black);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				label.setForeground(UIHelper.getSelectedColot());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				label.setForeground(UIHelper.getSelectedColot());
			}
		});
		return label; // 生成字号为12，字体为宋体，字形带有下划线的字体
	}

	public Font createFont(int fontSize) {
		return new Font(Config.FONT_NAME, Config.FONT_STYLE, fontSize);
	}

	public JButton createImageButton(String imagename) {
		JButton btn = new JButton(new ImageIcon(imagename));
		btn.setBorder(BorderFactory.createEmptyBorder());
		return btn;
	}

	public static ImageIcon createFixedSizeImage(String filename, int width, int height) {
		ImageIcon icon = new ImageIcon(filename);
		Image image = icon.getImage();
		image = image.getScaledInstance(width, height, Image.SCALE_FAST);
		return new ImageIcon(image);
	}
	
	public static ImageIcon createFixedSizeImage(URL filename, int width, int height) {
		ImageIcon icon = new ImageIcon(filename);
		Image image = icon.getImage();
		image = image.getScaledInstance(width, height, Image.SCALE_FAST);
		return new ImageIcon(image);
	}
	
	public static ImageIcon createFixedSizeImageWithNull(URL filename, int width, int height) {
		//ImageIcon icon = new ImageIcon(filename);
		//Image image = icon.getImage();
		BufferedImage bufferedImage ;
		try {
			bufferedImage = ImageIO.read(filename);
		} catch (IOException e) {
			return null;
		}
		Image image = bufferedImage;
		if (image == null)
			return null;
		image= image.getScaledInstance(width, height, Image.SCALE_FAST);
		return new ImageIcon(image);
	}

	public static void createHorizonalHintBox(JPanel parent, JComponent c,
			String txt) {
		parent.setAlignmentX(Component.LEFT_ALIGNMENT);
		parent.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 5));
		c.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel l1 = N9ComponentFactory.createLabel_style3(txt);
		l1.setAlignmentX(Component.LEFT_ALIGNMENT);
		parent.add(l1);
	}

	public JPanel create3DBox(JComponent c) {
		JPanel p = createHorizontalPanel(true);
		p.add(c);
		return p;
	}

	public JFrame createSurroundFrame(JComponent c){
		JFrame frame = new JFrame();
		frame.add(c);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}
	
	public Tag createTag(String tag, String pressedImage, String normalImage){
		File f = new File(pressedImage);
		return new Tag(tag, new ImageIcon(pressedImage), new ImageIcon(normalImage));
		
	}
	
	private class ImagePanel extends JPanel {
		private Image image;

		public ImagePanel(String filename) {
			try {
				File f = new File(filename);
				image = ImageIO.read(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (image != null) {
				g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(),
						null);
			}
		}
	}
}
