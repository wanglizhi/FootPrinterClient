package ui.landmark;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import tools.Config;

public class LandmarkFrame extends JFrame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
		} catch (Exception e) {
			// TODO exception
		}
		new LandmarkFrame();
	}

	public LandmarkFrame() {
		this.setTitle("LandmarkTest");
		this.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CityPanel cp = new CityPanel();
		JScrollPane jsp = new JScrollPane(cp);
		jsp.getVerticalScrollBar().setUnitIncrement(20);
		this.add(jsp);
		setVisible(true);
	}
}
