package ui.plan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CommentView {
	private int x;
	private int y;
	private int width;
	private int realLength;
	private int length;
	private String comment;
	private Color background;
	private JPanel fatherPanel;
	private PlanBarItem planBarItem;
	private JTextArea commentArea;

	public CommentView(JPanel p, PlanBarItem pbi) {
		fatherPanel = p;
		planBarItem = pbi;
		width = Constant.BAR_WIDTH;
		comment = "在这里写备注";
		background = Color.LIGHT_GRAY;
		commentArea = new JTextArea(comment);
		commentArea.setBackground(background);
		commentArea.setLineWrap(true);
		commentArea.setVisible(false);
		fatherPanel.add(commentArea);
		if (fatherPanel instanceof CheckPlanPanel) {
			commentArea.setEditable(false);

			commentArea.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					planBarItem.proceedClick();
				}
			});
		}
	}

	public void drawComment(Graphics g) {
		Color origin = g.getColor();
		g.setColor(background);
		g.fillRect(x, y, length, width);
		g.setColor(origin);
	}

	public void showView(int x, int y, int length) {
		this.x = x + length;
		this.y = y;
		this.realLength = length;
		this.length = 0;
		commentArea.setText(comment);
		Thread t = new Thread(new ShowComment());
		t.start();
	}

	public void concealView() {
		comment = commentArea.getText();
		Thread t = new Thread(new ConcealComment());
		t.start();
	}

	public void setCommentAreaLocation(int x, int y, int length) {
		commentArea.setBounds(x, y, length, this.width - 20);
	}

	public void setCommentAreaVisible(boolean b) {
		commentArea.setVisible(b);
	}

	class ShowComment implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			commentArea.setVisible(true);
			for (int i = 0; i < realLength; i++) {
				x--;
				length++;
				commentArea.setBounds(x, y, length, width - 20);
				fatherPanel.repaint();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	class ConcealComment implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < realLength; i++) {
				x++;
				length--;
				commentArea.setBounds(x, y, length, width - 20);
				fatherPanel.repaint();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			planBarItem.setShowComment(false);
			commentArea.setVisible(false);
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRealLength() {
		return realLength;
	}

	public void setRealLength(int realLength) {
		this.realLength = realLength;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public JTextArea getCommentArea() {
		return commentArea;
	}

	public void setCommentArea(JTextArea commentArea) {
		this.commentArea = commentArea;
	}
}
