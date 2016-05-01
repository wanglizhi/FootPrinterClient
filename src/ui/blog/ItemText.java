package ui.blog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tools.Debugger;
import ui.blog.RecordPanel.addTextFrame;

public class ItemText extends Item {
	private String original;
	private ArrayList<String[]> allParagraghs = new ArrayList<String[]>(); //放段落啊
	
	public static int MAX_FONTSIZE = 36;
	public static int FONTSIZE_GAP = 4;
	public static int LEVEL = 6; // 对字数分级
	// public static int GAP = 40;

	//private String toDisplay;
	private int lengthOfString;
	private String[] tokens; // 将所有字分为几行

	/*
	 * 以下变量全部用于根据总字数不同(分为6档)而调整： 每行字数、字号、起始画点、行距
	 */

	private int[] charTable = { 20, 50, 80, 120, 150, 200, 300 };

	// 0判断字数的表驱动

	// private int[] charPerLineTable = { 8, 10, 14, 17, 20, 25,40,45 };//
	// （charPerLine -2）^2 = numChar 非中文
	private int[] charPerLineTable = { 7, 7, 9, 12, 15, 18, 26, 38 };// （charPerLine
																		// -2）^2
																		// =
																		// numChar
																		// 中文

	private int charPerLine; // int[NUM_TOTALCHAR];
	// 1每行字数

	private int[] fontSizeTable = new int[LEVEL + 2];
	private int fontSize;
	private Font font; // 设置字号
	// 2根据字数的不同，调整字体大小呢,字数越大，字体越小,最小为18,最大32,数组中元素从大到小

	private int[] startXYTable = { 120, 90, 70, 60, 50, 40, 30, 10 };// new
																		// int[NUM_TOTALCHAR];
	private int startXY;
	// 3根据字数的多少，调整显示在左上角的起点
	private boolean isChinese = true;
	private int gap;

	private void initTable() {
		for (int i = 0; i < fontSizeTable.length; i++) {
			fontSizeTable[i] = MAX_FONTSIZE - i * FONTSIZE_GAP;

		}
	}

	private void setStringLayout(int total) {
		int level = 0;
		for (int i = 0; i < LEVEL; i++) {
			if (total >= charTable[i]) {

				level++;

			}
		}
		// level -=1; //设置level的等级 0~6
		charPerLine = charPerLineTable[level];
		fontSize = fontSizeTable[level];
		font = new Font("楷体", Font.PLAIN, fontSize);
		startXY = startXYTable[level];
		if (level < 3)
			gap = 40;
		else
			gap = 25;

		width = Constants.PANEL_WIDTH; // 400
		if (total > charTable[charTable.length - 1]) {
			int moreCharPerLine = (total - charTable[charTable.length - 1]) / 10 + 1;// 其中的10是大约估计的行数。该变量表示每行增加的字数
			charPerLine += moreCharPerLine;

			width = startXY + (int) (charPerLine * fontSize * 1.15); // 变长了
		}
		// System.out.println("leng:" + width);
	}

