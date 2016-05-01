package ui.blog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import controller.BlogController;
import controller.BlogRepaintInterface;

import ui.blog.mp3.Mp3Controller;
import ui.blog.mp3.Mp3Player;
import ui.blog.mp3.Mp3State;

public class ItemAudio extends Item {

	private String fileName;
	private File song;

	// 控制时间的东西
	private int hasPlayedTime = 0; // 全局，记录已播放的时间
	private int songTotalTime = 0;

	// 显示播放或者暂停的boolean true的时候显示暂停，false的时候显示播放
	private boolean isOn = false;

	// 这个参数仅仅用于保证在暂停的时候还能显示播放位置，每次暂停都将该参数清零
	// 有一个bug是暂停的时候正在播放的小方块和总时长会不见啊，所以必须设置参数来保存
	private int hasNotPause = 1;
	private boolean isPause = false;
	private int storeCurrentSongTotal = 0;

	// 用于显示的量
	public static final int BASE_X = 50; // 条形轴的起点啊
	public static final int BASE_Y = 250; // 条形轴的起点啊
	public static final int BAR_LENGTH = 300;
	public static final int BAR_WIDTH = 10;
	public static final int BAR_HEIGHT = 25;
	public static final int ICON_WIDTH = 20;
	public static final int ICON_HEIGHT = 20;

	private int barX = 0 + BASE_X;

	private Image startIcon;
	private Image pauseIcon;
	private Image stopIcon;

	private Image block;
	private Image strip;

	// 与播放器相关的变量
	Mp3State mp3;
	Mp3Controller controller;
	Mp3Player player;

	public Image showImage;
	public String currentTime;
	private BlogRepaintInterface	temp;
	
	// 根据传经来的string创建组件
	public ItemAudio(String code, int cursor,final BlogRepaintInterface temp) {
		super(code, cursor);
		this.temp=temp;
		fileName=code;
		song = new File(code);
		width=400;
		mp3 = new Mp3State();

		controller = new Mp3Controller(mp3);
		System.out.println(code);
		player = new Mp3Player(mp3, code);
		player.start();

		try {
			block = ImageIO.read(new File("chan/cursor.png"));
			strip = ImageIO.read(new File("chan/strip.png"));
			startIcon = ImageIO.read(new File("chan/musicPlay.png"));
			stopIcon = ImageIO.read(new File("chan/musicStop.png"));
			pauseIcon = ImageIO.read(new File("chan/musicPause.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		showImage = startIcon;
		currentTime="";
	}

	public void runTimer() {
		Timer playTimer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if ((songTotalTime == hasPlayedTime)) {
					hasNotPause = 1;
					hasPlayedTime = 0;
				}

				if (hasNotPause == 1)
					songTotalTime = player.getCurrentDuration();

				if (mp3.getIsStarted()) {
					hasPlayedTime++;
					hasNotPause++;
				}

				String total = "";
				if (isPause)
					songTotalTime = storeCurrentSongTotal;
				if (songTotalTime != -1)
					total = "/" + secondToString(songTotalTime);
				currentTime = secondToString(hasPlayedTime)+total;

				double offset = 0;
				double percent = 0.0;
				if (songTotalTime != 0) {
					percent = (double) hasPlayedTime / (double) songTotalTime;
					offset = percent * BAR_LENGTH;
				}
				barX = BASE_X + (int) offset;
				temp.repaint();
			}
		});
		playTimer.start();
	}

	private String secondToString(int n) {
		String time = null;
		int second = 0;
		int minute = 0;
		int hour = 0;

		String hms[] = new String[3];

		second = n % 60;
		minute = n / 60;

		if (minute > 60) {
			hour = minute / 60;
			minute = minute % 60;
		}

		if (second < 10)
			hms[0] = "0" + second;
		else
			hms[0] = "" + second;

		if (minute < 10)
			hms[1] = "0" + minute + ":";
		else
			hms[1] = minute + ":";

		if (hour != 0)
			hms[2] = hour + ":";
		else
			hms[2] = "";

		time = hms[2] + hms[1] + hms[0];
		return time;
	}


	@Override
	public void draw(int left, Graphics g) {
		g.drawString(fileName,toLeft+left+100, Constant.SHOWLEFTUP+100);
		g.drawString(currentTime,toLeft+left+100, Constant.SHOWLEFTUP+150);
		g.drawImage(showImage,toLeft+left+100, Constant.SHOWLEFTUP+180,null);
		g.setColor(Color.blue);

		g.drawImage(strip, toLeft+left+BASE_X, 100+Constant.SHOWLEFTUP+BASE_Y,BAR_LENGTH,10, null);
		g.drawImage(block, toLeft+left+barX, 100+Constant.SHOWLEFTUP+(int)(BASE_Y-(double)1/2*BAR_HEIGHT+8), BAR_WIDTH, BAR_HEIGHT,null);
		g.setColor(Color.black);
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub
		if (!isOn) {
			// 不在播放
			controller.mp3Start();
			songTotalTime = player.getCurrentDuration();
			isOn = true;
			isPause = false;
			showImage=pauseIcon;
		} else {
			// 在播放中
			isOn = false;
			storeCurrentSongTotal = songTotalTime;
			controller.mp3Pause();
			hasNotPause = 1;
			isPause = true;
			showImage=startIcon;
		}
	}

	@Override
	public String toString() {
		return "<audio>"+fileName;
	}
}
