package businessLogicService;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import bean.PlanItemPO;
import bean.PlanPO;

import vo.PlanEntertainmentVO;
import vo.PlanHotelVO;
import vo.PlanItemVO;
import vo.PlanRestaurantVO;
import vo.PlanShoppingMallVO;
import vo.PlanSpotVO;
import vo.PlanVO;

public interface PlanBLService {

	// boolean createNewPlan(PlanVO planVO);
	// boolean modifyPlan(PlanVO planVO);
	// boolean deletePlan(int planID);
	// PlanVO searchByID(int planID);
	// ArrayList<PlanVO> searchByCity(int cityID);
	// ArrayList<PlanVO> searchByDate(Date date);
	boolean addApplause(int userID, int planID);

	boolean deleteApplause(int userID, int planID);

	boolean checkApplause(int userID, int planID);

	boolean savePlanItem(int planID, PlanItemVO planItemVO, Date currentDate);

	boolean modifyPlanItem(int planID, PlanItemVO planItemVO, Date currentDate);

	int savePlan(PlanVO planVO);

	boolean modifyPlan(PlanVO planVO);

	//增加收藏
	boolean addFavourite(int userID, int planItemID);

	// 检查收藏
	boolean checkFavourite(int userID, int ItemID);

	// 删除收藏
	boolean deleteFavourite(int userID, int ItemID);

	ArrayList<PlanItemVO> getAllFavourite(int userID, int cityID);

	ArrayList<PlanSpotVO> getSpots(int cityID);

	ArrayList<PlanHotelVO> getHotels(int cityID);

	ArrayList<PlanRestaurantVO> getRestaurants(int cityID);

	ArrayList<PlanEntertainmentVO> getEntertainments(int cityID);

	ArrayList<PlanShoppingMallVO> getShoppingMalls(int cityID);

	// boolean deletePlanItem(int planItemID);
	// boolean modifyPlanItem(PlanItemVO planItemPO);
	ArrayList<PlanItemVO> getPlanItems(int planID, Date day);
}