	/**
	 * 
	 * @param numOfCharPerLine
	 *            每行的字数 将传入的字符串分割成几行
	 */
	/*
	private void split(int numOfCharPerLine) {

		int lineNum = lengthOfString / numOfCharPerLine + 1; // 行数
		int offset = lengthOfString % numOfCharPerLine; // 最后一行多出的字数

		tokens = new String[lineNum];
		for (int i = 0; i < tokens.length; i++) {
			int start = i * numOfCharPerLine;
			int end = Math.min((i + 1) * numOfCharPerLine, lengthOfString - 1); // 防止数组越界
			if (start <= end)
				tokens[i] = toDisplay.substring(start, end);
			// System.out.println("start" + start + "end" + end);
		}
	}
*/
private String[] split(int numOfCharPerLine, String stringToSplit) {
		
		stringToSplit = "    "+stringToSplit+"  ";  //加上段首两个空格,后面加空格为了显示完整的算法
		int paraLength = stringToSplit.length(); //要被分割的字符长度
		
		int lineNum = paraLength / numOfCharPerLine + 1; // 行数
		int offset = paraLength % numOfCharPerLine; // 最后一行多出的字数

		String afterSplit[] = new String[lineNum];
		
		for (int i = 0; i < afterSplit.length; i++) {
			int start = i * numOfCharPerLine;
			int end = Math.min((i + 1) * numOfCharPerLine ,
					paraLength - 1); // 防止数组越界
			if(start<=end)
				afterSplit[i] = stringToSplit.substring(start, end);
			
			System.out.println("start"+start+"end"+end);

		}
		return afterSplit;
	}
	// 根据传经来的string创建组件
		public ItemText(String code, int cursor) {
			super(code, cursor);
			original = code ;
			initTable();
	
		//	this.toDisplay = original + "  ";
			lengthOfString = original.length();
			setStringLayout(lengthOfString);
	
			//split(charPerLine);
	/**@author SongShuo 用以下代码代替楼上一行*/
			if(original.contains("\n")){  //包含换行符的
				
				String[] temp = original.split("\n"); //temp中包含了每个段落的字
				
				for(int i = 0; i<temp.length;i++){
					if(temp[i]!=null)
						allParagraghs.add(split(charPerLine,temp[i])); //注意！！！这个长度参数是总的字数的
				}
	//			for(int i = 0 ;i< allParagraghs.get(0).length ; i++)
//	System.out.println("para1: " + allParagraghs.get(0)[i]);			
			}
			else{
				
				tokens = split(charPerLine,original+"  ");
				allParagraghs.add(tokens);
				
			}
	/**@author SongShuo*/
			
		}
	@Override
			public void draw(int left, Graphics g) {
				g.setColor(new Color(208, 236, 242));
				g.fillRect(toLeft + left, Constant.SHOWLEFTUP, width, Constant.SHOWHEIGHT);
				g.setFont(font);
				g.setColor(Color.BLACK);
		//		int i = 0;
		//		for (; i < tokens.length; i++) {
		//			if (tokens[i] != null)
		//				g.drawString(tokens[i], startXY + left + toLeft, Constant.SHOWLEFTUP + startXY + i * gap);
		//		}
				//修改了paintcomponent
				int count = 0;  //用来记录行数的
				for(int j = 0; j< allParagraghs.size(); j++){
					String[] item = allParagraghs.get(j); //每个item[i]里面放一个段落的文字啊
					
					for (int i = 0; i <item.length; i++) {
						if(item[i]!=null){
							g.drawString(item[i], startXY+ left + toLeft,Constant.SHOWLEFTUP + startXY + count * gap);
							count++;
		//System.out.println("item 的长度"+ item.length);
						}
					}
					//count ++;
				}
			}
	class Constants {
		public static final int PANEL_HEIGHT = 400;
		public static final int PANEL_WIDTH = 400;

	}

	@Override
	public void click() {
		new addTextFrame(original);
	}

	public String getOriginal() {
		return original;
	}

	@Override
	public String toString() {
		return "<text>" + original;
	}

	class addTextFrame extends JFrame implements FocusListener {
		int width = 500;
		int height = 600;
		JTextArea jta;
		JScrollPane jsp;//
		JButton sureButton;
		JButton quitButton;

		public addTextFrame(String temp) {
			super("添加文字");
			jta = new JTextArea(temp);
			jta.setFont(new Font("黑体", Font.PLAIN, 28));
			jta.setLineWrap(true);
			jsp = new JScrollPane(jta);
			jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			sureButton = new JButton("确认修改");
			quitButton = new JButton("取消");
			initial();
			JPanel southPanel = new JPanel();
			southPanel.setLayout(new FlowLayout());
			southPanel.add(sureButton);
			southPanel.add(quitButton);
			add(BorderLayout.CENTER, jsp);
			add(BorderLayout.SOUTH, southPanel);
			this.setLocation(Constant.SHOWWIDTH / 2 - width / 2, 668 / 2 - height / 2);
			this.setSize(new Dimension(width, height));
			setUndecorated(true);
			setVisible(true);
			addFocusListener(this);
		}

		private void initial() {
			
			sureButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					//debug!!!!!
					allParagraghs = new ArrayList<String[]>(); 
					//!!!!!!!!!!!!!!!!!!!!!
					String code = jta.getText();
					original = code + "  ";
					initTable();
					Debugger.log("in surebutton actionlistener"+original);
				//	toDisplay=original + "  ";
					lengthOfString = original.length();
					setStringLayout(lengthOfString);
					//split(charPerLine);
					/**@author SongShuo 重新修改用以下代码代替了上面一行*/
					if(original.contains("\n")){  //包含换行符的
						
						String[] temp = original.split("\n"); //temp中包含了每个段落的字
						
						for(int i = 0; i<temp.length;i++){
							if(temp[i]!=null)
								allParagraghs.add(split(charPerLine,temp[i])); //注意！！！这个长度参数是总的字数的
						}
				//		for(int i = 0 ;i< allParagraghs.get(0).length ; i++)
			//System.out.println("para1: " + allParagraghs.get(0)[i]);			
					}
					else{
						
						tokens = split(charPerLine,original+"  ");
						allParagraghs.add(tokens);
						
					}
					/**end @author SongShuo*/
					
					addTextFrame.this.setVisible(false);
					addTextFrame.this.dispose();
				}
			});
			quitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addTextFrame.this.setVisible(false);
					addTextFrame.this.dispose();
				}
			});
		}

		public void focusGained(FocusEvent e) {
		}

		public void focusLost(FocusEvent e) {
			// 为什么没用？？？在frame失去焦点时销毁
			addTextFrame.this.setVisible(false);
			addTextFrame.this.dispose();
		}
	}
}
