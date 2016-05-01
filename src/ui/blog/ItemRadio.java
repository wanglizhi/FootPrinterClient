package ui.blog;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ItemRadio extends Item{
	public String radioPath;
	public String radioName;
	public Image radioImage;

	
	public ItemRadio(String code, int cursor) {
		super(code, cursor);
		width=400;
		radioPath="blog/"+code;
		radioName=code;
		try {
			radioImage=ImageIO.read(new File("chan/movie.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void draw(int left, Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(radioImage, toLeft+left, Constant.SHOWLEFTUP, width, height, null);
		g.setFont(new Font("隶书", Font.BOLD, 20));
		g.setColor(Color.blue);
		g.drawString(radioName, toLeft+left+20, Constant.SHOWLEFTUP+20);
	}
	//根据传经来的string创建组件

	@Override
	public void click() {
		// TODO Auto-generated method stub
//		display = new Display();
//		shell = new Shell(display);
//		shell.setText("Video Displayer");
//		shell.setSize(800,600);
//        browser = new Browser(shell,SWT.FILL);
//        browser.setBounds(5,30,780,560);
//        browser.setUrl(radioPath);
//        
//		shell.open();
//		
//        while (!shell.isDisposed()) {
//            if (!display.readAndDispatch())
//              display.sleep();
//        }
//        display.dispose();
		File file=new File(radioPath);
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "<vedio>"+radioName;
	}

}
