package ui.home;

/**
 * @author SongShuo
 */
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import ui.foundation.BasePanel;
import vo.LandmarkVO;

public class RecommandedPanel extends BasePanel{
	private LandmarkVO landmarkVO ;
	private JLabel imgLabel = new JLabel();
	private JLabel nameAndLikeLabel = new JLabel();
	private JLabel tagLabel = new JLabel();
	private Font oldFont  = new Font("����",Font.BOLD,12);
	private Font clickedFont =  new Font("����",Font.BOLD,14); 
	
	private boolean isClicked = false;
	//������췽���Ǹ�׮��ʵ���ϵͳ���ݿ��õ�����
	public RecommandedPanel(String name, boolean isApplause, int applauseNum,String tag1,String tag2){
		
		this.setPreferredSize(new Dimension(150,300));
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		landmarkVO = new LandmarkVO(name,isApplause,applauseNum,tag1,tag2);
		
		imgLabel.setIcon(landmarkVO.picture);
		//imgLabel.setPreferredSize(new Dimension(150,150));
		imgLabel.setBounds(0, 0, 150, 150);
		nameAndLikeLabel.setText(landmarkVO.name+"   "+landmarkVO.applauseNum+" ��");
		nameAndLikeLabel.setForeground(UIHelper.getGray());
		nameAndLikeLabel.setFont(oldFont);
		tagLabel.setText("tag:	"+" "+landmarkVO.tag1+"  "+landmarkVO.tag2);
		tagLabel.setFont(oldFont);
		tagLabel.setForeground(UIHelper.getGray());
		this.add(imgLabel);
		this.add(nameAndLikeLabel);
		this.add(tagLabel);
		
	}
	
	public RecommandedPanel(LandmarkVO lVO){
		this.landmarkVO = lVO;

		imgLabel.setIcon(landmarkVO.picture);
		//imgLabel.setPreferredSize(new Dimension(150,150));
		imgLabel.setBounds(0, 0, 150, 150);
		nameAndLikeLabel.setText(landmarkVO.name+"   "+landmarkVO.applauseNum+" ��");
		nameAndLikeLabel.setForeground(UIHelper.getGray());
		nameAndLikeLabel.setFont(oldFont);
		tagLabel.setText("tag:	"+" "+landmarkVO.tag1+"  "+landmarkVO.tag2);
		tagLabel.setFont(oldFont);
		tagLabel.setForeground(UIHelper.getGray());
		this.add(imgLabel);
		this.add(nameAndLikeLabel);
		this.add(tagLabel);
	}
	private void changeFont(){
		if(isClicked){ //���
			
			tagLabel.setFont(clickedFont);
			nameAndLikeLabel.setFont(clickedFont);
		}
		else{ //�ƿ�
			tagLabel.setFont(oldFont);
			nameAndLikeLabel.setFont(oldFont);
		}
	}
	
	public void changeClickState(boolean is){
		isClicked = is;
		changeFont();
	}
	
	
}
