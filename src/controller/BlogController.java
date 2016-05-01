package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import businessLogicService.BlogBLService;

import startup.ControllerFactory;
import ui.blog.Constant;
import ui.blog.Item;
import ui.blog.ItemAudio;
import ui.blog.ItemPicture;
import ui.blog.ItemRadio;
import ui.blog.ItemText;
import ui.blog.ItemTitle;
import ui.blog.Label;
import ui.blog.RecordPanel;
import vo.BlogVO;

public class BlogController implements BlogRepaintInterface {
	private BlogBLService blogBLService;
	private ArrayList<Item> items;
	private ArrayList<Label> labels;
	private RecordPanel rp;
	private int cursor;

	String userName;
	String cityName;
	String title;
	int applauseNum;
	URL blogUrl;

	String serverIP;
	BlogVO vo;

	public BlogController(BlogBLService bbs) {
		this.blogBLService = bbs;
		cursor = Constant.GAPTOLEFT;// ��ͼʱ���ϼ��ϣ���ͼʱ��
		items = new ArrayList<Item>();
		labels = new ArrayList<Label>();
	}

	public void saveToLocal(ArrayList<Item> its) {
		this.items = its;
		String itemText = "";
		for (int i = 0; i < items.size(); i++) {
			Item tempItem = items.get(i);
			itemText += tempItem.toString();
			if (i == items.size() - 1) {
				break;
			}
			itemText += "\r\n";
		}
		File file = new File("blog/layout.txt");
		File commentsFile = new File("blog/comments.txt");
		try {
			if(!commentsFile.exists()) {
				commentsFile.createNewFile();
			}else {
				commentsFile.delete();
				commentsFile.createNewFile();
			}
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(itemText);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �ѵ�ǰ�ļ��е�test.txt�ͱ��ص�ͼƬ,��Ƶ,��Ƶ�ļ�����������,Ҫ���÷�����
	public boolean saveToServer(ArrayList<Item> its) {
		saveToLocal(its);
		// �ϴ�����������Ҫ���ip��ַ
		int blogID;
		if (vo.blogID<0) {
			 blogID= blogBLService.createNewBlog(vo);
		}else {
			blogID=vo.blogID;
			blogBLService.modifyBlog(vo);
		}
		blogBLService.upload(blogID);
		return true;
	}

	// �����ļ�
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// �½��ļ����������������л���
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// �½��ļ���������������л���
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// ��������
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// ˢ�´˻���������
			outBuff.flush();
		} finally {
			// �ر���
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	public static void main(String[] args) {
		BlogController blogController = null;
		blogController = new BlogController(null);
		JFrame jFrame = new JFrame("test");
		jFrame.setSize(1366, 668);
		jFrame.setLayout(null);
		jFrame.add(blogController.getPanel(null));
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
		blogController.rp.requestFocus();
	}

	public void repaint() {
		rp.repaint();
	}

	public RecordPanel getPanel(BlogVO vo) {
		cursor = Constant.GAPTOLEFT;// ��ͼʱ���ϼ��ϣ���ͼʱ��
		items = new ArrayList<Item>();
		labels = new ArrayList<Label>();
		this.vo=vo;
		//�ڽ�֮ǰ���blog
		File dir=new File("blog");
		File[] files=dir.listFiles();
		for (int i = 0; i < files.length; i++){
			File file=files[i];
			file.delete();
		}
		if (vo.blogID < 0) {// �������½���¼��������û�г�ʼ���κζ�����
			File f = new File("blog/layout.txt");
			labels.add(new Label(110, 520, 100, 100, new ImageIcon("chan/addPicture.png")));
			labels.add(new Label(319, 520, 100, 100, new ImageIcon("chan/addText.png")));
			labels.add(new Label(529, 520, 100, 100, new ImageIcon("chan/addMovie.png")));
			labels.add(new Label(738, 520, 100, 100, new ImageIcon("chan/addMusic.png")));
			labels.add(new Label(948, 520, 100, 100, new ImageIcon("chan/delete.png")));
			labels.add(new Label(1157, 520, 100, 100, new ImageIcon("chan/save.png")));
			labels.add(new Label(20, 30, 190, 30, new ImageIcon("chan/addTitle.png")));
			labels.add(new Label(1200, 30, 40, 40, new ImageIcon("chan/tuichu.png")));

			try {
				rp = new RecordPanel(cursor, items, labels, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			//�����޸Ľ���
			// BlogPO blogPO=blogBLService.searchByID(blogID);
			// userName=blogPO.getUserName();
			// cityName=blogPO.getCityName();
			// title=blogPO.getTitle();
			// applauseNum=blogPO.getApplauseNum();
			// blogUrl=blogPO.getUrl();
			// File inFile=new File(new URI(blogUrl.toString()+"/1.txt"));
			URL url = null;
			try {
				url = new URL("http://" + tools.Config.IP + "/footPrinter/blog/"+vo.blogID+"/layout.txt");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BufferedReader reader;
			try {
				reader = new BufferedReader(new InputStreamReader(url.openStream()));
				String record;
				while ((record = reader.readLine()) != null) {
					if (record.contains("<image>")) {// ����ͼƬ�Ȱ��ƶ˵ĸ��ƹ���
						String[] strings = record.split(">");
						String imageString = strings[1];
//						imageString=tools.FileUtils.translate(imageString);
//						String fileName = imageString.substring(imageString.lastIndexOf("\\"));
						BlogShowController.saveUrlFile("http://" + tools.Config.IP + "/footPrinter/blog/"+vo.blogID+"/" + imageString, "blog/" + imageString);

						System.out.println("ͼƬ��ȡ�ɹ�");
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
//						audioString=tools.FileUtils.translate(audioString);
//						String fileName = audioString.substring(audioString.lastIndexOf("\\"));
						BlogShowController.saveUrlFile("http://" + tools.Config.IP + "/footPrinter/blog/"+vo.blogID+"/" + audioString, "blog/" + audioString);

						ItemAudio audio = new ItemAudio(audioString, cursor, this);
						items.add(audio);
						cursor += audio.getWidth();
					} else if (record.contains("<vedio>")) {
						String[] strings = record.split(">");
						String audioString = strings[1];
//						audioString=tools.FileUtils.translate(audioString);
//						String fileName = audioString.substring(audioString.lastIndexOf("\\"));
						BlogShowController.saveUrlFile("http://" + tools.Config.IP + "/footPrinter/blog/"+vo.blogID+"/" + audioString, "blog/" + audioString);
						ItemRadio radio = new ItemRadio(audioString, cursor);
						items.add(radio);
						cursor += radio.getWidth();
					}
					cursor += Constant.SHOWPADWIDTH;
				}

				labels.add(new Label(110, 520, 100, 100, new ImageIcon("chan/addPicture.png")));
				labels.add(new Label(319, 520, 100, 100, new ImageIcon("chan/addText.png")));
				labels.add(new Label(529, 520, 100, 100, new ImageIcon("chan/addMovie.png")));
				labels.add(new Label(738, 520, 100, 100, new ImageIcon("chan/addMusic.png")));
				labels.add(new Label(948, 520, 100, 100, new ImageIcon("chan/delete.png")));
				labels.add(new Label(1157, 520, 100, 100, new ImageIcon("chan/save.png")));
				labels.add(new Label(20, 30, 190, 30, new ImageIcon("chan/addTitle.png")));
				labels.add(new Label(1200, 30, 40, 40, new ImageIcon("chan/tuichu.png")));

				rp = new RecordPanel(cursor, items, labels, this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rp;
	}

	public void toMyBlogPanel(){
		ControllerFactory.getInstance().homepageController.setTab(4, ControllerFactory.getInstance().blogHomeController.getMyBlogPanel());
		ControllerFactory.getInstance().blogHomeController.updateMyBlogs();

		//������ӵ��ǹ��캯�����
		cursor = Constant.GAPTOLEFT;// ��ͼʱ���ϼ��ϣ���ͼʱ��
		items = new ArrayList<Item>();
		labels = new ArrayList<Label>();
	}
}
