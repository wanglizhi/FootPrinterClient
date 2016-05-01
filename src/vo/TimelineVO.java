package vo;

import java.util.ArrayList;

public class TimelineVO {

	ArrayList<String> dateList;

	public TimelineVO(ArrayList<String> dateList) {
		super();
		this.dateList = dateList;
	}

	public ArrayList<String> getDateList() {
		return dateList;
	}

	public void setDateList(ArrayList<String> dateList) {
		this.dateList = dateList;
	}

}
