package ui.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;

/**
 * ��������һЩ�ض������������ʽ
 * 
 * @author SongShuo
 * 
 */
public class UIHelper {
	/**
	 * �����»�������
	 * 
	 * @param size
	 *            �����С
	 * @param type
	 *            ����
	 * @return
	 */
	public static Font getUnderlinedFont(int size, String type) {
		HashMap<TextAttribute, Object> myUnderLine = new HashMap<TextAttribute, Object>();
		myUnderLine.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON); // �����Ƿ����»���
		myUnderLine.put(TextAttribute.SIZE, size); // �����ֺ�
		myUnderLine.put(TextAttribute.FAMILY, type); // ����������
		Font font = new Font(myUnderLine);
		return font;
	}

	/**
	 * 
	 * @return δѡ�еİ�ť����ǩ����ɫ��ǳ��
	 */
	public static Color getUnSelectedColor() {
		Color c = new Color(54, 160, 228);
		return c;
	}

	/**
	 * @return ѡ�еİ�ť���ǩ��ɫ������
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
