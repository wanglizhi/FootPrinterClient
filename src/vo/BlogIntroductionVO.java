package vo;

import java.net.URL;

public class BlogIntroductionVO {

	public URL userImage;
	public int blogID;
	public String title;
	public String introduction;
	public BlogVO blogVO;

	public BlogIntroductionVO(URL userImage, int blogID, String title,
			String introduction, BlogVO blogVO) {
		super();
		this.userImage = userImage;
		this.blogID = blogID;
		this.title = title;
		this.introduction = introduction;
		this.blogVO = blogVO;
	}

}
