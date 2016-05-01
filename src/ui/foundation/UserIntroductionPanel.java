package ui.foundation;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import bean.UserPO;

import startup.CurrentUserStatus;
import tools.Debugger;
import vo.UserInforVO;

public class UserIntroductionPanel extends BasePanel {

	JLabel portrait = new JLabel();
	JLabel livePlace = new JLabel();
	JLabel qq = new JLabel();
	JLabel tags = new JLabel();
	JLabel joinDate = new JLabel();
	
	public UserIntroductionPanel() {
		

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentY(TOP_ALIGNMENT);
		this.setAlignmentX(LEFT_ALIGNMENT);

		updateUserInfo();

		this.add(Box.createRigidArea(VGAP10));
		this.add(portrait);
		this.add(Box.createRigidArea(VGAP10));
		this.add(livePlace);
		this.add(Box.createRigidArea(VGAP10));
		this.add(joinDate);
		this.add(Box.createRigidArea(VGAP10));
		this.add(qq);
		this.add(Box.createRigidArea(VGAP10));
		this.add(tags);
		this.add(Box.createRigidArea(VGAP10));
	}
	
	public void updateUserInfo(){
		UserPO infoPO = CurrentUserStatus.getInstance().getCurrentUserPO();
		UserInforVO infoVO = CurrentUserStatus.getInstance().getCurrentUserVO();
		portrait.setIcon(createFixedSizeImage(
				infoPO.getHead(), 200, 200));
		livePlace.setText("���ӵ�:" + infoPO.getAddress());
		qq.setText("�ҵ�qq��" + infoPO.getQq());
		joinDate.setText("����ʱ��:" + infoPO.getRegisteredDate().toString());
		Debugger.log(infoPO.getTags());
//		if (infoPO.getTags().length() != 0)
//			tags.setText("��ϲ��:" + infoPO.getTags().split("_")[0] + "��" + infoPO.getTags().split("_")[1]);
	}

}
