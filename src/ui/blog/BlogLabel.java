package ui.blog;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import businessLogicService_imp.BlogHomeBLService_imp;
import controller.BlogHomeController;

import tools.Config;
import ui.foundation.BasePanel;
import vo.BlogIntroductionVO;
import vo.BlogVO;

public class BlogLabel extends BasePanel {

	JLabel recommendBlogHead;
	
	BlogVO blogVO;

	public BlogLabel(BlogIntroductionVO blogIntroductionVO) {
		
		this.blogVO = blogIntroductionVO.blogVO;

		this.setLayout(null);

		JPanel headline = createHorizontalPanel(false);
		headline.add(new JLabel(createFixedSizeImage(
				blogIntroductionVO.userImage, 70, 70)));
		headline.add(Box.createRigidArea(HGAP20));
		if (blogIntroductionVO.title.length() > 5)
			recommendBlogHead = createUnderLineLabel(blogIntroductionVO.title.substring(0, 4) + "...", 15);
		else
			recommendBlogHead = createUnderLineLabel(blogIntroductionVO.title, 15);
		headline.add(recommendBlogHead);
//		headline.add(Box.createRigidArea(HGAP20));
//		this.add(headline);
		JLabel introduction = new JLabel(blogIntroductionVO.introduction);
//		this.add(Box.createRigidArea(VGAP20));
//		this.add(introduction);
		
		headline.setBounds(20, 20, 200, 100);
		introduction.setBounds(20, 120, 200, 100);
		this.add(headline);
		this.add(introduction);
		this.setSize(250, 200);
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(250, 200));
	}

	public JLabel getTitle(){
		return recommendBlogHead;
	}

	public BlogVO getBlogVO(){
		return blogVO;
	}
	
	public static void main(String args[]) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
		} catch (Exception e) {
			// TODO exception
		}
		JFrame frame = new JFrame("test");
		frame.add(new BlogLabel(new BlogIntroductionVO(null, 0, "adasdadads", "adasdadaxaacad", null)));
		frame.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}
