package goNotes.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Edit extends JButton {
	private static String expandToolTip="click to show edit menu";
	private static String collapseToolTip="click to hide edit menu";
	@SuppressWarnings("unused")
	private final ViewFrame viewFrame;
	public Edit(ViewFrame viewFrame) {
		this.setToolTipText(expandToolTip);
		this.viewFrame=viewFrame;
		this.setBorderPainted(false);
		this.setBorder(null);
		this.setContentAreaFilled(false);
		this.setOpaque(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		try {
			this.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/resources/resizerIcon.png"))));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		this.setPreferredSize(new Dimension(30, 30));
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!viewFrame.getEditPanel().isShown()) {
					viewFrame.showEditPanel();
					setToolTipText(collapseToolTip);
				}
				else { 
					viewFrame.hideEditPanel();
					setToolTipText(expandToolTip);
				}
				
			}
		});
	}
}
