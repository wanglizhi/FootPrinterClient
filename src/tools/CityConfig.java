package tools;

import java.util.HashMap;
import java.util.Map;

public class CityConfig {

	public static final String[] regions = new String[]{"����","����", "������", "A-G", "H-L", "M-T", "W-Z"};
	
	public static final String[][] citys = new String[][]{
			{
				"����",
				"�̽�",
				"����",
				"��ɽ",
				"����",
				"������",
				"����",
				"�������",
				"���ͺ���",
				"��³ľ��",
				"��̨",
				"����",
				"֣��",
				"���",
				"̫ԭ",
				"����",
				"����",
				"ʯ��ׯ",
				"��Ȫ",
				"�ػʵ�",
				"����",
				"����",
				"����",
				"����",
				"����",
				"�Ͼ�",
				"����",
				"����",
				"����",
				"����",
				"����",
				"����",
				"�ɶ�",
				"�人",
				"����",
				"����",
				"����",
				"����",
				"���",
				"����",
				"����",
				"�Ϻ�"
			},
			{
				"����",
				"������",
				"����",
				"����",
				"����",
				"���",
				"����",
				"����",
				"����",
				"����",
				"��",
				"�㵺",
				"����",
				"����",
				"�׶�",
				"���ݵ�",
				"�ʴ�",
				"��ɽ",
				"����",
				"����"
			},
			{
				"�¼���",
				"��¡��",
				"������",
				"�Ž�",
				"����",
				"�ռ���",
				"����",
				"�ŵ���",
				"����",
				"����",
				"������",
				"��־����",
				"����",
				"������",
				"˳��",
				"�ᰲ",
				"��߷",
				"����",
				"���",
				"����Ŭ��",
				"���ǵ�",
				"��",
				"�굺",
				"�ؿ�̩",
				"����",
				"��÷��",
				"����",
				"������",
				"˹��ͼ���",
				"��˹����",
				"��ʲ��",
				"��ʲ����",
				"������",
				"Ү·����",
				"�µ���",
				"����",
				"������",
				"����"
			},
			{
				"��˹����",
				"��ɽ",
				"�ŵ���",
				"����",
				"��ʲ����",
				"������",
				"�ɶ�",
				"����",
				"����",
				"��߷",
				"����",
				"����",
				"����",
				"����",
				"����",
				"��ɽ",
				"���ǵ�",
				"�Ž�",
				"�㵺",
				"����",
				"�굺",
				"����"
			}, 
			{
				"������",
				"����",
				"����",
				"����",
				"���ͺ���",
				"��־����",
				"�ᰲ",
				"��¡��",
				"����",
				"���ݵ�",
				"����",
				"���",
				"����",
				"����",
				"��Ȫ",
				"������",
				"����",
				"����"},
				{
					"����",
					"������",
					"������",
					"������",
					"����",
					"����",
					"������",
					"����",
					"�Ͼ�",
					"����",
					"�̽�",
					"�ռ���",
					"�������",
					"�ػʵ�",
					"����",
					"����",
					"����",
					"�ʴ�",
					"����",
					"����",
					"�Ϻ�",
					"����",
					"����",
					"��",
					"����",
					"ʯ��ׯ",
					"�׶�",
					"˳��",
					"˹��ͼ���",
					"��÷��",
					"����",
					"�ؿ�̩",
					"��ʲ��",
					"̫ԭ",
					"���"
				},
				{
					"����",
					"��³ľ��",
					"����",
					"�人",
					"����Ŭ��",
					"����",
					"������",
					"����",
					"���",
					"���",
					"��",
					"�µ���",
					"�¼���",
					"����",
					"����",
					"��̨",
					"����",
					"����",
					"Ү·����",
					"����",
					"����",
					"����",
					"����",
					"����",
					"֣��"
				}};
	
	public static final Map<String, Integer> CityToID = new HashMap<String, Integer>(){
		{
			put("����",new Integer(1));
			put("�̽�",new Integer(2));
			put("����",new Integer(3));
			put("��ɽ",new Integer(4));
			put("����",new Integer(5));
			put("������",new Integer(6));
			put("����",new Integer(7));
			put("�������",new Integer(8));
			put("���ͺ���",new Integer(9));
			put("��³ľ��",new Integer(10));
			put("��̨",new Integer(11));
			put("����",new Integer(12));
			put("֣��",new Integer(13));
			put("���",new Integer(14));
			put("̫ԭ",new Integer(15));
			put("����",new Integer(16));
			put("����",new Integer(17));
			put("ʯ��ׯ",new Integer(18));
			put("��Ȫ",new Integer(19));
			put("�ػʵ�",new Integer(20));
			put("����",new Integer(21));
			put("����",new Integer(22));
			put("����",new Integer(23));
			put("����",new Integer(24));
			put("����",new Integer(25));
			put("�Ͼ�",new Integer(26));
			put("����",new Integer(27));
			put("����",new Integer(28));
			put("����",new Integer(29));
			put("����",new Integer(30));
			put("����",new Integer(31));
			put("����",new Integer(32));
			put("�ɶ�",new Integer(33));
			put("�人",new Integer(34));
			put("����",new Integer(35));
			put("����",new Integer(36));
			put("����",new Integer(37));
			put("����",new Integer(38));
			put("���",new Integer(39));
			put("����",new Integer(40));
			put("����",new Integer(41));
			put("�Ϻ�",new Integer(42));
			put("����",new Integer(43));
			put("������",new Integer(44));
			put("����",new Integer(45));
			put("����",new Integer(46));
			put("����",new Integer(47));
			put("���",new Integer(48));
			put("����",new Integer(49));
			put("����",new Integer(50));
			put("����",new Integer(51));
			put("����",new Integer(52));
			put("��",new Integer(53));
			put("�㵺",new Integer(54));
			put("����",new Integer(55));
			put("����",new Integer(56));
			put("�׶�",new Integer(57));
			put("���ݵ�",new Integer(58));
			put("�ʴ�",new Integer(59));
			put("��ɽ",new Integer(60));
			put("����",new Integer(61));
			put("����",new Integer(62));
			put("�¼���",new Integer(63));
			put("��¡��",new Integer(64));
			put("������",new Integer(65));
			put("�Ž�",new Integer(66));
			put("����",new Integer(67));
			put("�ռ���",new Integer(68));
			put("����",new Integer(69));
			put("�ŵ���",new Integer(70));
			put("����",new Integer(71));
			put("����",new Integer(72));
			put("������",new Integer(73));
			put("��־����",new Integer(74));
			put("����",new Integer(75));
			put("������",new Integer(76));
			put("˳��",new Integer(77));
			put("�ᰲ",new Integer(78));
			put("��߷",new Integer(79));
			put("����",new Integer(80));
			put("���",new Integer(81));
			put("����Ŭ��",new Integer(82));
			put("���ǵ�",new Integer(83));
			put("��",new Integer(84));
			put("�굺",new Integer(85));
			put("�ؿ�̩",new Integer(86));
			put("����",new Integer(87));
			put("��÷��",new Integer(88));
			put("����",new Integer(89));
			put("������",new Integer(90));
			put("˹��ͼ���",new Integer(91));
			put("��˹����",new Integer(92));
			put("��ʲ��",new Integer(93));
			put("��ʲ����",new Integer(94));
			put("������",new Integer(95));
			put("Ү·����",new Integer(96));
			put("�µ���",new Integer(97));
			put("����",new Integer(98));
			put("������",new Integer(99));
			put("����",new Integer(100));
		}
	};
}
