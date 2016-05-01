package businessLogicService;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import vo.BlogIntroductionVO;
import vo.PlanIntroductionVO;

public interface SpaceBLService {

	public ArrayList<BlogIntroductionVO> getBlogs();

	public ArrayList<PlanIntroductionVO> getPlans();

	public ImageIcon getMap(int zoom);
	
	public void changeCenter(String center, int zoom);
	
	public void changeMapType(String type, int zoom);

}
