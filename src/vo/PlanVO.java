package vo;

import java.util.Date;

public class PlanVO {

	public String title;
	public int applauseNum;
	public int planID;
	public int userID;
	public String userName;
	public int cityID;
	public Date startDate;
	public Date endDate;

	public PlanVO(String title, int applauseNum) {
		super();
		this.title = title;
		this.applauseNum = applauseNum;
	}
	
	public PlanVO(int id,String userName,String title, int applauseNum) {
		super();
		this.planID=id;
		this.title = title;
		this.userName=userName;
		this.applauseNum = applauseNum;
	}
	
	public PlanVO(String title){
		this.title = title;
	}
	public PlanVO(String title, int applauseNum, int planID, int userID,
			int cityID, Date startDate, Date endDate) {
		super();
		this.title = title;
		this.applauseNum = applauseNum;
		this.planID = planID;
		this.userID = userID;
		this.cityID = cityID;
		this.startDate = startDate;
		this.endDate = endDate;
	}

}
