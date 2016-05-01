package ui.space;

import javax.swing.JFrame;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import tools.Config;
import businessLogicService_imp.SpaceBLService_imp;
import controller.SpaceController;

public class TestFrame extends JFrame {

	public TestFrame() {
		this.setTitle("test map display");
		this.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		SpacePanel sp = new SpacePanel(new SpaceController(
				new SpaceBLService_imp(null, null)));
		this.add(sp);

		setVisible(true);
	}

	public static void main(String args[]) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
		} catch (Exception e) {
			// TODO exception
		}
		new TestFrame();
	}

}
