package ui.blog;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class ItemPicture extends Item{
	public ImageIcon icon;
	private String imgpath;
	private String imgString;
	
	//根据传经来的string创建组件
		public ItemPicture(String code,int cursor){
			super(code,cursor);
			imgString=code;
			imgpath="blog/"+code;
			icon=new ImageIcon(imgpath);
			if (icon.getIconHeight()<=Constant.SHOWHEIGHT) {
				width=icon.getIconWidth();
				height=icon.getIconHeight();
			}else {
				width=icon.getIconWidth()*Constant.SHOWHEIGHT/icon.getIconHeight();
			}
		}

		@Override
		public void draw(int left, Graphics g) {
			g.drawImage(icon.getImage(), toLeft+left, Constant.SHOWLEFTUP, width, height, null);
		}

		@Override
		public void click() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String toString() {
			return "<image>"+imgString;
		}
}
