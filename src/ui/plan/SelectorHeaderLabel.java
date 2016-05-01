package ui.plan;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class SelectorHeaderLabel extends JLabel implements MouseListener {
	private boolean isSelected;
	private int x;
	private int y;
	private int index;
	private Font origin;
	private Font afterSelected;

	private Selector selector;

	public SelectorHeaderLabel(String s, int index, Selector selector) {
		// TODO Auto-generated constructor stub
		super(s);
		this.index = index;
		this.x = Constant.SELECTORHEADER_X + index
				* Constant.SELECTORHEADER_LABEL_LENGTH;
		this.y = Constant.SELECTORHEADER_Y;
		this.origin = Constant.SELECTORHEADER_FONT;
		this.setBounds(x, y, Constant.SELECTORHEADER_LABEL_LENGTH,
				Constant.SELECTORHEADER_LABEL_WIDTH);
		this.setFont(origin);
		// this.afterSelected=UIHelper.getUnderlinedFont(12, "ºÚÌå");
		this.afterSelected = new Font("ºÚÌå", Font.BOLD, 18);
		this.addMouseListener(this);
		this.selector = selector;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		selector.switchType(this.index);
		selector.setCurrentHeaderIndex(this.index);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		this.setFont(afterSelected);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		this.setFont(origin);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
