package businessLogicService_imp;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;

import tools.Debugger;
import tools.FileUtils;
import tools.MapUtils;
import vo.BlogIntroductionVO;
import vo.BlogVO;
import bean.BlogPO;
import businessLogicService.BlogHomeBLService;
import dataService.BlogDataService;

public class BlogHomeBLService_imp implements BlogHomeBLService {

	BlogDataService blogDataService;

	// cache
	ArrayList<BlogPO> currentBlogs = new ArrayList<BlogPO>();
	ArrayList<BlogPO> currentDateBlogs = new ArrayList<BlogPO>();

	public BlogHomeBLService_imp(BlogDataService blogDataService) {
		this.blogDataService = blogDataService;
	}

	@Override
	public ArrayList<BlogPO> searchByDate(Date date) {

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

		currentDateBlogs.clear();
		for (BlogPO p : currentBlogs)
			if (p.getPublishTime().before(endDate)
					&& p.getPublishTime().after(startDate))
				currentDateBlogs.add(p);

		ArrayList<BlogIntroductionVO> currentBlogIntroductionVOs = new ArrayList<BlogIntroductionVO>();
		for (BlogPO p : currentDateBlogs) {
			currentBlogIntroductionVOs.add(toIntroduction(p));
		}

		return currentDateBlogs;
	}

	@Override
	public ArrayList<BlogPO> search(String key) {
		currentBlogs.clear();
		currentDateBlogs.clear();
		// 按照城市名搜索
		 try {
			currentBlogs = blogDataService.search(key);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		initCurrentBlogs();

		for (BlogPO p : currentBlogs)
			currentDateBlogs.add(p);

		return currentDateBlogs;
	}

	@Override
	public ArrayList<BlogPO> searchByUser(int userID) {
		currentBlogs.clear();
		currentDateBlogs.clear();
		// 按照城市名搜索
		try {
			currentBlogs = blogDataService.searchByUserID(userID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (BlogPO p : currentBlogs)
			currentDateBlogs.add(p);

		return currentDateBlogs;
	}


	@Override
	public ArrayList<BlogIntroductionVO> getPage(int page_num) {
		ArrayList<BlogIntroductionVO> currentBlogIntroductionVOs = new ArrayList<BlogIntroductionVO>();
		for (int i = (page_num - 1) * 9; i < page_num * 9; i++)
			if (i < currentDateBlogs.size())
				currentBlogIntroductionVOs.add(toIntroduction(currentDateBlogs
						.get(i)));
			else
				break;
		return currentBlogIntroductionVOs;
	}

	@Override
	public int getPagesNum() {
		return currentDateBlogs.size() / 9 + 1;
	}

	@Override
	public ArrayList<BlogIntroductionVO> getAllPages() {
		// TODO Auto-generated method stub
		ArrayList<BlogIntroductionVO> currentBlogIntroductionVOs = new ArrayList<BlogIntroductionVO>();
		for (BlogPO p : currentDateBlogs)
			currentBlogIntroductionVOs.add(toIntroduction(p));
		return currentBlogIntroductionVOs;
	}

	// private

	public static BlogIntroductionVO toIntroduction(BlogPO blogPO) {
		BlogIntroductionVO blogIntroductionVO = new BlogIntroductionVO(
				blogPO.getUserImage(), blogPO.getBlogID(), blogPO.getTitle(),
				null, toBlog(blogPO));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		blogIntroductionVO.introduction = "<html><p>作者名字："
				+ blogPO.getUserName() + "</p><p>目的城市：" + blogPO.getCityName()
				+ "</p><p>出发时间：" + sdf.format(blogPO.getPublishTime())
				+ "</p><p>" + blogPO.getApplauseNum() + "个人觉得这篇博客很赞"
				+ "</p><p><br>";

		return blogIntroductionVO;
	}

	private static BlogVO toBlog(BlogPO blogPO) {
		return new BlogVO(blogPO.getBlogID(), blogPO.getUserName(),
				blogPO.getCityName(), blogPO.getTitle(), blogPO.getCityID(),
				blogPO.getUserID(), null);
	}

	private ArrayList<BlogPO> getAllDate() {
		currentDateBlogs.clear();

		for (BlogPO p : currentBlogs)
			currentDateBlogs.add(p);
		return currentDateBlogs;
	}

	@Override
	public ImageIcon getMap(int page_num) {
		// TODO Auto-generated method stub
		ArrayList<String> cityList = new ArrayList<String>();
		for (int i = (page_num - 1) * 9; i < page_num * 9; i++)
			if (i < currentDateBlogs.size()) {
				BlogPO p = currentDateBlogs.get(i);
				if (p.getCityName() != null
						&& !cityList.contains(p.getCityName()))
					cityList.add(p.getCityName());
			}
		return MapUtils.getMap(cityList, 300, 450);
	}

	@Override
	public boolean delete(int blogID) {
		try {
			return blogDataService.delete(blogID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
