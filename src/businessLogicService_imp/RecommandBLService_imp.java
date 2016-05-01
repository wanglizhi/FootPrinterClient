package businessLogicService_imp;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import dataService.RecommandDataService;

import startup.CurrentUserStatus;
import tools.Debugger;
import tools.LandmarkType;
import ui.foundation.BasePanel;
import vo.BlogVO;
import vo.LandmarkVO;
import vo.PlaceVO;
import vo.PlanVO;
import bean.BlogPO;
import bean.LandmarkPO;
import bean.PlacePO;
import bean.PlanPO;
import bean.UserPO;
import businessLogicService.RecommandBLService;

public class RecommandBLService_imp implements RecommandBLService {

	CurrentUserStatus currentUserStatus = CurrentUserStatus.getInstance();
	UserPO userPO ;//= currentUserStatus.getCurrentUserPO();
	RecommandDataService recommandDataService;

	public RecommandBLService_imp(RecommandDataService rds) {
		recommandDataService = rds;
		userPO = currentUserStatus.getCurrentUserPO();
	}

	// 根据当前用户，推荐计划、记录、景点
	@Override
	public ArrayList<PlanVO> recommandPlan() {
		ArrayList<PlanPO> polist =null;
		ArrayList<PlanVO> volist =new ArrayList<PlanVO>();
		
		try {
			polist =  recommandDataService.recommandPlan(currentUserStatus.getCurrentUserPO().getUserID());
			for(int i = 0 ;i<polist.size();i++){
				volist.add(POToVO(polist.get(i)));
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Debugger.log("!!!plan recommand from server"+polist.size());
		return volist;
	}

	@Override
	public ArrayList<BlogVO> recommandBlog() {
		ArrayList<BlogVO> volist = new ArrayList<BlogVO>();
		ArrayList<BlogPO> polist = null;
		
		try {
			polist = recommandDataService.recommandBlog(currentUserStatus.getCurrentUserPO().getUserID());
			for(int i = 0; i<polist.size() ; i++){
				volist.add(POToVO(polist.get(i)));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		Debugger.log("!!!blog recommand from server"+polist.size());
		return volist;
	}

	@Override
	public ArrayList<PlaceVO> recommandLandmark() {
		ArrayList<PlaceVO> volist = new ArrayList<PlaceVO>();
		ArrayList<LandmarkPO> polist = null;
		
		try {
			polist = recommandDataService.recommandLandmark(currentUserStatus.getCurrentUserPO().getUserID());
		
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		for(int i = 0 ;i<polist.size() ; i++){
			volist.add(POToVO(polist.get(i)));
		}
		Debugger.log("!!!landmark recommand from server"+polist.size());
		return volist;
	}
	
	private PlanVO POToVO(PlanPO planPO){
		return new PlanVO(planPO.getTitle(), planPO.getApplauseNum(),
				planPO.getPlanID(), planPO.getUserID(), planPO.getCityID(),
				planPO.getStartDate(), planPO.getEndDate());
		
	}
	private BlogVO POToVO(BlogPO po){
		BlogVO vo = new BlogVO(po.getBlogID(),po.getUserName(),po.getCityName(),po.getTitle(),po.getCityID(),po.getUserID(),po.getUserImage());
		
		return vo;
	}
	
	private PlaceVO POToVO(LandmarkPO po){
		PlaceVO vo = null;
		ImageIcon image=null;
			image=BasePanel.createFixedSizeImage(po.getImage(), 300, 240);
		vo = new PlaceVO(po.getID(),po.getName(),po.getCityID(),LandmarkType.LANDMARK,po.getIntroduction(),po.getType(),po.getApplauseNum(),image,po.getPrice(),false);
		return vo;
	}
}
