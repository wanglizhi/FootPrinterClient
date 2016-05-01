package ui.plan;

import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import tools.Debugger;
import ui.foundation.BasePanel;
import vo.PlanIntroductionVO;
import vo.PlanVO;

public class PlanLabel extends BasePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// data
	JLabel cityImage;
	JLabel title;
	JLabel introduction;
	JLabel name;
	JButton delete;
	
	PlanVO planVO;

	public PlanLabel(PlanIntroductionVO planIntroductionVO) {

		this.planVO = planIntroductionVO.planVO;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentY(TOP_ALIGNMENT);
		this.setAlignmentX(LEFT_ALIGNMENT);

		cityImage = new JLabel(createFixedSizeImage(
				planIntroductionVO.cityImage, 150, 135));
		// planItroduction = new JLabel("<html><h4>" + introduction.getTitle() +
		// "</h4><p>" + introduction.getStartDate() + "出发</p></html>");

		title = createUnderLineLabel(planIntroductionVO.title, 15);
		introduction = new JLabel(planIntroductionVO.introduction);
		name = new JLabel("作者名字:" + planIntroductionVO.name); 

		Debugger.log(planIntroductionVO.title + " " + (cityImage.getIcon() == null));
		
		if (cityImage.getIcon() == null)
			cityImage.setIcon(createFixedSizeImage(
					"img/defaultPlanImage.jpg", 150, 140));
		
		Debugger.log(planIntroductionVO.title + "2 " + (cityImage.getIcon() == null));
		
		this.add(Box.createRigidArea(VGAP10));
		this.add(cityImage);
		this.add(Box.createRigidArea(VGAP10));
		this.add(title);
		this.add(Box.createRigidArea(VGAP10));
		this.add(introduction);
		this.add(Box.createRigidArea(VGAP10));
		this.add(name);
	}
	
	public PlanVO getPlanVO(){
		return planVO;
	}

//	@Override
//	public void addMouseListener(MouseListener m){
//		Debugger.log("to checkPlanPanel");
//		title.addMouseListener(m);
//	}
	
	public JLabel getTitle(){
		return title;
	}
}
