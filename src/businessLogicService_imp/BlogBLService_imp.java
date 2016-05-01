package businessLogicService_imp;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import tools.Config;
import tools.Debugger;
import tools.FileUtils;
import vo.BlogVO;
import bean.BlogPO;
import bean.FilePO;
import businessLogicService.BlogBLService;
import dataService.BlogDataService;
import dataService.CityDataService;
import dataService.UploadDataService;

public class BlogBLService_imp implements BlogBLService {

	private BlogDataService blogDataService;
	private UploadDataService uploadDataService;
	private UploadDataService up;
	private String serverIP;

	public BlogBLService_imp(String serverIP,UploadDataService up,BlogDataService blogDataService) {
		this.serverIP=serverIP;
		this.up=up;
		this.uploadDataService=uploadDataService;
		this.blogDataService = blogDataService;
	}

	@Override
	public int createNewBlog(BlogVO blogVO) {
		Date date=new Date();
		BlogPO po=new BlogPO(blogVO.userID, blogVO.cityID, blogVO.title, date);
		int blogID=-1;
		try {
			blogID=blogDataService.add(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blogID;
	}

	@Override
	public boolean modifyBlog(BlogVO blogVO) { 
		//这里只修改这些，其他的items重传一遍，包括布局文件
		BlogPO po=new BlogPO(blogVO.blogID,blogVO.userID,blogVO.cityID, blogVO.title);
		try {
			blogDataService.modify(po);
			return true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteBlog(int blogID) {
		try {
			return blogDataService.delete(blogID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addApplause(int userID, int blogID) {
		try {
			return blogDataService.addApplause(userID, blogID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteApplause(int userID, int blogID) {
		try {
			return blogDataService.deleteApplause(userID, blogID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkIsApplaused(int userID, int blogID) {
		try {
			return blogDataService.checkApplause(userID, blogID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public BlogPO searchByID(int blogID) {
		try {
			return blogDataService.searchByID(blogID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<BlogPO> searchByTitle(String key) {
		return null;
	}

	@Override
	public ArrayList<BlogPO> searchByCity(String cityName) {
		return null;
	}

	@Override
	public boolean showBlog(BlogPO blogPO) {
		return false;
	}

	@Override
	//将chan文件夹传上去
	public void upload(int blogID) {
		// TODO Auto-generated method stub
		System.out.println("开始上传文件");
		File dir=new File("blog");
		File[] files=dir.listFiles();
		try {
			up.mkdir(blogID);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < files.length; i++) {
			File file=files[i];
			if(!file.isDirectory()){
				try {
//					String serverPath="http://"+serverIP+"/blog/"+String.valueOf(blogID)+"/"+file.getName();
					String serverPath = "blog/"+String.valueOf(blogID)+"/"+file.getName();
//					System.out.println(serverPath);
//					byte[] b = FileUtils.createByteArray(file);
//					Debugger.log("" + b.length);
					up.upload(new FilePO(serverPath, FileUtils.file2String(file)));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("结束上传文件");
		for (int i = 0; i < files.length; i++){
			File file=files[i];
			file.delete();
		}
		dir.delete();
		dir=new File("blog");
		dir.mkdir();
		System.out.println("清空缓存文件");
	}

	@Override
	public void uploadcomments(int blogID) {
		// TODO Auto-generated method stub
		File comFile=new File("blog/comments.txt");
		try {
			String serverPath = "blog/"+String.valueOf(blogID)+"/"+comFile.getName();
			up.upload(new FilePO(serverPath, FileUtils.file2String(comFile)));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
