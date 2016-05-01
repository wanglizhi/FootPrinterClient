package businessLogicService;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;

import vo.PlanIntroductionVO;
import bean.PlanPO;

public interface PlanHomeBLService {

	public ArrayList<PlanPO> searchByDate(Date date);

	public ArrayList<PlanPO> searchByUser(int userID);

	public ArrayList<PlanPO> search(String key);

	public ArrayList<PlanIntroductionVO> getPage(int page_num);

	public ArrayList<PlanIntroductionVO> getAllPages();

	public int getPagesNum();
	
	public ImageIcon getMap(int page_num);

	public ImageIcon getMap(String marker);

	public boolean delete(int planID);
}
