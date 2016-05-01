package tools;

import java.util.HashMap;
import java.util.Map;

public class CityConfig {

	public static final String[] regions = new String[]{"国内","东亚", "东南亚", "A-G", "H-L", "M-T", "W-Z"};
	
	public static final String[][] citys = new String[][]{
			{
				"北京",
				"盘锦",
				"大连",
				"鞍山",
				"沈阳",
				"哈尔滨",
				"长春",
				"齐齐哈尔",
				"呼和浩特",
				"乌鲁木齐",
				"烟台",
				"济南",
				"郑州",
				"天津",
				"太原",
				"银川",
				"西宁",
				"石家庄",
				"酒泉",
				"秦皇岛",
				"桂林",
				"厦门",
				"丽江",
				"昆明",
				"大理",
				"南京",
				"徐州",
				"苏州",
				"无锡",
				"扬州",
				"三亚",
				"海口",
				"成都",
				"武汉",
				"杭州",
				"宁波",
				"温州",
				"绍兴",
				"香港",
				"深圳",
				"广州",
				"上海"
			},
			{
				"东京",
				"名古屋",
				"京都",
				"札幌",
				"冲绳",
				"箱根",
				"伊豆",
				"静冈",
				"大阪",
				"奈良",
				"神户",
				"广岛",
				"福冈",
				"长崎",
				"首尔",
				"济州岛",
				"仁川",
				"釜山",
				"庆州",
				"大邱"
			},
			{
				"新加坡",
				"吉隆坡",
				"马六甲",
				"古晋",
				"曼谷",
				"普吉岛",
				"清迈",
				"芭堤雅",
				"甲米",
				"宿雾",
				"马尼拉",
				"胡志明市",
				"河内",
				"下龙湾",
				"顺化",
				"会安",
				"大叻",
				"暹粒",
				"金边",
				"西哈努克",
				"狗骨岛",
				"象岛",
				"龟岛",
				"素可泰",
				"清莱",
				"苏梅岛",
				"仰光",
				"曼德勒",
				"斯里巴加湾",
				"阿斯塔纳",
				"塔什干",
				"比什凯克",
				"喀布尔",
				"耶路撒冷",
				"新德里",
				"孟买",
				"博卡拉",
				"马累"
			},
			{
				"阿斯塔纳",
				"鞍山",
				"芭堤雅",
				"北京",
				"比什凯克",
				"博卡拉",
				"成都",
				"冲绳",
				"大阪",
				"大叻",
				"大理",
				"大连",
				"大邱",
				"东京",
				"福冈",
				"釜山",
				"狗骨岛",
				"古晋",
				"广岛",
				"广州",
				"龟岛",
				"桂林"
			}, 
			{
				"哈尔滨",
				"海口",
				"杭州",
				"河内",
				"呼和浩特",
				"胡志明市",
				"会安",
				"吉隆坡",
				"济南",
				"济州岛",
				"甲米",
				"金边",
				"京都",
				"静冈",
				"酒泉",
				"喀布尔",
				"昆明",
				"丽江"},
				{
					"马累",
					"马六甲",
					"马尼拉",
					"曼德勒",
					"曼谷",
					"孟买",
					"名古屋",
					"奈良",
					"南京",
					"宁波",
					"盘锦",
					"普吉岛",
					"齐齐哈尔",
					"秦皇岛",
					"清莱",
					"清迈",
					"庆州",
					"仁川",
					"三亚",
					"厦门",
					"上海",
					"绍兴",
					"深圳",
					"神户",
					"沈阳",
					"石家庄",
					"首尔",
					"顺化",
					"斯里巴加湾",
					"苏梅岛",
					"苏州",
					"素可泰",
					"塔什干",
					"太原",
					"天津"
				},
				{
					"温州",
					"乌鲁木齐",
					"无锡",
					"武汉",
					"西哈努克",
					"西宁",
					"下龙湾",
					"暹粒",
					"香港",
					"箱根",
					"象岛",
					"新德里",
					"新加坡",
					"宿雾",
					"徐州",
					"烟台",
					"扬州",
					"仰光",
					"耶路撒冷",
					"伊豆",
					"银川",
					"札幌",
					"长春",
					"长崎",
					"郑州"
				}};
	
