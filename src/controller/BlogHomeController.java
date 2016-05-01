package controller;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import startup.ControllerFactory;
import startup.CurrentUserStatus;
import tools.Debugger;
import ui.blog.BlogHomePanel;
import ui.blog.MyBlogPanel;
import vo.BlogIntroductionVO;
import vo.BlogVO;
import businessLogicService.BlogHomeBLService;

public class BlogHomeController {

	int currentPage;
	int allPageNum;

	BlogHomeBLService blogHomeBLService;

	private BlogHomePanel blogHomePanel;
	private MyBlogPanel myBlogPanel;

	public BlogHomeController(BlogHomeBLService blogHomeBLService) {

		this.currentPage = 1;
		this.allPageNum = 1;

		this.blogHomeBLService = blogHomeBLService;
	}

	public ArrayList<BlogIntroductionVO> search(String key) {
		blogHomeBLService.search(key);
		this.currentPage = 1;
		this.allPageNum = blogHomeBLService.getPagesNum();
		return blogHomeBLService.getPage(1);
	}

	public ArrayList<BlogIntroductionVO> nextPage() {
		return blogHomeBLService.getPage(++currentPage);
	}

	public ArrayList<BlogIntroductionVO> prevPage() {
		return blogHomeBLService.getPage(--currentPage);
	}

	public int getPagesNum() {
		return allPageNum;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public ArrayList<BlogIntroductionVO> searchByDate(Date date) {
		blogHomeBLService.searchByDate(date);
		this.currentPage = 1;
		this.allPageNum = blogHomeBLService.getPagesNum();
		return blogHomeBLService.getPage(1);
	}

	// myBlog

	public ArrayList<BlogIntroductionVO> searchByUser(int userID) {
		blogHomeBLService.searchByUser(userID);
		return blogHomeBLService.getAllPages();
	}

	public ArrayList<BlogIntroductionVO> getAllBlogIntroductions() {
		return blogHomeBLService.getAllPages();
	}

	public ImageIcon getMap() {
		return blogHomeBLService.getMap(currentPage);
	}

	public JPanel getBlogHomePanel() {
		if (blogHomePanel == null)
			blogHomePanel = new BlogHomePanel(this);
		return blogHomePanel;
	}

	public MyBlogPanel getMyBlogPanel() {
		if (myBlogPanel == null)
			myBlogPanel = new MyBlogPanel(this);
		updateMyBlogs();
		return myBlogPanel;
	}

	public void toCheckBlogPanel(BlogVO blogVO) {

		ControllerFactory.getInstance().homepageController.setTab(4,
				ControllerFactory.getInstance().blogShowController.getPanel(
						blogVO.blogID, blogVO.userID));
	}

	public void toNewBlogPanel(BlogVO blogVO) {
		ControllerFactory.getInstance().homepageController
				.setTab(4, ControllerFactory.getInstance().blogController
						.getPanel(blogVO));
	}

	public void updateMyBlogs() {
		myBlogPanel.refreshMyBlogResults(searchByUser(CurrentUserStatus
				.getInstance().getCurrentUserID()));
	}

	public void deleteBlog(int blogID) {
		blogHomeBLService.delete(blogID);
		updateMyBlogs();
	}
}
