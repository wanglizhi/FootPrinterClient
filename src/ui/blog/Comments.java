package ui.blog;

import java.awt.Color;
import java.awt.Graphics;

public class Comments{
	public String[] comments;
	
	public Comments(String original) {
		comments=original.split("//");
	}

	public void draw(Graphics g) {
		int i=0;
		for (String s : comments) {
			g.drawString(s, Constant.COMMENTLEFT+10, Constant.COMMENTUP + 20+27*i);
			i++;
		}
	}
}