	public static final Map<String, Integer> CityToID = new HashMap<String, Integer>(){
		{
			put("北京",new Integer(1));
			put("盘锦",new Integer(2));
			put("大连",new Integer(3));
			put("鞍山",new Integer(4));
			put("沈阳",new Integer(5));
			put("哈尔滨",new Integer(6));
			put("长春",new Integer(7));
			put("齐齐哈尔",new Integer(8));
			put("呼和浩特",new Integer(9));
			put("乌鲁木齐",new Integer(10));
			put("烟台",new Integer(11));
			put("济南",new Integer(12));
			put("郑州",new Integer(13));
			put("天津",new Integer(14));
			put("太原",new Integer(15));
			put("银川",new Integer(16));
			put("西宁",new Integer(17));
			put("石家庄",new Integer(18));
			put("酒泉",new Integer(19));
			put("秦皇岛",new Integer(20));
			put("桂林",new Integer(21));
			put("厦门",new Integer(22));
			put("丽江",new Integer(23));
			put("昆明",new Integer(24));
			put("大理",new Integer(25));
			put("南京",new Integer(26));
			put("徐州",new Integer(27));
			put("苏州",new Integer(28));
			put("无锡",new Integer(29));
			put("扬州",new Integer(30));
			put("三亚",new Integer(31));
			put("海口",new Integer(32));
			put("成都",new Integer(33));
			put("武汉",new Integer(34));
			put("杭州",new Integer(35));
			put("宁波",new Integer(36));
			put("温州",new Integer(37));
			put("绍兴",new Integer(38));
			put("香港",new Integer(39));
			put("深圳",new Integer(40));
			put("广州",new Integer(41));
			put("上海",new Integer(42));
			put("东京",new Integer(43));
			put("名古屋",new Integer(44));
			put("京都",new Integer(45));
			put("札幌",new Integer(46));
			put("冲绳",new Integer(47));
			put("箱根",new Integer(48));
			put("伊豆",new Integer(49));
			put("静冈",new Integer(50));
			put("大阪",new Integer(51));
			put("奈良",new Integer(52));
			put("神户",new Integer(53));
			put("广岛",new Integer(54));
			put("福冈",new Integer(55));
			put("长崎",new Integer(56));
			put("首尔",new Integer(57));
			put("济州岛",new Integer(58));
			put("仁川",new Integer(59));
			put("釜山",new Integer(60));
			put("庆州",new Integer(61));
			put("大邱",new Integer(62));
			put("新加坡",new Integer(63));
			put("吉隆坡",new Integer(64));
			put("马六甲",new Integer(65));
			put("古晋",new Integer(66));
			put("曼谷",new Integer(67));
			put("普吉岛",new Integer(68));
			put("清迈",new Integer(69));
			put("芭堤雅",new Integer(70));
			put("甲米",new Integer(71));
			put("宿雾",new Integer(72));
			put("马尼拉",new Integer(73));
			put("胡志明市",new Integer(74));
			put("河内",new Integer(75));
			put("下龙湾",new Integer(76));
			put("顺化",new Integer(77));
			put("会安",new Integer(78));
			put("大叻",new Integer(79));
			put("暹粒",new Integer(80));
			put("金边",new Integer(81));
			put("西哈努克",new Integer(82));
			put("狗骨岛",new Integer(83));
			put("象岛",new Integer(84));
			put("龟岛",new Integer(85));
			put("素可泰",new Integer(86));
			put("清莱",new Integer(87));
			put("苏梅岛",new Integer(88));
			put("仰光",new Integer(89));
			put("曼德勒",new Integer(90));
			put("斯里巴加湾",new Integer(91));
			put("阿斯塔纳",new Integer(92));
			put("塔什干",new Integer(93));
			put("比什凯克",new Integer(94));
			put("喀布尔",new Integer(95));
			put("耶路撒冷",new Integer(96));
			put("新德里",new Integer(97));
			put("孟买",new Integer(98));
			put("博卡拉",new Integer(99));
			put("马累",new Integer(100));
		}
	};
}
