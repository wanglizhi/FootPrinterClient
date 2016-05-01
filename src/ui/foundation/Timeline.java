package ui.foundation;

import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import vo.TimelineVO;

public class Timeline extends BasePanel {

	Hashtable<Integer, JLabel> timelabels = new Hashtable<Integer, JLabel>();

	JSlider timeline;

	public Timeline(TimelineVO timelineVO) {
		initTimeLabels(timelineVO);
		timeline = new JSlider(SwingConstants.VERTICAL);
		timeline.setOpaque(false);// add by jb2011：不填充默认背景色（否则放到白色面板板会很难看 ）
		timeline.setPaintTicks(true);
		timeline.setMajorTickSpacing(20);
		timeline.setMinorTickSpacing(5);
		timeline.setPaintLabels(true);
		timeline.setLabelTable(timelabels);
		timeline.setValue(100);

		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel l1 = createHeadLabel(" 时间轴 ", 3);
		l1.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(l1);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		add(timeline);

		setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
	}

	private void initTimeLabels(TimelineVO timelineVO) {
		int num = timelineVO.getDateList().size();
		int step = 100 / num;
		for (int i = 0; i < 100; i += step)
			timelabels.put(new Integer(i), new JLabel(timelineVO.getDateList()
					.get(i / step)));
		timelabels.put(new Integer(100), new JLabel("所有时间"));
	}

	public JSlider getSlider() {
		return timeline;
	}

	public Date getDate(int value) {
		String dateStr = null;

		Enumeration<Integer> e = timelabels.keys();

		while (e.hasMoreElements()) {
			int key = e.nextElement();
			if (value - key >= 0 && value - key < 10) {
				dateStr = timelabels.get(key).getText();
				break;
			}
		}

		if (dateStr == "所有时间")
			return null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return date;
	}
}
