package goNotes.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import goNotes.metier.Note;

@SuppressWarnings("serial")
public class EditPanel extends JPanel {
	//attributes
	private Vector<JButton> colorChoosingButtons ;
	private boolean isShown=false;
	private final ViewFrame viewFrame;
	
	//getters & setters
	public boolean isShown() {return isShown;}
	public void setShown(boolean v) {isShown=v;}
	
	//methods
	private void initFrame() {
		this.setLayout(new GridLayout(2, 1));
	}
	private void initComponents() {
		//text options
		this.add(new JPanel());
		
		//colors
		JPanel colorChoosingPanel = new JPanel();
		colorChoosingPanel.setLayout(new GridLayout(1, 13));
		colorChoosingButtons = new Vector<JButton>();
		for(int i=0;i<13;i++) {
			JButton button = new JButton();
			//set border thicker if colorSelected
			if(Note.colorSets[i].equals(viewFrame.getNote().getColorSet()))
				button.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			else
				button.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			colorChoosingButtons.add(button);
			colorChoosingPanel.add(button);
			button.setBackground(Note.colorSets[i].getMainColor());
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//reset all borders
					for(int j=0;j<13;j++) {
						colorChoosingButtons.get(j).setBorder(BorderFactory.createLineBorder(Color.black, 1));
					}
					//set the source border
					((JButton)arg0.getSource()).setBorder(BorderFactory.createLineBorder(Color.black, 3));
					// change color
					int index= colorChoosingButtons.indexOf(button);
					viewFrame.recolor(index);
					
				}
			});
		}
		this.add(colorChoosingPanel);
		
		
	}
	
	//constructors
	public EditPanel(ViewFrame viewFrame) {
		this.viewFrame=viewFrame;
		initFrame();
		initComponents();
	}
}
