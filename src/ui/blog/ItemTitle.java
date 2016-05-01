package ui.blog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ItemTitle extends Item{
	String title;
	String location;
	String date;
	private static int TITLETOUP=100;
	private static int LOCATIONTOUP=150;
	private static int DATETOUP=200;
	
	//根据传经来的string创建组件
	public ItemTitle(String code,int cursor){
		super(code,cursor);
		String[] strings=code.split("/");
		title=strings[0];
		location=strings[1];
		date=strings[2];
		width=400;
	}

	@Override
	public void draw(int left, Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.BOLD,25));
		g.drawString(title, toLeft+left+width/2-title.length()*10, Constant.SHOWLEFTUP+TITLETOUP);
		g.setFont(new Font("隶书", Font.BOLD,18));
		g.drawString(location, toLeft+left+width/2-location.length(), Constant.SHOWLEFTUP+LOCATIONTOUP);
		g.setFont(new Font("隶书", Font.PLAIN,18));
		g.drawString(date, toLeft+left+width/2-date.length()*4, Constant.SHOWLEFTUP+DATETOUP);
	
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub
		
	}

	public String getTitle() {
		return title;
	}

	public String getLocation() {
		return location;
	}

	public String getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "<title>"+title+"/"+location+"/"+date;
	}
	
}
