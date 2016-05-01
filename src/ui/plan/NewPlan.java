package ui.plan;

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

import businessLogicService_imp.PlanHomeBLService_imp;

import startup.CurrentUserStatus;
import tools.CityConfig;
import tools.FileUtils;
import tools.MapUtils;
import ui.foundation.BasePanel;
import ui.foundation.CityChooser;
import ui.foundation.DateChooser;
import vo.PlanVO;
import controller.PlanHomeController;

public class NewPlan extends BasePanel {

	CityChooser cityChooser;

	DateChooser startChooser;

	JLabel map;

	JButton next;
	JButton cancel;

	JTextField title;

	JFrame frame;

	PlanHomeController planHomeController;

	public NewPlan(final PlanHomeController planHomeController)
			throws HeadlessException {

		// init begin

		this.setLayout(new BorderLayout(30, 20));

		this.planHomeController = planHomeController;

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
				// 跳转到新建计划界面
				frame.setVisible(false);
				planHomeController.toNewPlanPanel(getPlanVO());
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
		JLabel l2 = createHeadLabel("出发时间", 5);
		l2.setBounds(0, 80, 150, 30);
		centerPanel.add(l2);
		startChooser.setBounds(0, 120, 150, 30);
		centerPanel.add(startChooser);
		JLabel l3 = createHeadLabel("标题", 5);
		l3.setBounds(0, 160, 150, 30);
		centerPanel.add(l3);
		title.setBounds(0, 200, 150, 30);
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

	private PlanVO getPlanVO() {
		return new PlanVO(title.getText(), 0, 0, CurrentUserStatus
				.getInstance().getCurrentUserID(),
				CityConfig.CityToID.get(cityChooser.getRawCity()),
				startChooser.getDate(), null);
	}

	public static void main(String[] args) {

		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
		} catch (Exception e) {
			// TODO exception
		}

		JFrame newPlan = new JFrame("newPlan");
		newPlan.add(new NewPlan(new PlanHomeController(
				new PlanHomeBLService_imp(null))));
		newPlan.setSize(600, 500);
		newPlan.setLocationRelativeTo(null);
		newPlan.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		newPlan.setVisible(true);

	}

}
