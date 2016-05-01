package ui.blog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import startup.CurrentUserStatus;
import tools.CityConfig;
import tools.MapUtils;
import ui.foundation.BasePanel;
import ui.foundation.CityChooser;
import ui.foundation.DateChooser;
import vo.BlogVO;
import businessLogicService_imp.BlogHomeBLService_imp;
import controller.BlogHomeController;

public class NewBlog extends BasePanel {

	CityChooser cityChooser;

	DateChooser startChooser;

	JLabel map;

	JButton next;
	JButton cancel;

	JTextField title;

	JFrame frame;

	BlogHomeController BlogHomeController;

	public NewBlog(final BlogHomeController BlogHomeController)
			throws HeadlessException {

		// init begin

		this.setLayout(new BorderLayout(30, 20));

		this.BlogHomeController = BlogHomeController;

		cityChooser = new CityChooser();

		startChooser = new DateChooser(new Date());

		map = new JLabel(MapUtils.getMap(cityChooser.getRawCity(), 300, 300));

		next = createColorButton(BEButtonUI.NormalColor.lightBlue, Color.white,
				" 确 认 ");
		cancel = createColorButton(BEButtonUI.NormalColor.lightBlue,
				Color.white, " 取 消 ");

		title = new JTextField();
		title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		title.setText(cityChooser.getRawCity() + "之旅");
		// init end
		// listener begin

		cityChooser.addPropertyChangeListener("text",
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						map.setIcon(MapUtils.getMap(cityChooser.getRawCity(),
								300, 300));
						title.setText(cityChooser.getRawCity() + "之旅");
					}
				});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});

		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				BlogHomeController.toNewBlogPanel(getBlogVO());
			}
		});

		// listener end
		// add begin
		JPanel centerPanel = createVerticalPanel(false);
		centerPanel.setLayout(null);
		JPanel buttonPanel = createHorizontalPanel(false);

		JLabel l1 = createHeadLabel("目的地", 5);
		l1.setBounds(0, 0, 150, 30);
		centerPanel.add(l1);
		cityChooser.setBounds(0, 40, 150, 30);
		centerPanel.add(cityChooser);
		JLabel l2 = createHeadLabel("标题", 5);
		l2.setBounds(0, 80, 150, 30);
		centerPanel.add(l2);
		title.setBounds(0, 120, 150, 30);
		centerPanel.add(title);

		buttonPanel.add(Box.createRigidArea(HGAP30));
		buttonPanel.add(next);
		buttonPanel.add(Box.createRigidArea(new Dimension(380, 1)));
		buttonPanel.add(cancel);
		buttonPanel.add(Box.createRigidArea(HGAP30));
		buttonPanel.setPreferredSize(new Dimension(600, 50));

		this.add(new JPanel(), BorderLayout.WEST);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(create3DBox(map), BorderLayout.EAST);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setSize(600, 500);
		// add end
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public BlogVO getBlogVO() {
		return new BlogVO(-1, null, cityChooser.getRawCity(), title.getText(),
				CityConfig.CityToID.get(cityChooser.getRawCity()),
				CurrentUserStatus.getInstance().getCurrentUserID(),null);
	}

	public static void main(String[] args) {

		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
		} catch (Exception e) {
			// TODO exception
		}

		JFrame newBlog = new JFrame("newBlog");
		newBlog.add(new NewBlog(new BlogHomeController(
				new BlogHomeBLService_imp(null))));
		newBlog.setSize(600, 500);
		newBlog.setLocationRelativeTo(null);
		newBlog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		newBlog.setVisible(true);

	}

}
