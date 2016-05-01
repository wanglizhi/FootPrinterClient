package controller;

import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import bean.BlogPO;
import businessLogicService.BlogBLService;

import startup.ControllerFactory;
import sun.security.krb5.Config;
import ui.blog.Comments;
import ui.blog.Constant;
import ui.blog.Item;
import ui.blog.ItemAudio;
import ui.blog.ItemPicture;
import ui.blog.ItemRadio;
import ui.blog.ItemText;
import ui.blog.ItemTitle;
import ui.blog.Label;
import ui.blog.RecordPanel;
import ui.blog.RecordShowPanel;
import vo.BlogVO;

public class BlogShowController implements BlogRepaintInterface {
	private BlogBLService blogBLService;
	private ArrayList<Item> items;
	private ArrayList<Label> labels;
	private RecordShowPanel rsp;
	private int gapToLeft;// 最左边的组件距离边缘的距离
	private int cursor;

	String userName;
	String cityName;
	String title;
	int applauseNum;
	URL blogUrl;

	int userID;
	int blogID;
	boolean isnotHaveComment;

	public BlogShowController(BlogBLService bbs) {
		this.blogBLService = bbs;
		cursor = Constant.GAPTOLEFT;// 绘图时不断加上，绘图时用
		items = new ArrayList<Item>();
		labels = new ArrayList<Label>();
		isnotHaveComment = false;
	}

	// 复制文件
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	public static void main(String[] args) {
		BlogShowController blogController = null;
		blogController = new BlogShowController(null);
		JFrame jFrame = new JFrame("test");
		jFrame.setSize(1366, 668);
		jFrame.setLayout(null);
		jFrame.add(blogController.getPanel(0, 0));
		jFrame.setUndecorated(true);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
		// blogController.rsp.requestFocus();
	}

	public void addLike() {
		applauseNum++;
		blogBLService.addApplause(userID, blogID);
	}

	public void minusLike() {
		if (applauseNum > 0) {
			applauseNum--;
			blogBLService.deleteApplause(userID, blogID);
		}
	}

	public void repaint() {
		// TODO Auto-generated method stub
		rsp.repaint();
	}

	public RecordShowPanel getPanel(final int blogID, final int userID) {
		cursor = Constant.GAPTOLEFT;// 绘图时不断加上，绘图时用
		items = new ArrayList<Item>();
		labels = new ArrayList<Label>();
		isnotHaveComment = false;

//		rsp = new RecordShowPanel();
//		 new Thread(){
//		 public void run() {
		load(blogID, userID);
//		 };
//		 }.start();
		return rsp;
	}

