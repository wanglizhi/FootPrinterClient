package ui.plan;

import java.awt.Font;

public class Constant {
	public static int FIRST_BAR_X = 50;
	public static int FIRST_BAR_Y = 140;
	public static int BAR_LENGTH = 720;
	public static int BAR_WIDTH = 60;
	public static int BAR_GAP = 15;
	public static int ITEM_GAP = 10;
	public static int TIME_WIDTH = 5;
	public static int TIME_LENGTH = 10;
	public static int PLANBAR_NUM = 6;
	public static int EACH_BAR_TIME = 24 * 60 / PLANBAR_NUM;
	public static int UNIT_LENGTH = BAR_LENGTH / EACH_BAR_TIME;

	public static int BIGGER_SIZE = 3;

	public static int FIRST_SPOT_X = 950;
	public static int FIRST_SPOT_Y = 170;
	public static int SPOT_ITEM_WIDTH = 50;
	public static int SPOT_ITEM_LENGTH = 80;
	public static int SPOT_TEXT_WIDTH = 10;
	public static int SPOT_TEXT_LENGTH = 30;
	public static int SPOT_GAP = 10;
	public static int SPOT_TEXT_GAP_X = 50;
	public static int SPOT_TEXT_GAP_Y = 30;

	public static int SELECTOR_CTR_FIRST_X = 900;
	public static int SELECTOR_CTR_FIRST_Y = 230;
	public static int SELECTOR_CTR_WIDTH = 30;
	public static int SELECTOR_CTR_LENGTH = 30;
	public static int SELECTOR_GAP = 30;

	public static int PANEL_LENGTH = 1366;
	public static int PANEL_WIDTH = 668;

	public static Font SELECTORHEADER_FONT = new Font("ºÚÌå", Font.PLAIN, 16);
	public static int SELECTORHEADER_LABEL_WIDTH = 20;
	public static int SELECTORHEADER_LABEL_LENGTH = 50;
	public static int SELECTORHEADER_X = FIRST_SPOT_X;
	public static int SELECTORHEADER_Y = FIRST_SPOT_Y
			- SELECTORHEADER_LABEL_LENGTH ;

	public static int CTR_BUTTON_WIDTH = 50;
	public static int CTR_BUTTON_LENGTH = 50;
	public static int CTR_BUTTON_SHRINK = 5;
	public static int CTR_BUTTON_FIRST_X = 50;
	public static int CTR_BUTTON_FIRST_Y = 60;
	public static int CTR_BUTTON_SECOND_X = 750;
	public static int CTR_BUTTON_SECOND_Y = CTR_BUTTON_FIRST_Y;
	public static int CTR_DATE_X = 375;
	public static int CTR_DATE_Y = CTR_BUTTON_FIRST_Y;
	public static int CTR_DATE_WIDTH = 50;
	public static int CTR_DATE_LENGTH = 100;
	
	public static int APPLAUSE_WIDTH=30;
	public static int APPLAUSE_LENGTH=30;
	public static int APPLAUSE_X=500;
	public static int APPLAUSE_Y=70;

	public static int FAVOR_LENGTH = 80;
	public static int FAVOR_WIDTH = 70;
	public static int FAVOR_X = 900;
	public static int FAVOR_Y = 500;

	public static int MAP_X = 900;
	public static int MAP_Y = 140;
	public static int MAP_LENGTH = 380;
	public static int MAP_WIDTH = 310;

	public static int EXIT_LENGTH = 50;
	public static int EXIT_WIDTH = 50;
	public static int PLAN_EXIT_X = 1130;
	public static int PLAN_EXIT_Y = 60;
	
	public static int PLAN_MAP_X=900;
	public static int PLAN_MAP_Y=380;
	public static int PLAN_MAP_LENGTH=300;
	public static int PLAN_MAP_WIDTH=195;
	

	public static Font ORIGIN_FONT = new Font("", Font.BOLD, 13);
	public static Font DATE_FONT = new Font("", Font.BOLD, 18);

	public static String toNormalTime(int minuteTime) {
		char normalTime[] = new char[5];
		normalTime[0] = (char) (minuteTime / 60 / 10 + ('0' - 0));
		normalTime[1] = (char) ((minuteTime / 60) % 10 + ('0' - 0));
		normalTime[2] = ':';
		normalTime[3] = (char) ((minuteTime % 60) / 10 + ('0' - 0));
		normalTime[4] = (char) ((minuteTime % 60) % 10 + ('0' - 0));
		String s = new String(normalTime);
		return s;
	}
}
