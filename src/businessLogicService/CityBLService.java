package businessLogicService;

import java.util.ArrayList;

import vo.BlogVO;
import vo.CityVO;
import vo.PlanVO;

public interface CityBLService {

	public CityVO showCity(int cityID);
	
	public ArrayList<PlanVO> recommendPlan(int cityID);

	public ArrayList<BlogVO> recommendBlog(int cityID);
	
	public boolean addApplause(int userID, int cityID);

	public boolean deleteApplause(int userID, int cityID);

	public boolean checkIsApplaused(int userID, int cityID);

	public ArrayList<CityVO> searchByName(String cityName);
}
