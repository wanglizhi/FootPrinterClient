package businessLogicService_imp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import dataService.PlaceDataService;
import dataService.PlanDataService;

import startup.ClientNetworkInit;
import vo.PlanEntertainmentVO;
import vo.PlanHotelVO;
import vo.PlanItemVO;
import vo.PlanRestaurantVO;
import vo.PlanShoppingMallVO;
import vo.PlanSpotVO;
import vo.PlanVO;
import bean.PlacePO;
import bean.PlanItemPO;
import bean.PlanPO;
import businessLogicService.PlanBLService;

public class PlanBLService_imp implements PlanBLService {

	private PlanDataService planDataService;
	private PlaceDataService landmarkDataService;
	private PlaceDataService hotelDataService;
	private PlaceDataService restaurantDataService;
	private PlaceDataService entertainmentDataService;
	private PlaceDataService marketDataService;

	public PlanBLService_imp() {
		planDataService = ClientNetworkInit.planDataService;
		landmarkDataService = ClientNetworkInit.landmarkDataService;
		hotelDataService = ClientNetworkInit.hotelDataService;
		restaurantDataService = ClientNetworkInit.restaurantDataService;
		entertainmentDataService = ClientNetworkInit.entertainmentDataService;
		marketDataService = ClientNetworkInit.marketDataService;
	}

	@Override
	public boolean addApplause(int userID, int planID) {
		// TODO Auto-generated method stub
		try {
			return planDataService.addApplause(userID, planID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteApplause(int userID, int planID) {
		// TODO Auto-generated method stub
		try {
			return planDataService.deleteApplause(userID, planID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean checkApplause(int userID, int planID) {
		// TODO Auto-generated method stub
		try {
			return planDataService.checkApplause(userID, planID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean savePlanItem(int planID, PlanItemVO planItemVO,
			Date currentDate) {
		// TODO Auto-generated method stub
		PlanItemPO itemPO = new PlanItemPO(planID, planItemVO.landmarkID,
				planItemVO.landmarkType, planItemVO.landmark, currentDate,
				planItemVO.indexX, planItemVO.indexY, planItemVO.isUsed,
				planItemVO.startTime, planItemVO.endTime, planItemVO.comment);
		try {
			planDataService.addPlanItem(itemPO);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int savePlan(PlanVO planVO) {
		// TODO Auto-generated method stub
		PlanPO planPO = new PlanPO(planVO.userID, planVO.cityID,
				planVO.applauseNum, planVO.startDate, planVO.endDate,
				planVO.title);
		try {
			return planDataService.add(planPO);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public ArrayList<PlanItemVO> getPlanItems(int planID, Date day) {
		// TODO Auto-generated method stub
		ArrayList<PlanItemPO> pos;
		try {
			pos = planDataService.getPlanItems(planID, day);

			ArrayList<PlanItemVO> vos = new ArrayList<PlanItemVO>();
			for (PlanItemPO po : pos) {
				PlanItemVO vo = new PlanItemVO(po);
				vos.add(vo);
			}
			return vos;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ArrayList<PlanSpotVO> getSpots(int cityID) {
		// TODO Auto-generated method stub
		ArrayList<PlanSpotVO> spots = new ArrayList<PlanSpotVO>();

		try {
			ArrayList<PlacePO> landPOs = landmarkDataService
					.searchByCityID(cityID);
			for (PlacePO lp : landPOs) {
				PlanSpotVO psv = new PlanSpotVO(lp, 0);
				spots.add(psv);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return spots;
	}

	@Override
	public ArrayList<PlanHotelVO> getHotels(int cityID) {
		// TODO Auto-generated method stub
		ArrayList<PlanHotelVO> hotels = new ArrayList<PlanHotelVO>();
		try {
			ArrayList<PlacePO> hotelPOs = hotelDataService
					.searchByCityID(cityID);
			for (PlacePO hp : hotelPOs) {
				PlanHotelVO phv = new PlanHotelVO(hp, 1);
				hotels.add(phv);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hotels;
	}

	@Override
	public ArrayList<PlanRestaurantVO> getRestaurants(int cityID) {
		// TODO Auto-generated method stub
		ArrayList<PlanRestaurantVO> restaurantVOs = new ArrayList<PlanRestaurantVO>();
		try {
			ArrayList<PlacePO> restaurantPOs = restaurantDataService
					.searchByCityID(cityID);
			for (PlacePO rp : restaurantPOs) {
				PlanRestaurantVO prv = new PlanRestaurantVO(rp, 2);
				restaurantVOs.add(prv);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return restaurantVOs;
	}

	@Override
	public ArrayList<PlanEntertainmentVO> getEntertainments(int cityID) {
		// TODO Auto-generated method stub
		ArrayList<PlanEntertainmentVO> entertainmentVOs = new ArrayList<PlanEntertainmentVO>();
		try {
			ArrayList<PlacePO> entertainmentPOs = entertainmentDataService
					.searchByCityID(cityID);
			for (PlacePO ep : entertainmentPOs) {
				PlanEntertainmentVO pev = new PlanEntertainmentVO(ep, 3);
				entertainmentVOs.add(pev);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return entertainmentVOs;
	}

	@Override
	public ArrayList<PlanShoppingMallVO> getShoppingMalls(int cityID) {
		// TODO Auto-generated method stub
		ArrayList<PlanShoppingMallVO> planShoppingMallVOs = new ArrayList<PlanShoppingMallVO>();
		try {
			ArrayList<PlacePO> marketPOs = marketDataService
					.searchByCityID(cityID);
			for (PlacePO mp : marketPOs) {
				PlanShoppingMallVO pspv = new PlanShoppingMallVO(mp, 4);
				planShoppingMallVOs.add(pspv);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return planShoppingMallVOs;
	}

	@Override
	public boolean addFavourite(int userID, int planItemID) {
		// TODO Auto-generated method stub
		try {
			return planDataService.addFavourite(userID, planItemID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<PlanItemVO> getAllFavourite(int userID, int cityID) {
		// TODO Auto-generated method stub
		ArrayList<PlanItemPO> pos;
		try {
			pos = planDataService.getAllFavourite(userID, cityID);
			ArrayList<PlanItemVO> vos = new ArrayList<PlanItemVO>();
			for (PlanItemPO po : pos) {
				PlanItemVO vo = new PlanItemVO(po, po.getPicUrl());
				System.out.println("ÊÕ²ØµÄ£¡£¡"+po.getPicUrl());
				vos.add(vo);
			}
			return vos;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean modifyPlan(PlanVO planVO) {
		// TODO Auto-generated method stub
		PlanPO planPO = new PlanPO(planVO.planID, planVO.userID, planVO.cityID,
				planVO.applauseNum, planVO.startDate, planVO.endDate,
				planVO.title);
		try {
			return planDataService.modify(planPO);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean modifyPlanItem(int planID, PlanItemVO planItemVO,
			Date currentDate) {
		// TODO Auto-generated method stub
		PlanItemPO itemPO = new PlanItemPO(planID, planItemVO.planItemID, planItemVO.landmarkID,
				planItemVO.landmarkType, planItemVO.landmark, currentDate,
				planItemVO.indexX, planItemVO.indexY, planItemVO.isUsed,
				planItemVO.startTime, planItemVO.endTime, planItemVO.comment);
		try {
			planDataService.modifyPlanItem(itemPO);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkFavourite(int userID, int ItemID) {
		// TODO Auto-generated method stub
		try {
			return planDataService.checkFavourite(userID, ItemID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteFavourite(int userID, int ItemID) {
		// TODO Auto-generated method stub
		try {
			return planDataService.deleteFavourite(userID, ItemID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
