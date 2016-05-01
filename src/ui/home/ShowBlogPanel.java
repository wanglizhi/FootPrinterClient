package ui.home;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.foundation.BasePanel;
import vo.BlogVO;
import tools.*;
/**
 * 用于将热门记录blog的缩略图显示在主界面上
 * @author SongShuo
 *
 */
public class ShowBlogPanel extends JPanel{
	
	public static final int PIC_WIDTH = 150;
	public static final int PIC_HEIGHT = 150;
	private BlogVO blogVO;
	private JLabel imageLabel = new JLabel();
	private JLabel nameLabel = new JLabel();
	private JLabel likeLabel = new JLabel();
	
	private Font oldFont  = new Font("宋体",Font.BOLD,12);
	private Font clickedFont =  new Font("宋体",Font.BOLD,14); 
	
	private boolean isClicked = false;
	/*
	public ShowBlogPanel(String title,int likeNum){
		
		this.setPreferredSize(new Dimension(150,300));
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		//imageLabel.setBounds(0, 0, 150, 150);
		
		blogVO = new BlogVO(po.getBlogID(),po.getUserName(),po.getCityName(),po.getTitle(),po.getCityID(),po.getUserID(),po.getUserImage());
		;
		if(blogVO.picture !=null){
			imageLabel.setIcon(blogVO.picture);
		}
		else{
			ImageIcon pic = BasePanel.createFixedSizeImage(DefaultFileName.DEFUALT_BLOG_PIC_FILE,PIC_WIDTH,PIC_HEIGHT);
			imageLabel.setIcon(pic);
		
		}
			
		
		imageLabel.setPreferredSize(new Dimension(PIC_WIDTH,PIC_HEIGHT));
		imageLabel.setBounds(0 , 0,PIC_WIDTH,PIC_HEIGHT);
		nameLabel.setText(blogVO.title);
		nameLabel.setForeground(UIHelper.getGray());
		likeLabel.setText(blogVO.likeNum+"  赞");
		likeLabel.setForeground(UIHelper.getGray());
		
		this.add(imageLabel);
		this.add(nameLabel);
		this.add(likeLabel);
		
	}
	*/
	public ShowBlogPanel(BlogVO bVO){
		
		this.setPreferredSize(new Dimension(150,300));
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		blogVO = bVO;
		ImageIcon picIcon = BasePanel.createFixedSizeImage(bVO.pictureFile, PIC_WIDTH,PIC_HEIGHT);
		
		if(picIcon==null){
			picIcon = BasePanel.createFixedSizeImage(DefaultFileName.DEFUALT_BLOG_PIC_FILE,PIC_WIDTH,PIC_HEIGHT);
		}
		imageLabel.setIcon(picIcon);
		
		imageLabel.setPreferredSize(new Dimension(PIC_WIDTH,PIC_HEIGHT));
		imageLabel.setBounds(0 , 0,PIC_WIDTH,PIC_HEIGHT);
		
		nameLabel.setText(blogVO.title);
		nameLabel.setForeground(UIHelper.getGray());
		
		likeLabel.setText(blogVO.likeNum+"  赞");
		likeLabel.setForeground(UIHelper.getGray());
		
		this.add(imageLabel);
		this.add(nameLabel);
		this.add(likeLabel);
	
	}

	private void changeFont(){
		if(isClicked){ //点击
			
			likeLabel.setFont(clickedFont);
			nameLabel.setFont(clickedFont);
		}
		else{ //移开
			likeLabel.setFont(oldFont);
			nameLabel.setFont(oldFont);
		}
	}
	
	public void changeClickState(boolean is){
		isClicked = is;
		changeFont();
	}
	
	public BlogVO getBlogVO(){
		return blogVO;
	}
	
}
