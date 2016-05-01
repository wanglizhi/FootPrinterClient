package vo;

import java.net.URL;

public class PlanIntroductionVO {

	public int planid;
	public URL cityImage;
	public String title;
	public String introduction;
	public String name;
	public PlanVO planVO;

	public PlanIntroductionVO(int planid, URL cityImage, String title,
			String introduction, PlanVO planVO, String name) {
		super();
		this.planid = planid;
		this.cityImage = cityImage;
		this.title = title;
		this.introduction = introduction;
		this.planVO = planVO;
		this.name = name;
	}

}
