package businessLogicService;

import java.util.ArrayList;

import vo.BlogVO;
import bean.BlogPO;

//�߼�����Ҫ���Ǵӷ��������poת��Ϊvo����controller
public interface BlogBLService {
	int createNewBlog(BlogVO blogVO);

	boolean modifyBlog(BlogVO blogVO);

	boolean deleteBlog(int blogID);

	BlogPO searchByID(int blogID);

	ArrayList<BlogPO> searchByTitle(String key);

	ArrayList<BlogPO> searchByCity(String cityName);

	boolean showBlog(BlogPO blogPO);

	boolean addApplause(int userID, int blogID);

	boolean deleteApplause(int userID, int blogID);

	boolean checkIsApplaused(int userID, int blogID);

	void upload(int blogID);

	void uploadcomments(int blogID);
}
