package businessLogicService;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;

import vo.BlogIntroductionVO;
import bean.BlogPO;

public interface BlogHomeBLService {

	public ArrayList<BlogPO> searchByDate(Date date);

	public ArrayList<BlogPO> searchByUser(int userID);

	public ArrayList<BlogPO> search(String key);

	public ArrayList<BlogIntroductionVO> getPage(int page_num);

	public ArrayList<BlogIntroductionVO> getAllPages();

	public int getPagesNum();
	
	public ImageIcon getMap(int page_num);
	
	public boolean delete(int blogID);

}