	private void load(int blogID, int userID) {
		//先清空blog
		File dir=new File("blog");
		File[] files=dir.listFiles();
		for (int i = 0; i < files.length; i++){
			File file=files[i];
			file.delete();
		}
		boolean isLike = blogBLService.checkIsApplaused(userID, blogID);
		BlogPO blogPO = blogBLService.searchByID(blogID);
		this.blogID = blogID;
		this.userID = userID;
		userName = blogPO.getUserName();
		cityName = blogPO.getCityName();
		title = blogPO.getTitle();
		applauseNum = blogPO.getApplauseNum();
		BlogVO vo = new BlogVO(userName, cityName, title, applauseNum, null);
		URL url = null;
		try {
			url = new URL("http://" + tools.Config.IP + "/footPrinter/blog/" + blogID + "/layout.txt");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String record;
			while ((record = reader.readLine()) != null) {
				if (record.contains("<image>")) {// 遇到图片先把云端的复制过来
					String[] strings = record.split(">");
					String imageString = strings[1];
					// imageString=tools.FileUtils.translate(imageString);
					saveUrlFile("http://" + tools.Config.IP + "/footPrinter/blog/" + blogID + "/" + imageString, "blog/" + imageString);

					System.out.println("图片获取成功");
					ItemPicture picture = new ItemPicture(imageString, cursor);
					items.add(picture);
					cursor += picture.getWidth();
				} else if (record.contains("<text>")) {
					String[] strings = record.split(">");
					String textString = strings[1];
					ItemText text = new ItemText(textString, cursor);
					items.add(text);
					cursor += text.getWidth();
				} else if (record.contains("<title>")) {
					String[] strings = record.split(">");
					String titleString = strings[1];
					ItemTitle title = new ItemTitle(titleString, cursor);
					items.add(title);
					cursor += title.getWidth();
				} else if (record.contains("<audio>")) {
					String[] strings = record.split(">");
					String audioString = strings[1];
					// audioString=tools.FileUtils.translate(audioString);
					saveUrlFile("http://" + tools.Config.IP + "/footPrinter/blog/" + blogID + "/" + audioString, "blog/" + audioString);

					ItemAudio audio = new ItemAudio(audioString, cursor, this);
					items.add(audio);
					cursor += audio.getWidth();
				} else if (record.contains("<vedio>")) {
					String[] strings = record.split(">");
					String audioString = strings[1];
					// audioString=tools.FileUtils.translate(audioString);
					saveUrlFile("http://" + tools.Config.IP + "/footPrinter/blog/" + blogID + "/" + audioString, "blog/" + audioString);
					ItemRadio radio = new ItemRadio(audioString, cursor);
					items.add(radio);
					cursor += radio.getWidth();
				}
				cursor += Constant.SHOWPADWIDTH;
			}
			URL commenturl = null;
			try {
				commenturl = new URL("http://" + tools.Config.IP + "/footPrinter/blog/" + blogID + "/comments.txt");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 下面读取comments
			File comFile = new File("blog/comments.txt");
			BufferedWriter output = new BufferedWriter(new FileWriter(comFile));
			ArrayList<Comments> comments = new ArrayList<Comments>();
			reader = new BufferedReader(new InputStreamReader(commenturl.openStream()));
			boolean isFirst = true;
			while ((record = reader.readLine()) != null) {
				if (isFirst) {
					output.write(record);
					isFirst = false;
					if (record.equals("")) {
						isnotHaveComment = true;
					} else {
						isnotHaveComment = false;
						comments.add(new Comments(record));
					}
				} else {
					comments.add(new Comments(record));
					output.write("\r\n" + record);
					isnotHaveComment = false;
				}
			}
//			// 把原来等待的界面进程结束
//			rsp.stopTimmer.stop();
			rsp = new RecordShowPanel(comments, items, vo, isLike, this);
			for (Item item : items) {
				if (item instanceof ItemAudio) {
					((ItemAudio) item).runTimer();
				}
			}
			output.close();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 获取网络文件，转存到fileDes中，fileDes需要带文件后缀名
	public static void saveUrlFile(String fileUrl, String fileDes) throws Exception {
		File toFile = new File(fileDes);
		if (toFile.exists()) {
			return;
		}
		toFile.createNewFile();
		FileOutputStream outImgStream = new FileOutputStream(toFile);
		outImgStream.write(getUrlFileData(fileUrl));
		outImgStream.close();
	}

	// 获取链接地址文件的byte数据
	public static byte[] getUrlFileData(String fileUrl) throws Exception {
		URL url = new URL(fileUrl);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.connect();
		InputStream cin = httpConn.getInputStream();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = cin.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		cin.close();
		byte[] fileData = outStream.toByteArray();
		outStream.close();
		return fileData;
	}

	public void toBlogHomePanel() {
		ControllerFactory.getInstance().homepageController.setTab(4, ControllerFactory.getInstance().blogHomeController.getBlogHomePanel());
		File comFile = new File("blog/comments.txt");
		comFile.delete();

		cursor = Constant.GAPTOLEFT;// 绘图时不断加上，绘图时用
		items = new ArrayList<Item>();
		labels = new ArrayList<Label>();
		isnotHaveComment = false;
	}

	public void addComment(String comment) {
		try {
			File comFile = new File("blog/comments.txt");
			BufferedWriter output = new BufferedWriter(new FileWriter(comFile, true));
			if (isnotHaveComment) {
				output.write(comment);
				isnotHaveComment = false;
			} else {
				output.write("\r\n" + comment);
			}
			output.close();
			blogBLService.uploadcomments(blogID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
