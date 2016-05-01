package ui.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;

/**
 * 用来生成一些特定的字体或者样式
 * 
 * @author SongShuo
 * 
 */
public class UIHelper {
	/**
	 * 生成下划线字体
	 * 
	 * @param size
	 *            字体大小
	 * @param type
	 *            字体
	 * @return
	 */
	public static Font getUnderlinedFont(int size, String type) {
		HashMap<TextAttribute, Object> myUnderLine = new HashMap<TextAttribute, Object>();
		myUnderLine.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON); // 定义是否有下划线
		myUnderLine.put(TextAttribute.SIZE, size); // 定义字号
		myUnderLine.put(TextAttribute.FAMILY, type); // 定义字体名
		Font font = new Font(myUnderLine);
		return font;
	}

	/**
	 * 
	 * @return 未选中的按钮及标签的颜色，浅蓝
	 */
	public static Color getUnSelectedColor() {
		Color c = new Color(54, 160, 228);
		return c;
	}

	/**
	 * @return 选中的按钮或标签颜色，深蓝
	 */
	public static Color getSelectedColot() {
		Color c = new Color(9, 113, 178);
		return c;
	}

	public static Color getGray() {
		Color gray = Color.gray;
		return gray;
	}
}
