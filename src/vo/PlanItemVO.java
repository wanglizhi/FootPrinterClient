package vo;

import java.net.URL;

import ui.plan.PlanBarItem;
import bean.PlanItemPO;

public class PlanItemVO {
	public int planItemID;
	public int landmarkID;
	public int landmarkType;
	public String landmark;
	public int startTime;
	public int endTime;
	public URL picURL;
	// 备注
	public String comment;
	public int indexX;
	public int indexY;
	public boolean isUsed;

	public PlanItemVO(int landmarkID, int landmarkType, String landmark, int startTime, int endTime,
			String comment, int indexX, int indexY, boolean isUsed) {
		super();
		this.landmarkID=landmarkID;
		this.landmarkType=landmarkType;
		this.landmark = landmark;
		this.startTime = startTime;
		this.endTime = endTime;
		this.comment = comment;
		this.indexX = indexX;
		this.indexY = indexY;
		this.isUsed = isUsed;
	}

	public PlanItemVO(PlanItemPO po) {
		this.planItemID = po.getPlanItemID();
		this.landmarkID=po.getLanmarkID();
		this.landmarkType=po.getLandmarkType();
		this.landmark = po.getLandmarkName();
		this.startTime = po.getStartTime();
		this.endTime = po.getEndTime();
		this.comment = po.getComment();
		this.indexX = po.getIndexX();
		this.indexY = po.getIndexY();
		this.isUsed = po.isUsed();
	}

	public PlanItemVO(PlanBarItem pbi) {
		this.planItemID = pbi.getPlanItemID();
		this.landmarkID=pbi.getLandmarkID();
		this.landmarkType=pbi.getLandmarkType();
		this.landmark = pbi.getLandMark();
		this.startTime = pbi.getStartTime();
		this.endTime = pbi.getEndTime();
		this.comment = pbi.getComment();
		this.indexX = pbi.getBarIndex();
		this.indexY = pbi.getIndex();
		this.isUsed = pbi.isUsed();
	}
	
	//为收藏项创建的构造方法
	public PlanItemVO(PlanItemPO po, URL picURL){
		this(po);
		this.picURL=picURL;
	}

}
