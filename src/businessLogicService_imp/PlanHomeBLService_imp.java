package businessLogicService_imp;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;

import tools.Debugger;
import tools.MapUtils;
import vo.PlanIntroductionVO;
import vo.PlanVO;
import bean.PlanPO;
import businessLogicService.PlanHomeBLService;
import dataService.PlanDataService;

public class PlanHomeBLService_imp implements PlanHomeBLService {

	PlanDataService planDataService;

	// cache
	ArrayList<PlanPO> currentPlans = new ArrayList<PlanPO>();
	ArrayList<PlanPO> currentDatePlans = new ArrayList<PlanPO>();

	public PlanHomeBLService_imp(PlanDataService planDataService) {
		this.planDataService = planDataService;
	}

	@Override
	public ArrayList<PlanPO> searchByDate(Date date) {

		if (date == null)
			return getAllDate();

		// to be test
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.MONTH, 1);
		Date endDate = calender.getTime();
		Date startDate = date;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Debugger.log("startDate : " + sdf.format(startDate));
		Debugger.log("endDate : " + sdf.format(endDate));

		currentDatePlans.clear();
		for (PlanPO p : currentPlans)
			if (p.getStartDate().before(endDate)
					&& p.getStartDate().after(startDate))
				currentDatePlans.add(p);

		ArrayList<PlanIntroductionVO> currentPlanIntroductionVOs = new ArrayList<PlanIntroductionVO>();
		for (PlanPO p : currentDatePlans) {
			currentPlanIntroductionVOs.add(toIntroduction(p));
		}

		return currentDatePlans;
	}

	@Override
	public ArrayList<PlanPO> search(String key) {
		currentPlans.clear();
		currentDatePlans.clear();
		// 按照城市名搜索
		 try {
			currentPlans = planDataService.search(key);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		initCurrentPlans();

		 Debugger.log("planSize : " + currentPlans.size());
		 
		for (PlanPO p : currentPlans)
			currentDatePlans.add(p);

		return currentDatePlans;
	}

	@Override
	public ArrayList<PlanPO> searchByUser(int userID) {
		currentPlans.clear();
		currentDatePlans.clear();
		// 按照城市名搜索
		 try {
			currentPlans = planDataService.searchByUser(userID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		initCurrentPlans();

		for (PlanPO p : currentPlans)
			currentDatePlans.add(p);

		return currentDatePlans;
	}



	@Override
	public ArrayList<PlanIntroductionVO> getPage(int page_num) {
		ArrayList<PlanIntroductionVO> currentPlanIntroductionVOs = new ArrayList<PlanIntroductionVO>();
		for (int i = (page_num - 1) * 9; i < page_num * 9; i++)
			if (i < currentDatePlans.size())
				currentPlanIntroductionVOs.add(toIntroduction(currentDatePlans
						.get(i)));
			else
				break;
		return currentPlanIntroductionVOs;
	}

	@Override
	public int getPagesNum() {
		return currentDatePlans.size() / 9 + 1;
	}

	@Override
	public ArrayList<PlanIntroductionVO> getAllPages() {
		// TODO Auto-generated method stub
		ArrayList<PlanIntroductionVO> currentPlanIntroductionVOs = new ArrayList<PlanIntroductionVO>();
		for (PlanPO p : currentDatePlans)
			currentPlanIntroductionVOs.add(toIntroduction(p));
		return currentPlanIntroductionVOs;
	}

	// private

	public static PlanIntroductionVO toIntroduction(PlanPO planPO) {
		PlanIntroductionVO planIntroductionVO = new PlanIntroductionVO(
				planPO.getPlanID(), null, planPO.getTitle(), null, toPlan(planPO),planPO.getUserName());

		planIntroductionVO.cityImage = planPO.getPlanImage();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		planIntroductionVO.introduction = "<html> "
				+ sdf.format(planPO.getStartDate()) + "出发， <br>"
				+ planPO.getApplauseNum() + "个人觉得这篇plan很赞 + </html>";

		return planIntroductionVO;
	}

	private static PlanVO toPlan(PlanPO planPO) {
		return new PlanVO(planPO.getTitle(), planPO.getApplauseNum(),
				planPO.getPlanID(), planPO.getUserID(), planPO.getCityID(),
				planPO.getStartDate(), planPO.getEndDate());
	}

	private ArrayList<PlanPO> getAllDate() {
		currentDatePlans.clear();

		for (PlanPO p : currentPlans)
			currentDatePlans.add(p);
		return currentDatePlans;
	}

	// 需要城市名
	@Override
	public ImageIcon getMap(int page_num) {
		ArrayList<String> cityList = new ArrayList<String>();
		for (int i = (page_num - 1) * 9; i < page_num * 9; i++)
			if (i < currentDatePlans.size()) {
				PlanPO p = currentDatePlans.get(i);
				if (p.getCityName() != null
						&& !cityList.contains(p.getCityName()))
					cityList.add(p.getCityName());
			}
		return MapUtils.getMap(cityList, 300, 450);
	}

	@Deprecated
	@Override
	public ImageIcon getMap(String marker) {
		return null;
	}

	public boolean delete(int planID){
		try {
			return planDataService.delete(planID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
