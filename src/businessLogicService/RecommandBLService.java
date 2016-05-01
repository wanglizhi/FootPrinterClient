package businessLogicService;

import java.util.ArrayList;

import vo.BlogVO;
import vo.LandmarkVO;
import vo.PlaceVO;
import vo.PlanVO;

public interface RecommandBLService {

	public ArrayList<PlanVO> recommandPlan();

	public ArrayList<BlogVO> recommandBlog();

	public ArrayList<PlaceVO> recommandLandmark();

}
