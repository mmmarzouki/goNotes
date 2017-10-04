package goNotes.view;

import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EditPanel extends JPanel {
	private boolean isShown=false;
	
	public boolean isShown() {return isShown;}
	public void setShown(boolean v) {isShown=v;}
	
	public EditPanel() {
		this.setBackground(Color.black);
	}
}
