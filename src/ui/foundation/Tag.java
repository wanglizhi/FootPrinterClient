package ui.foundation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Tag extends JLabel implements MouseListener{
	String tag;
	ImageIcon pressedImage;
	ImageIcon normalImage;
	boolean isPressed;
	
	public Tag(String tag, ImageIcon pressedImage, ImageIcon normalImage) {
		super();
		this.tag = tag;
		this.pressedImage = pressedImage;
		this.normalImage = normalImage;
		this.setIcon(normalImage);
		isPressed = false;
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(isPressed){
			isPressed = false;
			this.setIcon(normalImage);
		}
		else{
			isPressed = true;
			this.setIcon(pressedImage);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (!isPressed)
			this.setIcon(pressedImage);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (!isPressed)
			this.setIcon(normalImage);
		
	}
	
	public boolean isPressed(){
		return isPressed;
	}
	
	public String getTag(){
		return tag;
	}
}
